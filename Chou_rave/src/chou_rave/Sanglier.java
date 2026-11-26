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

public class Sanglier extends Avatar {

    public Sanglier() {
        super("/Images/Sanglier.png");
        this.nomPouvoir = "Charge";
        this.vitesse = 4.5; // Légèrement plus lourd au repos
    }

    @Override
    public void activerPouvoir() {
        if (tempsRecharge == 0 && estVivant()) {
            this.dureePouvoir = 75;   // Attaque active pendant 3 secondes
            this.tempsRecharge = 175; // 7 secondes avant de pouvoir recharger
            System.out.println("Le sanglier charge !");
        }
    }

    @Override
    public boolean estEnAttaque() {
        return this.dureePouvoir > 0;
    }

    @Override
    public void miseAJour() {
        // Petit bonus de vitesse pendant la charge
        this.vitesse = estEnAttaque() ? 7.5 : 4.5;
        super.miseAJour();
    }

    @Override
    protected void dessinerApparence(Graphics2D contexte) {
        // Aura rouge autour du sanglier pendant l'attaque
        if (estEnAttaque()) {
            contexte.setColor(new Color(255, 50, 0, 120));
            contexte.fillOval((int) x - 6, (int) y - 6, (int) largeur + 12, (int) hauteur + 12);
        }
        super.dessinerApparence(contexte);
    }
}
