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
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JeuFermier {
    JeuCoordonnees position; 
    Image imageFermier;
    
    public JeuFermier(int x, int y, String nomFichierImage){
        this.position = new JeuCoordonnees(x, y);
        this.imageFermier = new ImageIcon(nomFichierImage).getImage();
    }
    
    // FONCTION QUI DÉFINIT LES OBSTACLES DE LA NOUVELLE MAP
    private boolean estUnObstacle(int x, int y) {
        // 1. Limites de la nouvelle carte (0 à 24)
        if (x < 0 || x > 24 || y < 0 || y > 24) {
            return true;
        }
        
        // 2. La grande zone de forêt / arbres en haut (y de 0 à 8 environ)
        if (y <= 8 && (x <= 18)) {
            return true;
        }
        
        // 3. La rivière horizontale au milieu (y entre 11 et 14 environ)
        if (y >= 11 && y <= 14) {
            // Sauf sur le pont au milieu (x entre 12 et 13 par exemple)
            if (x >= 12 && x <= 13) {
                return false; // Le pont est franchissable !
            }
            return true; // Le reste de la rivière est un obstacle
        }
        
        // 4. L'enclos et le potager en bas à droite
        if (x >= 15 && y >= 17) {
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
            
            // Sécurité anti-sortie pour la nouvelle taille (0 à 24)
            if (this.position.getX() < 0) this.position.setX(0);
            if (this.position.getX() > 24) this.position.setX(24);
            if (this.position.getY() < 0) this.position.setY(0);
            if (this.position.getY() > 24) this.position.setY(24);
        }
    }
}

    
