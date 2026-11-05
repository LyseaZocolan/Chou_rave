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

public class Jeu {
    private Avatar monAvatar;
    private ArrayList<Nourriture> listeNourriture;

    public Jeu() {
        this.monAvatar = new Avatar(); 
        this.listeNourriture = new ArrayList<>();
    }

    public void miseAJour() {
        // Met à jour les éléments du jeu (seulement l'avatar pour ce test)[cite: 17]
        this.monAvatar.miseAJour(); 
        
        ArrayList<Nourriture> aSupprimer = new  ArrayList<>();
        for (Nourriture n : listeNourriture) {
            if (detecterCollision(this.monAvatar, n)) {
                // 2. Si ça touche, on tente de ramasser. 
                // ramasserNourriture renvoie 'true' si le sac n'est pas plein (max 3)
                if (this.monAvatar.ramasserNourriture(n.getNom())) {
                    aSupprimer.add(n); // On note l'objet pour le supprimer de la map
                }
            }
        }
        
        // 3. On enlève de la carte les légumes qui ont été mangés/ramassés
        this.listeNourriture.removeAll(aSupprimer);
        
        // Optionnel : Régénérer des légumes si la map est vide
        if (this.listeNourriture.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                this.listeNourriture.add(new Nourriture());
            }
        }
    }
    private boolean detecterCollision(Avatar avatar, Nourriture n){
        if ((n.getX() >= avatar.getX() + avatar.getLargeur()) || (n.getX() + n.getTaille() <= avatar.getX()) || (n.getY() >= avatar.getY() + avatar.getHauteur()) || (n.getY() +n.getTaille() <= avatar.getY())){
            return false;
        }
        else {
            return true;
        }
    }

    public void rendu(Graphics2D contexte) {
        for (Nourriture n : listeNourriture){
            n.rendu(contexte);
        }
        this.monAvatar.rendu(contexte); 
    }

    public Avatar getAvatar() {
        return this.monAvatar;
    }
}
