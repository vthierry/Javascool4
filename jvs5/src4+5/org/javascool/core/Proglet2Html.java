package org.javascool.core;

import java.io.File;
import java.io.IOException;
import org.json.JSONObject;
import org.javascool.tools.FileManager;

import org.javascool.widgets.MainFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import org.javascool.macros.Macros;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import org.javascool.widgets.Console;
import org.javascool.tools.UserConfig;

/** Définit le mécanisme de compilation en ligne d'une proglet dans sa version jvs5.
 * - Attention il faut que la proglet ait été convertie en jvs5 (conversion des docs XML en HTML, du fichier de méta-donnée en .json).
 * @see <a href="Proglet2Html.java.html">code source</a>
 * @serial exclude
 */
public class Proglet2Html {
  // @factory
  private Proglet2Html() {}

  /** Compile sous forme de répertoire web une proglet donnée.
   * <p>Les erreurs de compilation ou de construction s'affichent dans la console.</p>
   * @param htmlDir Le répertoire cible de la construction, qui sera <i>détruit</i> avant d'être utilisé pour créer les fichiers.
   * @param progletDir Le répertoire où se trouvent les fichiers de la proglet.
   * @return La valeur true en cas de succès, false si il y a des erreurs de compilation.
   *
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors de la compilation ou construction.
   */
  public static boolean build(String htmlDir, String progletDir) {
    try {
      if (!new File(progletDir).exists())
	throw new IllegalArgumentException("Le répertoire "+progletDir+" n'existe pas");
      if (!new File(progletDir).isDirectory())
	throw new IllegalArgumentException("Le fichier "+progletDir+" n'est pa sun répertoire");
      JSONObject params = Proglet2Jar.getProgletParameters(progletDir);
      // Creation d'un répertoire vierge
      System.out.println("> création de "+htmlDir);
      JarManager.rmDir(new File(htmlDir));
      new File(htmlDir).mkdirs();
      // Expansion des fichiers de base du site
      addBootstrap(htmlDir);
      FileManager.save(htmlDir + File.separator + "index.html", "<script>location.replace('help.html');</script>");
      // Copy des fichiers de la proglet
      for (String file : FileManager.list(progletDir)) {
	if (new File(file).isFile() && !(file.endsWith(".jar") || file.endsWith(".zip"))) {
	  if (file.endsWith(".html")) {
	    FileManager.save(htmlDir + File.separator + new File(file).getName(), encapsulates(FileManager.load(file), params));
	  } else if (file.endsWith(".java")) {
	    JarManager.copyFile(file, htmlDir + File.separator + "api" +  
				File.separator + "org" + File.separator + "javascool" + File.separator + "proglets" + File.separator + params.getString("name") + 
				File.separator + new File(file).getName());
	  } else {
	    if (file.endsWith(".jvs"))
	      FileManager.save(htmlDir + File.separator + new File(file).getName() + ".html",
			       encapsulates("<br/><br/><div align=\"right\"><a href=\""+new File(file).getName()+".jar\">@lancer le programme</a></div><br/><pre class=\"prettyprint linenums\">\n\n" + FileManager.load(file) + "\n</pre>", params));
	    JarManager.copyFile(file, htmlDir + File.separator + new File(file).getName());
	  }
	}
      }
      // Génération des fichiers liés aux sources java
      {
	System.out.println("> compilation 1/2 de "+params.getString("name"));
	String progletJar = htmlDir + File.separator + "javascool-proglet-"+params.getString("name")+".jar";
	Proglet2Jar.build(progletJar, progletDir);
	System.out.println("> compilation 2/2 de "+params.getString("name"));
	String jsv2jarJar = htmlDir + File.separator + "javascool-jvs2jar-"+params.getString("name")+".jar";
	Proglet2Jar.build(jsv2jarJar, progletDir);
	System.out.println("> compilation de la javadoc");
	javadoc(params.getString("name"), System.getProperty("java.class.path"), htmlDir + File.separator + "api", htmlDir + File.separator + "api");
	for (String file : FileManager.list(htmlDir))
	  if (file.endsWith(".jvs")) {
	    System.out.println("> compilation de "+new File(file).getName());
	    Utils.javaStart("-jar "+jsv2jarJar+" "+file, 60);
	  }
      }
      return true;
    } catch (Throwable e) {
      System.out.println(e);
      e.printStackTrace();
      return false;
    }
  }
  /**
   * @see #build(String, String)
   */
  public static boolean build(String htmlDir) {
    return build(htmlDir+"-html", htmlDir);
  }
  // Encapsulate le corps d'une page html
  private static String encapsulates(String body, JSONObject params) throws Exception {
    return (progletHtmlHeader + body + progletHtmlTrailer)
      .replaceAll("@name", params.getString("name"))
      .replaceAll("@title", params.getString("title"))
      .replaceAll("@icon", params.getString("icon"))
      .replaceAll("@author", params.getString("author"))
      .replaceAll("@email", params.getString("email"))
      .replaceAll("@base-url", ".")
      .replaceAll("href=\"http://newtab\\?", "href=\"")
      .replaceAll("< *a +href=\"http://editor\\?([^\\.]*).jvs\"", "<a href=\"$1.jvs.html\"");
  }

