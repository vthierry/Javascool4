/*********************************************************************************
* Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
* Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2004.  All rights reserved.   *
*********************************************************************************/
package org.javascool.widgets;

// Used to build the gui
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;

// Used to manage links
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLDecoder;
import javax.swing.text.Document;
import org.javascool.tools.Macros;

// Used to manage keystroke
import java.util.ArrayList;

/** Definit un visualisateur de pages HTML3.
 * <p><i>Note:</i> L'implémentation disponible ne rend utilisable que le "vieux" HTML-3.</p>
 * <p><i>Conseil:</i> Ecrire les pages en <a href="ttp://javascool.gforge.inria.fr/v4/index.php?page=developers&action=doc-xml>HML</a> (en XHTML simplifié),
 * la maintenance et polyvalence des pages en sera renforcée.</p>
 * <div id="URLs"><p><b>Mécanismes d'affichage des contenus:</b><ul>
 * <li>Les pages locales d'extension <tt>*.htm</tt> sont réputées être du HTML3 et sont affichées ici.</li>
 * <li>Les autres pages <tt>http://</tt>, <tt>file://</tt>, etc.. sont visualiées dans le navigateur du système, extérieur à javascool.</li>
 * <li>Il est possible d'ouvrir des pages dans une cible autre que ce visualisateur: <ul>
 *   <li>Les liens de la forme <tt>jvs://?editor:<i>location</i></tt> ouvrent le document dans l'éditeur de JavaScool. </li>
 *   <li>Les liens de la forme <tt>jvs://?browser:<i>location</i></tt> ouvrent le document dans un autre onglet de JavaScool. </li>
 * </ul> Il sont produits par les tags <tt>&lt;a target="editor" . . </tt> du XML.
 * En cas d'échec les contenus sont dirigés vers le navigateur du système, extérieur à javascool.</li>
 * <li>Les liens de la forme <tt>string://?value="text"</tt> permettent d'afficher directement du texte HTML3.</li>
 * <li>Les autres liens font l'objet d'un appel à la méthode <tt>doBrowse()</tt> ce qui permet de définir des URI dépendant de l'application.</li>
 * </ul></div>
 *
 * @see <a href="HtmlDisplay.java.html">source code</a>
 * @serial exclude
 */
public class HtmlDisplay extends JPanel {
  private static final long serialVersionUID = 1L;

  /** Le panneau d'affichage du texte. */
  private JEditorPane pane;
  /** Les bottons de navigation. */
  private JButton home, prev, next;

  /** Définit le préfix pour une chaîne. */
  static private final String stringPrefix = "string://?value=:";
  /** Définit le préfix pour une ouverture dans l'éditeur. */
  static private final String editorPrefix = "vs://?editor:";
  /** Définit le préfix pour une ouverture dans un onglet. */
  static private final String browserPrefix = "jvs://?browser:";

  // @bean
  public HtmlDisplay() {
    setLayout(new BorderLayout());
    {
      ToolBar bar = new ToolBar();
      home = bar.addTool("Page initiale", "org/javascool/widgets/icons/refresh.png", new Runnable() {
	  public void run() {
	    if(urls.hasHome())
	      update(urls.getHome(), false);
	    updateButtons();
	  }});
      prev = bar.addTool("Page précédente", "org/javascool/widgets/icons/prev.png", new Runnable() {
	  public void run() {
          if(urls.hasPrev())
            update(urls.getPrev(), false);
          updateButtons();
	  }});
       next = bar.addTool("Page suivante", "org/javascool/widgets/icons/next.png", new Runnable() {
	  public void run() {
          if(urls.hasNext())
            update(urls.getNext(), false);
          updateButtons();
	  }});
       add(bar, BorderLayout.NORTH);
    }
    {
      pane = new JEditorPane();
      pane.setBackground(Color.WHITE);
      pane.setEditable(false);
      pane.setContentType("text/html");
      pane.addHyperlinkListener(new HyperlinkListener() {
                                  @Override
                                  public void hyperlinkUpdate(HyperlinkEvent e) {
                                    if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                                      update(e.getDescription(), true);
                                    }
                                  }
                                }
                                );
      JScrollPane spane = new JScrollPane(pane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      add(spane, BorderLayout.CENTER);
    }
  }
  /** Mets à jour les boutons selon l'état de la pile. */
  private void updateButtons() {
    home.setEnabled(urls.hasHome());
    prev.setEnabled(urls.hasPrev());
    next.setEnabled(urls.hasNext());
  }
  /** Définit une pile d'URL avec le mécanisme de home/prev/next. */
  private class URLStack extends ArrayList<URL>{
    private static final long serialVersionUID = 1L;
    /** Index courant dans la pile. */
    private int current = -1;

