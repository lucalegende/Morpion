package fr.guehenneux.tutorial.morpion.presentation;

import java.awt.GridLayout;

import javax.swing.JPanel;

import fr.guehenneux.tutorial.morpion.modele.Case;
import fr.guehenneux.tutorial.morpion.modele.Morpion;

/**
 * 
 * @author guehenneux
 * 
 */
public class PresentationMorpion extends JPanel {

	/**
	 * 
	 * @param modele
	 */
	public PresentationMorpion(Morpion modele) {

		/*
		 * l'affichage du morpion se fait naturellement dans une grille de 3x3
		 */

		setLayout(new GridLayout(3, 3));

		Case caseMorpion;

		for (int y = 0; y < 3; y++) {

			for (int x = 0; x < 3; x++) {

				caseMorpion = modele.getCase(x, y);
				add(caseMorpion.presentation);

			}

		}

	}

}