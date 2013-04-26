package org.javascool.proglets.plurialgo.langages.modele;

import org.javascool.proglets.plurialgo.divers.Divers;

/**
 * Cette classe doit �tre �tendue pour chaque langage de programmation implant�.
*/
public class Variable extends InfoTypee {
	
	public Variable() {
	}
	
	void ecrireXml(StringBuffer buf, int indent) {
		Divers.ecrire(buf, "<variable", indent);
		Divers.ecrireAttrXml(buf, "nom", nom);
		Divers.ecrireAttrXml(buf, "type", type);
		Divers.ecrire(buf, "/>");
	}
	
	void ecrireRetourXml(StringBuffer buf, int indent) {
		Divers.ecrire(buf, "<return", indent);
		Divers.ecrireAttrXml(buf, "nom", nom);
		Divers.ecrireAttrXml(buf, "type", type);
		Divers.ecrire(buf, "/>");
	}
	
	void ecrireProprieteXml(StringBuffer buf, int indent) {
		Divers.ecrire(buf, "<propriete", indent);
		Divers.ecrireAttrXml(buf, "nom", nom);
		Divers.ecrireAttrXml(buf, "type", type);
		Divers.ecrire(buf, "/>");
	}
}
