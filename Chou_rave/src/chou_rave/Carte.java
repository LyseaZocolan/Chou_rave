/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chou_rave;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
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
            String fichierCarte = (nomFichier != null && !nomFichier.isEmpty()) ? nomFichier : "carte(5).txt";

            // 1. Lecture du fichier texte de la carte (avec recherche dans les packages)
            InputStream fluxCarte = ouvrirFluxCarte(fichierCarte);
            if (fluxCarte == null) {
                System.err.println("ERREUR : Fichier carte introuvable -> " + fichierCarte);
                return;
            }
            BufferedReader lecteur = new BufferedReader(new InputStreamReader(fluxCarte));

            this.largeur = 50;
            this.hauteur = 32;
            this.tailleTuile = 32;

            // 2. Chargement du tileset
            BufferedImage tileset = chargerTileset("tileset(4).png");
            if (tileset == null) {
                System.err.println("ERREUR : Tileset introuvable -> tileset(4).png");
                lecteur.close();
                return;
            }

            int nbColonnesTileset = 6;
            int totalTuiles = 24;
            this.tuiles = new BufferedImage[totalTuiles];

            for (int i = 0; i < tuiles.length; i++) {
                int x = (i % nbColonnesTileset) * tailleTuile;
                int y = (i / nbColonnesTileset) * tailleTuile;
                tuiles[i] = tileset.getSubimage(x, y, tailleTuile, tailleTuile);
            }

            // 3. Lecture de la matrice du décor ligne par ligne
            this.decor = new int[hauteur][largeur];
            for (int j = 0; j < hauteur; j++) {
                String ligne = lecteur.readLine();
                if (ligne == null) break;
                String[] tuilesLigne = ligne.trim().split("\\s+");
                for (int i = 0; i < largeur && i < tuilesLigne.length; i++) {
                    decor[j][i] = Integer.parseInt(tuilesLigne[i]);
                }
            }

            lecteur.close();

        } catch (Exception ex) {
            Logger.getLogger(Carte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private InputStream ouvrirFluxCarte(String nom) throws Exception {
        // Test via le classpath (build/classes)
        String[] cheminsRessources = { nom, "/chou_rave/" + nom, "/resources/" + nom, "/ig/" + nom };
        for (String chemin : cheminsRessources) {
            InputStream is = getClass().getResourceAsStream(chemin);
            if (is != null) return is;
        }
        // Secours en chemin relatif dans src/
        String[] cheminsLocaux = { "src/chou_rave/" + nom, "src/resources/" + nom, "src/ig/" + nom, nom };
        for (String chemin : cheminsLocaux) {
            File f = new File(chemin);
            if (f.exists()) return new FileInputStream(f);
        }
        return null;
    }

    private BufferedImage chargerTileset(String nom) throws Exception {
        String[] cheminsRessources = { "/ig/" + nom, "/resources/" + nom, "/chou_rave/" + nom };
        for (String chemin : cheminsRessources) {
            URL url = getClass().getResource(chemin);
            if (url != null) return ImageIO.read(url);
        }
        String[] cheminsLocaux = { "src/ig/" + nom, "src/resources/" + nom, "src/chou_rave/" + nom };
        for (String chemin : cheminsLocaux) {
            File f = new File(chemin);
            if (f.exists()) return ImageIO.read(f);
        }
        return null;
    }

    public void rendu(Graphics2D contexte) {
        if (decor == null || tuiles == null) {
            return;
        }
        for (int j = 0; j < hauteur; j++) {
            for (int i = 0; i < largeur; i++) {
                int numTuile = decor[j][i];
                if (numTuile >= 0 && numTuile < tuiles.length) {
                    contexte.drawImage(tuiles[numTuile], i * tailleTuile, j * tailleTuile, null);
                }
            }
        }
    }

    public void miseAJour() {
        // La carte est statique, rien à mettre à jour
    }
}