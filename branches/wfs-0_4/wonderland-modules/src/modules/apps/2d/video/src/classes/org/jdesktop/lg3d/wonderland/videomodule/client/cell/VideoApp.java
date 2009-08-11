/**
 * Project Looking Glass
 *
 * $RCSfile$
 *
 * Copyright (c) 2004-2008, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision$
 * $Date$
 * $State$
 */
package org.jdesktop.lg3d.wonderland.videomodule.client.cell;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.media.Player;

import javax.swing.SwingUtilities;
import javax.vecmath.Point3f;
import org.jdesktop.lg3d.wonderland.appshare.AppGroup;
import org.jdesktop.lg3d.wonderland.appshare.AppWindowGraphics2DApp;
import org.jdesktop.lg3d.wonderland.appshare.SimpleControlArb;
import org.jdesktop.lg3d.wonderland.appshare.DrawingSurface;
import org.jdesktop.lg3d.wonderland.appshare.SimpleDrawingSurface;

import org.jdesktop.lg3d.wonderland.darkstar.client.ChannelController;
import org.jdesktop.lg3d.wonderland.darkstar.client.cell.SharedApp2DImageCell;

import org.jdesktop.lg3d.wonderland.scenemanager.EventController;
import org.jdesktop.lg3d.wonderland.scenemanager.hud.HUD;
import org.jdesktop.lg3d.wonderland.scenemanager.hud.HUD.HUDButton;
import org.jdesktop.lg3d.wonderland.scenemanager.hud.HUDFactory;
import org.jdesktop.lg3d.wonderland.videomodule.common.JMFSnapper;
import org.jdesktop.lg3d.wonderland.videomodule.common.VideoCellMessage;
import org.jdesktop.lg3d.wonderland.videomodule.common.VideoCellMessage.Action;
import org.jdesktop.lg3d.wonderland.videomodule.common.VideoCellMessage.PlayerState;
import org.jdesktop.lg3d.wonderland.videomodule.common.VideoSource;

/**
 *
 * Video player application
 *
 * @author nsimpson
 */
public class VideoApp extends AppWindowGraphics2DApp {

    private static final Logger logger =
            Logger.getLogger(VideoApp.class.getName());
    protected VideoSource videoInstance;
    protected static final float ACTIVE_FRAME_RATE = 10.0f;
    protected static final float INACTIVE_FRAME_RATE = 2.0f;
    protected static final double DEFAULT_WIDTH = 1280;
    protected static final double DEFAULT_HEIGHT = 960;
    protected DrawingSurface drawingSurface;
    protected JMFSnapper snapper;
    private Timer frameTimer;
    private FrameUpdateTask frameUpdateTask;
    private float frameRate = 0f;
    private float preferredFrameRate = ACTIVE_FRAME_RATE;
    private double preferredWidth = 0;  // none, use media width
    private double preferredHeight = 0; // none, use media height
    private boolean synced = false;
    private String video;
    private HUDButton msgButton;
    protected Object actionLock = new Object();
    private VideoSourceDialog videoDialog;
    private boolean hudEnabled = true;
    private boolean audioEnabled = false;
    private boolean audioUserMuted = false;
    private boolean inControl = false;

    public VideoApp(SharedApp2DImageCell cell) {
        this(cell, 0, 0, (int) DEFAULT_WIDTH, (int) DEFAULT_HEIGHT);
    }

    public VideoApp(SharedApp2DImageCell cell, int x, int y, int width, int height) {
        super(new AppGroup(new SimpleControlArb()), true, x, y, width, height, cell);

        drawingSurface = new SimpleDrawingSurface();
        drawingSurface.setSize(width, height);
        drawingSurface.addSurfaceListener(new DrawingSurface.SurfaceListener() {

            public void redrawSurface() {
                repaint();
            }
        });

        initVideoDialog();
        addEventListeners();

        setShowing(true);
    }

    public void setVideoInstance(VideoSource videoInstance) {
        this.videoInstance = videoInstance;
        loadVideo(videoInstance.getSource());
    }

