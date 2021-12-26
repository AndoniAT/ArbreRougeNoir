package arbre;


import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;


/**
 * Implantation de l'interface Collection bas√©e sur les arbres binaires de
 * recherche. Les √©l√©ments sont ordonn√©s soit en utilisant l'ordre naturel (cf
 * Comparable) soit avec un Comparator fourni √† la cr√©ation.
 * 
 * Certaines m√©thodes de AbstractCollection doivent √™tre surcharg√©es pour plus
 * d'efficacit√©.
 * 
 * @param <E> le type des cl√©s stock√©es dans l'arbre
 */
public class ABR<E> extends AbstractCollection<E> {
	private Noeud racine;
	private int taille;
	private Comparator<? super E> cmp;

	// ====================== ARBRE ======================

	// Consructeurs
		/**
		 * Cree un arbre vide. Les Elements sont ordonnes selon l'ordre naturel
		 */
		public ABR() {
			racine = null;
			taille = 0;
			//comp = (e1, e2) -> ((Comparable) e1).compareTo((Comparable) e2);
			
			cmp = new Comparator<E>() {
				public int compare(E e1, E e2) {
					return  ((Comparable<E>) e1).compareTo(e2);
				}
			};
		}
		
	/**
	 * Cree un arbre vide. Les Elemments sont compares selon l'ordre impose par
	 * le comparateur
	 * 
		 * @param cmp le comparateur utilise pour definir l'ordre des Elements
		 */
		public ABR(Comparator<? super E> cmp) {
			racine = null;
			taille = 0;
			
			this.cmp = cmp;
		}
	
	
		/**
		 * Constructeur par recopie. Creer un arbre qui contient les memes Elements
		 * que c. L'ordre des Elements est l'ordre naturel.
		 * 
		 * @param c
		 *            la collection ‡† copier
		 */
		public ABR(Collection<? extends E> c) {
			racine = null;
			taille = 0;
			//comp = (e1, e2) -> ((Comparable) e1).compareTo((Comparable) e2);
			
			cmp = new Comparator<E>() {
				public int compare(E e1, E e2) {
					return  ((Comparable<E>) e1).compareTo(e2);
				}
			};
			
			System.out.println("hola");
			//ABR<E> abr2 = new ABR<>();
			
			ABRIterator it = (ABR<E>.ABRIterator) c.iterator();
			
			System.out.println(it.noeudIterator.cle);
			
			while(it.noeudIterator.estFilsDroit() || it.noeudIterator.estFilsGauche()) {
				it.next();
			}
			
			add(it.noeudIterator.cle);
			
			addGauche(it.noeudIterator);
			addDroit(it.noeudIterator);
			
		}
		
		private void addGauche(Noeud n) {
			n = n.gauche;
			
			add(n.cle);
			
			if(n.ilyaFilsGauche())
				addGauche(n);
			
			if(n.ilyaFilsDroit())
				addDroit(n);
		}
		
		private void addDroit(Noeud n) {
			n = n.droit;
			add(n.cle);
			
			if(n.ilyaFilsGauche())
				addGauche(n);
			
			if(n.ilyaFilsDroit())
				addDroit(n);	
		}
		

		@Override
		public Iterator<E> iterator() {
			return new ABRIterator();
		}

		@Override
		public int size() {
			return taille;
		}

		@Override
		public boolean contains(Object o){
			ABRIterator abrIterator = (ABR<E>.ABRIterator) this.iterator();
			do {
				if(abrIterator.noeudIterator.cle.equals(o)) 
					return true;
				
				if (!abrIterator.hasNext())
					break;
				
				abrIterator.next();
				
			} while(abrIterator.hasPrec());
			 			
			return false;
		}
		
		// Quelques methodes utiles

		/**
		 * Recherche une cle. Cette methode peut etre utilise par
		 * {@link #contains(Object)} et {@link #remove(Object)}
		 * 
		 * @param o
		 *            la cle a† chercher
		 * @return le noeud qui contient la cle ou null si la cle n'est pas trouvee.
		 */
		private Noeud rechercher(Object o) {
			ABRIterator abrIterator = (ABR<E>.ABRIterator) this.iterator();
					
			do {
				if(abrIterator.noeudIterator.cle.equals(o)) 
				return abrIterator.noeudIterator;
				
				if (!abrIterator.hasNext())
				break;
					
				abrIterator.next();
					
			} while(abrIterator.hasPrec());
					 			
			return null;
		}

