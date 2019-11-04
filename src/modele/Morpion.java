package fr.guehenneux.tutorial.morpion.modele;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import fr.guehenneux.tutorial.morpion.presentation.PresentationMorpion;

/**
 * Represente un morpion. Un morpion est une grille de 3x3 cases.
 * 
 * @author guehenneux
 * 
 */
public class Morpion {

	/**
	 * generateur pseudo-aleatoire permettant a l'ordinateur de choisir une case
	 * parmi les meilleures
	 */
	private static final Random RANDOM = new Random();

	/**
	 * la grille contient les 9 cases du morpion dans un tableau 3x3
	 */
	private Case[][] grille;

	/**
	 * le joueur humain
	 */
	private Joueur humain;

	/**
	 * le joueur ordinateur
	 */
	private Joueur ordinateur;

	/**
	 * compteur du nombre de cases jouees, ce compteur est mis a jour apres
	 * chaque coup, il permet de savoir facilement quand la grille est pleine
	 */
	private int nombreCasesJouees;

	/**
	 * presentation du morpion (panneau contenant 9 cases)
	 */
	public PresentationMorpion presentation;

	/**
	 * Cree un nouveau morpion 3x3 vide.
	 * 
	 * @param ordinateurCommence
	 *            indique si l'ordinateur doit commencer
	 */
	public Morpion(boolean ordinateurCommence) {

		/*
		 * on initialise la grille et on cree les 9 cases
		 */

		grille = new Case[3][3];

		for (int x = 0; x < 3; x++) {

			for (int y = 0; y < 3; y++) {

				grille[x][y] = new Case(this);

			}

		}

		/*
		 * on cree les deux joueurs
		 */

		humain = new Joueur("X", Color.BLUE);
		ordinateur = new Joueur("O", Color.RED);

		/*
		 * on initialise le nombre de cases jouees
		 */

		nombreCasesJouees = 0;

		/*
		 * on cree la presentation
		 */

		presentation = new PresentationMorpion(this);

		if (ordinateurCommence) {
			jouerOrdinateur();
		}

	}

	/**
	 * 
	 * @param x
	 *            abscisse de la case de la gauche vers la droite (0, 1 ou 2)
	 * @param y
	 *            ordonnee de la cas du haut vers le bas (0, 1 ou 2)
	 * @return la case situee aux coordonnees donnees
	 */
	public Case getCase(int x, int y) {
		return grille[x][y];
	}

	/**
	 * Cette methode permet de valider le coup d'un humain, elle declenche
	 * automatiquement la replique de l'ordinateur si la partie n'est pas
	 * terminee par le coup de l'humain.
	 * 
	 * @param caseJouee
	 *            case jouee par le joueur humain
	 */
	public void jouerHumain(Case caseJouee) {

		/*
		 * on n'autorise pas l'humain a jouer si la partie est terminee
		 */

		if (!partieFinie()) {

			/*
			 * en enregistre que l'humain a joue dans cette case
			 */

			caseJouee.jouerDefinitivement(humain);
			nombreCasesJouees++;

			/*
			 * si la partie n'est pas terminee, on fait jouer l'ordinateur
			 */

			if (!partieFinie()) {
				jouerOrdinateur();
			}

		}

	}

	/**
	 * 
	 * @return true si la partie est finie, false sinon
	 */
	private boolean partieFinie() {
		return getVainqueur() != null || nombreCasesJouees == 9;
	}

	/**
	 * fait jouer l'ordinateur
	 */
	private void jouerOrdinateur() {

		/*
		 * on recupere la meilleure case a jouer pour l'ordinateur
		 */

		Case meilleureCase = getMeilleureCase();

		/*
		 * on joue cette case
		 */

		meilleureCase.jouerDefinitivement(ordinateur);
		nombreCasesJouees++;

	}

	/**
	 * 
	 * @param joueur
	 *            joueur dont on cherche a evaluer la position
	 * @return l'evaluation de la position actuelle pour le joueur (1 si la
	 *         position est gagnante, 0 si la position est nulle, -1 si la
	 *         position est perdante)
	 */
	private int evaluation(Joueur joueur) {

		Joueur vainqueur = getVainqueur();

		int evaluation;

		if (vainqueur == joueur) {
			evaluation = 1;
		} else if (vainqueur == null) {
			evaluation = 0;
		} else {
			evaluation = -1;
		}

		return evaluation;

	}

	/**
	 * 
	 * @param joueur
	 *            le joueur venant de jouer
	 * @return le joueur adverse
	 */
	private Joueur getAdversaire(Joueur joueur) {

		Joueur adversaire;

		if (joueur == ordinateur) {
			adversaire = humain;
		} else {
			adversaire = ordinateur;
		}

		return adversaire;

	}

