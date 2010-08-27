package org.jdesktop.wonderland.modules.scriptingComponent.common;

import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.common.cell.messages.CellMessage;

public class ScriptingComponentChangeMessage extends CellMessage
    {
    private String[] eventNames;
    private String[] eventScriptType;

    public ScriptingComponentChangeMessage(CellID cellID, String[] EventNames, String[] ScriptType)
        {
        super(cellID);
        this.eventNames = EventNames;
        this.eventScriptType = ScriptType;
        }

    public String[] getEventNames()
        {
        return this.eventNames;
        }
    
    public void setEventNames(String[] EventNames)
        {
        this.eventNames = EventNames;
        }
    
    public String[] getScriptType()
        {
        return this.eventScriptType;
        }
    
    public void setScriptType(String[] ScriptType)
        {
        this.eventScriptType = ScriptType;
        }
    }