/*
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

package org.jdesktop.wonderland.modules.annotations.client.display;

import org.jdesktop.wonderland.modules.annotations.common.AnnotationSettings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.modules.annotations.common.Annotation;

/**
 * A JPanel backend for a HUDComponent representing an Annotation. Could be
 * displayed in world or on HUD, via the HUDComponent.
 *
 * At the moment, the HUD
 * system has certain limitations that limited the functionality of the in-world
 * version. Thus, raw Entities (AnnotationEntity) are used to display Annotations
 * in world, and the AnnotationPane/HUDComponent are used only for editing the
 * annotation on the HUD.
 *
 * @author mabonner
 */
public class AnnotationPane extends javax.swing.JPanel {

  private static Logger logger = Logger.getLogger(AnnotationPane.class.getName());

  /**
   * graphical configuration for this panel
   */
  private AnnotationSettings settings;

  private static final String userName = LoginManager.getPrimary().getPrimarySession().getUserID().getUsername();

  /**
   * used for choosing background and font color
   */
  private JColorChooser colorChooser = new JColorChooser();

  /**
   * note if the annotation settings have been adjusted
   */
  private boolean settingsChanged = false;

  /** Creates new form AnnotationPane
   * @param a annotation this pane represents
   * @param c the cell this pane is associated with
   */
  public AnnotationPane(Annotation a, Cell c) {
    initComponents();
    // setSettings will adjust background of components, so call it only
    // AFTER initComponents has been called
    setSettings(a.getSettings());

    setPreferredSize(new Dimension(375, 300));

    // panes are currently used only for editing, so the pane is in edit mode
    // by default now
    showEdits(true);

    // set text to reflect annotation
    author.setText(a.getCreator());
    date.setText(a.getModified());
    cellID.setText(c.getCellID().toString());
    cellName.setText(c.getName());
    message.setText(a.getText());
  }

  /**
   * overwritten to provide white border and rounded corners
   * @param g
   */
// Currently not supported by HUD
//    @Override
//    protected void paintComponent(Graphics g) {
//      int x = 0;
//      int y = 0;
//      int w = getWidth() - 68;
//      int h = getHeight() - 68;
//      int arc = 30;
//
//      Graphics2D g2 = (Graphics2D) g.create();
//      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//              RenderingHints.VALUE_ANTIALIAS_ON);
//
//      g2.setColor(new Color(0, 0, 0, 220));
//      g2.fillRoundRect(x, y, w, h, arc, arc);
//
//      g2.setStroke(new BasicStroke(3f));
//      g2.setColor(Color.WHITE);
//      g2.drawRoundRect(x, y, w, h, arc, arc);
//
//      g2.dispose();
//    }

  public void setSettings(AnnotationSettings s){
    if(s == null){
      logger.severe("[Anno Pane] settings set to null!");
    }
    settings = s;
    setBackground(settings.getBackgroundColor());
    bgColorButton.setBackground(settings.getBackgroundColor());
    setForeground(settings.getFontColor());
    // set author/editted to be italic
    Font italicFont =new Font(settings.getFont().getName(),Font.ITALIC,settings.getFont().getSize());
    italicFont = italicFont.deriveFont(14f);
    author.setFont(italicFont);
    date.setFont(italicFont);
  }

