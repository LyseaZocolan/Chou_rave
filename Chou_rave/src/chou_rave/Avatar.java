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
import java.util.ArrayList; 

public class Avatar {
    protected double x, y;
    private boolean toucheGauche, toucheDroite, toucheHaut, toucheBas;
    private double vitesse = 5.0; 

    // Nouvelles caractéristiques pour la survie
    private double enduranceMax = 1000.0;
    private double enduranceActuelle;
    
    // Inventaire
    private int capaciteSac = 3;
    private ArrayList<String> inventaire;
    

    public Avatar() {
        this.x = 185; 
        this.y = 185;
        this.toucheGauche = false;
        this.toucheDroite = false;
        this.toucheHaut = false;
        this.toucheBas = false;
        
        // L'animal commence avec toute son endurance
        this.enduranceActuelle = this.enduranceMax;
        
        this.inventaire = new ArrayList<>();
        
    }

    public void miseAJour() {
        // Détermine si le joueur donne l'ordre de bouger
        boolean veutBouger = toucheGauche || toucheDroite || toucheHaut || toucheBas;

        // Condition de mouvement : vouloir bouger ET avoir de l'endurance
        if (veutBouger && enduranceActuelle > 0) {
            if (this.toucheGauche) { x -= vitesse; }
            if (this.toucheDroite) { x += vitesse; }
            if (this.toucheHaut) { y -= vitesse; }
            if (this.toucheBas) { y += vitesse; }
            
            // Le mouvement consomme de l'endurance
            enduranceActuelle -= 0.5; 
            
        } 
        // Récupération : si on ne bouge pas et que la jauge n'est pas pleine
        else if (!veutBouger && enduranceActuelle < enduranceMax) {
            enduranceActuelle += 1.0; // Se remplit deux fois plus lentement qu'elle ne se vide
        }

        // Sécurités pour bloquer les valeurs
        if (enduranceActuelle < 0) enduranceActuelle = 0;
        if (enduranceActuelle > enduranceMax) enduranceActuelle = enduranceMax;

        // Limites de la carte
        if (x < 0) x = 0;
        if (x > 370) x = 370; 
        if (y < 0) y = 0;
        if (y > 340) y = 340; 
    }

    public void rendu(Graphics2D contexte) {
        // 1. Dessin de l'avatar
        contexte.setColor(Color.BLUE);
        contexte.fillOval((int)x, (int)y, 30, 30); 
        
        // 2. Affichage de la jauge d'endurance au-dessus de l'avatar
        // Fond rouge de la barre
        contexte.setColor(Color.RED);
        contexte.fillRect((int)x, (int)y - 10, 30, 5); 
        // Remplissage vert proportionnel à l'endurance restante
        contexte.setColor(Color.GREEN);
        int largeurBarre = (int) ((enduranceActuelle / enduranceMax) * 30);
        contexte.fillRect((int)x, (int)y - 10, largeurBarre, 5); 
        
        contexte.setColor(Color.BLACK);
        contexte.drawString("Sac à dos :"+inventaire.size() +"/" + capaciteSac, 10, 20);
        
        for (int i = 0; i< capaciteSac; i++){
            if (i < inventaire.size()) {
                contexte.setColor(Color.orange); // case remplie
                contexte.fillRect(10 + (i*30), 30, 25, 25);
            }
            else {
                contexte.setColor(Color.gray); // case vide
                contexte.drawRect(10 + (i*30), 30, 25, 25);
            }
        }
    }
    
    public boolean ramasserNourriture(String aliment) {
        if (this.inventaire.size() < this.capaciteSac) {
            this.inventaire.add(aliment);
            return true; // L'objet a été ramassé
        }
        return false; // Le sac est plein
    }

    public void deposerNourritureTerrier() {
        this.inventaire.clear(); // Vide le sac
    }

    // --- Contrôles clavier ---
    public void setToucheGauche(boolean etat) { this.toucheGauche = etat; }
    public void setToucheDroite(boolean etat) { this.toucheDroite = etat; }
    public void setToucheHaut(boolean etat) { this.toucheHaut = etat; }
    public void setToucheBas(boolean etat) { this.toucheBas = etat; }
    
    public double getLargeur() { return 30.0; }
    public double getHauteur() {return 30.0; }
    public double getX() {return x; }
    public double getY() {return y; }
}