  // Genere la doc liée aux source java
  private static void javadoc(String name, String classPath, String srcDir, String apiDir) throws IOException {
    // Mise en place des dossiers source et cible
    apiDir = new File(apiDir).getCanonicalPath();
    srcDir = new File(srcDir).getCanonicalPath();
    if (!apiDir.equals(srcDir))
      JarManager.rmDir(new File(apiDir));
    new File(apiDir).mkdirs();
    String files[] = FileManager.list(srcDir, ".*\\.java$", 5);
    // Lancement du logiciel javadoc avec les bonnes options
    if (files.length > 0) {
      String argv = "-quiet\t-classpath\t" + classPath + "\t-d\t" + apiDir +
	"\t-link\thttp://download.oracle.com/javase/6/docs/api" + "\t-windowtitle\tJava's Cool "
	+ name + "\t-doctitle\tJava's Cool " + name +
	"\t-public\t-author\t-version\t-nodeprecated\t-nohelp\t-nonavbar\t-notree\t-charset\tutf-8";
      for (String f : files)
	argv += "\t" + f;
      try {
	com.sun.tools.javadoc.Main.execute(argv.split("\t"));
      } catch (Throwable e) {
	throw new IllegalStateException("Erreur à la création de la javadoc du répertoire \"" + srcDir + "\", «" + e + "»");
      }
      // Copie et encapsulation des sources pour accéder au code
      for (String file : files)
	FileManager.save(apiDir + File.separator + file.substring(srcDir.length()) + ".html",
			 "<pre class=\"prettyprint linenums\">\n" + FileManager.load(file) + "\n</pre>");
      
      // Manipulation des fichiers html générés pour lisser le style
      for (String file : FileManager.list(apiDir, ".*\\.html", 5)) {
	String text = FileManager.load(file)
	  .replaceFirst("(?s)<!DOCTYPE.*<body[^>]*>", "")
	  .replaceFirst("</body>\\s*</html>", "")
	  .replaceAll("class=\"(blockList|inheritance)\"", "class=\"unstyled\"")
	  .replaceAll("<table[^>]*>", "<table class=\"table table-striped table-bordered\">")
	  .replaceAll("<(/?)code>", "<$1pre>");
	text = (javadocHtmlHeader + text + progletHtmlTrailer)
	  .replaceAll("@name", name).replaceAll(
						"@base-url",
						file.substring(apiDir.length() + 1)
						.replaceAll("[^/]+", "..")
						.substring(1));
	FileManager.save(file, text);
      }
      if (new File(apiDir + File.separator + "overview-summary.html").exists())
	JarManager.copyFile(apiDir + File.separator + "overview-summary.html", apiDir + File.separator + "index.html");
      addBootstrap(apiDir);
    }
  }

  // Ajout des fichier du style HTML local
  private static void addBootstrap(String apiDir) throws IOException {
    JarManager.copyResource("org/javascool/core/proglet-css.zip", apiDir + File.separator + "proglet-css.zip");
    JarManager.jarExtract(apiDir + File.separator + "proglet-css.zip", apiDir);
    new File(apiDir + File.separator + "proglet-css.zip").delete();
  }
  
