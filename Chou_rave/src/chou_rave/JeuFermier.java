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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class JeuFermier {
    JeuCoordonnees position; 
    Image imageFermier;
    private int[][] grilleCarte; // 32 lignes (y) x 50 colonnes (x)
    
    public JeuFermier(int x, int y, String nomFichierImage){
        this.position = new JeuCoordonnees(x, y);
        
        try {
            this.imageFermier = ImageIO.read(getClass().getResource(nomFichierImage));
        } catch (IOException ex) {
            Logger.getLogger(JeuFermier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        chargerCarte();
    }
    
    // Lecture simple de carte(5).txt (50 colonnes x 32 lignes)
    private void chargerCarte() {
        this.grilleCarte = new int[32][50];
        try {
            BufferedReader lecteur = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("carte(5).txt"))
            );
            for (int y = 0; y < 32; y++) {
                String ligne = lecteur.readLine();
                if (ligne != null) {
                    String[] cases = ligne.trim().split("\\s+");
                    for (int x = 0; x < 50 && x < cases.length; x++) {
                        grilleCarte[y][x] = Integer.parseInt(cases[x]);
                    }
                }
            }
            lecteur.close();
        } catch (Exception ex) {
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
    
    // Fonction qui définit les obstacles selon a matrice de la map (50x32)
    private boolean estUnObstacle(int x, int y) {
        // Limites de la carte (x de 0 à 49, y de 0 à 31)
        if (x < 0 || x >= 50 || y < 0 || y >= 32) {
            return true;
        }
        
        int numTuile = grilleCarte[y][x];
        
        // La rivière (tous les numéros 6 dans carte(5).txt)
        if (numTuile == 6) {
            return true;
        }
        
        // La forêt (tuiles 3, 4, 5 dans la partie forêt en haut)
        if (y <= 9 && (numTuile == 3 || numTuile == 4 || numTuile == 5)) {
            return true;
        }
        
        // Sinon, la case est libre
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
}

    
