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

public class Lapin extends Avatar {

    public Lapin() {
        super("/Images/Lapin.png");
        this.nomPouvoir = "Nage (Passif)";
    }

    @Override
    public boolean peutNager() {
        return true; // Autorise le passage dans les zones estDansEau()
    }

    @Override
    protected void dessinerApparence(Graphics2D contexte) {
        // Petit cercle d'eau autour du lapin quand il nage
        if (estDansEau(this.x, this.y)) {
            contexte.setColor(new Color(0, 180, 255, 130));
            contexte.fillOval((int) x - 4, (int) y + 12, (int) largeur + 8, (int) hauteur - 10);
        }
        super.dessinerApparence(contexte);
    }
}
