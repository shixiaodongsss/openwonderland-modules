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
package org.jdesktop.wonderland.modules.rockwellcollins.clickablelink.client;

import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jdesktop.wonderland.client.cell.properties.CellPropertiesEditor;
import org.jdesktop.wonderland.client.cell.properties.annotation.PropertiesFactory;
import org.jdesktop.wonderland.client.cell.properties.spi.PropertiesFactorySPI;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.modules.rockwellcollins.clickablelink.common.ClickableLinkComponentServerState;

/**
 * Panel for setting the properties of the ClickableLinkComponent. Mostly
 * cribbed off of the PortalProperties page.
 *
 * @author Ben (shavnir)
 * @author Ronny Standtke <ronny.standtke@fhnw.ch>
 */
@PropertiesFactory(ClickableLinkComponentServerState.class)
public class ClickableLinkComponentProperties
        extends JPanel implements PropertiesFactorySPI {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/rockwellcollins/clickablelink/"
            + "client/Bundle");
    private CellPropertiesEditor editor = null;
    private String linkURL = null;

    /** Creates new form ClickableLinkComponentProperties */
    public ClickableLinkComponentProperties() {
        initComponents();
        TextFieldListener listener = new TextFieldListener();
        linkTextField.getDocument().addDocumentListener(listener);
    }

    @Override
    public String getDisplayName() {
        return BUNDLE.getString("Clickable_Link_URL");
    }

    @Override
    public void apply() {
        CellServerState cellServerState = editor.getCellServerState();
        CellComponentServerState cellComponentServerState =
                cellServerState.getComponentServerState(
                ClickableLinkComponentServerState.class);
        ClickableLinkComponentServerState state =
                (ClickableLinkComponentServerState) cellComponentServerState;
        if (state == null) {
            state = new ClickableLinkComponentServerState();
        }

        String serverURL = linkTextField.getText().trim();
        if (serverURL.length() == 0) {
            serverURL = null;
        }
        state.setLinkURL(serverURL);
        editor.addToUpdateList(state);
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public JPanel getPropertiesJPanel() {
        return this;
    }

    @Override
    public void open() {
        CellServerState cellServerState = editor.getCellServerState();
        CellComponentServerState cellComponentServerState =
                cellServerState.getComponentServerState(
                ClickableLinkComponentServerState.class);
        ClickableLinkComponentServerState state =
                (ClickableLinkComponentServerState) cellComponentServerState;
        if (state != null) {
            linkURL = state.getLinkURL();
            if (linkURL != null) {
                linkTextField.setText(linkURL);
            }
        }
    }

    @Override
    public void restore() {
        linkTextField.setText(linkURL);
    }

    @Override
    public void setCellPropertiesEditor(CellPropertiesEditor editor) {
        this.editor = editor;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        linkLabel = new javax.swing.JLabel();
        linkTextField = new javax.swing.JTextField();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/jdesktop/wonderland/modules/rockwellcollins/clickablelink/client/Bundle"); // NOI18N
        linkLabel.setText(bundle.getString("ClickableLinkComponentProperties.linkLabel.text")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(linkLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(linkTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(linkLabel)
                    .add(linkTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private class TextFieldListener implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            checkDirty();
        }

        public void removeUpdate(DocumentEvent e) {
            checkDirty();
        }

        public void changedUpdate(DocumentEvent e) {
            checkDirty();
        }

        private void checkDirty() {
            if (editor == null) {
                return;
            }
            boolean clean = linkTextField.getText().equals(linkURL);
            editor.setPanelDirty(ClickableLinkComponentProperties.class, !clean);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel linkLabel;
    private javax.swing.JTextField linkTextField;
    // End of variables declaration//GEN-END:variables
}
