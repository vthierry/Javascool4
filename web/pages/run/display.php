<?php 
    showBrowser(
        array(
            array("Java's Cool","index.php"),
            array("Lancement","")
        ),
        array(
            array("Licence","?page=run&action=licence"),
            array("Usage","?page=run&action=screenshot")
        )
    );
?>

<div class="display">
    <p>Pour lancer Java's Cool sans installation, cliquez sur un des liens ci-dessus. Si le lancement échoue, vous devrez probablement installer Java (version au moins 1.6). 
En cas d'échec voir la section "Plus d'informations" en bas de cette page.</p>

    <div class="label2">Windows</div>
<p>Téléchargez Java's Cool au format <tt>JAR</tt> exécutable <b><a href="javascool-proglets.jar">ici</a></b>, en choisissant l'option «ouvrir avec Java».</p>
    <div class="label2">Mac OS</div>
<p>Téléchargez Java's Cool au format <tt>JAR</tt> exécutable <b><a href="javascool-proglets.jar">ici</a></b>, en choisissant l'option «ouvrir avec Java».</p>
<p>Ou utiliser le dossier application <tt>.app</tt> proposé <b><a href="javascool-proglets.app.zip">ici</a></b>.</p>
    <div class="label2">Linux</div>
	<p>Sous linux, téléchargez Java's Cool au format <tt>JAR</tt> <b><a href="javascool-proglets.jar">ici</a></b>, puis suivez les instructions ci-dessous : 
    <table><tr><td><img src="images/screen1.png" alt="screenshot" style="width: 300px; height: auto;"/></td><td>
                <span class="label-arrow" /></td><td>
                <img src="images/screen2.png" alt="screenshot" style="width: 300px; height: auto;"/></td></tr></table></p>
                
<p>-On peut aussi choisir <tt>Sauver sous</tt> pour ne pas télécharger le logiciel à chaque lancement puis <tt>Ouvrir avec . . <i>java</i></tt>.</p>
<p>-On peut aussi lancer à partir d'une ligne de commande <tt>java -jar javascool-proglets.jar</tt> une fois le logiciel téléchargé.</p>
    

<div class="label2">Plus d'informations</div>
    <?php echo wiki_get_contents('JavaScool:Lancement'); ?>
</div>
