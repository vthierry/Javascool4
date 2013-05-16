/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.python;

import java.util.Iterator;

import org.javascool.proglets.plurialgo.divers.Divers;


/**
 * Cette classe h�rite de la classe homonyme du mod�le.
*/
public class Si extends org.javascool.proglets.plurialgo.langages.modele.Si {
	
	public Si() {
	}

	public void ecrire(Programme prog, StringBuffer buf, int indent) {
		if (isSelon()) {
			if (isSinon()) {
				Divers.ecrire(buf, "default =>", indent);
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
				Divers.ecrire(buf, "break;", indent+1);
			}
			else if ( isSi() || isSinonSi() ) {
				String valeur = this.getValeurSelon();
				Divers.ecrire(buf, "case " + valeur + " :", indent);
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
				Divers.ecrire(buf, "break;", indent+1);
			}
		}
		else {
			if (isSi()) {
				Divers.ecrire(buf, "if " + this.getCondition() + " : ", indent);
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
			}
			if (isSinonSi()) {
				Divers.ecrire(buf, "elif " + this.getCondition() + " : ", indent);
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
			}
			if (isSinon()) {
				Divers.ecrire(buf, "else : ", indent);
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
			}
		}
	}
	
}
