/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.javascool;

import org.javascool.proglets.plurialgo.divers.*;

/**
 * Cette classe h�rite de la classe homonyme du mod�le.
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
