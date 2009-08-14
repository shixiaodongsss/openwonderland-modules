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

package org.jdesktop.wonderland.modules.timeline.client;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Logger;
import org.jdesktop.wonderland.modules.timeline.common.provider.TimelineDate;

/**
 * A simple slider that lets you easily update the avatar's position on the
 * timeline spiral.
 *
 * @author Drew Harry <drew_harry@dev.java.net>
 */
public class TimelineMovementHUDPanel extends javax.swing.JPanel {

    private static final Logger logger =
        Logger.getLogger(TimelineMovementHUDPanel.class.getName());

    private final TimelineCell cell;

    private boolean ignoreChangeEvents = false;
    private DateFormat dateFormatMedium = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private DateFormat dateFormatLong = DateFormat.getDateInstance(DateFormat.LONG);

    /** Creates new form TimelineMovementHUDPanel */
    public TimelineMovementHUDPanel(TimelineCell cell) {
        this.cell = cell;

	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
        	initComponents();
	    }
	});
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        positionSlider = new javax.swing.JSlider();
        endDateLabel = new javax.swing.JLabel();
        startDateLabel = new javax.swing.JLabel();
        curDateLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));

        positionSlider.setMinorTickSpacing(10);
        positionSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        positionSlider.setPaintLabels(true);
        positionSlider.setPaintTicks(true);
        positionSlider.setValue(0);
        positionSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                positionSliderStateChanged(evt);
            }
        });

        endDateLabel.setFont(endDateLabel.getFont().deriveFont(endDateLabel.getFont().getStyle() | java.awt.Font.BOLD));
        endDateLabel.setForeground(new java.awt.Color(255, 255, 255));
        endDateLabel.setText("End Date");

        startDateLabel.setFont(startDateLabel.getFont().deriveFont(startDateLabel.getFont().getStyle() | java.awt.Font.BOLD));
        startDateLabel.setForeground(new java.awt.Color(255, 255, 255));
        startDateLabel.setText("Start Date");

        curDateLabel.setFont(curDateLabel.getFont().deriveFont(curDateLabel.getFont().getStyle() | java.awt.Font.BOLD));
        curDateLabel.setForeground(new java.awt.Color(255, 255, 255));
        curDateLabel.setText("September 88, 2222");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(curDateLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(positionSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(endDateLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .add(startDateLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(endDateLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 315, Short.MAX_VALUE)
                        .add(startDateLabel))
                    .add(positionSlider, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(curDateLabel)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void positionSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_positionSliderStateChanged

        if(!ignoreChangeEvents)
            cell.moveAvatarToHeightFraction(((float)this.positionSlider.getValue())/this.positionSlider.getMaximum());

    }//GEN-LAST:event_positionSliderStateChanged

    /**
     * Updates the location of the position slider based. Is generally triggered
     * by avatar movements around the spiral, so the UI for the navigation
     * represents the actual current location.
     * 
     * @param position
     */
    public void setSliderLocation(final float position) {

       // cheating a bit here - this is only true for min=0, but
       // that's an assumption I think we can make here since we
       // own the slider.
       
       // we wrap the setValue in these isadjusting calls to avoid
       // triggering an infinite loop of change requests.

	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
       		ignoreChangeEvents = true;
        	positionSlider.setValue((int) (positionSlider.getMaximum() * position));
       		ignoreChangeEvents = false;
	    }
	});
    }

    public void setStartDate(final Date date) {
	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
        	startDateLabel.setText(dateFormatMedium.format(date));
	    }
	});
    }

    public void setEndDate(final Date date) {
	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
        	endDateLabel.setText(dateFormatMedium.format(date));
	    }
	});
    }

    public void setDateLabel(final TimelineDate date) {
	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
        	curDateLabel.setText(dateFormatLong.format(date.getMiddle()));
	    }
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel curDateLabel;
    private javax.swing.JLabel endDateLabel;
    private javax.swing.JSlider positionSlider;
    private javax.swing.JLabel startDateLabel;
    // End of variables declaration//GEN-END:variables

}
