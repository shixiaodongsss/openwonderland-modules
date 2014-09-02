/**
 * Open Wonderland
 *
 * Copyright (c) 2011, Open Wonderland Foundation, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * The Open Wonderland Foundation designates this particular file as
 * subject to the "Classpath" exception as provided by the Open Wonderland
 * Foundation in the License file that accompanied this code.
 */

package org.jdesktop.wonderland.modules.poster.client;

import java.util.logging.Logger;
import javax.swing.JEditorPane;

/**
 * Panel to render a poster.
 * @author Bernard Horan
 * @author Jon Kaplan
 */
public class PosterPanel extends javax.swing.JPanel {
    private static final Logger LOGGER =
            Logger.getLogger(PosterPanel.class.getName());

    private final PosterCell cell;

    PosterPanel(PosterCell cell) {
        this.cell = cell;
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

        posterPane = new PosterEditorPane(cell);

        setFocusable(false);

        posterPane.setBorder(null);
        posterPane.setEditable(false);
        posterPane.setFocusable(false);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, posterPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, posterPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane posterPane;
    // End of variables declaration//GEN-END:variables

    void updateLabel() {
        posterPane.setText(cell.getPosterText());
    }

    JEditorPane getPosterPane() {
        return posterPane;
}
 }