package fr.guehenneux.tutorial.morpion.modele;

import java.awt.Color;

/**
 * Represente un joueur de morpion.
 * 
 * @author guehenneux
 * 
 */
public class Joueur {

	/**
	 * symbole associe au joueur (en general X ou O)
	 */
	public String symbole;

	/**
	 * couleur associee au joueur (par exemple rouge ou bleu)
	 */
	public Color couleur;

	/**
	 * 
	 * @param symbole
	 *            symbole associe au joueur (en general X ou O)
	 * @param couleur
	 *            couleur associee au joueur (en general rouge ou bleu)
	 */
	public Joueur(String symbole, Color couleur) {

		this.symbole = symbole;
		this.couleur = couleur;

	}

}