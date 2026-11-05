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
    
    //FONCTION QUI DÉFINIT LES OBSTACLES DE LA MAP
    private boolean estUnObstacle(int x, int y) {
        // 1. Si on sort du cadre de la carte (0 à 11)
        if (x < 0 || x > 11 || y < 0 || y > 11) {
            return true;
        }
        
        
        // 3. La Ferme / Bâtiments / Enclos du haut (en haut à droite)
        if (x >= 4 && x <= 11 && y <= 2) {
            return true;
        }
        
        // 4. La Mare (en bas à gauche)
        if (x >= 1 && x <= 5 && y >= 7 && y <= 10) {
            return true;
        }
        
        // 5. Le Potager (en bas à droite)
        if (x >= 6 && y >= 6) {
            return true;
        }
        
        // Sinon, la case est libre
        return false;
    }

    public void deplacerAleatoirement(){
        // 1. Liste des 4 déplacements possibles autour du fermier
        List<JeuCoordonnees> mouvementsPossibles = new ArrayList<>();
        mouvementsPossibles.add(new JeuCoordonnees(position.getX() + 1, position.getY())); // Droite
        mouvementsPossibles.add(new JeuCoordonnees(position.getX() - 1, position.getY())); // Gauche
        mouvementsPossibles.add(new JeuCoordonnees(position.getX(), position.getY() + 1)); // Bas
        mouvementsPossibles.add(new JeuCoordonnees(position.getX(), position.getY() - 1)); // Haut
        
        // 2. On ne garde que les mouvements qui ne sont pas des obstacles
        List<JeuCoordonnees> mouvementsValides = new ArrayList<>();
        for (JeuCoordonnees coord : mouvementsPossibles) {
            if (!estUnObstacle(coord.getX(), coord.getY())) {
                mouvementsValides.add(coord);
            }
        }
        
        // 3. S'il y a des chemins valides, on en choisit un au hasard
        if (!mouvementsValides.isEmpty()) {
            Collections.shuffle(mouvementsValides);
            JeuCoordonnees nouvellePosition = mouvementsValides.get(0);
            
            // On met à jour la position
            this.position.setX(nouvellePosition.getX());
            this.position.setY(nouvellePosition.getY());
        }
    }
}

    
