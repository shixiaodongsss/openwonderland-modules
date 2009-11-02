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
/*
 * LoopSegmentEditor.java
 *
 * Created on Aug 14, 2009, 6:56:30 AM
 */

package org.jdesktop.wonderland.modules.marbleous.client.ui;

import org.jdesktop.wonderland.modules.marbleous.common.BigDropSegmentSettings;
import org.jdesktop.wonderland.modules.marbleous.common.BigDropTrackSegmentType;
import org.jdesktop.wonderland.modules.marbleous.common.LoopSegmentSettings;
import org.jdesktop.wonderland.modules.marbleous.common.TrackSegment;

/**
 *
 * @author paulby
 */
public class BigDropSegmentEditor extends SegmentEditor {

    /** Creates new form LoopSegmentEditor */
    public BigDropSegmentEditor() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        easeOutRadiusTF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        heightTF = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Ease Out Radius :");
        add(jLabel1, new java.awt.GridBagConstraints());

        easeOutRadiusTF.setText("jTextField1");
        easeOutRadiusTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                easeOutRadiusTFActionPerformed(evt);
            }
        });
        add(easeOutRadiusTF, new java.awt.GridBagConstraints());

        jLabel2.setText("Ramp Height :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(jLabel2, gridBagConstraints);

        heightTF.setText("jTextField1");
        heightTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heightTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(heightTF, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void easeOutRadiusTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_easeOutRadiusTFActionPerformed
        BigDropSegmentSettings s = (BigDropSegmentSettings) segment.getSegmentSettings();
        s.setRadius(Float.parseFloat(easeOutRadiusTF.getText()));

        BigDropTrackSegmentType t = new BigDropTrackSegmentType();
        t.updateSegment(segment, s);
        notifySegmentChanged();
    }//GEN-LAST:event_easeOutRadiusTFActionPerformed

    private void heightTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heightTFActionPerformed
        BigDropSegmentSettings s = (BigDropSegmentSettings) segment.getSegmentSettings();
        s.setHeight(Float.parseFloat(heightTF.getText()));

        BigDropTrackSegmentType t = new BigDropTrackSegmentType();
        t.updateSegment(segment, s);
        notifySegmentChanged();
    }//GEN-LAST:event_heightTFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField easeOutRadiusTF;
    private javax.swing.JTextField heightTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

    public void setSegment(TrackSegment segment) {
        this.segment = segment;
        BigDropSegmentSettings s = (BigDropSegmentSettings) segment.getSegmentSettings();
        easeOutRadiusTF.setText(Float.toString(s.getRadius()));
        heightTF.setText(Float.toString(s.getHeight()));
    }


}