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
package org.jdesktop.wonderland.modules.modulator.server;

import org.jdesktop.wonderland.common.cell.ClientCapabilities;
import org.jdesktop.wonderland.common.cell.state.CellClientState;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.modules.modulator.common.ModulatorCellClientState;
import org.jdesktop.wonderland.modules.modulator.common.ModulatorCellServerState;
import org.jdesktop.wonderland.modules.scriptingComponent.server.ScriptingComponentMO;
import org.jdesktop.wonderland.server.cell.CellMO;
import org.jdesktop.wonderland.server.comms.WonderlandClientID;
        
public class ModulatorCellMO extends CellMO {

    private String modelURI = null;

    public ModulatorCellMO()
        {
        addComponent(new ScriptingComponentMO(this), ScriptingComponentMO.class);
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClientCellClassName(WonderlandClientID clientID,
            ClientCapabilities capabilities) {

         return "org.jdesktop.wonderland.modules.modulator.client.ModulatorCell";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellClientState getClientState(CellClientState state,
            WonderlandClientID clientID, ClientCapabilities capabilities) {

        if (state == null) {
            state = new ModulatorCellClientState();
        }
        ((ModulatorCellClientState)state).setModelURI(modelURI);
        return super.getClientState(state, clientID, capabilities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellServerState getServerState(CellServerState state) {
        if (state == null) {
            state = new ModulatorCellServerState();
        }
        ((ModulatorCellServerState)state).setModelURI(modelURI);
        return super.getServerState(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setServerState(CellServerState state) {
        super.setServerState(state);
        this.modelURI = ((ModulatorCellServerState)state).getModelURI();
    }
}
