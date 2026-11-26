/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chou_rave;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class fenetregrapique extends JFrame implements ActionListener, KeyListener {

    private static final int LARGEUR_FENETRE = 960;
    private static final int HAUTEUR_FENETRE = 640;

    private BufferedImage framebuffer;
    private JLabel ecran;
    private Timer timer;

    private Avatar monAvatar;
    private ArrayList<FermierTest> listeFermiers;
    private Rectangle zoneTerrier;

    public fenetregrapique() {
        this.setTitle("Chou_rave - Bac à sable de test");
        this.setSize(LARGEUR_FENETRE, HAUTEUR_FENETRE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.framebuffer = new BufferedImage(LARGEUR_FENETRE, HAUTEUR_FENETRE, BufferedImage.TYPE_INT_ARGB);
        this.ecran = new JLabel(new ImageIcon(this.framebuffer));
        this.setContentPane(this.ecran);

        // Initialisation des éléments de test
        this.monAvatar = new Lapin(); // Personnage par défaut au lancement
        this.zoneTerrier = new Rectangle(64, 128, 64, 64);

        this.listeFermiers = new ArrayList<>();
        // Fermier 1 : taille standard 32x32 au-dessus de la rivière
        this.listeFermiers.add(new FermierTest(400, 200, 32, 32, 3.0, 260, 750));
        // Fermier 2 : grand fermier 48x48 sous la rivière
        this.listeFermiers.add(new FermierTest(300, 510, 48, 48, 2.5, 100, 750));

        // Écouteur clavier + focus forcé
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setVisible(true);
        this.requestFocusInWindow();

        // Boucle de jeu à 25 FPS (40 ms), alignée sur les durées des pouvoirs
        this.timer = new Timer(40, this);
        this.timer.start();
    }

    private void changerPersonnage(Avatar nouvelAvatar) {
        double ancienX = this.monAvatar.getX();
        double ancienY = this.monAvatar.getY();
        this.monAvatar = nouvelAvatar;
        // Conserve la position actuelle, ou réapparaît au départ si Game Over
        if (this.monAvatar.estDansEau(ancienX, ancienY) && !this.monAvatar.peutNager()) {
            this.monAvatar.setPosition(185, 185);
        } else {
            this.monAvatar.setPosition(ancienX, ancienY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        miseAJour();
        dessiner();
        this.ecran.repaint();
    }

    private void miseAJour() {
        this.monAvatar.miseAJour();

        // Mise à jour des fermiers de test
        for (FermierTest fermier : listeFermiers) {
            fermier.miseAJour();
        }

        // 1. Interaction œufs de la Poule -> ralentissement des fermiers
        if (this.monAvatar instanceof Poule) {
            Poule poule = (Poule) this.monAvatar;
            for (Oeuf oeuf : poule.getOeufs()) {
                if (oeuf.isActif()) {
                    for (FermierTest fermier : listeFermiers) {
                        if (oeuf.getHitbox().intersects(fermier.getHitbox())) {
                            fermier.ralentir(100); // 4 secondes
                            oeuf.desactiver();
                            System.out.println("Fermier ralenti par un œuf !");
                        }
                    }
                }
            }
        }

        // 2. Collisions Avatar <-> Fermiers
        for (FermierTest fermier : listeFermiers) {
            if (fermier.estEtourdi()) {
                continue;
            }
            if (this.monAvatar.getHitbox().intersects(fermier.getHitbox())) {
                if (this.monAvatar.estInvisible()) {
                    continue; // Taupe sous terre
                } else if (this.monAvatar.estEnAttaque()) {
                    fermier.etourdir(75); // Sanglier en charge (3s)
                    System.out.println("Fermier assommé par le sanglier !");
                } else {
                    this.monAvatar.perdreVie();
                }
            }
        }
    }

    private void dessiner() {
        Graphics2D g = this.framebuffer.createGraphics();

        // 1. Fond d'herbe
        g.setColor(new Color(120, 185, 95));
        g.fillRect(0, 0, LARGEUR_FENETRE, HAUTEUR_FENETRE);

        // 2. Grille 32x32 + dessin des zones d'eau correspondant à Avatar.estDansEau()
        int nbCols = LARGEUR_FENETRE / 32;
        int nbLignes = HAUTEUR_FENETRE / 32;
        for (int cx = 0; cx < nbCols; cx++) {
            for (int cy = 0; cy < nbLignes; cy++) {
                boolean riviere = (cy >= 11 && cy <= 14);
                boolean mare = (cx >= 1 && cx <= 5 && cy >= 7 && cy <= 8);
                if (riviere || mare) {
                    g.setColor(new Color(55, 145, 225));
                    g.fillRect(cx * 32, cy * 32, 32, 32);
                }
                g.setColor(new Color(0, 0, 0, 25));
                g.drawRect(cx * 32, cy * 32, 32, 32);
            }
        }

        // 3. Terrier de test (pour déposer la nourriture avec R)
        g.setColor(new Color(120, 80, 40));
        g.fillOval(zoneTerrier.x, zoneTerrier.y, zoneTerrier.width, zoneTerrier.height);
        g.setColor(Color.WHITE);
        g.drawString("TERRIER", zoneTerrier.x + 6, zoneTerrier.y + 36);

        // 4. Fermiers de test
        for (FermierTest fermier : listeFermiers) {
            fermier.rendu(g);
        }

        // 5. Avatar + HUD (vies, sac, endurance, pouvoir, œufs)
        this.monAvatar.rendu(g);

        // 6. Aide commandes en haut à droite
        g.setColor(new Color(0, 0, 0, 170));
        g.fillRect(LARGEUR_FENETRE - 360, 10, 345, 78);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("ZQSD : Bouger | ESPACE : Pouvoir | E : Carotte | R : Terrier", LARGEUR_FENETRE - 350, 30);
        g.drawString("Changer animal : F1 Poule | F2 Sanglier | F3 Renard", LARGEUR_FENETRE - 350, 50);
        g.drawString("                 F4 Taupe | F5 Lapin  (T : Reset vies)", LARGEUR_FENETRE - 350, 70);

        g.dispose();
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        int code = evt.getKeyCode();

        // Déplacements
        if (code == KeyEvent.VK_D) this.monAvatar.setToucheDroite(true);
        if (code == KeyEvent.VK_Q) this.monAvatar.setToucheGauche(true);
        if (code == KeyEvent.VK_Z) this.monAvatar.setToucheHaut(true);
        if (code == KeyEvent.VK_S) this.monAvatar.setToucheBas(true);

        // Pouvoir spécial
        if (code == KeyEvent.VK_SPACE) {
            this.monAvatar.activerPouvoir();
        }

        // Ramasser / Déposer au terrier
        if (code == KeyEvent.VK_E) {
            this.monAvatar.ramasserNourriture("Carotte");
        }
        if (code == KeyEvent.VK_R) {
            if (this.monAvatar.getHitbox().intersects(zoneTerrier)) {
                this.monAvatar.deposerNourritureTerrier();
                System.out.println("Nourriture déposée dans le terrier !");
            } else {
                System.out.println("Approche-toi du terrier pour déposer !");
            }
        }

        // Changement de personnage en direct (F1 à F5 ou pavé numérique 1 à 5)
        if (code == KeyEvent.VK_F1 || code == KeyEvent.VK_NUMPAD1) changerPersonnage(new Poule());
        if (code == KeyEvent.VK_F2 || code == KeyEvent.VK_NUMPAD2) changerPersonnage(new Sanglier());
        if (code == KeyEvent.VK_F3 || code == KeyEvent.VK_NUMPAD3) changerPersonnage(new Renard());
        if (code == KeyEvent.VK_F4 || code == KeyEvent.VK_NUMPAD4) changerPersonnage(new Taupe());
        if (code == KeyEvent.VK_F5 || code == KeyEvent.VK_NUMPAD5) changerPersonnage(new Lapin());

        // Reset rapide après un Game Over
        if (code == KeyEvent.VK_T) {
            changerPersonnage(new Lapin());
            this.monAvatar.setPosition(185, 185);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        int code = evt.getKeyCode();
        if (code == KeyEvent.VK_D) this.monAvatar.setToucheDroite(false);
        if (code == KeyEvent.VK_Q) this.monAvatar.setToucheGauche(false);
        if (code == KeyEvent.VK_Z) this.monAvatar.setToucheHaut(false);
        if (code == KeyEvent.VK_S) this.monAvatar.setToucheBas(false);
    }

    @Override
    public void keyTyped(KeyEvent evt) {}

    // --- Classe interne temporaire simulant un fermier ---
    private static class FermierTest {
        private double x, y;
        private int largeur, hauteur;
        private double vitesseBase;
        private double minX, maxX;
        private int direction = 1;
        private int compteurRalentissement = 0;
        private int compteurEtourdi = 0;

        public FermierTest(double x, double y, int largeur, int hauteur, double vitesse, double minX, double maxX) {
            this.x = x;
            this.y = y;
            this.largeur = largeur;
            this.hauteur = hauteur;
            this.vitesseBase = vitesse;
            this.minX = minX;
            this.maxX = maxX;
        }

        public void ralentir(int dureeFrames) { this.compteurRalentissement = dureeFrames; }
        public void etourdir(int dureeFrames) { this.compteurEtourdi = dureeFrames; }
        public boolean estEtourdi() { return this.compteurEtourdi > 0; }

        public void miseAJour() {
            double mult = 1.0;
            if (compteurEtourdi > 0) {
                compteurEtourdi--;
                mult = 0.0;
            } else if (compteurRalentissement > 0) {
                compteurRalentissement--;
                mult = 0.3;
            }

            this.x += this.direction * this.vitesseBase * mult;
            if (this.x <= minX) { this.x = minX; this.direction = 1; }
            if (this.x >= maxX) { this.x = maxX; this.direction = -1; }
        }

        public void rendu(Graphics2D g) {
            if (compteurEtourdi > 0) {
                g.setColor(Color.YELLOW);
            } else if (compteurRalentissement > 0) {
                g.setColor(Color.CYAN);
            } else {
                g.setColor(new Color(180, 40, 40));
            }
            g.fillRect((int) x, (int) y, largeur, hauteur);
            g.setColor(Color.BLACK);
            g.drawRect((int) x, (int) y, largeur, hauteur);

            String etat = (compteurEtourdi > 0) ? "KO" : (compteurRalentissement > 0 ? "LENT" : "FERMIER");
            g.drawString(etat, (int) x, (int) y - 4);
        }

        public Rectangle getHitbox() {
            return new Rectangle((int) x, (int) y, largeur, hauteur);
        }
    }

    public static void main(String[] args) {
        new fenetregrapique();
    }
}