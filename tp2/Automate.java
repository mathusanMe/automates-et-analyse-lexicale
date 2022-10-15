import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class Automate extends HashSet<Etat> {

    private Etat init;

    public Automate(Etat e) {
        super();
        this.init = e;
        this.initialiseAutomate(e);
    }

    @Override
    public String toString() {
        String s = "" + this.size() + " états; ";
        s += "état intial : " + init.getId()+ "\n\n";
        for (Etat e : this) {
            s += e.toString() + "\n";
        }
        return s;
    }

    // Retourne true si et seulement si e n'était pas déjà dans l'ensemble.
    boolean ajouteEtatSeul(Etat e) {
	    return this.add(e);
    }

    // Construit un automate à partir de l'ensemble des états accessibles
    // depuis l'état initial.
    // Retourne true si et seulement si l'état initial n'était pas déjà
    // présent dans l'automate.
    boolean initialiseAutomate(Etat e) {
        if (!ajouteEtatSeul(e))
            return false;
        for (char c : e.alphabet()) {
            for (Etat e1: e.succ(c))
            initialiseAutomate(e1);
        }
        return true;
    }

    // Retourne le nombre de transitions dans l'automate
    int nombreTransitions() {
        int nbTransitions = 0;
	    for (Etat e : this) {
            for (char c : e.alphabet()) {
                nbTransitions += e.succ(c).size();
            }
        }
	    return nbTransitions;
    }

    // Retourne l'ensemble des lettres utilisées dans l'automate.
    Set<Character> alphabet() {
        Set<Character> alphabetSet = new HashSet<Character>();
        for (Etat e : this) {
            alphabetSet.addAll(e.alphabet());
        }
	    return alphabetSet;
    }

    // Retourne true si et seulement si l'automate est complet
    boolean estComplet() {
	    // à écrire
	    return false;
    }

    // Pour true si et seulement si l'automate est déterministe
    boolean estDeterministe() {
        for (Etat e : this) {
            for (char c : e.alphabet()) {
                if (e.succ(c).size() > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    // premier algorithme d'acceptation d'un mot
    boolean accepte(String mot) {
      	return this.init.accepte(mot);
    }

    // algorithme plus efficace
    boolean accepte2(String mot) {
        Set<Etat> S = new HashSet<Etat>();
        S.add(init);
        for (int i = 0; i < mot.length(); i++) {
            Set<Etat> tmp = new HashSet<Etat>();
            char c = mot.charAt(i);
            for (Etat e : S) {
                Set<Etat> succTmp = e.succ(c);
                if (succTmp != null) {
                    tmp.addAll(succTmp);
                }
                
            }
            S = tmp;
        }
        for (Etat e : S) {
            if (e.estAcceptant()) {
                return true;
            }
        }
        return false;
    }

    boolean enleverEtatsCoAccessible(Etat e, Set<Etat> visite) {
        boolean boo = false;
        for (char c : e.alphabet()) {
            Set<Etat> tmp = e.succ(c);
            for (Etat ne : tmp) {
                if (!visite.contains(ne)) {
                    if (ne.estAcceptant()) {
                        boo = boo || true;
                    }
                    else {
                        visite.add(ne);
                        if (!enleverEtatsCoAccessible(ne, visite)) {
                            this.remove(ne);
                            e.removeTransition(c, ne);
                        } else {
                            return true;
                        }
                    }
                    // visite.remove(ne);           pas necessaire car l'omission permet d'ameliorer la complexite 
                }
            }
        }
        return boo;
    }

    // Pour enlever les états co-accessibles
    void enleverEtatsCoAccessible() {
        if (init == null) {
            return;
        }
        if (!enleverEtatsCoAccessible(init, new HashSet<Etat>())) {
            init = null;
            return;
        }
    }

    // Déterminisation
    Automate determinisation(){
        // à écrire
        return null;
    }
}
