/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chou_rave;

/**
 *
 * @author ariou
 */
import static chou_rave.JeuFenetre.listeFermiers;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Rectangle;

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
     
    public Jeu3(String nomAnimal) {
    this.carte = new Carte("carte(5).txt");
    this.monTerrier = new Terrier();
    this.listeNourriture = new ArrayList<>();

    switch (nomAnimal) {
        case "Poule":    this.monAvatar = new Poule();    break;
        case "Sanglier": this.monAvatar = new Sanglier(); break;
        case "Renard":   this.monAvatar = new Renard();   break;
        case "Taupe":    this.monAvatar = new Taupe();    break;
        case "Lapin":    this.monAvatar = new Lapin();    break;
        default:         this.monAvatar = new Lapin();    break;
    }

    for (int i = 0; i < 8; i++) {
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
        
        // 1. Si l'avatar est une Poule, vérifier si un fermier marche sur un œuf
        if (this.monAvatar instanceof Poule) {
            Poule poule = (Poule) this.monAvatar;
            for (Oeuf oeuf : poule.getOeufs()) {
                if (oeuf.isActif()) {
                    for (JeuFermier fermier : listeFermiers) {
                        if (oeuf.getHitbox().intersects(fermier.getHitbox())) {
                            fermier.ralentir(100); // Ralentit largement pendant 4s
                            oeuf.desactiver();     // L'œuf est écrasé
                            System.out.println("Un fermier a glissé sur un œuf !");
                        }
                    }
                }
            }
        }

        // 2. Collisions entre l'avatar et les fermiers
        for (JeuFermier fermier : listeFermiers) {
            if (fermier.estEtourdi()) {
                continue; // Un fermier assommé ne peut pas blesser le joueur
            }

            if (this.monAvatar.getHitbox().intersects(fermier.getHitbox())) {
                if (this.monAvatar.estInvisible()) {
                    // La Taupe sous terre passe sans être détectée
                    continue;
                } else if (this.monAvatar.estEnAttaque()) {
                    // Le Sanglier en charge assomme le fermier
                    fermier.etourdir(75);
                    System.out.println("Le sanglier a percuté un fermier !");
                } else {
                    // Contact normal : l'avatar perd une vie
                    this.monAvatar.perdreVie();
                }
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
