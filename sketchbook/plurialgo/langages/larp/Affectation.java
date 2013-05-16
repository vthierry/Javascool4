/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.larp;

import org.javascool.proglets.plurialgo.divers.Divers;

/**
 * Cette classe h�rite de la classe homonyme du mod�le.
*/
public class Affectation extends org.javascool.proglets.plurialgo.langages.modele.Affectation {

	public Affectation() {
	}
	
	public void ecrire(Programme prog, StringBuffer buf, int indent) {
		Divers.ecrire(buf, var + " = " + expression, indent);
	}
}
