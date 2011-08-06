/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import javax.swing.Box;
import org.javascool.core.Engine;

import  org.javascool.widgets.ToolBar;
import javax.swing.JButton;
import  org.javascool.widgets.StartStopButton;

/** The JVS top tool bar
 * @author Philippe VIENNE
 */
class JVSToolBar extends ToolBar {
  private static final long serialVersionUID = 1L;

  /** Boutons de l'interface. */
  private JButton compileButton;
  private StartStopButton runButton;

  // @bean
  public JVSToolBar() {
    setName("Java's cool ToolBar");
    init();
  }
  protected JVSToolBar(Boolean escapeInit) {
    setName("Java's cool ToolBar");
  }
  /** Initialize the toolBar with default button and setUps */
  private void init() {
    addTool("Nouvel activité", "org/javascool/widgets/icons/new.png", new Runnable() {
                   @Override
                   public void run() {
                     JVSMainPanel.getInstance().closeProglet();
                   }
                 }
                 );
    addTool("Nouveau fichier", "org/javascool/widgets/icons/new.png", new Runnable() {
                   @Override
                   public void run() {
                     JVSMainPanel.getInstance().newFile();
                   }
                 }
                 );
    addTool("Ouvrir un fichier", "org/javascool/widgets/icons/open.png", new Runnable() {
                   @Override
                   public void run() {
                     JVSMainPanel.getInstance().openFile();
                   }
                 }
                 );
    addTool("Sauver", "org/javascool/widgets/icons/save.png", new Runnable() {
                   @Override
                   public void run() {
                     JVSMainPanel.getInstance().saveFile();
                   }
                 }
                 );
    addTool("Fermer le fichier", "org/javascool/widgets/icons/remove.png", new Runnable() {
                   @Override
                   public void run() {
                     JVSMainPanel.getInstance().closeFile();
                   }
                 }
                 );

    compileButton = addTool("Compiler", "org/javascool/widgets/icons/compile.png", new Runnable() {
                                        @Override
                                        public void run() {
                                          JVSMainPanel.getInstance().compileFile();
                                        }
                                      }
                                      );

    addTool("Executer", runButton = new StartStopButton() {
	@Override
	  public void start() { Engine.getInstance().doRun(); }
	@Override
	  public void stop() { Engine.getInstance().doStop(); }
      });

    runButton.setVisible(false);


    add(Box.createHorizontalGlue());
  }
  public void enableCompileButton() {
    compileButton.setVisible(true);
    revalidate();
  }
  public void disableCompileButton() {
    compileButton.setVisible(false);
    revalidate();
  }
  public void enableStartStopButton() {
    runButton.setVisible(true);
    revalidate();
  }
  public void desactivateStartStopButton() {
    runButton.setVisible(false);
    revalidate();
  }
}
