/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IntroTestSection.java
 *
 * Created on Oct 18, 2011, 11:34:09 AM
 */
package org.jdesktop.wonderland.modules.clienttest.test.ui;

import java.util.ResourceBundle;
import javax.swing.JPanel;
import org.json.simple.JSONObject;

/**
 *
 * @author jkaplan
 */
public class IntroSection extends javax.swing.JPanel
    implements TestSection 
{
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/clienttest/test/ui/resources/Bundle");

    /** Creates new form IntroTestSection */
    public IntroSection() {
    }

    public void initialize(JSONObject config) {
        initComponents();

        textPane.setText(BUNDLE.getString("IntroText"));
    }

    public String getName() {
        return BUNDLE.getString("Introduction");
    }

    public JPanel getPanel() {
        return this;
    }

    public void sectionVisible() {
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textPane = new javax.swing.JEditorPane();

        setBackground(new java.awt.Color(255, 255, 255));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/jdesktop/wonderland/modules/clienttest/test/ui/resources/Bundle"); // NOI18N
        textPane.setContentType(bundle.getString("IntroSection.textPane.contentType")); // NOI18N
        jScrollPane1.setViewportView(textPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JEditorPane textPane;
    // End of variables declaration//GEN-END:variables

}
