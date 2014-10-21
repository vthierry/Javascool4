/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.carmetal;

import java.util.Iterator;
import org.javascool.proglets.plurialgo.divers.Divers;
import org.javascool.proglets.plurialgo.langages.modele.*;


/**
 * Cette classe hérite de la classe homonyme du modèle.
*/
public class Si extends ModeleSi {
	
	public Si() {
	}

	public void ecrire(Programme prog, StringBuffer buf, int indent) {
		if (isSelon()) {
			if (isSinon()) {
				Divers.ecrire(buf, "default :", indent);
				for (Iterator<ModeleInstruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
				Divers.ecrire(buf, "break;", indent+1);
			}
			else if ( isSi() || isSinonSi() ) {
				String valeur = this.getValeurSelon();
				Divers.ecrire(buf, "case " + valeur + " :", indent);
				for (Iterator<ModeleInstruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
				Divers.ecrire(buf, "break;", indent+1);
			}
		}
		else {
			if (isSi()) {
				Divers.ecrire(buf, "if " + this.getCondition() + " {", indent);
				for (Iterator<ModeleInstruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
				Divers.ecrire(buf, "}", indent);
			}
			if (isSinonSi()) {
				Divers.ecrire(buf, "else if " + this.getCondition() + " {", indent);
				for (Iterator<ModeleInstruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
				Divers.ecrire(buf, "}", indent);
			}
			if (isSinon()) {
				Divers.ecrire(buf, "else {", indent);
				for (Iterator<ModeleInstruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
				Divers.ecrire(buf, "}", indent);
			}
		}
	}
	
}
