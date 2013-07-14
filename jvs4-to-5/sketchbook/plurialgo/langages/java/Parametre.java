/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.java;

import org.javascool.proglets.plurialgo.divers.*;

/**
 * Cette classe hérite de la classe homonyme du modèle.
*/
public class Parametre extends org.javascool.proglets.plurialgo.langages.modele.Parametre {
	
	public Parametre() {
	}
	
	public void ecrire(Programme prog, StringBuffer buf) {
		prog.ecrireType(buf, this);
		Divers.ecrire(buf, " ");
		Divers.ecrire(buf, nom);
	}
	
}