  public void addSaveButtonListener(ActionListener l){
    saveButton.addActionListener(l);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    dateLabel = new javax.swing.JLabel();
    editableMessageScrollPane = new javax.swing.JScrollPane();
    editableMessage = new javax.swing.JTextArea();
    author = new javax.swing.JLabel();
    date = new javax.swing.JLabel();
    saveButton = new javax.swing.JButton();
    message = new javax.swing.JLabel();
    cancelButton = new javax.swing.JButton();
    cellIDLabel = new javax.swing.JLabel();
    cellID = new javax.swing.JLabel();
    cellNameLabel = new javax.swing.JLabel();
    cellName = new javax.swing.JLabel();
    jSeparator1 = new javax.swing.JSeparator();
    subject = new javax.swing.JLabel();
    editableSubject = new javax.swing.JTextField();
    jToolBar1 = new javax.swing.JToolBar();
    bgColorButton = new javax.swing.JButton();
    fontColorButton = new javax.swing.JButton();
    fontButton = new javax.swing.JButton();

    setBackground(new java.awt.Color(51, 51, 51));
    setMinimumSize(new java.awt.Dimension(350, 350));
    setPreferredSize(new java.awt.Dimension(450, 400));
    addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        formMouseClicked(evt);
      }
    });

    dateLabel.setText("@");

    editableMessage.setColumns(20);
    editableMessage.setLineWrap(true);
    editableMessage.setRows(5);
    editableMessage.setText("message");
    editableMessage.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        editableMessageKeyTyped(evt);
      }
    });
    editableMessageScrollPane.setViewportView(editableMessage);

    author.setText("name");

    date.setText("date");

    saveButton.setBackground(new java.awt.Color(153, 153, 255));
    saveButton.setText("save");
    saveButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveButtonActionPerformed(evt);
      }
    });

    message.setText("Message");

    cancelButton.setText("cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cancelButtonActionPerformed(evt);
      }
    });

    cellIDLabel.setText("Cell ID");

    cellID.setText("id");

    cellNameLabel.setText("-");

    cellName.setText("jLabel2");

    subject.setText("Subject");

    editableSubject.setText("subject");
    editableSubject.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        editableSubjectActionPerformed(evt);
      }
    });
    editableSubject.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        editableSubjectKeyTyped(evt);
      }
    });

    jToolBar1.setFloatable(false);
    jToolBar1.setRollover(true);

    bgColorButton.setToolTipText("Background Color");
    bgColorButton.setMaximumSize(new java.awt.Dimension(25, 20));
    bgColorButton.setMinimumSize(new java.awt.Dimension(25, 20));
    bgColorButton.setPreferredSize(new java.awt.Dimension(20, 20));
    bgColorButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bgColorButtonActionPerformed(evt);
      }
    });
    jToolBar1.add(bgColorButton);

    fontColorButton.setForeground(new java.awt.Color(204, 0, 0));
    fontColorButton.setText("A");
    fontColorButton.setToolTipText("FontColor");
    fontColorButton.setFocusable(false);
    fontColorButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    fontColorButton.setMaximumSize(new java.awt.Dimension(25, 20));
    fontColorButton.setMinimumSize(new java.awt.Dimension(25, 20));
    fontColorButton.setPreferredSize(new java.awt.Dimension(20, 20));
    fontColorButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    fontColorButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        fontColorButtonActionPerformed(evt);
      }
    });
    jToolBar1.add(fontColorButton);

    fontButton.setText("f");
    fontButton.setToolTipText("Font");
    fontButton.setFocusable(false);
    fontButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    fontButton.setMaximumSize(new java.awt.Dimension(25, 20));
    fontButton.setMinimumSize(new java.awt.Dimension(25, 20));
    fontButton.setPreferredSize(new java.awt.Dimension(20, 20));
    fontButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    fontButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        fontButtonActionPerformed(evt);
      }
    });
    jToolBar1.add(fontButton);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(subject)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(editableSubject, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(message)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(editableMessageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(cancelButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(saveButton))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addComponent(author)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date))
              .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(cellIDLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cellID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cellNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cellName)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(author)
              .addComponent(dateLabel)
              .addComponent(date))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(cellIDLabel)
              .addComponent(cellID)
              .addComponent(cellNameLabel)
              .addComponent(cellName)))
          .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(9, 9, 9)
        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(4, 4, 4)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(subject)
          .addComponent(editableSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(editableMessageScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(saveButton)
              .addComponent(cancelButton)))
          .addComponent(message))
        .addContainerGap(57, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

  public boolean isSettingsChanged() {
    return settingsChanged;
  }

  public void setSettingsChanged(boolean settingsChanged) {
    this.settingsChanged = settingsChanged;
  }

  public AnnotationSettings getSettings() {
    return settings;
  }





    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
      message.setText(editableMessage.getText());
      subject.setText(editableSubject.getText());
      saveButton.setEnabled(false);
      // if the annotation pane is ever used to simply display items as well, it
      // should showEdits(false) as well
//      showEdits(false);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void editableMessageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editableMessageKeyTyped
      // keys have been typed, activate save button
      saveButton.setEnabled(true);
    }//GEN-LAST:event_editableMessageKeyTyped

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
      if (evt.getClickCount() == 2 && !evt.isConsumed()) {
        // can only edit your own annotations
        if(userName.equals(author.getText())){
          showEdits(true);
        }
      }
    }//GEN-LAST:event_formMouseClicked

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
      // if the annotation pane is ever used to simply display items as well, it
      // should showEdits(false) as well
      editableMessage.setText(fromHTML(message.getText()));
      editableMessageScrollPane.setVisible(true);
      editableSubject.setText(fromHTML(subject.getText()));
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void editableSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editableSubjectActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_editableSubjectActionPerformed

    private void editableSubjectKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editableSubjectKeyTyped
      // keys have been typed, activate save button
      saveButton.setEnabled(true);
    }//GEN-LAST:event_editableSubjectKeyTyped

    private void bgColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bgColorButtonActionPerformed
      Color newColor = JColorChooser.showDialog(
                     colorChooser,
                     "Choose Annotation Background Color",
                     settings.getBackgroundColor());
      if(newColor != null){
        // apply the new color
        settings.setBackgroundColor(newColor);
        this.setBackground(newColor);
        bgColorButton.setBackground(newColor);
        // note that settings have been changed
        settingsChanged = true;
        // enable save
        saveButton.setEnabled(true);
      }
    }//GEN-LAST:event_bgColorButtonActionPerformed

    private void fontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontColorButtonActionPerformed
      Color newColor = JColorChooser.showDialog(
                     colorChooser,
                     "Choose Annotation Font Color",
                     settings.getFontColor());
      if(newColor != null){
        // apply the new color
        settings.setFontColor(newColor);
        setForegrounds(newColor);
        // note that settings have been changed
        settingsChanged = true;
        // enable save
        saveButton.setEnabled(true);
      }
    }//GEN-LAST:event_fontColorButtonActionPerformed

    private void fontButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontButtonActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_fontButtonActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel author;
  private javax.swing.JButton bgColorButton;
  private javax.swing.JButton cancelButton;
  private javax.swing.JLabel cellID;
  private javax.swing.JLabel cellIDLabel;
  private javax.swing.JLabel cellName;
  private javax.swing.JLabel cellNameLabel;
  private javax.swing.JLabel date;
  private javax.swing.JLabel dateLabel;
  private javax.swing.JTextArea editableMessage;
  private javax.swing.JScrollPane editableMessageScrollPane;
  private javax.swing.JTextField editableSubject;
  private javax.swing.JButton fontButton;
  private javax.swing.JButton fontColorButton;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JToolBar jToolBar1;
  private javax.swing.JLabel message;
  private javax.swing.JButton saveButton;
  private javax.swing.JLabel subject;
  // End of variables declaration//GEN-END:variables


  /**
   * get current text of panel
   */
  public String getText(){
    return message.getText();
  }

  /**
   * set current text of panel
   */
  public void setText(String s){
    message.setText(s);
  }

  /**
   * get current text of panel
   */
  public String getEditableText(){
    return editableMessage.getText();
  }

  /**
   * get current subject of panel
   */
  public String getEditableSubject() {
    return editableSubject.getText();
  }

  /**
   * set current text of panel
   */
  public void setEditableText(String s){
    editableMessage.setText(s);
  }
  
  /**
   * get current Author of panel
   */
  public String getAuthor(){
    return author.getText();
  }

  /**
   * set current Author of panel
   */
  public void setAuthor(String s){
    author.setText(s);
  }
  
  /**
   * get current date of panel
   */
  public String getDate(){
    return date.getText();
  }

  /**
   * set current date of panel
   */
  public void setDate(String s){
    date.setText(s);
  }

  /**
   * get current subject of panel
   */
  public String getSubject(){
    return subject.getText();
  }

  /**
   * set current subject of panel
   */
  public void setSubject(String s){
    subject.setText(s);
  }

  /**
   * set current editable subject of panel
   */
  public void setEditableSubject(String s){
    editableSubject.setText(s);
  }



  /**
   * switch between editing mode and none editing mode.
   *
   * Editing mode (true) displays save and cancel buttons, and a text area.
   *
   * Non-editing mode hides these buttons and displays text with a label.
   * @param b editing mode
   */
  private void showEdits(boolean b) {
    saveButton.setVisible(b);
    cancelButton.setVisible(b);
    
    // switch to editable (textarea) version
    if(b == true){
      editableMessage.setText(fromHTML(message.getText()));
      editableMessageScrollPane.setVisible(true);
      message.setVisible(false);

      editableSubject.setText(fromHTML(subject.getText()));
      editableSubject.setVisible(true);
      subject.setVisible(false);
    }
    else{
      editableMessageScrollPane.setVisible(false);
      message.setVisible(true);
      editableSubject.setVisible(false);
      subject.setVisible(true);
    }
  }

  /**
   * used to partially convert user entered text (from a text area) into html.
   * This will preserve newlines in a JLabel, for example.
   * Surrounds string in <html> and </html>, replaces \n with <br/>
   * @param s
   * @return
   */
  private String toHTML(String s){
    if(s.indexOf("<html>")  == 0   &&
            (s.indexOf("</html>") == s.length() - 7)){
      System.out.println("ALREADY HTMLD");
      return s;
    }
    String res = "<html>";
    res += s.replaceAll("\n", "<br/>");
    res += "</html>";
    return res;
  }

  /**
   * Opposite of toHTML, removes HTML formatting
   * use to move from a JLabel to something like a JTextArea
   * @param s
   * @return
   */
  String fromHTML(String s){
    if(s.indexOf("<html>")  == 0   &&
            (s.indexOf("</html>") == s.length() - 7)){
      System.out.println("convert back");
      s = s.replaceAll("<br/>", "\n");
      s = s.substring(6, s.length() - 8);
      return s;
    }

    return s;
  }

  private void setForegrounds(Color newColor) {
    this.setForeground(newColor);
    author.setForeground(newColor);
    dateLabel.setForeground(newColor);
    date.setForeground(newColor);
    cellName.setForeground(newColor);
    cellNameLabel.setForeground(newColor);
    cellID.setForeground(newColor);
    cellIDLabel.setForeground(newColor);
  }

  public void removeSaveButtonListener(ActionListener l) {
    saveButton.removeActionListener(l);
  }

  

}