	/**
	 * 
	 * @return la meilleure case a jouer pour l'ordinateur
	 */
	private Case getMeilleureCase() {

		Case caseTestee;

		/*
		 * liste des meilleures cases
		 */

		List<Case> meilleuresCases = new LinkedList<Case>();

		int evaluationCaseTestee;
		int evaluationMeilleuresCases = Integer.MIN_VALUE;

		/*
		 * on etablit la liste des meilleures cases en testant chaque case
		 */

		for (int x = 0; x < 3; x++) {

			for (int y = 0; y < 3; y++) {

				caseTestee = grille[x][y];

				if (caseTestee.estLibre()) {

					/*
					 * si la case est libre, on la joue (provisoirement, juste
					 * pour evaluer la position)
					 */

					caseTestee.jouerProvisoirement(ordinateur);
					nombreCasesJouees++;

					/*
					 * on evalue la position que ce coup nous donne
					 */

					evaluationCaseTestee = negamax(ordinateur);

					if (evaluationCaseTestee > evaluationMeilleuresCases) {

						/*
						 * si la position est meilleure que toutes celles
						 * obtenues avant, on l'enregistre
						 */

						meilleuresCases.clear();
						meilleuresCases.add(caseTestee);
						evaluationMeilleuresCases = evaluationCaseTestee;

					} else if (evaluationCaseTestee == evaluationMeilleuresCases) {

						/*
						 * si la position est de valeur egale aux meilleures
						 * obtenues avant, on l'ajoute a la liste
						 */

						meilleuresCases.add(caseTestee);

					}

					/*
					 * on annule le coup (il avait ete joue provisoirement afin
					 * d'evaluer la position)
					 */

					caseTestee.annuler();
					nombreCasesJouees--;

				}

			}

		}

		/*
		 * on choisit une case au hasard parmi les meilleures
		 */

		Case meilleureCase = meilleuresCases.get(RANDOM.nextInt(meilleuresCases
				.size()));

		return meilleureCase;

	}

	/**
	 * @param joueur
	 *            joueur dont on souhaite evaluer la position
	 * @return l'evaluation de la position du joueur, calculee recursivement par
	 *         l'algorithme negamax (variante de minimax)
	 */
	private int negamax(Joueur joueur) {

		int negamax;

		if (partieFinie()) {

			/*
			 * la position est finale, car soit le joueur a perdu, soit il a
			 * gagne, soit la grille est pleine : on donne donc l'evaluation
			 * directe : gagnante, perdante ou nulle
			 */

			negamax = evaluation(joueur);

		} else {

			/*
			 * la partie n'est pas terminee : on teste les differentes
			 * possibilites pour l'adversaire
			 */

			Joueur adversaire = getAdversaire(joueur);

			/*
			 * on suppose que l'adversaire va choisir le coup qui lui donne la
			 * meilleure position
			 */

			Case caseTestee;

			int negamaxCase;
			int negamaxAdversaire = Integer.MIN_VALUE;

			for (int x = 0; x < 3; x++) {

				for (int y = 0; y < 3; y++) {

					caseTestee = grille[x][y];

					if (caseTestee.estLibre()) {

						caseTestee.jouerProvisoirement(adversaire);
						nombreCasesJouees++;

						negamaxCase = negamax(adversaire);

						caseTestee.annuler();
						nombreCasesJouees--;

						if (negamaxCase > negamaxAdversaire) {
							negamaxAdversaire = negamaxCase;
						}

					}

				}

			}

			/*
			 * la valeur de la position pour un joueur est l'oppose de la valeur
			 * de la position de son adversaire
			 */

			negamax = -negamaxAdversaire;

		}

		return negamax;

	}

	/**
	 * 
	 * @return la joueur vainqueur (c'est-a-dire le joueur ayant realise un
	 *         alignement de 3 cases) ou bien null si aucun joueur n'a realise
	 *         un alignement de 3 cases
	 */
	private Joueur getVainqueur() {

		Joueur joueur;

		/*
		 * on teste d'abord les 3 alignements verticaux, de la gauche vers la
		 * droite
		 */

		for (int x = 0; x < 3; x++) {

			/*
			 * un joueur a realise un alignement vertical s'il a joue dans les 3
			 * cases de la colonne
			 */

			/*
			 * on recupere le joueur qui a joue dans la case en haut de la
			 * colonne
			 */

			joueur = grille[x][0].joueur;

			/*
			 * si ce joueur vaut null, c'est que personne n'a encore joue dans
			 * la case en haut de la colonne
			 */

			if (joueur != null && grille[x][1].joueur == joueur
					&& grille[x][2].joueur == joueur) {

				/*
				 * si le joueur a joue aussi dans les 2 autres cases de la
				 * colonne il a realise un alignement de 3 cases, c'est donc lui
				 * le vainqueur
				 */

				return joueur;

			}

		}

		/*
		 * on teste ensuite les 3 alignements horizontaux, du haut vers le bas
		 */

		for (int y = 0; y < 3; y++) {

			/*
			 * un joueur a realise un alignement horizontal s'il a joue dans les
			 * 3 cases de la ligne
			 */

			/*
			 * on recupere le joueur qui a joue dans la case a gauche de la
			 * ligne
			 */

			joueur = grille[0][y].joueur;

			/*
			 * si ce joueur vaut null, c'est que personne n'a encore joue dans
			 * la case a gauche de la ligne
			 */

			if (joueur != null && grille[1][y].joueur == joueur
					&& grille[2][y].joueur == joueur) {

				/*
				 * si le joueur a joue aussi dans les 2 autres cases de la
				 * lignes il a realise un alignement de 3 cases, c'est donc lui
				 * le vainqueur
				 */

				return joueur;

			}

		}

		/*
		 * on teste de la meme facon la diagonale qui part de la case en haut a
		 * gauche vers la case en bas a droite
		 */

		joueur = grille[0][0].joueur;

		if (joueur != null && grille[1][1].joueur == joueur
				&& grille[2][2].joueur == joueur) {

			return joueur;

		}

		/*
		 * on teste enfin, toujours de la meme facon, la diagonale qui part de
		 * la case en bas a gauche vers la case en haut a droite
		 */

		joueur = grille[0][2].joueur;

		if (joueur != null && grille[1][1].joueur == joueur
				&& grille[2][0].joueur == joueur) {

			return joueur;

		}

		/*
		 * si aucun joueur gagnant n'a ete trouve sur les 8 alignements
		 * possibles, on renvoit null
		 */

		return null;

	}

}