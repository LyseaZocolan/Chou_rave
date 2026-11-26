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
    private double x, y;
    private BufferedImage sprite;
    private boolean toucheGauche, toucheDroite, toucheHaut, toucheBas;
    private double vitesse = 5.0;
    private Avatar monAvatar;
    private double largeur = 32.0;
    private double hauteur = 32.0;
    private String pseudo;
    private int score;

    // Nouvelles caractéristiques pour la survie
    private double enduranceMax = 1000.0;
    private double enduranceActuelle;
    
    // Inventaire
    private int capaciteSac = 3;
    private ArrayList<String> inventaire;
    
    private int viesMax = 3;
    private int vies = 3;
    private int compteurInvincibilite = 0; 
    
    protected int dureePouvoir = 0;
    protected int tempsRecharge = 0;
    protected String nomPouvoir = "Aucun";
    

    public Avatar() {
        this("/Images/Lapin.png");
    }

    // Constructeur utilisé par les 5 animaux
    public Avatar(String cheminImage) {
        this.x = 185;
        this.y = 185;
        this.enduranceActuelle = this.enduranceMax;
        this.inventaire = new ArrayList<>();
        chargerImage(cheminImage);
    }

    protected void chargerImage(String cheminImage) {
        try {
            URL urlImage = getClass().getResource(cheminImage);
            if (urlImage != null) {
                this.sprite = ImageIO.read(urlImage);
            } else {
                System.out.println("Erreur : Image introuvable -> " + cheminImage);
                this.sprite = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
            this.sprite = null;
        }
    }

    // --- Méthodes destinées à être redéfinies par chaque animal ---
    public void activerPouvoir() {
        // Redéfini dans les sous-classes
    }

    public boolean peutNager() {
        return false; // Seul le Lapin renvoie true
    }

    public boolean estInvisible() {
        return false; // La Taupe renvoie true quand elle est sous terre
    }

    public boolean estEnAttaque() {
        return false; // Le Sanglier renvoie true quand il charge
    }

    // --- Définition des zones d'eau (en cases de 32x32 pixels) ---
    public boolean estDansEau(double posX, double posY) {
        int caseX = (int) ((posX + largeur / 2) / 32);
        int caseY = (int) ((posY + hauteur / 2) / 32);

        // Reprend la rivière horizontale définie dans JeuFermier (y entre 11 et 14)
        boolean riviere = (caseY >= 11 && caseY <= 14);
        // Tu peux ajouter d'autres zones d'eau ici (ex: une mare) :
        boolean mare = (caseX >= 1 && caseX <= 5 && caseY >= 7 && caseY <= 8);

        return riviere || mare;
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
        if (dureePouvoir > 0){
            dureePouvoir--;
        }
        if (tempsRecharge > 0){
            tempsRecharge--;
        }
        
        boolean veutBouger = toucheGauche || toucheDroite || toucheHaut || toucheBas;

        // Condition de mouvement : vouloir bouger ET avoir de l'endurance
        if (veutBouger && enduranceActuelle > 0) {
            double nextX = this.x;
            double nextY = this.y;
            if (this.toucheGauche) { nextX -= vitesse; }
            if (this.toucheDroite) { nextX += vitesse; }
            if (this.toucheHaut) { nextY -= vitesse; }
            if (this.toucheBas) { nextY += vitesse; }
            
            // Vérification de l'eau (bloque les animaux qui ne savent pas nager)
            if (!estDansEau(nextX, this.y) || peutNager()) {
                this.x = nextX;
            }
            if (!estDansEau(this.x, nextY) || peutNager()) {
                this.y = nextY;
            }
            
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
        if (x > 1024) x = 1024; 
        if (y < 0) y = 0;
        if (y > 1024) y = 1024; 
    }
    
    // Séparé de rendu() pour que Taupe/Sanglier/Lapin puissent modifier leur visuel sans casser le HUD
    protected void dessinerApparence(Graphics2D contexte) {
        if (this.sprite != null) {
            contexte.drawImage(this.sprite, (int) x, (int) y, (int) largeur, (int) hauteur, null);
        } else {
            contexte.setColor(Color.MAGENTA);
            contexte.fillRect((int) x, (int) y, (int) largeur, (int) hauteur);
        }
    }

    public void rendu(Graphics2D contexte) {
        // 1. Sprite de l'animal (clignote pendant l'invincibilité)
        boolean afficherSprite = (compteurInvincibilite == 0) || (compteurInvincibilite % 6 < 3);
        if (afficherSprite) {
            dessinerApparence(contexte);
        }

        // 2. Jauge d'endurance
        contexte.setColor(Color.RED);
        contexte.fillRect((int) x, (int) y - 10, (int) largeur, 5);
        contexte.setColor(Color.GREEN);
        int largeurBarre = (int) ((enduranceActuelle / enduranceMax) * largeur);
        contexte.fillRect((int) x, (int) y - 10, largeurBarre, 5);

        // 3. Inventaire
        contexte.setColor(Color.BLACK);
        contexte.drawString("Sac à dos : " + inventaire.size() + " / " + capaciteSac, 10, 20);
        for (int i = 0; i < capaciteSac; i++) {
            if (i < inventaire.size()) {
                contexte.setColor(Color.ORANGE);
                contexte.fillRect(10 + (i * 30), 30, 25, 25);
            } else {
                contexte.setColor(Color.GRAY);
                contexte.drawRect(10 + (i * 30), 30, 25, 25);
            }
        }

        // 4. Vies
        contexte.setColor(Color.BLACK);
        contexte.drawString("Vies : " + vies + " / " + viesMax, 10, 75);
        for (int i = 0; i < viesMax; i++) {
            if (i < vies) {
                contexte.setColor(Color.RED);
                contexte.fillOval(10 + (i * 25), 82, 18, 18);
            } else {
                contexte.setColor(Color.GRAY);
                contexte.drawOval(10 + (i * 25), 82, 18, 18);
            }
        }

        // 5. Statut du pouvoir spécial
        contexte.setColor(Color.BLACK);
        String etatPouvoir = (dureePouvoir > 0) ? "ACTIF" : (tempsRecharge == 0 ? "PRÊT [ESPACE]" : "Recharge (" + (tempsRecharge / 25 + 1) + "s)");
        contexte.drawString("Pouvoir (" + nomPouvoir + ") : " + etatPouvoir, 10, 120);

        // 6. Game Over
        if (!estVivant()) {
            contexte.setColor(new Color(0, 0, 0, 160));
            contexte.fillRect(0, 0, 1024, 1024);
            contexte.setColor(Color.RED);
            contexte.drawString("GAME OVER - Vous avez été attrapé !", 400, 500);
        }
    }
    
    // --- Vies, collisions et inventaire ---
    public void perdreVie() {
        if (compteurInvincibilite == 0 && vies > 0 && !estInvisible() && !estEnAttaque()) {
            this.vies--;
            this.compteurInvincibilite = 50;
            System.out.println("Touché ! Vies restantes : " + this.vies);
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
    
    public boolean estVivant() {
        return this.vies > 0;
    }

    public int getVies() {
        return this.vies;
    }


    public String getPseudo() {
        return pseudo;
    }
    
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getScore() {
        return score;
    }

    public int getPositionX() {
        return (int) this.x;   // le vrai nom de l'attribut modifié par setX
    }

    public int getPositionY() {
        return (int) this.y;   // le vrai nom de l'attribut modifié par setY
    }
 }