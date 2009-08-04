/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FontSizePane.java
 *
 * Created on Aug 3, 2009, 3:01:48 PM
 */

package org.jdesktop.wonderland.modules.annotations.client.display;

import javax.swing.event.ChangeListener;

/**
 *
 * @author Matt
 */
public class FontSizePane extends javax.swing.JPanel {

    /** Creates new form FontSizePane */
    public FontSizePane() {
        initComponents();
        slider.setMinimum(25);
        slider.setMaximum(400);
        slider.setValue(50);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    slider = new javax.swing.JSlider();

    slider.setBackground(new java.awt.Color(51, 51, 51));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(slider, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JSlider slider;
  // End of variables declaration//GEN-END:variables

  public void addFontSliderListener(ChangeListener l){
    slider.addChangeListener(l);
  }

  public float getFontSize(){
    int val = (int)slider.getValue();
    float modVal = val / 10000f;
    return modVal;
  }
}
