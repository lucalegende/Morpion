package application;

import javax.swing.JFrame;

import fr.guehenneux.tutorial.morpion.modele.Morpion;

/**
 * 
 * @author guehenneux
 * 
 */
public class ApplicationMorpion {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * on cree un morpion (true si on veut que l'ordinateur commence, false
		 * pour que l'humain commence)
		 */

		Morpion morpion = new Morpion(false);

		/*
		 * on cree une fenetre intitulee "Morpion"
		 */

		JFrame fenetre = new JFrame("Morpion");

		/*
		 * on definit sa largeur et sa hauteur en pixels
		 */

		fenetre.setSize(180, 200);

		/*
		 * cette methode permet de centrer la fenetre a l'ecran
		 */

		fenetre.setLocationRelativeTo(null);

		/*
		 * cette methode permet de quitter l'application quand l'utilisateur
		 * ferme la fenetre
		 */

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * on definit la presentation du morpion en tant que contenu de la
		 * fenetre
		 */

		fenetre.setContentPane(morpion.presentation);

		/*
		 * on rend la fenetre visible a l'ecran, l'utilisateur peut maintenant
		 * jouer
		 */

		fenetre.setVisible(true);

	}

}