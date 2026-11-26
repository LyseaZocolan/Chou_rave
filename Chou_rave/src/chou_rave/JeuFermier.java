/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chou_rave;

/**
 *
 * @author mrajuste
 */

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class JeuFermier {
    JeuCoordonnees position; 
    Image imageFermier;
    
    public JeuFermier(int x, int y, String nomFichierImage){
        this.position = new JeuCoordonnees(x, y);
        
        try {
            // nomFichierImage par exemple "/resources/fermier1.png"
            this.imageFermier = ImageIO.read(getClass().getResource(nomFichierImage));
        } catch (IOException ex) {
            Logger.getLogger(JeuFermier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Détecte si l'avatar est proche (rayonCases = distance en nombre de cases)
    public boolean detecteAvatar(Avatar avatar, int rayonCases) {
        if (avatar == null) {
            return false;
        }
        
        int tailleTuile = 32;
        int avatarCaseX = (int) (avatar.getX() / tailleTuile);
        int avatarCaseY = (int) (avatar.getY() / tailleTuile);
        
        int diffX = Math.abs(this.position.getX() - avatarCaseX);
        int diffY = Math.abs(this.position.getY() - avatarCaseY);
        
        return diffX <= rayonCases && diffY <= rayonCases;
    }
    
    // Si le fermier détecte l'avatar, l'avatar perd toute sa nourriture
    public void penaliserAvatar(Avatar avatar, int rayonCases) {
        if (detecteAvatar(avatar, rayonCases)) {
            avatar.perdreNourriture();
        }
    }
    
    // FONCTION QUI DÉFINIT LES OBSTACLES SELON LA MATRICE DE LA MAP (50x32)
    private boolean estUnObstacle(int x, int y) {
        // 1. Limites de la carte (x de 0 à 49, y de 0 à 31)
        if (x < 0 || x >= 50 || y < 0 || y >= 32) {
            return true;
        }
        
        // 2. La forêt / arbres en haut (y de 0 à 7 environ)
        if (y <= 7 && x <= 22) {
            return true;
        }
        
        // 3. La rivière horizontale (y entre 11 et 14 environ)
        if (y >= 11 && y <= 14) {
            // Sauf sur le pont (vers x = 16 ou 17 d'après la matrice)
            if (x >= 16 && x <= 17) {
                return false; // Le pont est franchissable !
            }
            return true; // Le reste de la rivière bloque
        }
        
        // 4. La maison et le potager en bas à droite (vers la fin de la matrice)
        if (x >= 21 && y >= 20) {
            return true;
        }
        
        // Sinon, la case est libre !
        return false;
    }

    public void deplacerAleatoirement(){
        List<JeuCoordonnees> mouvementsPossibles = new ArrayList<>();
        mouvementsPossibles.add(new JeuCoordonnees(position.getX() + 1, position.getY())); // Droite
        mouvementsPossibles.add(new JeuCoordonnees(position.getX() - 1, position.getY())); // Gauche
        mouvementsPossibles.add(new JeuCoordonnees(position.getX(), position.getY() + 1)); // Bas
        mouvementsPossibles.add(new JeuCoordonnees(position.getX(), position.getY() - 1)); // Haut
        
        List<JeuCoordonnees> mouvementsValides = new ArrayList<>();
        for (JeuCoordonnees coord : mouvementsPossibles) {
            if (!estUnObstacle(coord.getX(), coord.getY())) {
                mouvementsValides.add(coord);
            }
        }
        
        if (!mouvementsValides.isEmpty()) {
            Collections.shuffle(mouvementsValides);
            JeuCoordonnees nouvellePosition = mouvementsValides.get(0);
            
            this.position.setX(nouvellePosition.getX());
            this.position.setY(nouvellePosition.getY());
            
            // Sécurité anti-sortie (0 à 49 pour X, 0 à 31 pour Y)
            if (this.position.getX() < 0) this.position.setX(0);
            if (this.position.getX() > 49) this.position.setX(49);
            if (this.position.getY() < 0) this.position.setY(0);
            if (this.position.getY() > 31) this.position.setY(31);
        }
    }
    
    private int compteurRalentissement = 0; // Pour les œufs de la Poule
    private int compteurEtourdi = 0;        // Pour l'attaque du Sanglier

    public void ralentir(int dureeFrames) {
        this.compteurRalentissement = dureeFrames; // ex: 100 frames = 4 secondes
    }

    public void etourdir(int dureeFrames) {
        this.compteurEtourdi = dureeFrames; // ex: 75 frames = 3 secondes
    }

    public boolean estEtourdi() {
        return this.compteurEtourdi > 0;
    }

    // À appeler au début du déplacement / miseAJour() du fermier :
    public double getMultiplicateurVitesse() {
        if (compteurEtourdi > 0) {
            compteurEtourdi--;
            return 0.0; // Fermier immobilisé par le Sanglier
        }
        if (compteurRalentissement > 0) {
            compteurRalentissement--;
            return 0.3; // Vitesse divisée par ~3 à cause de l'œuf
        }
        return 1.0; // Vitesse normale
    }
}

    
