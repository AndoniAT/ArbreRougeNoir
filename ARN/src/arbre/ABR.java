package arbre;


import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;



/**
 * Implantation de l'interface Collection basÃ©e sur les arbres binaires de
 * recherche. Les Ã©lÃ©ments sont ordonnÃ©s soit en utilisant l'ordre naturel (cf
 * Comparable) soit avec un Comparator fourni Ã  la crÃ©ation.
 * 
 * Certaines mÃ©thodes de AbstractCollection doivent Ãªtre surchargÃ©es pour plus
 * d'efficacitÃ©.
 * 
 * @param <E> le type des clÃ©s stockÃ©es dans l'arbre
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
			racine = sentinelle;
			taille = 0;
			
			this.cmp = cmp;
		}
	
	
		/**
		 * Constructeur par recopie. Creer un arbre qui contient les memes Elements
		 * que c. L'ordre des Elements est l'ordre naturel.
		 * 
		 * @param c
		 *            la collection à  copier
		 */
		public ABR(Collection<? extends E> c) {
			racine = sentinelle;
			taille = 0;
			//comp = (e1, e2) -> ((Comparable) e1).compareTo((Comparable) e2);
			
			cmp = new Comparator<E>() {
				public int compare(E e1, E e2) {
					return  ((Comparable<E>) e1).compareTo(e2);
				}
			};
			
			//ABR<E> abr2 = new ABR<>();
			
			ABRIterator it = (ABR<E>.ABRIterator) c.iterator();
			
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
		 *            la cle a  chercher
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
				System.out.println("next : " + abrIterator.noeudIterator.cle);
					
			} while(abrIterator.hasPrec());
					 			
			return null;
		}

		/**
		 * Supprime le noeud z. Cette methode peut etre utilisee dans
		 * {@link #remove(Object)} et {@link Iterator#remove()}
		 * 
		 * @param z
		 *            le noeud a  supprimer
		 * @return le noeud contenant la cle qui suit celle de z dans l'ordre des
		 *         clées. Cette valeur de retour peut être utile dans
		 *         {@link Iterator#remove()}
		 */
		private Noeud supprimer(Noeud z) {
			  if (z.gauche.estSentinelle() || z.droit.estSentinelle()) { // si z a au moins une sentinnelle
				  y = z;												// y est le noeud z
			  } else {
				  y = z.suivant();				  						// y est le nœud à détacher 
			  }
			  if (!y.gauche.estSentinelle()) {							// Si le gauche de y n'est pas la sentinelle
				  x = y.gauche;											 // x sera le gauche de y		
			  } else {
				  x = y.droit; 				      						// x fils  unique de y ou la sentinelle si y n'a pas de fils
			  }
				    
			  x.pere = y.pere;											// le pere de z sera le pere de y

			  if (y.estRacine()) { 										// suppression de la racine
				    racine = x;											// la racien sera x
				  } else {
				    if (y.estFilsGauche()) {							// si y est gauche
				    	y.pere.gauche = x;							   // le gauche du pere est x
				    }
				    else {
				    	y.pere.droit = x;								// le droit du pere est x
				    }
				      
				  }

				  if (y != z) {										 // si y et z sont diff
					  z.cle = y.cle;								// On remplace les clés et le noeud a supprimer sera y et pas z
				  }
				  
				  if (!y.estRouge()) {								// si y est noir
					  supprimerCorrection(x);						// faire une correction
				  }
				  	
				  y = null;											// On peut supprimer y 
				  taille = taille - 1;								// noeud supprimer, mettre a jour notre compteur
				  System.out.println(taille);
				  return null;

				  
		}
		
		public void supprimerCorrection(Noeud x) {
			 Noeud w;
			  while (x != racine && x.couleur == N) {
			    // (*) est vérifié ici
			    if (x == x.pere.gauche) {
			      w = x.pere.droit; // le frère de x
			      if (w.couleur == R) {
			        // cas 1
			        w.couleur = N;
			        x.pere.couleur = R;
			        rotationGauche(x.pere);
			        w = x.pere.droit;
			      }
			      if (w.gauche.couleur == N && w.droit.couleur == N) {
			        // cas 2
			        w.couleur = R;
			        x = x.pere;
			      } else {
			        if (w.droit.couleur == N) {
			          // cas 3
			          w.gauche.couleur = N;
			          w.couleur = R;
			          
			          rotationDroite(w);
			          w = x.pere.droit;
			        }
			        // cas 4
			        w.couleur = x.pere.couleur;
			        x.pere.couleur = N;
			        w.droit.couleur = N;
			        rotationGauche(x.pere);
			        x = racine;
			      }
			    } else {
			      // idem en miroir, gauche <-> droite
			      // cas 1', 2', 3', 4'
			    	
			    	 w = x.pere.gauche; // le frère de x
				      if (w.couleur == R) {
				        // cas 1
				    	System.out.println("este" + w.cle);
				        w.couleur = N;
				        x.pere.couleur = R;
				        rotationDroite(x.pere);
				        w = x.pere.gauche;
				      }
				      if (w.droit.couleur == N && w.gauche.couleur == N) {
				        // cas 2
				        w.couleur = R;
				        x = x.pere;
				      } else {
				        if (w.gauche.couleur == N) {
				          // cas 3
				          w.droit.couleur = N;
				          w.couleur = R;
				          rotationGauche(w);
				          w = x.pere.gauche;
				        }
				        // cas 4
				        w.couleur = x.pere.couleur;
				        x.pere.couleur = N;
				        w.gauche.couleur = N;
				        rotationDroite(x.pere);
				        x = racine;
				      }
			    }
			  }
			  // (**) est vérifié ici
			  x.couleur = N;
			}

		
	
	
		
		// Pour un "joli" affichage

		@Override
		public String toString() {
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
			System.out.println("Ajouter : " + e);
			while (x.cle != null) {
			    y = x;
			    //System.out.println("soy x = " + x.cle);
			    x = cmp.compare(z.cle, x.cle) < 0 ? x.gauche : x.droit;
			    //System.out.println("soy x = " + x.cle);
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
			  
			  taille++;
			  return false;
			
		}
		
		
		
		public void ajouterCorrection(Noeud z) {	
			while (z.pere.estRouge()) { //  // tant que le père est rouge
			     
				// Z rouge e son père rouge, nous avons donce Propriété violée : 4 : deux noeuds rouges
			    
				if (z.pere.estFilsGauche()) {       // Si le pere est un fils gauche
			      y = z.pere.pere.droit;            // y est l'oncle de z 
			      
			      if (y.estRouge()) {            // Si sa couleur est rouge
			        // cas 1
			        z.pere.couleur = N;            // Changer la couleur du pere de z a noire
			        y.couleur = N;                 // Et celle du y aussi
			        z.pere.pere.couleur = R;       // La couleur de grand pere de z sera rouge
			        z = z.pere.pere;               // z devient son oncle
			      } else {                         //
			        if (z.estFilsDroit()) {       // Si z estFils Droit 
			        	
			          // cas 2
			          z = z.pere;					// z devient sont père 					
			          rotationGauche(z);		    // Et on fait la rotation 
			        } 
				        // cas 3
				        z.pere.couleur = N;			 // La couleur du père est noir
				        z.pere.pere.couleur = R;     // La couleur du grand père est rouge
				        rotationDroite(z.pere.pere); // Une fois que l'on a changé les couleurs nous pouvons faire la rotation
			      }
			    } else {
			      // même chose mais à droit
			      // On verifie les mêmes cas
			    	 y = z.pere.pere.gauche;          // l'oncle de z
				      if (y.estRouge()) {			 // On verifie si y est rouge
				        
				    	// cas 1
				        z.pere.couleur = N;            // La nouvelle couleur du père de z est noir
				        y.couleur = N;				  // le nouvelle couleur de y est noir
				        z.pere.pere.couleur = R;	 // La couleur du gran père devient rouge
				        z = z.pere.pere;			 // z est desormais son gran père
				      } else {
				        if (z.estFilsGauche()) {    // si z est gauche 
				          
				          // cas 2
				          z = z.pere;  				// z est le pere
				          rotationDroite(z);		// et on fait une rotation droite
				        }
				        	// Si z n'est pas fils gauche ou une fois que l'on a fait la roation droite correspondant
				        	// on peut changer les couleurs
					        // cas 3
					        z.pere.couleur = N;         // la couleur du pere de z est noire
					        z.pere.pere.couleur = R;    // et la couleur du grand pere de z est rouge
					        rotationGauche(z.pere.pere); // On peur donc faire une rotation a gauche en envoyant le grand père de z
				        
				      }
			    	
			    }
			  }
			  // Popriété violée : 2
			  
			racine.couleur = N; // La racine doit être tjrs noire
		}
		
		
		private boolean rotationDroite(Noeud noeud) {
			System.out.println("D");
			Noeud y = noeud.gauche;                   // y prend la le noeud qui es a gauche du noeud envoyé
			   noeud.gauche = y.droit;                // le noueveau gauche de noeud c'est le fils droit de y
			   
			   if(!y.droit.estSentinelle()) {              // Nous verifions que le droit n'est pas la sentinnelle  
				   y.droit.pere = noeud;              // on ne change pas le pere de la sentinelle
			   }
			   
			   y.pere = noeud.pere;                   // le pere de y est desormais le pere du noeud
			   
			   if (noeud.pere.estSentinelle()) {          // Nous verifions que noeud est la recine : si le de du noeud n'est pas la sentinnelle           
				   racine = y;					 	  // Si c'est le cas la nouvelle racine sera y
			   
			   } else if( noeud.estFilsDroit()) {     //Si le noeud est fils droit
			    	  noeud.pere.droit = y;  		  // le nouvele droit du pere sera y     
			      }else {
			    	  noeud.pere.gauche = y;  	      // Sinon le nouvel gauche du père sera y
			      }
			   
			   y.droit = noeud;  					  // le droit du y est le noeud
			   noeud.pere = y;						  // le nouveau pere de noeud est y et la rotation est complète
			return false;
		}
		
		private boolean rotationGauche(Noeud noeud) {
			System.out.println("G");
			Noeud y = noeud.droit; 							//  y est le droit du noeud envoyé						                     
			noeud.droit = y.gauche;						    // le nouveau droit du noeud est le gauche de y
			
			if(!y.gauche.estSentinelle()) {						// On verifie si le gauche de y n'est pas la sentinelle 
				y.gauche.pere = noeud; 						// on ne change pas le parent de la sentinelle
			}
			 y.pere = noeud.pere;							// Apres le nouveau père de y est le père du noeud
			   if (noeud.estRacine()) { 				    // On verifie si noeud est la racine
				   racine = y;								// Si c'est le cas la nouvelle racine est le noeud y
			   }
			   else if( noeud.estFilsGauche()) {            // si le noeud est un fils gauche 
			    	  noeud.pere.gauche = y;				// le nouveu gauche du noeud est y
			      }
			      else {									// S'il n'est pas un fils gauche
			    	  noeud.pere.droit = y;					// le nouveau droit du père est y
			      }
			   y.gauche = noeud;							// le nouveau gauche de y est le noeud
			   noeud.pere = y;								// le nouveau pere de noeud est y
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
			while(noeudRacine.pere.cle != null) {
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
		
		public boolean estRouge() {
			if(couleur == R) {
				return true;
			} else {
				return false;
			}
		}
		
		public boolean estSentinelle() {
			if(cle == null) {
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
				System.out.println("Avant : " + prec.cle + "  - Apres : " + noeudIterator.cle);
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
