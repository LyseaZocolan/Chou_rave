/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chou_rave;

/**
 *
 * @author ariou
 */
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Carte {
    private int largeur;
    private int hauteur;
    private int tailleTuile;
    private int[][] decor;
    private BufferedImage[] tuiles;

    public Carte(String nomFichier) {
        try {
            // 1. Lecture du fichier texte de la carte
        BufferedReader lecteur = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("carte(5).txt")));
        
        // D'après ta matrice, la carte fait 32 par 32 tuiles et chaque tuile fait 32 pixels
        this.largeur = 32;
        this.hauteur = 32;
        this.tailleTuile = 32;
        
        // 2. Chargement de l'image (le tileset) - C'est la ligne qui a disparu !
        BufferedImage tileset = ImageIO.read(getClass().getResource("tileset(4).png"));
            
            // Nombre de colonnes de tuiles dans ton image tileset(4).png (ajuste si nécessaire)
            int nbColonnesTileset = 6; 
            int totalTuiles = 24; // Nombre total de tuiles découpées estimé
            this.tuiles = new BufferedImage[totalTuiles];
            
            // Découpage automatique du tileset en petites images de 32x32 pixels
            for (int i = 0; i < tuiles.length; i++) {
                int x = (i % nbColonnesTileset) * tailleTuile;
                int y = (i / nbColonnesTileset) * tailleTuile;
                tuiles[i] = tileset.getSubimage(x, y, tailleTuile, tailleTuile);
            }
            
            // 3. Lecture de la matrice du décor ligne par ligne depuis le fichier texte
            this.decor = new int[hauteur][largeur];
            for (int j = 0; j < hauteur; j++) {
                String ligne = lecteur.readLine();
                String[] tuilesLigne = ligne.trim().split("\\s+");
                for (int i = 0; i < largeur; i++) {
                    decor[j][i] = Integer.parseInt(tuilesLigne[i]);
                }
            }
            
            lecteur.close();
            
        } catch (Exception ex) {
            Logger.getLogger(Carte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Méthode pour dessiner la carte à l'écran
    public void rendu(Graphics2D contexte) {
        
        for (int j = 0; j < hauteur; j++) {
            for (int i = 0; i < largeur; i++) {
                int numTuile = decor[j][i];
                // Dessine la tuile correspondante aux coordonnées (i * taille, j * taille)
                contexte.drawImage(tuiles[numTuile], i * tailleTuile, j * tailleTuile, null);
            }
        }
    }
    public void miseAJour() {
    // La carte est statique, rien à mettre à jour
}
}