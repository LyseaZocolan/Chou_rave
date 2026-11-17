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
    Image imageFermier; // Tu peux garder ou enlever cette ligne
    
    public JeuFermier(int x, int y, String nomFichierImage){
        this.position = new JeuCoordonnees(x, y);
        // Solution de secours : on évite le plantage si l'image est introuvable
        java.net.URL imageUrl = getClass().getResource(nomFichierImage);
        if (imageUrl != null) {
            this.imageFermier = new ImageIcon(imageUrl).getImage();
        }
    }
    
    // FONCTION QUI DÉFINIT LES OBSTACLES SELON LA MATRICE DE LA MAP (32x32)
    private boolean estUnObstacle(int x, int y) {
        // 1. Limites de la carte (0 à 31)
        if (x < 0 || x > 31 || y < 0 || y > 31) {
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
            
            // Sécurité anti-sortie (0 à 31)
            if (this.position.getX() < 0) this.position.setX(0);
            if (this.position.getX() > 31) this.position.setX(31);
            if (this.position.getY() < 0) this.position.setY(0);
            if (this.position.getY() > 31) this.position.setY(31);
        }
    }
}

    
