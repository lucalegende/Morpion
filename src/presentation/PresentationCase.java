package fr.guehenneux.tutorial.morpion.presentation;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.guehenneux.tutorial.morpion.modele.Case;
import fr.guehenneux.tutorial.morpion.modele.Joueur;
import fr.guehenneux.tutorial.morpion.modele.Morpion;

/**
 * 
 * @author guehenneux
 * 
 */
public class PresentationCase extends JPanel {

	/**
	 * modele du morpion dans lequel se trouve la case
	 */
	private Morpion morpion;

	/**
	 * modele de la case
	 */
	private Case modele;

	/**
	 * bouton affiche tant que la case n'a pas ete jouee
	 */
	private JButton bouton;

	/**
	 * label affiche quand la case a ete jouee (contient en theorie le caractere
	 * X ou O)
	 */
	private JLabel label;

	/**
	 * 
	 * @param morpion
	 * @param modele
	 */
	public PresentationCase(Morpion morpion, Case modele) {

		this.morpion = morpion;
		this.modele = modele;

		bouton = new JButton();
		label = new JLabel();

		/*
		 * Le type de layout definit la facon de positionner les composants, ici
		 * on veut que le bouton prenne toute la place disponible. En utilisant
		 * un BorderLayout et en ajoutant uniquement le bouton au centre, on
		 * garantit qu'il prendra toute la place, d'autres Layout permettent
		 * d'arriver au meme resultat.
		 */

		setLayout(new BorderLayout());
		add(bouton, BorderLayout.CENTER);

		/*
		 * on ajoute un ecouteur sur le bouton de facon a detecter un clic
		 */

		bouton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evenement) {
				jouer();
			}

		});

	}

	/**
	 * 
	 */
	public void jouer() {
		morpion.jouerHumain(modele);
	}

	/**
	 * permet de mettre a jour la presentation de la case apres qu'un joueur ait
	 * joue dedans
	 */
	public void actualiser() {

		/*
		 * on recupere le joueur qui a joue dans cette case
		 */

		Joueur joueur = modele.joueur;

		/*
		 * on supprime le bouton pour qu'on ne puisse plus jouer dans cette case
		 */

		remove(bouton);

		/*
		 * on met a jour le libelle avec le symbole du joueur (X ou O)
		 */

		label.setText(joueur.symbole);
		label.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(joueur.couleur);

		/*
		 * on ajoute le label
		 */

		add(label, BorderLayout.CENTER);

		/*
		 * on redessine
		 */

		revalidate();
		repaint();

	}

}