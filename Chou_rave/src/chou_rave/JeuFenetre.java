/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chou_rave;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private static List<JeuFermier> listeFermiers;
    private Carte carteJeu; // On intègre la classe Carte de ton projet

    public JeuFenetre() {
        setTitle("Jeu Chou-Rave - La Ferme");
        setSize(1024, 1024); // Adapté pour une carte de 32x32 tuiles de 32 pixels (32*32 = 1024)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        // On charge la carte à partir de son fichier texte
        carteJeu = new Carte("carte(5).txt");

        // On place nos 4 fermiers sur le chemin en terre de la map
        listeFermiers = new ArrayList<>();
        listeFermiers.add(new JeuFermier(16, 8, "fermier1.png"));  // Sur le chemin du haut
        listeFermiers.add(new JeuFermier(16, 12, "fermier2.png")); // Près du pont
        listeFermiers.add(new JeuFermier(16, 18, "fermier3.png")); // Sur le chemin du bas
        listeFermiers.add(new JeuFermier(22, 18, "fermier4.png")); // Vers la droite
    

        PanneauJeu panneau = new PanneauJeu();
        this.add(panneau);

        final int[] compteur = {0};

        // Timer réglé à 500 ms pour les déplacements
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compteur[0]++; 
                for (int i = 0; i < listeFermiers.size(); i++) {
                    JeuFermier fermier = listeFermiers.get(i);  
                    if (i == 0 || i == 1) {                         // les jeunes fermiers se déplacent tous les tics du timer
                        fermier.deplacerAleatoirement();
                    } else {
                        if (compteur[0] % 2 == 0) {                 // les vieux fermiers se déplacent tous les 2 tics pour paraître plus lents
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
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // On dessine la carte en tuiles grâce à la classe Carte
            carteJeu.rendu((Graphics2D) g);

            // On dessine chaque fermier par-dessus
            int tailleTuile = 32; // Chaque tuile fait 32 pixels
            
            for (JeuFermier fermier : listeFermiers) {
                // Vérification de sécurité : si l'image est chargée, on l'affiche
                if (fermier.imageFermier != null) {
                    g.drawImage(fermier.imageFermier, 
                                fermier.position.getX() * tailleTuile, 
                                fermier.position.getY() * tailleTuile, 
                                64, 64, // Taille d'affichage du fermier (2 tuiles de haut/large)
                                this);
                } else {
                    // Solution de secours : si l'image est absente, on dessine un carré rouge
                    g.setColor(Color.RED);
                    g.fillRect(fermier.position.getX() * tailleTuile, 
                               fermier.position.getY() * tailleTuile, 
                               32, 32);
                }
            }

            // Texte d'information
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Fermiers en patrouille : " + listeFermiers.size(), 20, 30);
        }
    }
    
//    public static List<JeuFermier> getlisteFermiers() {
//        return listeFermiers;
//    public static void main(String[] args) {
//        javax.swing.SwingUtilities.invokeLater(() -> {
//            JeuFenetre fenetre = new JeuFenetre();
//            fenetre.setVisible(true);
//        });
//    }
}