  private final static String progletHtmlHeader0 = 
    "<!DOCTYPE html>\n" +
    "<html lang=\"fr\">\n" +
    "<head>\n" +
    "  <title>Java'sCool «@name»</title>\n" +
    "  <meta charset=\"UTF-8\">\n" +
    "  <style type=\"text/css\">\n" +
    "     #main { margin-top: 80px;  }\n" +
    "  </style>\n" +
    "  <link href=\"@base-url/assets/pygments.css\" rel=\"stylesheet\">\n" +
    "  <link href=\"@base-url/assets/bootstrap/css/bootstrap.css\" rel=\"stylesheet\">\n" +
    "  <link href=\"@base-url/assets/bootstrap/css/bootstrap-responsive.css\" rel=\"stylesheet\">\n" +
    "  <link href=\"@base-url/assets/google-code-prettify/prettify.css\" type=\"text/css\" rel=\"stylesheet\"/>\n" +
    "  <script type=\"text/javascript\" src=\"@base-url/assets/google-code-prettify/prettify.js\"></script>\n" +
    "</head><body style=\"background-color:#FFFFFF;\" onload=\"prettyPrint()\">\n" +
    "\n";

  private final static String progletHtmlNavBar = 
    "<div class=\"navbar navbar-fixed-top\">\n" +
    "  <div class=\"navbar-inner\">\n" +
    "    <div class=\"container\">\n" +
    "      <a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">\n" +
    "        <span class=\"icon-bar\"></span>\n" +
    "        <span class=\"icon-bar\"></span>\n" +
    "      </a>\n" +
    "      <a class=\"brand\" href=\"@base-url/help.html\" style=\"margin: 0; margin-right: 30px; margin-top: 3px; padding:0;\">\n" +
    "        <img alt=\"logo\" src=\"@base-url/@icon\" style=\"height: 35px; padding: 0; margin: 0;\"/>&nbsp;<b>Java'sCool «@name»</b>\n" +
    "      </a>\n" +
    "      <div class=\"nav-collapse\">\n" +
    "        <ul class=\"nav\">\n" +
    "          <li><a href=\"@base-url/help.html\">Documentation</a></li>\n" +
    "          <li><a href=\"@base-url/javascool-proglet-@name.jar\">Lancement</a></li>\n" +
    "          <li><a href=\"@base-url/javascool-jvs2jar-@name.jar\">JvsCompilation</a></li>\n" +
    "          <li><a href=\"@base-url/api/org/javascool/proglets/@name/package-summary.html\">Implémentation</a></li>\n" +
    "          <li><a href=\"mailto:@email?subject=À propos de Java'sCool «@name»\">Contact</a></li>\n" +
    "        </ul>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "</div>\n" +
    "<div id=\"main\"><b><img align=\"left\" src=\"@icon\" alt=\"icon\"/>&nbsp;Java's Cool proglet «@name»</b><br/><div align=\"center\">@title</div><div align=\"right\"><i>@author</i></div></div>\n" +
    "<div class=\"container\">\n";

  private static final String progletHtmlHeader  = progletHtmlHeader0 + progletHtmlNavBar;

  private final static String javadocHtmlNavBar = 
    "<div class=\"navbar navbar-fixed-top\">\n" +
    "  <div class=\"navbar-inner\">\n" +
    "    <div class=\"container\">\n" +
    "      <a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">\n" +
    "        <span class=\"icon-bar\"></span>\n" +
    "        <span class=\"icon-bar\"></span>\n" +
    "      </a>\n" +
    "      <a class=\"brand\" href=\"@base-url/help.html\" style=\"margin: 0; margin-right: 30px; margin-top: 3px; padding:0;\">\n" +
    "        &nbsp;<b>Java'sCool «@name»</b>\n" +
    "      </a>\n" +
    "      <div class=\"nav-collapse\">\n" +
    "        <ul class=\"nav\">\n" +
    "          <li></li>\n" +
    "        </ul>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "</div>\n" +
    "<div id=\"main\"></div>\n" +
    "<div class=\"container\">\n";

  private static final String javadocHtmlHeader  = progletHtmlHeader0 + javadocHtmlNavBar;

  private final static String progletHtmlTrailer = 
    "</div>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/jquery.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-transition.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-alert.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-modal.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-dropdown.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-scrollspy.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-tab.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-tooltip.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-popover.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-button.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-collapse.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-carousel.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-typeahead.js\"></script>\n" +
    "</body></html>\n";
  
