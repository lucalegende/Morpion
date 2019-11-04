package fr.guehenneux.tutorial.morpion.modele;

import fr.guehenneux.tutorial.morpion.presentation.PresentationCase;

/**
 * Represente une case du morpion.
 * 
 * @author guehenneux
 * 
 */
public class Case {

	/**
	 * joueur ayant joue dans la case (null si aucun joueur n'a encore joue dans
	 * la case)
	 */
	public Joueur joueur;

	/**
	 * L'objet Case represente les donnees associees a la case et les methodes
	 * permettant de les manipuler. Sa presentation definit la maniere dont
	 * l'objet case interagit avec l'utilisateur (visuel, son, clics...)
	 */
	public PresentationCase presentation;

	/**
	 * @param morpion
	 *            morpion dans lequel se trouve la case
	 */
	public Case(Morpion morpion) {

		joueur = null;

		presentation = new PresentationCase(morpion, this);

	}

	/**
	 * 
	 * @return true si la case est libre, false si un joueur a deja joue dedans
	 */
	public boolean estLibre() {
		return joueur == null;
	}

	/**
	 * Joue un coup definitivement, met a jour la presentation.
	 * 
	 * @param joueur
	 *            joueur qui joue la case
	 */
	public void jouerDefinitivement(Joueur joueur) {

		this.joueur = joueur;

		presentation.actualiser();

	}

	/**
	 * Joue un coup provisoirement, peut etre annule.
	 * 
	 * @param joueur
	 *            joueur qui joue la case
	 */
	public void jouerProvisoirement(Joueur joueur) {
		this.joueur = joueur;
	}

	/**
	 * Annule le coup joue dans la case.
	 */
	public void annuler() {
		joueur = null;
	}

}