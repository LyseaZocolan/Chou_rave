/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chou_rave;

/**
 *
 * @author ariou
 */
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Exemple de classe jeu
 *
 * @author guillaume.laurent
 */
public class Jeu3 {

    private Carte carte;
    private Avatar monAvatar;
    private Terrier monTerrier;
    private ArrayList<Nourriture> listeNourriture;
     
    public Jeu3() {        
        this.carte = new Carte("carte(5).txt");
        this.monAvatar = new Avatar();
        this.monTerrier = new Terrier();
        this.listeNourriture = new ArrayList<>();
        
        for (int i=0; i < 8; i++){
            this.listeNourriture.add(new Nourriture());
        }
    }

    public void miseAJour() {
        this.carte.miseAJour();
        this.monAvatar.miseAJour();
        
        ArrayList<Nourriture> aSupprimer = new ArrayList<>();
        for (Nourriture n : listeNourriture){
            if (detecterCollision(this.monAvatar, n)){
                if (this.monAvatar.ramasserNourriture(n.getNom())){
                    aSupprimer.add(n);
                }
            }
        }
        this.listeNourriture.removeAll(aSupprimer);
        
        if (this.listeNourriture.isEmpty()){
            for (int i = 0; i < 8; i++){
                this.listeNourriture.add(new Nourriture());
            }
        }
    }

    public void essayerDeposerNourriture() {
        if (!((monTerrier.getX() >= monAvatar.getX() + monAvatar.getLargeur()) || 
              (monTerrier.getX() + monTerrier.getLargeur() <= monAvatar.getX()) || 
              (monTerrier.getY() >= monAvatar.getY() + monAvatar.getHauteur()) || 
              (monTerrier.getY() + monTerrier.getHauteur() <= monAvatar.getY()))) {
            
            this.monAvatar.deposerNourritureTerrier();
            System.out.println("Nourriture déposée au terrier !");
        }
    }

    private boolean detecterCollision(Avatar avatar, Nourriture n) {
        return !((n.getX() >= avatar.getX() + avatar.getLargeur()) || 
                 (n.getX() + n.getTaille() <= avatar.getX()) ||       
                 (n.getY() >= avatar.getY() + avatar.getHauteur()) || 
                 (n.getY() + n.getTaille() <= avatar.getY()));
    }
    
    public void rendu(Graphics2D contexte) {
        this.carte.rendu(contexte);
        this.monTerrier.rendu(contexte);
        for(Nourriture n : listeNourriture){
            n.rendu(contexte);
        }
        this.monAvatar.rendu(contexte);
    }
    public Avatar getAvatar(){
        return this.monAvatar;
    }

}
