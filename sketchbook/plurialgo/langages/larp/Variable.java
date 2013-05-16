/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.larp;


/**
 * Cette classe h�rite de la classe homonyme du mod�le.
*/
public class Variable extends org.javascool.proglets.plurialgo.langages.modele.Variable {
	
	public Variable() {
	}
	
	public Variable(String nom, String type) {
		this.nom = nom;
		this.type = type;
		this.mode = null;
	}
	
	public void ecrire(Programme prog, StringBuffer buf, int indent) {
		// vide puisqu'on ne d�clare pas les variables en Larp
	}

}
