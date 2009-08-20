/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., All Rights Reserved
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
package org.jdesktop.wonderland.modules.pdfviewer.server.cell;

import com.jme.math.Vector2f;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;
import java.util.logging.Logger;
import org.jdesktop.wonderland.common.ExperimentalAPI;
import org.jdesktop.wonderland.common.cell.ClientCapabilities;
import org.jdesktop.wonderland.common.cell.state.CellClientState;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.modules.appbase.server.cell.App2DCellMO;
import org.jdesktop.wonderland.modules.sharedstate.common.SharedData;
import org.jdesktop.wonderland.modules.sharedstate.common.SharedString;
import org.jdesktop.wonderland.modules.sharedstate.server.SharedMapListenerSrv;
import org.jdesktop.wonderland.modules.sharedstate.server.SharedMapSrv;
import org.jdesktop.wonderland.modules.sharedstate.server.SharedStateComponentMO;
import org.jdesktop.wonderland.modules.pdfviewer.common.PDFViewerConstants;
import org.jdesktop.wonderland.modules.pdfviewer.common.cell.PDFViewerCellClientState;
import org.jdesktop.wonderland.modules.pdfviewer.common.cell.PDFViewerCellServerState;
import org.jdesktop.wonderland.modules.sharedstate.common.SharedBoolean;
import org.jdesktop.wonderland.modules.sharedstate.common.SharedInteger;
import org.jdesktop.wonderland.server.cell.annotation.UsesCellComponentMO;
import org.jdesktop.wonderland.server.comms.WonderlandClientID;

/**
 * A server cell associated with a PDF viewer
 *
 * @author nsimpson
 */
@ExperimentalAPI
public class PDFViewerCellMO extends App2DCellMO implements SharedMapListenerSrv {

    private static final Logger logger = Logger.getLogger(PDFViewerCellMO.class.getName());
    @UsesCellComponentMO(SharedStateComponentMO.class)
    private ManagedReference<SharedStateComponentMO> sscRef;
    private ManagedReference<SharedMapSrv> statusMapRef;
    // the preferred width
    private int preferredWidth;
    // the preferred height
    private int preferredHeight;
    // whether to decorate the window with a frame
    private boolean decorated;
    // the currently loaded document
    private String documentURI = "https://lg3d-wonderland.dev.java.net/presentations/TrainForSucess-July2008.pdf";
    // the current page
    private int currentPage = 1;        // first page
    // the scroll position
    private int scrollPosition = 0;     // not scrolled
    // slide show mode
    private boolean slideShow = false;  // not in a slide show

    public PDFViewerCellMO() {
        super();
        addComponent(new SharedStateComponentMO(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getClientCellClassName(WonderlandClientID clientID, ClientCapabilities capabilities) {
        return "org.jdesktop.wonderland.modules.pdfviewer.client.cell.PDFViewerCell";
    }

    @Override
    protected void setLive(boolean live) {
        super.setLive(live);
        if (live) {
            // get or create the shared maps we use
            SharedMapSrv statusMap = sscRef.get().get(PDFViewerConstants.STATUS_MAP);
            statusMap.addSharedMapListener(this);

            // put the current status
            statusMap.put(PDFViewerConstants.DOCUMENT_URI, SharedString.valueOf(documentURI));
            statusMap.put(PDFViewerConstants.PAGE_NUMBER, SharedInteger.valueOf(currentPage));
            statusMap.put(PDFViewerConstants.PAGE_POSITION, SharedInteger.valueOf(scrollPosition));
            statusMap.put(PDFViewerConstants.SLIDE_SHOW_MODE, SharedBoolean.valueOf(slideShow));
            statusMapRef = AppContext.getDataManager().createReference(statusMap);
        }
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public CellClientState getClientState(CellClientState cellClientState,
            WonderlandClientID clientID, ClientCapabilities capabilities) {
        if (cellClientState == null) {
            cellClientState = new PDFViewerCellClientState(pixelScale);
        }
        ((PDFViewerCellClientState) cellClientState).setPreferredWidth(preferredWidth);
        ((PDFViewerCellClientState) cellClientState).setPreferredHeight(preferredHeight);
        ((PDFViewerCellClientState) cellClientState).setDecorated(decorated);

        return super.getClientState(cellClientState, clientID, capabilities);
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public CellServerState getServerState(CellServerState state) {
        if (state == null) {
            state = new PDFViewerCellServerState();
        }

        return super.getServerState(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setServerState(CellServerState serverState) {
        super.setServerState(serverState);
        PDFViewerCellServerState state = (PDFViewerCellServerState) serverState;
        preferredWidth = state.getPreferredWidth();
        preferredHeight = state.getPreferredHeight();
        decorated = state.getDecorated();
        pixelScale = new Vector2f(state.getPixelScaleX(), state.getPixelScaleY());
    }

    public boolean propertyChanged(SharedMapSrv map, WonderlandClientID sourceID,
            String key, SharedData oldData, SharedData newData) {
        if (map.getName().equals(PDFViewerConstants.STATUS_MAP)) {
            return handleStatusChange(sourceID, key, oldData, newData);
        } else {
            logger.warning("unrecognized shared map: " + map.getName());
            return true;
        }
    }

    private boolean handleStatusChange(WonderlandClientID sourceID,
            String key, SharedData oldData, SharedData newData) {

        if (key.equals(PDFViewerConstants.DOCUMENT_URI)) {
            // a new media file
            documentURI = ((SharedString) newData).getValue();
        } else if (key.equals(PDFViewerConstants.PAGE_NUMBER)) {
            // page has changed
            currentPage = ((SharedInteger) newData).getValue();
        } else if (key.equals(PDFViewerConstants.PAGE_POSITION)) {
            // page has scrolled
            scrollPosition = ((SharedInteger) newData).getValue();
        } else if (key.equals(PDFViewerConstants.SLIDE_SHOW_MODE)) {
            // slide show mode
            slideShow = ((SharedBoolean) newData).getValue();
        }
        return true;
    }
}
