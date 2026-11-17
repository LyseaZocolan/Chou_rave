/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chou_rave;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author mrajuste
 */
    


public class JeuFenetre extends JFrame {
    private List<JeuFermier> listeFermiers;

    public JeuFenetre() {
        setTitle("Jeu Chou-Rave - La Ferme");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        // On crée la liste de nos 4 fermiers sur des zones de chemins libres :
        listeFermiers = new ArrayList<>();
        listeFermiers.add(new JeuFermier(2, 5, "fermier1.png")); // Fermier 0 (Rapide)
        listeFermiers.add(new JeuFermier(6, 4, "fermier2.png")); // Fermier 1 (Rapide)
        listeFermiers.add(new JeuFermier(1, 3, "fermier3.png")); // Fermier 2 (Lent)
        listeFermiers.add(new JeuFermier(9, 5, "fermier4.png")); // Fermier 3 (Lent)

        PanneauJeu panneau = new PanneauJeu();
        this.add(panneau);

        // Un petit compteur pour rythmer les vitesses
        final int[] compteur = {0};

        // Timer réglé à 500 ms (0.5 seconde) pour accélérer le tout !
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compteur[0]++; // On avance le compteur à chaque tic

                // On parcourt les fermiers un par un grâce à leur numéro (index i)
                for (int i = 0; i < listeFermiers.size(); i++) {
                    JeuFermier fermier = listeFermiers.get(i);

                    // Si c'est le fermier 0 ou 1 : ils bougent à CHAQUE tic (toutes les 0.5s)
                    if (i == 0 || i == 1) {
                        fermier.deplacerAleatoirement();
                    } 
                    // Si c'est le fermier 2 ou 3 : ils bougent UNE FOIS SUR DEUX (toutes les 1s)
                    else {
                        if (compteur[0] % 2 == 0) { 
                            fermier.deplacerAleatoirement();
                        }
                    }   
                }
                panneau.repaint(); 
            }
        });
        timer.start();
    }

    class PanneauJeu extends JPanel {
        private Image imageFond;

        public PanneauJeu() {
            // On charge juste l'image de fond ici
            imageFond = new ImageIcon("carte.png").getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // On dessine la carte
            g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);

            // On dessine chaque fermier avec sa propre image
            int tailleCase = 40; 
            
            for (JeuFermier fermier : listeFermiers) {
                g.drawImage(fermier.imageFermier, 
                            fermier.position.getX() * tailleCase, 
                            fermier.position.getY() * tailleCase, 
                            150, 150, 
                            this);
            }

            // Texte d'information
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Fermiers en patrouille : " + listeFermiers.size(), 20, 30);
        }
    }
}