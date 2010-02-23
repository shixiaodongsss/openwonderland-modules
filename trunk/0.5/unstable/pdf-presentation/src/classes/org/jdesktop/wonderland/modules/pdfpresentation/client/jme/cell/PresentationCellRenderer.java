/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2010, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * Sun designates this particular file as subject to the "Classpath"
 * exception as provided by Sun in the License file that accompanied
 * this code.
 */

package org.jdesktop.wonderland.modules.pdfpresentation.client.jme.cell;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.image.Texture.MagnificationFilter;
import com.jme.image.Texture.MinificationFilter;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.RenderManager;
import org.jdesktop.mtgame.RenderUpdater;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.content.ContentImportManager;
import org.jdesktop.wonderland.client.jme.ClientContextJME;
import org.jdesktop.wonderland.client.jme.cellrenderer.BasicRenderer;
import org.jdesktop.wonderland.common.cell.CellStatus;
import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.modules.pdf.client.DeployedPDF;
import org.jdesktop.wonderland.modules.pdf.client.PDFContentImporter;
import org.jdesktop.wonderland.modules.pdf.client.PDFDeployer;
import org.jdesktop.wonderland.modules.pdfpresentation.client.PresentationCell;

public class PresentationCellRenderer extends BasicRenderer {
    private Node node = null;

    private PresentationCell cell;

    private static final Logger logger =
            Logger.getLogger(PresentationCellRenderer.class.getName());

    // The PDF object generated by the PDF deployer library.
    private DeployedPDF pdf = null;

    // Keeps track of the slide objects so we can
    // move them around in response to HUD events.
    private List<Spatial> slides = new Vector<Spatial>();

    // Controls the relationship between the size of the rendered texture
    // (as generated from the PDF library) and the object in world.
    // Making this value bigger makes slides appear bigger in world.
    public static final float SLIDE_SIZE_FACTOR = 0.00125F;

