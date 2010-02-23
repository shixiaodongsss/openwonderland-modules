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
package org.jdesktop.wonderland.modules.cmu.common.messages.servercmu;

import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.modules.cmu.common.events.responses.CMUResponseFunction;

/**
 * Message containing an event response, sent to the CMU player for processing.
 * @author kevin
 */
public class CMUEventResponseMessage extends ServerCMUMessage {
    private final CMUResponseFunction response;

    public CMUEventResponseMessage(CellID cellID, CMUResponseFunction response) {
        super(cellID);
        this.response = response;
    }

    public CMUResponseFunction getResponse() {
        return response;
    }
}
