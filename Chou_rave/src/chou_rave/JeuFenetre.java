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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author mrajuste
 */
public class JeuFenetre extends JFrame {

    public static List<JeuFermier> listeFermiers = new ArrayList<>();
    private Carte carteJeu;

    public JeuFenetre() {
        setTitle("Test Affichage Fermiers - Chou-Rave");
        setSize(1600, 1024); // 50x32 tuiles de 32 pixels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        carteJeu = new Carte("carte(5).txt");

        listeFermiers = new ArrayList<>();
        listeFermiers.add(new JeuFermier(40, 5, "/resources/fermier1.png"));
        listeFermiers.add(new JeuFermier(16, 8, "/resources/fermier2.png"));
        listeFermiers.add(new JeuFermier(16, 24, "/resources/fermier3.png"));
        listeFermiers.add(new JeuFermier(38, 18, "/resources/fermier4.png"));

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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            carteJeu.rendu((Graphics2D) g);

            int tailleTuile = 32;

            for (JeuFermier fermier : listeFermiers) {
                if (fermier.imageFermier != null) {
                    g.drawImage(fermier.imageFermier,
                            fermier.position.getX() * tailleTuile,
                            fermier.position.getY() * tailleTuile,
                            tailleTuile * 4, tailleTuile * 4, // Agrandi x2 (64x64)
                            this);
                } else {
                    // Carré rouge de secours si l'image n'est pas chargée
                    g.setColor(Color.RED);
                    g.fillRect(fermier.position.getX() * tailleTuile,
                            fermier.position.getY() * tailleTuile,
                            tailleTuile, tailleTuile);
                }
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Fermiers en patrouille : " + listeFermiers.size(), 20, 30);
        }
    }

    public static List<JeuFermier> getlisteFermiers() {
        return listeFermiers;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JeuFenetre fenetre = new JeuFenetre();
            fenetre.setVisible(true);
        });
    }
}
