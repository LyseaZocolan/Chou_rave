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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO; 

public class Avatar {
    protected double x, y;
    protected BufferedImage sprite;
    private boolean toucheGauche, toucheDroite, toucheHaut, toucheBas;
    private double vitesse = 5.0;
    private Avatar monAvatar;
    private double largeur = 32.0;
    private double hauteur = 32.0;

    // Nouvelles caractéristiques pour la survie
    private double enduranceMax = 1000.0;
    private double enduranceActuelle;
    
    // Inventaire
    private int capaciteSac = 3;
    private ArrayList<String> inventaire;
    
    private int viesMax = 3;
    private int vies = 3;
    private int compteurInvincibilite = 0; 
    private boolean estVivant;
    

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
        
        try {
            // Adapte le chemin selon l'emplacement exact de ton image
            URL urlImage = getClass().getResource("/resources/Lapin.png");
            if (urlImage != null) {
                this.sprite = ImageIO.read(urlImage);
            } else {
                System.out.println("Erreur : Image introuvable au chemin spécifié.");
                this.sprite = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
            this.sprite = null;
        }
        
    }

    public void miseAJour() {
        // Détermine si le joueur donne l'ordre de bouger
        if (!estVivant()) {
            return;
        }

        // Décrémente la période d'invincibilité après un coup
        if (compteurInvincibilite > 0) {
            compteurInvincibilite--;
        }
        
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
        if (x > 994) x = 994; 
        if (y < 0) y = 0;
        if (y > 994) y = 994; 
    }

    public void rendu(Graphics2D contexte) {
        // 1. Dessin de l'avatar
        contexte.setColor(Color.BLUE);
        contexte.fillOval((int)x, (int)y, 30, 30); 
        
        boolean afficherSprite = (compteurInvincibilite == 0) || (compteurInvincibilite % 6 < 3);
        if (afficherSprite) {
            if (this.sprite != null) {
                contexte.drawImage(this.sprite, (int) x, (int) y, (int) largeur, (int) hauteur, null);
            } else {
                contexte.setColor(Color.MAGENTA);
                contexte.fillRect((int) x, (int) y, (int) largeur, (int) hauteur);
            }
        }
        
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
        
        contexte.setColor(Color.BLACK);
        contexte.drawString("Vies : " + vies + " / " + viesMax, 10, 75);
        for (int i = 0; i < viesMax; i++) {
            if (i < vies) {
                contexte.setColor(Color.RED); // Vie pleine
                contexte.fillOval(10 + (i * 25), 82, 18, 18);
            } else {
                contexte.setColor(Color.GRAY); // Vie perdue
                contexte.drawOval(10 + (i * 25), 82, 18, 18);
            }
        }

        // 5. Écran simple de Game Over
        if (!estVivant()) {
            contexte.setColor(new Color(0, 0, 0, 160));
            contexte.fillRect(0, 0, 1024, 1024);
            contexte.setColor(Color.RED);
            contexte.drawString("GAME OVER - Vous avez été attrapé !", 400, 500);
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
    
     public void perdreNourriture() {
        this.inventaire.clear(); // Vide tout le sac si le fermier repère l'avatar
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

    
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void perdreVie() {
        if (compteurInvincibilite == 0 && vies > 0) {
            this.vies--;
            this.compteurInvincibilite = 50; // 2 secondes d'invincibilité
            System.out.println("Touché ! Vies restantes : " + this.vies);
        }
    }

    public boolean estVivant() {
        return this.vies > 0;
    }

    public int getVies() {
        return this.vies;
    }


    String getPseudo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    int getScore() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    int getPositionX() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

 }