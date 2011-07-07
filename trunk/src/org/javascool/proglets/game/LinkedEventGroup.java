/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.util.logging.Logger;

/**
 * This class allows the objects to recieve events with the syntax : 
 * <code>object.onClick("callbackFunction");</code>
 * The callback function is added to the global register of callback functions
 * for this event associated with this, which implements EventCatcher so that
 * the object can decide if it wants to catch the event or not.
 * @author gmatheron
 */
public abstract class LinkedEventGroup implements EventCatcher {
    /**
     * Default constructor
     */
    public LinkedEventGroup() {
    }
    
    private static final Logger LOG = Logger.getLogger(LinkedEventGroup.class.getName());

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     * @param always If set to true, the callback will always be performed for the
     * element even if the event did not occur on it.
     */
    public void onClick(String s, boolean always) {
        Functions.getSingleton().m_onClick.add(new EventListener(s, this, always));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseEntered(String s, boolean always) {
        Functions.getSingleton().m_onMouseEntered.add(new EventListener(s, this, always));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseExited(String s, boolean always) {
        Functions.getSingleton().m_onMouseExited.add(new EventListener(s, this, always));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMousePressed(String s, boolean always) {
        Functions.getSingleton().m_onMousePressed.add(new EventListener(s, this, always));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseReleased(String s, boolean always) {
        Functions.getSingleton().m_onMouseReleased.add(new EventListener(s, this, always));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseDown(String s, boolean always) {
        Functions.getSingleton().m_onMouseDown.add(new EventListener(s, this, always));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseUp(String s, boolean always) {
        Functions.getSingleton().m_onMouseUp.add(new EventListener(s, this, always));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseMoved(String s, boolean always) {
        Functions.getSingleton().m_onMouseMoved.add(new EventListener(s, this, always));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseDragged(String s, boolean always) {
        Functions.getSingleton().m_onMouseDragged.add(new EventListener(s, this, always));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseWheelState argument
     * @param s The function to callback
     */
    public void onMouseWheelUp(String s, boolean always) {
        Functions.getSingleton().m_onMouseWheelUp.add(new EventListener(s, this, always));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseWheelState argument
     * @param s The function to callback
     */
    public void onMouseWheelDown(String s, boolean always) {
        Functions.getSingleton().m_onMouseWheelDown.add(new EventListener(s, this, always));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseWheelState argument
     * @param s The function to callback
     */
    public void onMouseWheelMoved(String s, boolean always) {
        Functions.getSingleton().m_onMouseWheelMoved.add(new EventListener(s, this, always));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseWheelState argument
     * @param s The function to callback
     */
    public void onFrame(String s, boolean always) {
        Functions.getSingleton().m_onFrame.add(new EventListener(s, this, always));
    }
}
