package org.javascool.gui;

import org.javascool.core.ProgletEngine;

import org.javascool.widgets.ToolBar;
import javax.swing.JButton;
import org.javascool.widgets.StartStopButton;
import org.javascool.builder.ProgletsBuilder;

/** La barre d'outils de Java's cool
 * Elle est placée en haut de l'interface. Elle contient les boutons de gestion
 * des fichiers, de compilation et d'éxecution.
 * @see org.javascool.widgets.StartStopButton
 * @see org.javascool.gui.JVSPanel
 */
class JVSToolBar extends ToolBar {

    private static final long serialVersionUID = 1L;
    /** Boutons de l'interface. */
    private JButton compileButton;
    private StartStopButton runButton;

    /** Instance de la classe */
    private static JVSToolBar jvstb;
    
    public static JVSToolBar getInstance(){
        if(jvstb==null){
            jvstb=new JVSToolBar();
        }
        return jvstb;
    }
    
    private JVSToolBar() {
        setName("Java's cool ToolBar");
        init();
    }

    /** Initialize la barre d'outils en créant les bouttons */
    private void init() {
        addTool("Nouvelle activité", "org/javascool/widgets/icons/new.png", new Runnable() {

            @Override
            public void run() {
                JVSPanel.getInstance().closeProglet();
            }
        });
        addTool("Nouveau fichier", "org/javascool/widgets/icons/new.png", new Runnable() {

            @Override
            public void run() {
                JVSPanel.getInstance().newFile();
            }
        });
        addTool("Ouvrir un fichier", "org/javascool/widgets/icons/open.png", new Runnable() {

            @Override
            public void run() {
                JVSPanel.getInstance().openFile();
            }
        });
        addTool("Sauver", "org/javascool/widgets/icons/save.png", new Runnable() {

            @Override
            public void run() {
                JVSPanel.getInstance().saveFile();
            }
        });
        addTool("Fermer le fichier", "org/javascool/widgets/icons/remove.png", new Runnable() {

            @Override
            public void run() {
                JVSPanel.getInstance().closeFile();
            }
        });

        compileButton = addTool("Compiler", "org/javascool/widgets/icons/compile.png", new Runnable() {

            @Override
            public void run() {
                JVSPanel.getInstance().compileFile();
            }
        });

        addTool("Executer", runButton = new StartStopButton() {

            private static final long serialVersionUID = 1L;

            @Override
            public void start() {
                ProgletEngine.getInstance().doRun();
            }

            @Override
            public void stop() {
                ProgletEngine.getInstance().doStop();
            }

            @Override
            public boolean isRunning() {
                return ProgletEngine.getInstance().isRunning();
            }
        });

        runButton.setVisible(false);
        // Crée le menu de construction de proglets si pertinent
        if (ProgletsBuilder.hasProglets()) {
            pbutton = addRightTool("Proglet Builder", new Runnable() {

                @Override
                public void run() {
                    org.javascool.builder.DialogFrame.startProgletMenu(pbutton);
                }
            });
        }
    }
    // @ inner-class-variable
    private JButton pbutton;

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

    public void disableStartStopButton() {
        runButton.setVisible(false);
        revalidate();
    }
}
