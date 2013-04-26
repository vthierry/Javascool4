/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.modele;

import java.util.ArrayList;
import java.util.Iterator;

import org.javascool.proglets.plurialgo.divers.Divers;



/**
 * Cette classe doit �tre �tendue pour chaque langage de programmation implant�.
*/
public class TantQue extends Noeud {
	
	// variables utilis�es par BindingCastor
	/**
	 * La condition de la boucle.
	 */
	public String condition;
	/**
	 * Instructions internes � la boucle.
	 */
	public ArrayList<Instruction> instructions;
	// autres variables
	/**
	 * Bient�t obsol�te ?
	 */
	public String schema;	
	
	public TantQue() {
		instructions = new ArrayList<Instruction>();
	}
	
	// ---------------------------------------------
	// Noeuds
	// ---------------------------------------------
	
	public final void parcoursEnfants() {
		for(Iterator<Instruction> iter=instructions.iterator(); iter.hasNext(); ) {
			Noeud nd = (Noeud) iter.next();
			nd.parent = this;
			nd.parcoursEnfants();
		}
	}
	
	// ---------------------------------------------
	// Autres fonctions
	// ---------------------------------------------

	public final boolean isTantQue() {
		return "tantque".equals(schema);
	}
	
	public String getCondition() {
		if (condition==null) return "";
		if (!condition.trim().startsWith("(")) return "("+condition+")"; 
		return condition;
	}
	
	// ---------------------------------------------
	// Xml
	// ---------------------------------------------
	
	void ecrireXml(StringBuffer buf, int indent) {
		Divers.ecrire(buf, "<tantque", indent);
		Divers.ecrireAttrXml(buf, "condition", condition);
		Divers.ecrire(buf, ">");
		for (Iterator<Instruction> iter=instructions.iterator(); iter.hasNext();) {
			Instruction instr = iter.next();
			instr.ecrireXml(buf, indent+1);
		}
		Divers.ecrire(buf, "</tantque>", indent);
	}

}