    public PresentationCellRenderer(Cell c) {
        super(c);
        this.cell = (PresentationCell) c;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStatus(CellStatus status, boolean increasing) {
        super.setStatus(status, increasing);

        // If we are becoming active, turn off the lighting
        if (status == CellStatus.ACTIVE && increasing == true) {
            setLightingEnabled(false);
        }
    }

    @Override
    protected Node createSceneGraph(Entity entity) {

        // Make just one empty node that stuff will get attached to later. 
        node = new Node();

        // Pulled this in from the old PresentationCellRenderer. This sphere
        // was pretty much all that old class did.
        TriMesh dot = new Sphere("origin sphere", Vector3f.ZERO, 10, 10, 0.25f);
        node.attachChild(dot);

        // Kick off a thread that will manage the loading asynchronously. 
        Thread t = new PageLoadingThread(node);
        t.start();

        node.setModelBound(new BoundingBox());
        node.updateModelBound();

        // XXX NO LONGER NEEDED, DELETE?
        // This makes the slides appear perpandicular to the user's view, instead of
        // parallel to it. This causes some trouble with children cell, because
        // this is a node rotation, not a cell rotation it doesn't propegate
        // to children of the cell, ie the moving platform. 
//        node.setLocalRotation(new Quaternion().fromAngleNormalAxis((float) (Math.PI / 2), new Vector3f(0,1,0)));
        // XXX END NO LONGER NEEDED
        
        node.setLocalScale(cell.getScale());

        node.setModelBound(new BoundingBox());
        return node;
}
    
    /**
     * Get the validity of the specified page in the currently open document
     * @return true if the page is within the range of pages of the current
     * document, false otherwise
     */
    public boolean isValidPage(int p) {
        return ((pdf != null) && (p > 0) && (p <= pdf.getNumberOfSlides()));
    }

    // Called when the layout of slides is updated. Triggered by some other user changing the
    // layout parameters or tpe.
    public void layoutUpdated() {
        // Go through each of the slides and move them to their current position.
        logger.warning("Layout updated trigger.");
        
        int i=0;
        for(Spatial s : this.slides) {
            placeSlide(s, i);
            i++;
        }

        // Set the overall scale of the cell in a proper render thread.
        ClientContextJME.getWorldManager().addRenderUpdater(new RenderUpdater() {
            public void update(Object arg) {
               node.setLocalScale(cell.getLayout().getScale());
            }
        }, null);
    }

    private class PageLoadingThread extends Thread {

        private Node node;

        public PageLoadingThread(Node n) {
            node = n;
        }

        public void run() {

            logger.fine("Creating scene graph for entity: " + entity);

            String name = cell.getCellID().toString();

            String pdfURI = ((PresentationCell)cell).getSourceURI();

            try {
                pdf = PDFDeployer.loadDeployedPDF(pdfURI);
            } catch (MalformedURLException ex) {
                Logger.getLogger(PresentationCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PresentationCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JAXBException ex) {
                Logger.getLogger(PresentationCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }

            int maxHeight = (int) (pdf.getMaximumSlideHeight() * SLIDE_SIZE_FACTOR);
            int maxWidth = (int) (pdf.getMaximumSlideWidth() * SLIDE_SIZE_FACTOR);

            // Push this data into the layout, since we need it
            // for managing the moving platform.
            cell.getLayout().setMaxSlideWidth(maxWidth);
            cell.getLayout().setMaxSlideHeight(maxHeight);

            PDFContentImporter pdfContentImporter = ((PDFContentImporter)ContentImportManager.getContentImportManager().getContentImporter("pdf", true));

            // centered around 0,0, calculate starting position.            
            for (int i = 0; i < pdf.getNumberOfSlides(); i++) {

                // logger.warning("currentCenter: " + currentCenter + " (page " + i + ")");
                // for each page, we need to:
                //   1. make a new Box
                //   2. get the texture for this page from WebDAV
                //   3. apply the texture to the box
                //   4. move the position pointer for the next box

                
                URL pageTextureURI;
                BufferedImage pageTexture = null;
                try {
                    // the +1 is because pages are actually 1 based, but all our internal
                    // representations are 0 based.
                    pageTextureURI = PDFDeployer.loadPDFPage(pdfURI, i+1);
                        // Get a buffered image from that URI.
                    pageTexture = ImageIO.read(pageTextureURI);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(PresentationCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                        Logger.getLogger(PresentationCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Dispatch the JME specific stuff to a thread that we can run inside
                // the RenderingThread.
                ClientContextJME.getWorldManager().addRenderUpdater((RenderUpdater)new NewSlideUpdater(node, pageTexture, i, maxHeight, maxWidth), null);

                // Knocking this code out for now. Not sure why it's here or
                // what it's doing. 
//                if(pageTexture.getHeight()*SLIDE_SIZE_FACTOR>maxHeight)
//                    maxHeight = pageTexture.getHeight()*SLIDE_SIZE_FACTOR;
//
//                if(pageTexture.getWidth()*SLIDE_SIZE_FACTOR>maxWidth)
//                    maxWidth = pageTexture.getWidth()*SLIDE_SIZE_FACTOR;
            }
        }
    }

    private void newSlide(Spatial s) {
        slides.add(s);
    }


    /**
     * Given a spatial and its index, place it properly relative to its parent
     * using the slide metadata.
     *
     * @param slide
     * @param index
     */
    private void placeSlide(final Spatial slide, final int index) {

        logger.warning("Kicking off slide(" + index + ") placement at: " + slide.getLocalTranslation() + "with rotation " + slide.getLocalRotation());

        ClientContextJME.getWorldManager().addRenderUpdater(new RenderUpdater() {
            public void update(Object arg) {
                CellTransform transform = cell.getLayout().getSlides().get(index).getTransform();


//                Quaternion baseRotation = new Quaternion().fromAngleAxis(
//                    (float) (Math.PI / 2), new Vector3f(0, 1, 0));

//                slide.setLocalRotation(baseRotation.mult(transform.getRotation(null)));

                slide.setLocalRotation(transform.getRotation(null));
                slide.setLocalTranslation(transform.getTranslation(null));

                logger.warning("Placing slide(" + index + ") at: " + slide.getLocalTranslation() + "with rotation " + slide.getLocalRotation());
                logger.warning("World translation: " + slide.getWorldTranslation());

                ClientContextJME.getWorldManager().addToUpdateList(slide);
            }
            },null);
            
//        slide.setTransform(pdfCell.getLayout()..get(i).getTransform());
    }

    /**
     * This updater is used to create nodes when the cell is first loaded. It shouldn't be called
     * when slides are just being moved around.
     * 
     */
    private class NewSlideUpdater implements RenderUpdater {

        private Node parent;
        private BufferedImage page;
        private int index;
        private int height;
        private int width;

        public NewSlideUpdater(Node p, BufferedImage texture, int i, int h, int w) {
            parent = p;
            page = texture;
            index = i;
            height = h;
            width = w;
        }

        public void update(Object arg0) {
            Texture texture = TextureManager.loadTexture(page, MinificationFilter.BilinearNoMipMaps, MagnificationFilter.Bilinear, true);

            texture.setWrap(Texture.WrapMode.BorderClamp);
            texture.setTranslation(new Vector3f());

            TriMesh currentSlide = new Box(cell.getCellID().toString() + "_" + index, new Vector3f(),  width, height, 0.1f);
            node.attachChild(currentSlide);
            logger.finer("Just attached slide to node, with (" + width + "," + height + ")");

            RenderManager rm = ClientContextJME.getWorldManager().getRenderManager();

            TextureState ts = (TextureState) rm.createRendererState(RenderState.StateType.Texture);
            ts.setTexture(texture);
            ts.setEnabled(true);

            MaterialState ms = (MaterialState) rm.createRendererState(RenderState.StateType.Material);
            ms.setDiffuse(ColorRGBA.white);
            ms.setColorMaterial(MaterialState.ColorMaterial.Diffuse);
            currentSlide.setRenderState(ms);

            currentSlide.setRenderState(ts);
            currentSlide.setSolidColor(ColorRGBA.white);

            // This interfaces with the layout stored in the
            // pdfCell to put it in the right place.
            logger.warning("About to place slides in a new slide updater context.");
            placeSlide(currentSlide, index);

            currentSlide.setModelBound(new BoundingBox());
            currentSlide.updateModelBound();


            ClientContextJME.getWorldManager().addToUpdateList(currentSlide);

            // This is not the right place to be doing this - it really only
            // needs to get done after the last slide. But it's easier than
            // making sure we run an update only at the end, and it keeps
            // the bounds constantly up to date as slides get added.
            node.setLocalScale(cell.getScale());
            node.updateModelBound();
            node.updateRenderState();

            ClientContextJME.getWorldManager().addToUpdateList(node);

            newSlide(currentSlide);
        }
    }
}
