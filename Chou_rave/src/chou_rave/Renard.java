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

public class Renard extends Avatar {
    protected final double vitesseBase = 6.0;    // Plus rapide que les autres (5.0)
    protected final double vitesseSprint = 10.0; // Vitesse pendant le sprint

    public Renard() {
        super("/Images/Renard.png");
        this.nomPouvoir = "Sprint";
        this.vitesse = vitesseBase;
    }

    @Override
    public void activerPouvoir() {
        if (tempsRecharge == 0 && estVivant() && enduranceActuelle > 15) {
            this.dureePouvoir = 75;   // Sprint de 3 secondes
            this.tempsRecharge = 150; // 6 secondes de recharge
            System.out.println("Le renard sprinte !");
        }
    }

    @Override
    public void miseAJour() {
        this.vitesse = (dureePouvoir > 0) ? vitesseSprint : vitesseBase;
        super.miseAJour();
    }

    @Override
    protected void dessinerApparence(Graphics2D contexte) {
        if (dureePouvoir > 0) {
            contexte.setColor(new Color(255, 165, 0, 110));
            contexte.drawOval((int) x - 4, (int) y - 4, (int) largeur + 8, (int) hauteur + 8);
        }
        super.dessinerApparence(contexte);
    }
}