		/**
		 * Supprime le noeud z. Cette methode peut etre utilisee dans
		 * {@link #remove(Object)} et {@link Iterator#remove()}
		 * 
		 * @param z
		 *            le noeud a† supprimer
		 * @return le noeud contenant la cle qui suit celle de z dans l'ordre des
		 *         clÈes. Cette valeur de retour peut Ítre utile dans
		 *         {@link Iterator#remove()}
		 */
		private Noeud supprimer(Noeud z) {
			Noeud y = null;
			Noeud x = racine;
			
			if (z.gauche == null || z.droit == null)
			    y = z;
			  else
			    y = z.suivant();
			    // y est le núud ‡ dÈtacher

			  if (y.gauche != null)
			    x = y.gauche;
			  else
			    x = y.droit;
			    // x est le fils unique de y ou null si y n'a pas de fils

			  if (x != null) x.pere = y.pere;

			  if (y.pere == null) { // suppression de la racine
			    racine = x;
			  } else {
			    if (y == y.pere.gauche)
			      y.pere.gauche = x;
			    else
			      y.pere.droit = x;
			  }

			  if (y != z) z.cle = y.cle;
			  	y = null;
			  	
			  return null;
		}
	
	
		
		// Pour un "joli" affichage

		@Override
		public String toString() {
			StringBuffer buf = new StringBuffer();
			toString(racine, buf, "", maxStrLen(racine));
			return buf.toString();
		}

		private void toString(Noeud x, StringBuffer buf, String path, int len) {
			if (x == null)
				return;
			toString(x.droit, buf, path + "D", len);
			for (int i = 0; i < path.length(); i++) {
				for (int j = 0; j < len + 6; j++)
					buf.append(' ');
				char c = ' ';
				if (i == path.length() - 1)
					c = '+';
				else if (path.charAt(i) != path.charAt(i + 1))
					c = '|';
				buf.append(c);
			}
			buf.append("-- " + x.cle.toString());
			if (x.gauche != null || x.droit != null) {
				buf.append(" --");
				for (int j = x.cle.toString().length(); j < len; j++)
					buf.append('-');
				buf.append('|');
			}
			buf.append("\n");
			toString(x.gauche, buf, path + "G", len);
		}