    /** Ajoute un élément dans la pile. */
    public void push(URL url) {
      if(this.size() > 0)
        this.current = this.size();
      else
        this.current = 0;
      add(url);
    }
    public URL getCurrent() {
      return current < 0 ? null : get(current);
    }
    public boolean hasHome() {
      return current >= 0;
    }
    public URL getHome() {
      if(hasHome())
        current = 0;
      return getCurrent();
    }
    public boolean hasPrev() {
      return current > 0;
    }
    public URL getPrev() {
      if(hasPrev())
        current--;
      return getCurrent();
    }
    public boolean hasNext() {
      return current < size() - 1;
    }
    public URL getNext() {
      if(hasNext())
        current++;
      return getCurrent();
    }
  }
  private URLStack urls = new URLStack();

  /** Affiche une page de texte HTML3 dans le visualisateur.
   * @param location L'URL de la page à afficher.
   * @return Cet objet, permettant de définir la construction <tt>new HtmlDisplay().setPage(..)</tt>.
   */
  public HtmlDisplay setPage(String location) {
    update(location, true);
    return this;
  }
  public HtmlDisplay setPage(URL location) {
    return setPage(location != null ? location.toString() : "null location");
  }
  /** Affiche un texte HTML3 dans le visualisateur.
   * @param text Le texte à afficher.
   * @return Cet objet, permettant de définir la construction <tt>new HtmlDisplay().setText(..)</tt>.
   */
  public HtmlDisplay setText(String text) {
    try {
      return setPage(stringPrefix + URLEncoder.encode(text, "UTF-8"));
    } catch(java.io.UnsupportedEncodingException e) { throw new IllegalStateException("UTF-8 n'est pas reconnu comme encodage: (" + e + ") c'est un bug Java !");
    }
  }
  /** Implémentation du mécanisme de gestion des URL spécifiques.
   * <p>Cette routine est appelée pour gérer des URL specifiques d'une application donnée.</p>
   * @param location L'URL à traiter.
   * @return Cette méthode doit retourner true si l'URL à été traité et false si l'URL n'a pas été reconnu ou traité.
   */
  public boolean doBrowse(String location) {
    return false;
  }
  /** Gestion des URLs extenes par le navigateur du système. */
  private void browse(String location) {
    try {
      java.awt.Desktop.getDesktop().browse(new java.net.URI(location));
    } catch(Exception e) {
      setText("Cette page est à l'adresse internet: <tt>«" + location.replaceFirst("^(mailto):.*", "$1: ...") + "»</tt> (non accessible ici).");
    }
  }
  /** Mécanisme de gestion des URL. */
  private void update(String location, boolean push) {
    try {     
      URL url = urls.isEmpty() ? Macros.getResourceURL(location) : new URL(urls.getCurrent(), location);
       // Gestion des contenus textuels
      if(location.startsWith(stringPrefix)) { // Affichage de texte
        pane.setText(URLDecoder.decode(location.substring(stringPrefix.length()), "UTF-8"));
      } else if(location.matches("^(http|https|rtsp|mailto):.*$")) {    // Gestion des URL externes
        browse(location);
      } else if(location.matches(".*\\.htm$")) { // Gestion des URLs en HTML3
        pane.getDocument().putProperty(Document.StreamDescriptionProperty, null);
        pane.setPage(new URL(urls.getCurrent(), location));
      } else if(location.startsWith(editorPrefix)) { // Affichage dand JavaScool
        org.javascool.gui.Desktop.getInstance().addFile(location.substring(editorPrefix.length()));
      } else if(location.startsWith(browserPrefix)) { // Affichage dand JavaScool
        org.javascool.gui.Desktop.getInstance().addTab(location.substring(browserPrefix.length()));
      } else if(!doBrowse(location)) { // Délégation au client
        setText("Le lien : <tt>«" + location + "»</tt> n'a pas pu être affiché");
      }
    } catch(Exception e) {
      setText("Le lien : <tt>«" + location + "»</tt> génère une erreur \"" + e.toString() + "\"");
    }
  }
  private void update(URL location, boolean push) {
    update(location.toString(), push);
  }
}

