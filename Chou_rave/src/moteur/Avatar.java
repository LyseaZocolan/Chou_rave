/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moteur;

/**
 *
 * @author jbelot
 */
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
// import javax.imageio.ImageIO; (pour charger une image plus tard)

public class Avatar {
    protected BufferedImage sprite;
    protected double x, y;
    
    // Intentions de déplacement du joueur
    private boolean toucheGauche, toucheDroite, toucheHaut, toucheBas;

    public Avatar() {
        // Initialisation de la position de départ
        this.x = 100;
        this.y = 100;
        
        this.toucheGauche = false;
        this.toucheDroite = false;
        this.toucheHaut = false;
        this.toucheBas = false;
        
        // Pour l'instant, pas de vraie image, tu pourras charger un sprite plus tard
        // this.sprite = ImageIO.read(getClass().getResource("../resources/carre.png"));
    }

    public void miseAJour() {
        // Mise à jour de la position selon la touche appuyée
        if (this.toucheGauche) { x -= 5; }
        if (this.toucheDroite) { x += 5; }
        if (this.toucheHaut) { y -= 5; }
        if (this.toucheBas) { y += 5; }
        
        // Il faudra ajouter ici des limites pour ne pas sortir de la carte (0 à largeur/hauteur)
    }

    public void rendu(Graphics2D contexte) {
        // Dessine l'avatar à sa position (si tu as un sprite)
        // contexte.drawImage(this.sprite, (int) x, (int) y, null);
        
        // En attendant d'avoir une image, tu peux dessiner un simple carré de couleur :
        contexte.fillRect((int)x, (int)y, 30, 30); 
    }

    // --- Méthodes pour recevoir les commandes du clavier ---
    public void setToucheGauche(boolean etat) { this.toucheGauche = etat; }
    public void setToucheDroite(boolean etat) { this.toucheDroite = etat; }
    public void setToucheHaut(boolean etat) { this.toucheHaut = etat; }
    public void setToucheBas(boolean etat) { this.toucheBas = etat; }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
}