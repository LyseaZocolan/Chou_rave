/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chou_rave;

/**
 *
 * @author jbelot
 */
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Poule extends Avatar {
    private ArrayList<Oeuf> oeufs;

    public Poule() {
        super("/resources/Poule.png");
        this.nomPouvoir = "Ponte d'œuf";
        this.oeufs = new ArrayList<>();
    }

    @Override
    public void activerPouvoir() {
        if (tempsRecharge == 0 && estVivant()) {
            // Pond un œuf sous la poule
            this.oeufs.add(new Oeuf(this.x + 8, this.y + 8));
            this.tempsRecharge = 75; // 3 secondes entre deux œufs
            System.out.println("La poule a pondu un œuf piège !");
        }
    }

    @Override
    public void rendu(Graphics2D contexte) {
        // Dessine les œufs au sol avant la poule
        for (Oeuf o : oeufs) {
            o.rendu(contexte);
        }
        super.rendu(contexte);
    }

    public ArrayList<Oeuf> getOeufs() {
        return this.oeufs;
    }
}