		private int maxStrLen(Noeud x) {
			return x == null ? 0 : Math.max(x.cle.toString().length(),
					Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
		}

		// TODO : voir quelles autres methodes il faut surcharger
		@Override
		public boolean add(E e) {
			if(racine == null) {
				Noeud noeud = new Noeud(e);
				racine = noeud;
			} else {
				if (cmp.compare(e, racine.cle) < 0) {
					if(racine.gauche == null) {
						Noeud noeud = new Noeud(e);
						racine.gauche = noeud;
						noeud.pere = racine;
					} else {
						ABR abrtmp = new ABR<>();
						abrtmp.racine = racine.gauche;
						abrtmp.add(e);
					}
					
				} else if(e.equals(racine.cle)){
							System.out.println("Element " + e + " dÈj‡ existant (Non ajoutÈ)");
							return false;
					
						} else {
								if(racine.droit == null) {
									Noeud noeud = new Noeud(e);
									racine.droit= noeud;
									noeud.pere = racine;
								} else {
									ABR abrtmp = new ABR<>();
									abrtmp.racine = racine.droit;
									abrtmp.add(e);
							
								}
						
						}
			
			}
			
			this.taille = this.taille + 1;
			if(racine.cle.equals(5)) {
				System.out.println("taille " + taille);
			}
			
			return false;
			
		}
		
	// ============================================

		
		
	// ====================== NOEUD ======================
	
	private class Noeud {
		E cle;
		Noeud gauche;
		Noeud droit;
		Noeud pere;

		Noeud(E cle) {
			this.cle = cle;
			this.gauche = null;
			this.droit = null;
			this.pere = null;
		}

		/**
		 * Renvoie le noeud contenant la cle minimale du sous-arbre enracine
		 * dans ce noeud
		 * 
		 * @return le noeud contenant la cle minimale du sous-arbre enracine
		 *         dans ce noeud
		 */
		Noeud minimum() {
			if(ilyaFilsGauche()) {
				return this.gauche.minimum();
			} else {
				return this;
			}
			
		}

		/**
		 * Renvoie le successeur de ce noeud
		 * 
		 * @return le noeud contenant la cle qui suit la cle de ce noeud dans
		 *         l'ordre des cles, null si c'es le noeud contenant la plus
		 *         grande cle
		 */
		Noeud suivant() {
			
			if(ilyaFilsDroit()) {
				return this.droit.minimum();
			}
			
			if(!ilyaFilsDroit() && estFilsGauche()) {
				return this.pere;
			} 
			
			if(!ilyaFilsDroit() && estFilsDroit()) {
				if(this.pere.estRacine()) {
					System.out.println("Il n'y a pas d'element suivant");
					return this;
				}
				
				if (this.pere.estFilsGauche()) {
					return this.pere.pere;
				}
				
				if (this.pere.estFilsDroit()) {
					Noeud n = this.pere;
					while(!n.estFilsGauche()) n = n.pere;
					n = n.pere;
					return n;
				}
			}
			
			return null;
		}
		
		/**
		 * Renvoie le predecesseur de ce noeud
		 * 
		 * @return le noeud contenant la cle predecesseure de la cle de ce noeud dans
		 *         l'ordre des cles, null si c'es le noeud contenant la plus
		 *         grande cle
		 */
		Noeud precedent() {
			Noeud noeudRacine  = this;
			while(noeudRacine.pere != null) {
				noeudRacine = noeudRacine.pere;
			}

			if(noeudRacine.minimum().equals(this) && !this.estRacine()) {
				return this;
			}
			
			
			Noeud n = noeudRacine.minimum();
			while(!n.isPrecedent(this)) {
				n = n.suivant();
			}
			
			return n;
		}
		
		private boolean isPrecedent(Noeud n) {
			if(this.suivant() == n) {
				return true;
			} else {
				return false;
			}
		}
		
		
		private boolean estFilsGauche() {
			if(estRacine()) {
				return false;
			}
			
			if(this == this.pere.gauche) {
				return true;
			} else {
				return false;
			}
		}
		
		private boolean estFilsDroit() {
			if(estRacine()) {
				return false;
			}
			
			if(this == this.pere.droit) {
				return true;
			} else {
				return false;
			}
		}
		
		private boolean ilyaFilsDroit(){
			if(this.droit != null) {
				return true;
			} else {
				return false;
			}
		}
		
		private boolean ilyaFilsGauche(){
			if(gauche != null) {
				return true;
			} else {
				return false;
			}
		}
		
		private boolean estRacine() {
			if(pere == null) {
				return true;
			} else {
				return false;
			}
		}
		
		
	}
	
	// ============================================

	
	
	// ==================== ITERADOR ========================
	

	/**
	 * Les itreateurs doivent parcourir les Elements dans l'ordre ! Ceci peut se
	 * faire facilement en utilisant {@link Noeud#minimum()} et
	 * {@link Noeud#suivant()}
	 */
	private class ABRIterator implements Iterator<E> {
		
		private Noeud noeudIterator;
		private Noeud prec;
		
		public ABRIterator() {
			// Valider aussi si l'arbre est a null
			if(racine == null) {
				System.out.println("L'arbre est vide");
				noeudIterator = null;
			} else {
				noeudIterator = racine.minimum();
			}
			
		}
		
		public boolean hasNext() {

			
			if(noeudIterator.suivant() != noeudIterator) {
				return true;
			} else {
				return false;
			}
		}
		
		public boolean hasPrec() {
			if(noeudIterator.precedent() != noeudIterator) {
				return true;
			} else {
				return false;
			}
		}

		public E next() {
			// Valider aussi si est a null
			
			if(this.hasNext()) {
				
				prec = noeudIterator;
				noeudIterator = noeudIterator.suivant();
				System.out.println("Antes : " + prec.cle + "  - Despues : " + noeudIterator.cle);
			}
			return noeudIterator.cle;
		}
		
		// hay que usar una exepcion (next debe ser llamado primero IllegalStateException)
		public void remove() {
			Noeud noeudSupprimer = rechercher(this.noeudIterator.cle);
			supprimer(noeudSupprimer);
		}
		
		public Noeud getNoeud() {
			return noeudIterator;
		}
		
		public Noeud getNoeudPrecedent() {
			return prec;
		}
		
	}
	
	// ============================================

}
