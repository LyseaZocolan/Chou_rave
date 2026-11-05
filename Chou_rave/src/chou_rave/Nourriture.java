/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chou_rave;

/**
 *
 * @author jbelot
 */
import java.awt.Color;
import java.awt.Graphics2D;

public class Nourriture {
    private double x, y;
    private Color couleur;
    private String nom;
    private double taille = 15;

    public Nourriture() {
        // Apparition aléatoire sur la map (en gardant une marge par rapport aux bords de 400px)
        this.x = Math.random() * 370;
        this.y = Math.random() * 340;
        
        // Attribution aléatoire d'un type de légume/fruit
        double proba = Math.random();
        if (proba < 0.33) {
            this.couleur = Color.ORANGE;
            this.nom = "Carotte";
        } else if (proba < 0.66) {
            this.couleur = new Color(128, 0, 128); // Violet
            this.nom = "Navet";
        } else {
            this.couleur = Color.RED;
            this.nom = "Pomme";
        }
    }

    public void rendu(Graphics2D contexte) {
        contexte.setColor(this.couleur);
        contexte.fillOval((int)x, (int)y, (int)taille, (int)taille);
    }

    // Getters nécessaires pour le calcul des collisions[cite: 20, 24]
    public double getX() { return x; }
    public double getY() { return y; }
    public double getTaille() { return taille; }
    public String getNom() { return nom; }
}