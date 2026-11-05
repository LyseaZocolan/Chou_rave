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

public class Terrier {
    private double x, y;
    private double largeur = 70;
    private double hauteur = 50;

    public Terrier() {
        // Position fixe de la grotte (en haut à gauche)
        this.x = 300;
        this.y = 300;
    }

    public void rendu(Graphics2D contexte) {
        // Base de la grotte (marron)
        contexte.setColor(new Color(101, 67, 33)); 
        contexte.fillOval((int)x, (int)y, (int)largeur, (int)hauteur);
        
        // Entrée de la grotte (noir)
        contexte.setColor(Color.BLACK);
        contexte.fillOval((int)x + 15, (int)y + 15, (int)largeur - 30, (int)hauteur - 20);
    }

    // Getters pour les calculs de collision
    public double getX() { return x; }
    public double getY() { return y; }
    public double getLargeur() { return largeur; }
    public double getHauteur() { return hauteur; }
}