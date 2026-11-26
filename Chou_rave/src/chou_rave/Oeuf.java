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
import java.awt.Rectangle;

public class Oeuf {
    private double x, y;
    private int largeur = 16;
    private int hauteur = 20;
    private boolean actif = true;

    public Oeuf(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void rendu(Graphics2D contexte) {
        if (!actif) return;
        contexte.setColor(Color.WHITE);
        contexte.fillOval((int) x, (int) y, largeur, hauteur);
        contexte.setColor(Color.DARK_GRAY);
        contexte.drawOval((int) x, (int) y, largeur, hauteur);
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) x, (int) y, largeur, hauteur);
    }

    public boolean isActif() { return actif; }
    public void desactiver() { this.actif = false; }
}
