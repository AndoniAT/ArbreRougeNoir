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
	private static String R = "rouge";
	private static String N = "noir";
	private Noeud sentinelle = new Noeud();
	private Noeud x;
	private Noeud y;

	// ====================== ARBRE ======================

	// Consructeurs
		/**
		 * Cree un arbre vide. Les Elements sont ordonnes selon l'ordre naturel
		 */
		public ABR() {
			racine = sentinelle;
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
			
			//ABR<E> abr2 = new ABR<>();
			
			ABRIterator it = (ABR<E>.ABRIterator) c.iterator();
			
			System.out.println(it.noeudIterator.cle);
			
			while(it.noeudIterator.pere.cle != null) {
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
			System.out.println("racineee " + racine.cle);
			StringBuffer buf = new StringBuffer();
			toString(racine, buf, "", maxStrLen(racine));
			return buf.toString();
		}

		private void toString(Noeud x, StringBuffer buf, String path, int len) {
			if (x == null || x.cle == null) 
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
			buf.append("-- " + x.cle.toString() + " " + x.couleur);
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
			if(x == sentinelle || x.cle == null || x == null) {
				return 0;
			} else {
				return Math.max(x.cle.toString().length(),
						Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
			}
						
		}

		// TODO : voir quelles autres methodes il faut surcharger
		@Override
		public boolean add(E e) {
			Noeud z = new Noeud(e);
			
			y = sentinelle;
			x = racine;
			
			while (x.cle != null) {
			    y = x;
			    x = cmp.compare(z.cle, racine.cle) < 0 ? x.gauche : x.droit;
			  }
			  z.pere = y;
			  if (y.cle == null) { // arbre vide
			    racine = z;
			  } else {
			    if (cmp.compare(z.cle, y.cle) < 0)
			      y.gauche = z;
			    else
			      y.droit = z;
			  }
			  
			  z.gauche = z.droit = sentinelle;
			  z.couleur = R;
			  ajouterCorrection(z);
			  
			  return false;
			
		}
		
		
		public void ajouterCorrection(Noeud z) {	
			while (z.pere.couleur == R) {
			    // (*) La seule propriÈtÈ RN violÈe est (4) : z et z.pere sont rouges
			    if (z.pere == z.pere.pere.gauche) {
			      y = z.pere.pere.droit; // l'oncle de z
			      if (y.couleur == R) {
			        // cas 1
			        z.pere.couleur = N;
			        y.couleur = N;
			        z.pere.pere.couleur = R;
			        z = z.pere.pere;
			      } else {
			        if (z == z.pere.droit) {
			          // cas 2
			          z = z.pere;
			          rotationGauche(z);
			        } //else if (z == z.pere.gauche) {
				        // cas 3
				        z.pere.couleur = N;
				        z.pere.pere.couleur = R;
				        rotationDroite(z.pere.pere);
			        //}
			        
			      }
			    } else {
			      // idem en miroir, gauche <-> droite
			      // cas 1', 2', 3'
			    	 y = z.pere.pere.gauche; // l'oncle de z
				      if (y.couleur == R) {
				        // cas 1
				        z.pere.couleur = N;
				        y.couleur = N;
				        z.pere.pere.couleur = R;
				        z = z.pere.pere;
				      } else {
				        if (z == z.pere.gauche) {
				          // cas 2
				          z = z.pere;
				          rotationDroite(z);
				        } //else if (z == z.pere.gauche) {
					        // cas 3
					        z.pere.couleur = N;
					        z.pere.pere.couleur = R;
					        rotationGauche(z.pere.pere);
				        //}
				        
				      }
			    	
			    }
			  }
			  // (**) La seule propriÈtÈ (potentiellement) violÈe est (2)
			  racine.couleur = N;
		}
		
		
		private boolean rotationDroite(Noeud noeud) {
			Noeud y = noeud.gauche;
			   noeud.gauche = y.droit;
			   if(y.droit != null) y.droit.pere = noeud; // on ne change pas le pere de la sentinelle
			   y.pere = noeud.pere;
			   if (noeud.pere.cle == null) racine = y;
			   else
			      if( noeud.pere.droit == noeud) noeud.pere.droit = y;
			      else noeud.pere.gauche = y;
			   y.droit = noeud;
			   noeud.pere = y;
			return false;
		}
		
		private boolean rotationGauche(Noeud noeud) {
			Noeud y = noeud.droit;
			noeud.droit = y.gauche;
			if(y.gauche.cle != null) y.gauche.pere = noeud;// on ne change pas le parent de la sentinelle
			   y.pere = noeud.pere;
			   if (noeud.pere.cle == null) racine = y;
			   else
			      if( noeud.pere.gauche == noeud ) noeud.pere.gauche = y;
			      else noeud.pere.droit = y;
			   y.gauche = noeud;
			   noeud.pere = y;
			return false;
		}
	// ============================================

		
		
	// ====================== NOEUD ======================
	
	private class Noeud {
		E cle;
		Noeud gauche;
		Noeud droit;
		Noeud pere;
		String couleur;

		Noeud(E cle) {
			this.cle = cle;
			this.gauche = sentinelle;
			this.droit = sentinelle;
			this.pere = sentinelle;
			this.couleur = R;
		}
		
		Noeud() {
			this.gauche = null;
			this.droit = null;
			this.pere = null;
			this.couleur = N;
		}

		/**
		 * Renvoie le noeud contenant la cle minimale du sous-arbre enracine
		 * dans ce noeud
		 * 
		 * @return le noeud contenant la cle minimale du sous-arbre enracine
		 *         dans ce noeud
		 */
		Noeud minimum() {
			if(ilyaFilsGauche() && this.gauche.cle != null) {
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
				return true;
			}
			
			if(this == this.pere.gauche) {
				return true;
			} else {
				return false;
			}
		}
		
		private boolean estFilsDroit() {
			if(estRacine()) {
				return true;
			}
			
			if(this == this.pere.droit) {
				return true;
			} else {
				return false;
			}
		}
		
		private boolean ilyaFilsDroit(){
			if(this.droit.cle != null) {
				return true;
			} else {
				return false;
			}
		}
		
		private boolean ilyaFilsGauche(){
			if(this.gauche.cle != null) {
				return true;
			} else {
				return false;
			}
		}
		
		private boolean estRacine() {
			if(pere.cle == null) {
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
			if(noeudIterator.suivant() != noeudIterator && noeudIterator.suivant().cle != null) {
				return true;
			} else {
				return false;
			}
		}
		
		public boolean hasPrec() {
			if(noeudIterator.precedent() != noeudIterator && noeudIterator.precedent().cle != null) {
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
				//System.out.println("Antes : " + prec.cle + "  - Despues : " + noeudIterator.cle);
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
