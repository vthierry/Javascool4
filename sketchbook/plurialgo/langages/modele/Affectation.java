/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.modele;

import org.javascool.proglets.plurialgo.divers.Divers;

/**
 * Cette classe doit �tre �tendue pour chaque langage de programmation implant�.
*/
public class Affectation extends Noeud {
	
	// variables utilis�es par BindingCastor
	/**
	 * Le nom de la variable d'affectation.
	 */
	public String var;
	/**
	 * L'expression affect�e � la variable.
	 */
	public String expression;
	
	public Affectation() {		
	}
	
	// ---------------------------------------------
	// Noeuds
	// ---------------------------------------------

	public void parcoursEnfants() {
	}
	
	// ---------------------------------------------
	// fonctions booleennes
	// ---------------------------------------------
	
	public boolean isAffTabSimple(){
		String txt_expr = expression.trim();
		if (txt_expr.startsWith("[[")) return false;
		if (txt_expr.startsWith("[")) return true;
		return false;
	}
	
	public boolean isAffMatSimple(){
		String txt_expr = expression.trim();
		if (txt_expr.startsWith("[[")) return true;
		return false;
	}

	// ---------------------------------------------
	// Xml
	// ---------------------------------------------
	
	void ecrireXml(StringBuffer buf, int indent) {
		Divers.ecrire(buf, "<affectation", indent);
		Divers.ecrireAttrXml(buf, "var", var);
		Divers.ecrireAttrXml(buf, "expression", expression);
		Divers.ecrire(buf, "/>");
	}
	
}
