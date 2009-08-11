/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.timeline.common.provider;

/**
 * Listen for changes to a result.
 * @author Jonathan Kaplan <kaplanj@dev.java.net>
 */
public interface TimelineResultListener {
    /**
     * Notification when a result is added
     * @param obj the data that was added
     */
    public void added(DatedObject obj);

    /**
     * Notification when a result is removed
     * @param obj the date that was removed
     */
    public void removed(DatedObject obj);
}