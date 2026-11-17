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

/**
 *
 * @author mrajuste
 */
    

public class JeuFenetre extends JFrame {
    private List<JeuFermier> listeFermiers;

    public JeuFenetre() {
        setTitle("Jeu Chou-Rave - La Ferme");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        // On place nos 4 fermiers sur le chemin principal de la nouvelle map
        listeFermiers = new ArrayList<>();
        listeFermiers.add(new JeuFermier(12, 6, "fermier1.png"));  // Sur le chemin du haut près du pont
        listeFermiers.add(new JeuFermier(12, 9, "fermier2.png"));  // Juste avant le pont
        listeFermiers.add(new JeuFermier(12, 16, "fermier3.png")); // Sur le chemin en bas après le pont
        listeFermiers.add(new JeuFermier(18, 16, "fermier4.png")); // Sur le chemin vers la droite

        PanneauJeu panneau = new PanneauJeu();
        this.add(panneau);

        final int[] compteur = {0};

        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compteur[0]++; 
                for (int i = 0; i < listeFermiers.size(); i++) {
                    JeuFermier fermier = listeFermiers.get(i);
                    if (i == 0 || i == 1) {
                        fermier.deplacerAleatoirement();
                    } else {
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
            imageFond = new ImageIcon("carte.png").getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);

            // Taille d'affichage des fermiers (adaptée à la grande map)
            int tailleCase = 32; // Ajusté pour correspondre aux cases de la nouvelle map
            
            for (JeuFermier fermier : listeFermiers) {
                g.drawImage(fermier.imageFermier, 
                            fermier.position.getX() * tailleCase, 
                            fermier.position.getY() * tailleCase, 
                            80, 80, // Taille visible du personnage
                            this);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Fermiers en patrouille : " + listeFermiers.size(), 20, 30);
        }
    }
}