    /**
     * Set up event listeners for keyboard and mouse events
     */
    private void addEventListeners() {
        addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {
                logger.finest("key pressed on app");
                dispatchKeyEvent(e);
            }

            public void keyReleased(KeyEvent e) {
                logger.finest("key released on app");
//                dispatchKeyEvent(e);
            }

            public void keyTyped(KeyEvent e) {
                logger.finest("key typed on app");
//                dispatchKeyEvent(e);
            }
        });

        addMouseMotionListener(new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
                logger.finest("mouse dragged on app");
                dispatchMouseEvent(e);
            }

            public void mouseMoved(MouseEvent e) {
                logger.finest("mouse moved on app");
                dispatchMouseEvent(e);
            }
        });

        addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                logger.finest("mouseClicked on app");
                dispatchMouseEvent(e);
            }

            public void mousePressed(MouseEvent e) {
                logger.finest("mousePressed on app");
                dispatchMouseEvent(e);
            }

            public void mouseReleased(MouseEvent e) {
                logger.finest("mouseReleased on app");
                dispatchMouseEvent(e);
            }

            public void mouseEntered(MouseEvent e) {
                logger.finest("mouseEntered on app");
                dispatchMouseEvent(e);
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                logger.finest("mouseExited on app");
                dispatchMouseEvent(e);
                repaint();
            }
        });
    }

    /**
     * Initialize the dialog for opening PDF documents
     */
    private void initVideoDialog() {
        videoDialog = new VideoSourceDialog(null, false);
        videoDialog.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideVideoDialog();
                if (evt.getActionCommand().equals("OK")) {
                    if (isSynced()) {
                        sendCameraRequest(Action.SET_SOURCE, null);
                    } else {
                        loadVideo(videoDialog.getVideoURL());
                    }
                }
            }
        });
    }

    /**
     * Display the video source dialog
     */
    private void showVideoDialog() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                videoDialog.setVisible(true);
            }
        });
    }

    /**
     * Hide the open video source dialog
     */
    public void hideVideoDialog() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                videoDialog.setVisible(false);
            }
        });
    }

    public void loadVideo(String video) {
        if (video != null) {
            // stop the current video
            stop();

            // load the new video
            this.video = video;
            videoDialog.setVideoURL(video);
            snapper = new JMFSnapper(video);

            // stop the new video
            stop();

            BufferedImage frame = snapper.getFrame();
            if (frame != null) {
                double w = frame.getWidth();
                double h = frame.getHeight();

                if (preferredWidth != 0) {
                    // width preference
                    if (w != preferredWidth) {
                        double aspect = h / w;
                        w = preferredWidth;
                        h = preferredWidth * aspect;
                    }
                }
                if ((w > 0) && (h > 0)) {
                    logger.fine("resizing app window to fit video: " + w + "x" + h);
                    setSize((int) w, (int) h);
                }
            }
            setInControl(inControl);
        }
    }

    public String getVideo() {
        return video;
    }

    public void setFrameRate(float rate) {
        logger.info("--- setting frame rate to: " + rate);
        showHUDMessage("fps: " + (int) rate, 2000);
    }

    public float getFrameRate() {
        return preferredFrameRate;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setState(PlayerState state) {
        logger.fine("setting state to: " + state);
        switch (state) {
            case PLAYING:
                play(true);
                break;
            case PAUSED:
                play(false);
                break;
            case STOPPED:
                play(false);
                break;
        }

    }

    public PlayerState getState() {
        // REMIND: differentiate between stopped and paused?
        if (isPlaying()) {
            return PlayerState.PLAYING;
        } else {
            return PlayerState.PAUSED;
        }
    }

    public synchronized void setPosition(double time) {
        logger.fine("setting position to: " + time);

        if (snapper != null) {
            snapper.setPosition((time > snapper.getDuration()) ? 0 : time);
        }
    }

    public double getPosition() {
        double time = 0;
        if (snapper != null) {
            time = snapper.getPosition();
        }

        return time;
    }

    public void setPreferredWidth(double preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

    public void setPreferredHeight(double preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    public void setInControl(boolean inControl) {
        this.inControl = inControl;
        // When the application gains control, increase the frame rate to the
        // frame rate defined in the application's setup data (from the WFS
        // properties) or to the rate set by the user. Also, enable audio, 
        // unless the user had previously muted.
        // 
        // When the application loses control, reduce the frame rate to
        // the inactive rate to reduce cpu load. Also mute audio, regardless
        // of what the user has done.
        //
        // Ultimately, the frame rate should vary depending on avatar proximity
        // to the video application and whether the video is in view.
        if (inControl == true) {
            setFrameRate(preferredFrameRate);
        } else {
            setFrameRate(INACTIVE_FRAME_RATE);
        }
        setAudioEnabled(inControl);
    }

    public boolean isInControl() {
        return inControl;
    }

    public void setAudioEnabled(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;

        if (audioEnabled == true) {
            if (audioUserMuted == false) {
                // user hasn't manually muted video
                mute(false);
            }
        } else {
            mute(true);
        }
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }

    /**
     * Show a status message in the HUD
     * @param message the string to display in the message
     */
    protected void showHUDMessage(String message) {
        showHUDMessage(message, HUD.NO_TIMEOUT);
    }

    /**
     * Show a status message in the HUD and remove it after a timeout
     * @param message the string to display in the message
     * @param timeout the period in milliseconds to display the message for
     */
    protected void showHUDMessage(String message, int timeout) {
        if (hudEnabled == true) {
            URL[] imgURLs = {HUD.SIMPLE_BOX_IMAGE_URL,
                EventController.class.getResource("resources/preferences-system-windows.png")
            };

            Point[] imagePoints = {new Point(), new Point(10, 10)};

            // dismiss currently active HUD message
            if ((msgButton != null) && msgButton.isActive()) {
                hideHUDMessage(true);
            }

            // display a new HUD message
            msgButton = HUDFactory.getHUD().addHUDMultiImageButton(imgURLs,
                    imagePoints, message, new Point(50, 25),
                    Font.decode("dialog" + "-BOLD-14"),
                    -150, 100, 150, 100,
                    timeout, true);
        }
    }

    /**
     * Hide the HUD message
     * @param immediately if true, remove the message now, otherwise slide it
     * off the screen first
     */
    private void hideHUDMessage(boolean immediately) {
        if (hudEnabled == true) {
            if (msgButton != null) {
                if (!immediately) {
                    msgButton.changeLocation(new Point(-45, 100));
                }

                msgButton.setActive(false);
            }
        }
    }

    public void setHUDEnabled(boolean enabled) {
        if (hudEnabled == true) {
            hideHUDMessage(true);
        }
        hudEnabled = enabled;
    }

    public boolean isHUDEnabled() {
        return hudEnabled;
    }

    public void play(boolean play) {
        logger.fine("--- play: " + play);

        // perform local play action
        if (play == true) {
            start();
            showHUDMessage("Playing", 2000);
        } else {
            stop();
            showHUDMessage("Paused");
        }

    }

    public boolean isPlaying() {
        boolean playing = false;
        if (snapper != null) {
            // ask the player for the play state, this has the most
            // accurate status of what the user is seeing
            playing = snapper.getPlayerState() == Player.Started;
            logger.finest("--- playing == " + playing + " (" + snapper.getPlayerState() + ")");
        }

        return playing;
    }

    private void start() {
        if ((snapper == null) || (snapper.hasPlayer() == false)) {
            return;
        }

        if (isPlaying() && (frameRate != preferredFrameRate)) {
            // frame rate has changed, need to restart play
            stop();
        }

        if (!isPlaying()) {
            if (preferredFrameRate > 0) {
                logger.info("--- starting playback");
                snapper.startMovie();
                frameRate = preferredFrameRate;
                frameTimer = new Timer();
                frameUpdateTask = new FrameUpdateTask();
                frameTimer.scheduleAtFixedRate(frameUpdateTask, 0, (long) (1000 * 1f / frameRate));
            }
        }
    }

    private void stop() {
        if ((snapper == null) || (snapper.hasPlayer() == false)) {
            return;
        }

        logger.info("--- stopping playback");
        snapper.stopMovie();
        frameRate = 0;
        if (frameTimer != null) {
            frameTimer.cancel();
        }

        frameTimer = null;
    }

    public void cue(double start, double cueLeadIn) {
        CueVideo cuer = new CueVideo(start, cueLeadIn);
        new Thread(cuer).start();
    }

    private class CueVideo implements Runnable {

        private double start;
        private double cueLeadIn;
        private Timer cueTimer;
        private boolean muteState;
        private boolean hudState;

        public CueVideo(double start, double cueLeadIn) {
            this.start = start;
            this.cueLeadIn = cueLeadIn;
        }

        public void run() {
            logger.fine("--- scheduling cue in from: " + (start - cueLeadIn) + " to " + start);
            muteState = isMuted();
            hudState = isHUDEnabled();
            setHUDEnabled(false);
            stop();
            setPosition(start - cueLeadIn);
            snapper.setStopTime(start);
            mute(true);
            cueTimer = new Timer();
            start();
            cueTimer.schedule(new CueCompleteTask(), (long) cueLeadIn*2);
        }

        private class CueCompleteTask extends TimerTask {

            public void run() {
                if (snapper.getPlayerState() == 600) {
                    logger.fine("--- premature stop !!");
                }
                stop();
                snapper.setStopTime(JMFSnapper.RESET_STOP_TIME);
                mute(muteState);
                setHUDEnabled(hudState);
                cueTimer.cancel();
                logger.fine("--- cue complete");
            }
        }
    }

    public void mute(boolean muting) {
        if (snapper != null) {
            logger.fine("--- " + ((muting == true) ? "muting" : "unmuting"));
            snapper.mute(muting);
            showHUDMessage(isMuted() ? "Muted" : "Unmuted", 2000);
        }
    }

    public boolean isMuted() {
        return (snapper != null) ? snapper.isMuted() : true;
    }

    protected void dispatchKeyEvent(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F:
                // change the frame rate
                if ((e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) == KeyEvent.SHIFT_DOWN_MASK) {
                    logger.fine("increasing frame rate");
                    preferredFrameRate++;
                    setFrameRate(preferredFrameRate);
                    if (isPlaying() == true) {
                        // already playing, re-start
                        start();
                    }
                } else {
                    logger.fine("decreasing frame rate");
                    preferredFrameRate = (preferredFrameRate > 0) ? preferredFrameRate - 1 : 0;
                    setFrameRate(preferredFrameRate);
                    if (isPlaying() == true) {
                        // already playing, re-start
                        start();
                    }
                }
                break;
            case KeyEvent.VK_M:
                // mute/unmute
                audioUserMuted = (isMuted() == false) ? true : false;
                mute(!isMuted());
                break;
            case KeyEvent.VK_O:
                // open a new video
                if (e.isControlDown() == true) {
                    showVideoDialog();
                }
                break;
            case KeyEvent.VK_P:
                // play/pause
                if (isPlaying()) {
                    // pause immediately for better responsiveness
                    // other clients will catch up to this state
                    play(false);
                    if (isSynced()) {
                        sendCameraRequest(Action.PAUSE, null);
                    }
                } else {
                    // play when server acknowledges request for better sync
                    // with other clients
                    if (isSynced()) {
                        sendCameraRequest(Action.PLAY, null);
                    }
                }
                break;
            case KeyEvent.VK_S:
                // re-sync with shared state
                setSynced(!isSynced());
                if (isSynced()) {
                    sendCameraRequest(Action.GET_STATE, null);
                }
                break;
        }
    }

    protected void dispatchMouseEvent(MouseEvent e) {
        switch (e.getID()) {
            case MouseEvent.MOUSE_CLICKED:
                break;
        }
    }

    protected class ActionScheduler extends Thread {

        private Action action;
        private Point3f point;

        public ActionScheduler(Action action, Point3f point) {
            this.action = action;
            this.point = point;
        }

        @Override
        public void run() {
            // wait for a retry window
            synchronized (actionLock) {
                try {
                    logger.fine("--- waiting for retry window");
                    actionLock.wait();
                } catch (Exception e) {
                    logger.warning("--- exception waiting for retry: " + e);
                }
            }
            // retry this request
            logger.fine("--- retrying");
            sendCameraRequest(action, point);
        }
    }

    protected void sendCameraRequest(Action action, Point3f position) {
        VideoCellMessage msg = null;

        switch (action) {
            case SET_SOURCE:
                msg = new VideoCellMessage(this.getCell().getCellID(),
                        ((VideoCell) cell).getUID(),
                        videoDialog.getVideoURL(),
                        action,
                        0);
                msg.setState(PlayerState.PLAYING);
                break;
            case PLAY:
            case PAUSE:
            case STOP:
                msg = new VideoCellMessage(this.getCell().getCellID(),
                        ((VideoCell) cell).getUID(),
                        getVideo(),
                        action,
                        getPosition());
                break;
            case GET_STATE:
                msg = new VideoCellMessage(this.getCell().getCellID(),
                        ((VideoCell) cell).getUID(),
                        VideoCellMessage.Action.GET_STATE);
                break;
        }

        if (msg != null) {
            // send request to server
            logger.info("--- sending camera request: " + msg);
            ChannelController.getController().sendMessage(msg);
        }
    }

    /**
     * Initiates a change to the camera's pan, tilt or zoom positions
     * @param action
     * @param position
     */
    protected void retryCameraRequest(Action action, Point3f position) {
        new ActionScheduler(action, position).start();
    }

    protected void handleResponse(VideoCellMessage msg) {
        String controlling = msg.getUID();
        String myUID = ((VideoCell) cell).getUID();
        boolean forMe = (myUID.equals(controlling));
        VideoCellMessage vcm = null;

        // a client may send a request while another camera has control.
        // the server denies the conflicting request and the client must
        // the re-issue the request when the client currently in control
        // relinquishes control
        switch (msg.getAction()) {
            case REQUEST_DENIED:
                // could retry request here
                break;
            case SET_SOURCE:
                if (isSynced() == true) {
                    logger.fine("--- performing action: " + msg.getAction());
                    loadVideo(msg.getSource());
                    cue(0.4, 0.1);
                }
                if (forMe == true) {
                    // notify everyone that the request has completed
                    vcm = new VideoCellMessage(msg);
                    vcm.setAction(Action.REQUEST_COMPLETE);
                }
                break;
            case PLAY:
            case PAUSE:
            case STOP:
                // only change playback if this cell has control
                if (isSynced()) {
                    // change the play state of the video
                    logger.fine("--- performing action: " + msg.getAction());
                    if (msg.getAction() == Action.PLAY) {
                        // starting to play
                        setPosition(msg.getPosition());
                        play(true);
                    } else {
                        // pausing
                        play(false);
                        cue(msg.getPosition(), 0.1);
                    }
                }
                if (forMe == true) {
                    // notify everyone that the request has completed
                    vcm = new VideoCellMessage(msg);
                    vcm.setAction(Action.REQUEST_COMPLETE);
                }
                break;
            case SET_STATE:
                if (forMe == true) {
                    if (isSynced()) {
                        logger.fine("--- syncing to state: " + msg);
                        loadVideo(msg.getSource());
                        //setPosition(msg.getPosition());
                        if (msg.getState() == PlayerState.PLAYING) {
                            play(true);
                        } else {
                            cue(msg.getPosition(), 0.1);
                        }
                        logger.fine("--- video synced");
                        showHUDMessage("sync complete", 2000);
                    }
                    // notify everyone that the request has completed
                    vcm = new VideoCellMessage(msg);
                    vcm.setAction(Action.REQUEST_COMPLETE);
                }
                break;
            case REQUEST_COMPLETE:
                synchronized (actionLock) {
                    try {
                        logger.fine("--- waking retry threads");
                        actionLock.notify();
                    } catch (Exception e) {
                        logger.warning("--- exception notifying retry threads: " + e);
                    }
                }
                break;
        }
        if (vcm != null) {
            logger.info("--- sending message: " + vcm);
            ChannelController.getController().sendMessage(vcm);
        }
    }

    public void getSynced() {
        logger.fine("--- syncing to shared state");
        sendCameraRequest(Action.GET_STATE, null);
    }

    /**
     * Set the size of the application
     * @param width the width of the application
     * @param height the height of the application
     */
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        drawingSurface.setSize(width, height);
    }

    @Override
    public void takeControl(MouseEvent me) {
        logger.fine("--- video has control");
        super.takeControl(me);
        setInControl(true);
    }

    @Override
    public void releaseControl(MouseEvent me) {
        logger.fine("--- video lost control");
        super.releaseControl(me);
        setInControl(false);
    }

    private class FrameUpdateTask extends TimerTask {

        public void run() {
            doFrameUpdate();
        }
    }

    protected void doFrameUpdate() {
        if (snapper != null) {
            if (snapper.getPlayerState() == Player.Started) {
                VideoApp.this.repaint();
            } else {
                logger.info("--- stopping frame update task because movie isn't playing: " + snapper.getPlayerState());
                stop();
            }
        }
    }

    /**
     * Paint contents of window
     */
    @Override
    protected void paint(Graphics2D g) {
        if (drawingSurface != null) {
            if (snapper != null) {
                BufferedImage frame = snapper.getFrame();

                if (frame != null) {
                    g.drawImage(frame, 0, 0, getWidth(), getHeight(), null);
                }
            }
        }
    }
}