  /** Lanceur de la construction de la proglet.
   * @param usage <tt>java org.javascool.core.Proglet2Html (jarFile progletDir|javadocName srcDir apiDir)</tt>
   */
  public static void main(String[] usage) {
    // @main
    if(usage.length == 2) {
      build(usage[0], usage[1]);
    } else if(usage.length == 1) {
      build(usage[0]);
    } else if(usage.length == 3) {
      try {
	javadoc(usage[0], System.getProperty("java.class.path"), usage[1], usage[2]);
      } catch(IOException e) {
	throw new RuntimeException(e.toString());
      }
    } else {
      new MainFrame().reset("Proglet2Html", "org/javascool/widgets/icons/compile.png", 800, 600, new JPanel() {
	  {
	    setLayout(new BorderLayout());
	    setBorder(BorderFactory.createTitledBorder("Compilation d'une proglet javascol"));
	    add(new JPanel() {
		{
		  add(new JButton("Choisir", Macros.getIcon("org/javascool/widgets/icons/open.png")) {
		      private static final long serialVersionUID = 1L;
		      {
			addActionListener(new ActionListener() {
			    private static final long serialVersionUID = 1L;
			    @Override
			    public void actionPerformed(ActionEvent e) {
			      (new JFileChooser() {
				  private static final long serialVersionUID = 1L;
				  {
				    setDialogTitle("Sélection du répertoire de la proglet . . ");
				    setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				    String path = UserConfig.getInstance("proglet2html").getProperty("proglet-path");
				    if (path != null)
				      setCurrentDirectory(new File(path));
                                if (path != null)
                                    setCurrentDirectory(new File(path));

				  }
				  public void run(ActionEvent e) {
				    if (showOpenDialog(((JButton) e.getSource()).getParent().getParent()) == JFileChooser.APPROVE_OPTION) {
				      updateLocation(getSelectedFile().getPath());
				    }
				  }
				}).run(e);
			    }
			  });
		      }});
		  add(new JPanel() {
		      {
			setBorder(BorderFactory.createTitledBorder("Répertoire de la proglet"));
			add(folder = (new JTextField(32) {
			    private static final long serialVersionUID = 1L;
			    {
			      addActionListener(new ActionListener() {
				  @Override
				  public void actionPerformed(ActionEvent e) {
				    updateLocation();
				  }
				});
			    }
			  }));
			add(new JLabel(File.separator));
			add(name = (new JTextField(12) {
			    private static final long serialVersionUID = 1L;
			    {
			      addActionListener(new ActionListener() {
				  @Override
				  public void actionPerformed(ActionEvent e) {
				    updateLocation();
				  }
				});
			    }
			  }));
		      }});
		  add(doit = new JButton("Compiler", Macros.getIcon("org/javascool/widgets/icons/compile.png")) {
		      private static final long serialVersionUID = 1L;
		      {
			addActionListener(new ActionListener() {
			    private static final long serialVersionUID = 1L;
			    @Override
			    public void actionPerformed(ActionEvent e) {
			      (new Thread(new Runnable() { public void run() {
				String path = folder.getText() + File.separator + name.getText();
				if (new File(path).exists()) {
				  System.out.println("Construction de "+new File(path).getName()+"..");
				  if (build(path))
				    System.out.println("achevée avec succès :\n Le répertoire '"+path+"-html' est disponible");
				} else {
				  System.out.println("Construction de "+new File(path).getName()+"..");
				  if (ProgletCreate.build(path))
				    System.out.println("achevée avec succès :\n Le répertoire '"+path+"' est disponible");
				  doit.setText("Compiler");
				}
			      }})).start();
			    }
			  });
		      }});
		  
		}}, BorderLayout.NORTH);
	    add(Console.getInstance());
	  }
	  private JTextField folder, name;
	  private JButton doit;
	  private void updateLocation(String path) {
	    if (name.getText().length() == 0 && new File(path + File.separator + "proglet.json").exists()) {
	      folder.setText(new File(path).getParent());
	      name.setText(new File(path).getName());
	    } else {
	      folder.setText(path);
	    }
	    updateLocation();
	  }
	  private void updateLocation() {
	    String path = folder.getText() + File.separator + name.getText();
	    if (new File(folder.getText()).isDirectory())
	      UserConfig.getInstance("proglet2html").setProperty("proglet-path", path);
	    else
	      System.out.println("Attention, le répertoire "+folder+" n'existe pas");
	    doit.setText(new File(path).exists() ? "Compiler" : "Créer");
	  }
	});
    }
  }
}