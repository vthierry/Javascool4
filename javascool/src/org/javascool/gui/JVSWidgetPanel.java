/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.applet.Applet;
import javax.swing.JApplet;
import javax.swing.JPanel;
import org.javascool.proglet.Proglet;

/**
 *
 * @author Philippe Vienne
 */
public class JVSWidgetPanel extends JVSTabs {
 private static final long serialVersionUID = 1L;
 
    private String progletTabId;

    public JVSWidgetPanel() {
        super();
    }

    public void setProglet(Proglet proglet) {
        if (proglet.getPanel() != null) {
            this.progletTabId = this.add("Proglet "+proglet.getName(), "", proglet.getPanel());
        }
        if (proglet.getHelpWidget()!=null){
            this.add("Aide de la proglet", "", proglet.getHelpWidget());
        }
    }

    public Applet getProgletPanel() {
        return JVSMainPanel.getCurrentProglet().getPanel();
    }
    
    public void focusOnProgletPanel(){
        if(progletTabId!=null){
            this.switchToTab(progletTabId);
        }
    }
    
    public void showConsole(){
        this.setSelectedIndex(this.indexOfTab("Console"));
    }
    
    public void openWebTab(String url,String tabName){
        JVSHtmlDisplay memo = new org.javascool.gui.JVSHtmlDisplay();
        memo.load(url);
        this.add(tabName, "", memo);
        this.setTabComponentAt(this.indexOfTab(tabName),new TabPanel(this));
        this.setSelectedComponent(memo);
    }
}
