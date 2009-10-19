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
package org.jdesktop.wonderland.modules.videoplayer.client;

import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import org.jdesktop.wonderland.modules.videoplayer.common.VideoPlayerState;

/**
 * Video player control panel
 *
 * @author nsimpson
 */
public class VideoPlayerControlPanel extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(VideoPlayerControlPanel.class.getName());
    protected boolean fillMode = false;
    protected ArrayList<VideoPlayerToolListener> cellMenuListeners = new ArrayList();
    protected Map toolMappings;
    protected Map colorMappings;
    protected VideoPlayerWindow window;
    protected VideoPlayerDragGestureListener gestureListener;
    private ImageIcon playIcon;
    private ImageIcon pauseIcon;

    public VideoPlayerControlPanel(VideoPlayerWindow window) {
        this.window = window;
        initComponents();
        initListeners();
        playIcon = new ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerPlay32x32.png"));
        pauseIcon = new ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerPause32x32.png"));
    }

    public void addCellMenuListener(VideoPlayerToolListener listener) {
        cellMenuListeners.add(listener);
    }

    public void removeCellMenuListener(VideoPlayerToolListener listener) {
        cellMenuListeners.remove(listener);
    }

    private void initListeners() {
        DragSource ds = DragSource.getDefaultDragSource();
        gestureListener = new VideoPlayerDragGestureListener(window);
        ds.createDefaultDragGestureRecognizer(dragHUDButton,
                DnDConstants.ACTION_COPY_OR_MOVE, gestureListener);
    }

    public void setMode(VideoPlayerState state) {
        switch (state) {
            case PLAYING:
                playHUDButton.setIcon(pauseIcon);
                break;
            case PAUSED:
                playHUDButton.setIcon(playIcon);
                break;
            case STOPPED:
                playHUDButton.setIcon(playIcon);
                break;
        }
    }

    /**
     * Gets whether a button is depressed
     * @param button the button to check
     * @return true if the button is depressed, false otherwise
     */
    public boolean isButtonDepressed(JButton button) {
        return button.isBorderPainted();
    }

    /**
     * Depress/undepress a button
     * @param button the button to depress
     * @param depress true to depress a button, false to undepress
     */
    public void depressButton(JButton button, boolean depress) {
        button.setBorderPainted(depress);
    }

    public void setOnHUD(boolean onHUD) {
        if (onHUD) {
            toggleHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerDock32x32.png")));
        } else {
            toggleHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerUndock32x32.png")));
        }
    }

    public void setSynced(boolean synced) {
        if (synced == true) {
            syncHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerSyncedYes32x32.png")));
        } else {
            syncHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerSyncedNo32x32.png")));
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toggleHUDButton = new javax.swing.JButton();
        openHUDButton = new javax.swing.JButton();
        rewindHUDButton = new javax.swing.JButton();
        stopHUDButton = new javax.swing.JButton();
        playHUDButton = new javax.swing.JButton();
        fastforwardHUDButton = new javax.swing.JButton();
        syncHUDButton = new javax.swing.JButton();
        dragHUDButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(231, 230, 230));

        toggleHUDButton.setBackground(new java.awt.Color(231, 230, 230));
        toggleHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerDock32x32.png"))); // NOI18N
        toggleHUDButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        toggleHUDButton.setBorderPainted(false);
        toggleHUDButton.setMargin(new java.awt.Insets(0, -4, 0, -4));
        toggleHUDButton.setMaximumSize(new java.awt.Dimension(38, 38));
        toggleHUDButton.setMinimumSize(new java.awt.Dimension(38, 38));
        toggleHUDButton.setOpaque(true);
        toggleHUDButton.setPreferredSize(new java.awt.Dimension(38, 38));
        toggleHUDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleHUDButtonActionPerformed(evt);
            }
        });

        openHUDButton.setBackground(new java.awt.Color(231, 230, 230));
        openHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerOpen32x32.png"))); // NOI18N
        openHUDButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        openHUDButton.setBorderPainted(false);
        openHUDButton.setMargin(new java.awt.Insets(0, -4, 0, -4));
        openHUDButton.setMaximumSize(new java.awt.Dimension(38, 38));
        openHUDButton.setMinimumSize(new java.awt.Dimension(38, 38));
        openHUDButton.setOpaque(true);
        openHUDButton.setPreferredSize(new java.awt.Dimension(38, 38));
        openHUDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openHUDButtonActionPerformed(evt);
            }
        });

        rewindHUDButton.setBackground(new java.awt.Color(231, 230, 230));
        rewindHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerSkipRev32x32.png"))); // NOI18N
        rewindHUDButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        rewindHUDButton.setBorderPainted(false);
        rewindHUDButton.setMargin(new java.awt.Insets(0, -4, 0, -4));
        rewindHUDButton.setMaximumSize(new java.awt.Dimension(38, 38));
        rewindHUDButton.setMinimumSize(new java.awt.Dimension(38, 38));
        rewindHUDButton.setOpaque(true);
        rewindHUDButton.setPreferredSize(new java.awt.Dimension(38, 38));
        rewindHUDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindHUDButtonActionPerformed(evt);
            }
        });

        stopHUDButton.setBackground(new java.awt.Color(231, 230, 230));
        stopHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerStop32x32.png"))); // NOI18N
        stopHUDButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        stopHUDButton.setBorderPainted(false);
        stopHUDButton.setMargin(new java.awt.Insets(0, -4, 0, -4));
        stopHUDButton.setMaximumSize(new java.awt.Dimension(38, 38));
        stopHUDButton.setMinimumSize(new java.awt.Dimension(38, 38));
        stopHUDButton.setOpaque(true);
        stopHUDButton.setPreferredSize(new java.awt.Dimension(38, 38));
        stopHUDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopHUDButtonActionPerformed(evt);
            }
        });

        playHUDButton.setBackground(new java.awt.Color(231, 230, 230));
        playHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerPlay32x32.png"))); // NOI18N
        playHUDButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        playHUDButton.setBorderPainted(false);
        playHUDButton.setMargin(new java.awt.Insets(0, -4, 0, -4));
        playHUDButton.setMaximumSize(new java.awt.Dimension(38, 38));
        playHUDButton.setMinimumSize(new java.awt.Dimension(38, 38));
        playHUDButton.setOpaque(true);
        playHUDButton.setPreferredSize(new java.awt.Dimension(38, 38));
        playHUDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playHUDButtonActionPerformed(evt);
            }
        });

        fastforwardHUDButton.setBackground(new java.awt.Color(231, 230, 230));
        fastforwardHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerSkipFwd32x32.png"))); // NOI18N
        fastforwardHUDButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fastforwardHUDButton.setBorderPainted(false);
        fastforwardHUDButton.setMargin(new java.awt.Insets(0, -4, 0, -4));
        fastforwardHUDButton.setMaximumSize(new java.awt.Dimension(38, 38));
        fastforwardHUDButton.setMinimumSize(new java.awt.Dimension(38, 38));
        fastforwardHUDButton.setOpaque(true);
        fastforwardHUDButton.setPreferredSize(new java.awt.Dimension(38, 38));
        fastforwardHUDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fastforwardHUDButtonActionPerformed(evt);
            }
        });

        syncHUDButton.setBackground(new java.awt.Color(231, 230, 230));
        syncHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerSyncedYes32x32.png"))); // NOI18N
        syncHUDButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        syncHUDButton.setBorderPainted(false);
        syncHUDButton.setMargin(new java.awt.Insets(0, -4, 0, -4));
        syncHUDButton.setMaximumSize(new java.awt.Dimension(38, 38));
        syncHUDButton.setMinimumSize(new java.awt.Dimension(38, 38));
        syncHUDButton.setOpaque(true);
        syncHUDButton.setPreferredSize(new java.awt.Dimension(38, 38));
        syncHUDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syncHUDButtonActionPerformed(evt);
            }
        });

        dragHUDButton.setBackground(new java.awt.Color(231, 230, 230));
        dragHUDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/videoplayer/client/resources/VideoPlayerDrag32x32.png"))); // NOI18N
        dragHUDButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        dragHUDButton.setBorderPainted(false);
        dragHUDButton.setMargin(new java.awt.Insets(0, -4, 0, -4));
        dragHUDButton.setMaximumSize(new java.awt.Dimension(38, 38));
        dragHUDButton.setMinimumSize(new java.awt.Dimension(38, 38));
        dragHUDButton.setOpaque(true);
        dragHUDButton.setPreferredSize(new java.awt.Dimension(38, 38));
        dragHUDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dragHUDButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(toggleHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(openHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(rewindHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(stopHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(playHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(fastforwardHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(syncHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(dragHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(toggleHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(stopHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(rewindHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(openHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(playHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(fastforwardHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(syncHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(dragHUDButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void toggleHUDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleHUDButtonActionPerformed
        Iterator<VideoPlayerToolListener> iter = cellMenuListeners.iterator();
        while (iter.hasNext()) {
            VideoPlayerToolListener listener = iter.next();
            listener.toggleHUD();
        }
}//GEN-LAST:event_toggleHUDButtonActionPerformed

    private void openHUDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openHUDButtonActionPerformed
        Iterator<VideoPlayerToolListener> iter = cellMenuListeners.iterator();
        while (iter.hasNext()) {
            VideoPlayerToolListener listener = iter.next();
            listener.openMedia();
        }
    }//GEN-LAST:event_openHUDButtonActionPerformed

    private void rewindHUDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rewindHUDButtonActionPerformed
        Iterator<VideoPlayerToolListener> iter = cellMenuListeners.iterator();
        while (iter.hasNext()) {
            VideoPlayerToolListener listener = iter.next();
            listener.rewind();
        }
    }//GEN-LAST:event_rewindHUDButtonActionPerformed

    private void stopHUDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopHUDButtonActionPerformed
        Iterator<VideoPlayerToolListener> iter = cellMenuListeners.iterator();
        while (iter.hasNext()) {
            VideoPlayerToolListener listener = iter.next();
            listener.stop();
        }
    }//GEN-LAST:event_stopHUDButtonActionPerformed

    private void playHUDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playHUDButtonActionPerformed
        Iterator<VideoPlayerToolListener> iter = cellMenuListeners.iterator();
        while (iter.hasNext()) {
            VideoPlayerToolListener listener = iter.next();
            if (window.isPlaying()) {
                listener.pause();
            } else {
                listener.play();
            }
        }
    }//GEN-LAST:event_playHUDButtonActionPerformed

    private void fastforwardHUDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fastforwardHUDButtonActionPerformed
        Iterator<VideoPlayerToolListener> iter = cellMenuListeners.iterator();
        while (iter.hasNext()) {
            VideoPlayerToolListener listener = iter.next();
            listener.fastForward();
        }
    }//GEN-LAST:event_fastforwardHUDButtonActionPerformed

    private void dragHUDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dragHUDButtonActionPerformed
        Iterator<VideoPlayerToolListener> iter = cellMenuListeners.iterator();
        while (iter.hasNext()) {
            VideoPlayerToolListener listener = iter.next();
            // TODO: implement drag
        }
    }//GEN-LAST:event_dragHUDButtonActionPerformed

    private void syncHUDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncHUDButtonActionPerformed
        // TODO: display a goto page dialog
    }//GEN-LAST:event_syncHUDButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dragHUDButton;
    private javax.swing.JButton fastforwardHUDButton;
    private javax.swing.JButton openHUDButton;
    private javax.swing.JButton playHUDButton;
    private javax.swing.JButton rewindHUDButton;
    private javax.swing.JButton stopHUDButton;
    private javax.swing.JButton syncHUDButton;
    private javax.swing.JButton toggleHUDButton;
    // End of variables declaration//GEN-END:variables
}
