/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.xml;


/**
 * Cette classe h�rite de la classe homonyme du mod�le.
*/
public class Variable extends org.javascool.proglets.plurialgo.langages.modele.Variable {
	public Variable() {
	}
	public Variable(String nom, String type, String mode) {
		this.nom = nom;
		this.type = type;
		this.mode = mode;
	}
}
