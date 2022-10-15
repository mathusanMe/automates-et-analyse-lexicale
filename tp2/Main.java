import java.util.HashSet;
import java.util.Set;

public class Main{

	public static String a30() {
		String s = "";
		for (int i = 0; i < 30; i++) {
			s += "a";
		}
		return s;
	}

    public static void testAutomate0(){
		Etat[] etats = new Etat[3];
		etats[0] = new Etat(0,false);
		etats[1] = new Etat(1,false);
		etats[2] = new Etat(2,true);
		etats[0].ajouteTransition('a', etats[1]);
		etats[0].ajouteTransition('a', etats[2]);
		etats[1].ajouteTransition('a', etats[1]);
		etats[1].ajouteTransition('c', etats[1]);
		etats[1].ajouteTransition('b', etats[2]);
		Automate a = new Automate(etats[0]);
		System.out.println(a);

		String s = "aaab";
		System.out.println("Mot " + s + " accepté ? " + a.accepte(s));

		Set<Etat> s1 = new HashSet<Etat>();
		s1.add(etats[0]);
		Set<Etat> s2 = new HashSet<Etat>();
		s2.add(etats[1]);
		s2.add(etats[2]);
		Set<Etat> s3 = new HashSet<Etat>();
		s3.add(etats[2]);
		s3.add(etats[1]);
		s3.add(etats[2]);

		System.out.println(" s1 == s2 ? " + s1.equals(s2));
		System.out.println(" s2 == s3 ? " + s2.equals(s3));

		System.out.println();
    }

    public static void testAutomate1(){
		Etat[] etats = new Etat[11];
		int len = etats.length;
		etats[0] = new Etat(0, false);

		for (int i = 1; i < len; i++) {
			if (i == len - 1) {											// état 10
				etats[i] = new Etat(i, true);
			} else {													// état 1 à 9 inclus
				etats[i] = new Etat(i, false);
			}
			if (i > 1) {												// agir sur tous les états (2 à 10 inclus) sauf l'état 1
				etats[i - 1].ajouteTransition('a', etats[i]);
				etats[i - 1].ajouteTransition('b', etats[i]);
			}
		}

		// transitions à partir de l'état 0
		etats[0].ajouteTransition('a', etats[0]);
		etats[0].ajouteTransition('b', etats[0]);
		etats[0].ajouteTransition('a', etats[1]);

		Automate a = new Automate(etats[0]);
		System.out.println(a);

		System.out.println("Nombre de transitions : " + a.nombreTransitions());
		System.out.println("Alphabet : " + a.alphabet());
		System.out.println("Déterministe? " + a.estDeterministe());
		System.out.println("Accepte mot du sujet : " + a.accepte2(a30()));					// accepte le mot a^30
    }

    public static void testAutomate2(){
		Etat[] etats = new Etat[31];
		int len = etats.length;
		etats[0] = new Etat(0, false);
		etats[0].ajouteTransition('a', etats[0]);
		etats[0].ajouteTransition('b', etats[0]);

		for (int i = 1; i < len; i++) {
			if (i == len - 1) {
				etats[i] = new Etat(i, true);
			} else {
				etats[i] = new Etat(i, false);
			}
			etats[i].ajouteTransition('a', etats[i]);
			etats[i].ajouteTransition('b', etats[i]);
			etats[i - 1].ajouteTransition('a', etats[i]);
			etats[i - 1].ajouteTransition('b', etats[i]);
		}
		Automate a = new Automate(etats[0]);
		System.out.println(a);

		System.out.println("Nombre de transitions : " + a.nombreTransitions());
		System.out.println("Alphabet : " + a.alphabet());
		System.out.println("Déterministe? " + a.estDeterministe());
		System.out.println("Accepte mot du sujet : " + a.accepte2(a30()));					// semble tourner à l'infini mais le soucis est que ça prend beaucoup de temps mais il terminera
																							// due à un grand nombre de transitions et d'états
    }

	public static void testAutomate3(){
		Etat[] etats = new Etat[3];
		etats[0] = new Etat(0, false);
		etats[1] = new Etat(1, true);
		etats[2] = new Etat(2, false);
		etats[0].ajouteTransition('a', etats[1]);
		etats[0].ajouteTransition('b', etats[2]);

		Automate a = new Automate(etats[0]);
		System.out.println(a);

		System.out.println("Nombre de transitions : " + a.nombreTransitions());
		System.out.println("Alphabet : " + a.alphabet());
		System.out.println("Déterministe? " + a.estDeterministe());
		a.enleverEtatsCoAccessible();
		System.out.println(a);
    }

    public static void main(String[] args){
		// testAutomate0();
		// System.out.println("------------------");

		// testAutomate1();
		// System.out.println("------------------");

		// testAutomate2();
		// System.out.println("------------------");

		testAutomate3();
    }
}
