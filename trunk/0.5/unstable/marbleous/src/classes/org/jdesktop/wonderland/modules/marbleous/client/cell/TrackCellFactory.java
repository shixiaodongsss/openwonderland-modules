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
package org.jdesktop.wonderland.modules.marbleous.client.cell;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.jdesktop.wonderland.client.cell.registry.annotation.CellFactory;
import org.jdesktop.wonderland.client.cell.registry.spi.CellFactorySPI;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.modules.marbleous.common.cell.TrackCellServerState;

/**
 * The cell factory for the animation test cell.
 */
@CellFactory
public class TrackCellFactory implements CellFactorySPI {

    public String[] getExtensions() {
        return new String[] {};
    }

    public <T extends CellServerState> T getDefaultCellServerState(Properties props) {
        TrackCellServerState state = new TrackCellServerState();

        // HACK!
        Map<String, String> metadata = new HashMap();
        metadata.put("sizing-hint", "2.0");
        state.setMetaData(metadata);

        return (T)state;
    }

    public String getDisplayName() {
        return "Marbleous";
    }

    public Image getPreviewImage() {
        return null;
//        URL url = TrackCellFactory.class.getResource("resources/sample_preview.jpg");
//        return Toolkit.getDefaultToolkit().createImage(url);
    }
}
