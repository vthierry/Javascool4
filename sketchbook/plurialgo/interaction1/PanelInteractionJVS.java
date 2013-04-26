/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.interaction1;

import javax.swing.JTabbedPane;

/**
 * Cette classe adapte � l'environnement Javascool
 * certains param�tres du conteneur par d�faut de l'interface graphique.
*/
public class PanelInteractionJVS extends PanelInteraction {
	private static final long serialVersionUID = 1L;
	
	public PanelInteractionJVS() {
		super();
	}
		
	/*
	 * Red�finition pour masquer l'onglet Complements.
	 */
	public void initOnglets() {
		// nord
		pConsole = new PanelConsole(null);
		this.add(pConsole,"North");
		// centre
		onglets = new JTabbedPane(JTabbedPane.TOP);
		onglets.setBackground(null);
		pPrincipal = new PanelPrincipal(this);	onglets.add("Principal", pPrincipal);
		pEdition = new PanelProgrammes(this); onglets.add("R�sultats", pEdition);
		pHtml = new PanelHtml(this); onglets.add("Documentation", pHtml);
		pXml = new PanelXml(this);  // onglets.add("Compl�ments", pXml);  // cach� pour des lyc�ens
		this.add(onglets, "Center");
	}

	/*
	 * Red�finition pour changer la configuration (langages, l'url de documentation...).
	 */
	public void initConfig() {
		PanelInteraction.urlDoc = "/org/javascool/proglets/plurialgo/aideJVS/";
		String[] langages = { "javascool", "vb", "larp", "javascript", "java" };
		PanelInteraction.langList = langages;
	}

}
