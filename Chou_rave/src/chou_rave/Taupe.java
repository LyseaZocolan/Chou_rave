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

public class Taupe extends Avatar {

    public Taupe() {
        super("/Images/Taupe.png");
        this.nomPouvoir = "Tunnel souterrain";
    }

    @Override
    public void activerPouvoir() {
        if (tempsRecharge == 0 && estVivant()) {
            this.dureePouvoir = 100;  // 4 secondes sous terre
            this.tempsRecharge = 200; // 8 secondes de recharge
            System.out.println("La taupe creuse sous la terre !");
        }
    }

    @Override
    public boolean estInvisible() {
        return this.dureePouvoir > 0;
    }

    @Override
    protected void dessinerApparence(Graphics2D contexte) {
        if (estInvisible()) {
            // Affiche une petite motte de terre à la place du sprite pour que le joueur se repère
            contexte.setColor(new Color(110, 70, 30, 200));
            contexte.fillOval((int) x + 4, (int) y + 10, (int) largeur - 8, (int) hauteur - 14);
            contexte.setColor(new Color(70, 40, 15));
            contexte.drawOval((int) x + 4, (int) y + 10, (int) largeur - 8, (int) hauteur - 14);
        } else {
            super.dessinerApparence(contexte);
        }
    }
}
