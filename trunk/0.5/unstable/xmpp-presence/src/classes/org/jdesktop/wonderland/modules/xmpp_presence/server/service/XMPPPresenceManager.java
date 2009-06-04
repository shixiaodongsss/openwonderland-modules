/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.xmpp_presence.server.service;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.impl.sharedutil.LoggerWrapper;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.server.UserListener;
import org.jdesktop.wonderland.server.UserManager;
import org.jdesktop.wonderland.server.comms.WonderlandClientID;

/**
 * Provides simple hooks for starting and stopping presence updating over XMPP.
 * When presence updating starts, the status message of the XMPP account
 * representing this server will update whenever a user logs in or out, such
 * that the message represents the current number of logged in users and their
 * names. 
 *
 * Right now, the manager (and its matching service) aren't designed to abstract out
 * the notion of XMPP connections. One nice future way to take this might be to
 * support multiple connections so that Cells can request XMPP conversations with
 * external people, or so each room could have its own presence account, etc.
 * @author drew
 */
public class XMPPPresenceManager {

    XMPPPresenceService service;

    private static final LoggerWrapper logger =
            new LoggerWrapper(Logger.getLogger(XMPPPresenceManager.class.getName()));

    public XMPPPresenceManager(XMPPPresenceService xmppPS)
    {
        service = xmppPS;

    }

    /**
     * Start updating the presence information. Tells the PresenceManager to plug into
     * the UserListener system so it can be notified of user join/leave events,
     * which it takes as signals to update the presence information.
     */
    public void startPresenceUpdating()
    {
        // Start a recurring task that updates the status messages.
        // AppContext.getTaskManager().schedulePeriodicTask(new StatusMessageUpdateTask(), 100, 3000);

        // lets try using the UserManager notification mechanism for deciding when a good time to update would be.
        UserManager manager = UserManager.getUserManager();

        // Using this inner class as an indirection to avoid serialization problems.
        manager.addUserListener(new PresenceUserListener());

        // Do one update now to change off the default "initializing presence" notice.
        // this.updatePresence();
    }

    /**
     * Trigger a one-time presence update. 
     */
    public void updatePresence()
    {
        service.doUpdateStatusMessage();
    }

    public void userLoggedOutEvent(WonderlandClientID clientID) {
        // both these methods do the same thing — all we care about is that we run
        // the update here, not what kind of event it was. We're not going to
        // maintain our own user list her based on join/leave events.

        AppContext.getTaskManager().scheduleTask(new StatusMessageUpdateTask());
    }

    public void userLoggedInEvent(WonderlandClientID clientID) {
        AppContext.getTaskManager().scheduleTask(new StatusMessageUpdateTask());
    }

    /**
     * Container class for listening for user events. We need this
     * because of managed object issues - this class can be serialized
     * easily because it maintains no reference to the Manager or
     * Service objects. 
     */
    public static class PresenceUserListener implements UserListener {

        public void userLoggedOut(WonderlandClientID clientID) {
            XMPPPresenceManager manager = AppContext.getManager(XMPPPresenceManager.class);
            manager.userLoggedOutEvent(clientID);
        }

        public void userLoggedIn(WonderlandClientID clientID) {
            XMPPPresenceManager manager = AppContext.getManager(XMPPPresenceManager.class);
            manager.userLoggedInEvent(clientID);
        }

    }
    

}
