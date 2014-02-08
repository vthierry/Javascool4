/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.javascript;

import java.util.*;

import org.javascool.proglets.plurialgo.divers.*;


/**
 * Cette classe hérite de la classe homonyme du modèle.
 * 
 * @author Raffinat Patrick
*/
public class Operation extends org.javascool.proglets.plurialgo.langages.modele.Operation {
	
	public Operation() {
	}
	
	public void ecrire(Programme prog, StringBuffer buf, int indent) {
		Variable retour = (Variable) getRetour();
		Divers.indenter(buf, indent);
		Divers.ecrire(buf, "function ");
		Divers.ecrire(buf, nom);
		Divers.ecrire(buf, "(");
		for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Parametre> iter=parametres.iterator(); iter.hasNext();) {
			Parametre param = (Parametre) iter.next();
			param.ecrire(prog, buf);
			if (iter.hasNext()) Divers.ecrire(buf, ", ");
		}
		Divers.ecrire(buf, ")");	
		Divers.ecrire(buf, " { ");
		for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Variable> iter=variables.iterator(); iter.hasNext();) {
			Variable var = (Variable) iter.next();
			var.ecrire(prog, buf, indent+1);
		}
		if (isFonction()) {
			retour.ecrire(prog, buf, indent+1);
		}
		for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=instructions.iterator(); iter.hasNext();) {
			Instruction instr = (Instruction) iter.next();
			instr.ecrire(prog, buf, indent+1);
		}
		if (isFonction()) {
			Divers.ecrire(buf, "return " + retour.nom + ";", indent+1);
		}
		Divers.ecrire(buf, "} ", indent);
	}
	
	public void ecrire(org.javascool.proglets.plurialgo.langages.modele.Programme prog, StringBuffer buf, int indent) {
		this.ecrire((org.javascool.proglets.plurialgo.langages.javascript.Programme)prog, buf, indent);
	}
	
}
