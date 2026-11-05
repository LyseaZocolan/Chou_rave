/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chou_rave;

/**
 *
 * @author jbelot
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


public class fenetregrapique extends JFrame implements ActionListener, KeyListener {
    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Jeu jeu;
    private Timer timer;

    public fenetregrapique() {
        // Configuration de la fenêtre[cite: 16]
        this.setTitle("Test Avatar - Déplacement avec ZQSD");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new Dimension(400, 400));
        this.setContentPane(this.jLabel1);
        this.pack(); // Ajuste la fenêtre à la dimension du JLabel
        
        // Double buffering (évite les clignotements visuels)[cite: 16]
        this.framebuffer = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics();
        
        this.jeu = new Jeu();
        
        // Ajout de l'écouteur clavier pour détecter les frappes[cite: 22]
        this.addKeyListener(this); 
        
        // Lancement de la boucle de jeu à 25 images par seconde (40 ms)[cite: 18]
        this.timer = new Timer(40, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 1. On dessine un fond "Map vide" vert très clair avant chaque image
        contexte.setColor(new Color(220, 240, 220)); 
        contexte.fillRect(0, 0, 400, 400);
        
        // 2. On met à jour et on dessine le jeu par-dessus[cite: 18]
        this.jeu.miseAJour();
        this.jeu.rendu(contexte);
        this.jLabel1.repaint();
    }

    // --- Gestion du Clavier (Z, Q, S, D) ---
    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_D) { 
            this.jeu.getAvatar().setToucheDroite(true);
        }
        if (evt.getKeyCode() == KeyEvent.VK_Q) { 
            this.jeu.getAvatar().setToucheGauche(true); 
        }
        if (evt.getKeyCode() == KeyEvent.VK_Z) { 
            this.jeu.getAvatar().setToucheHaut(true); 
        }
        if (evt.getKeyCode() == KeyEvent.VK_S) {
            this.jeu.getAvatar().setToucheBas(true); 
        }
        if (evt.getKeyCode() == KeyEvent.VK_E) { 
            this.jeu.getAvatar().ramasserNourriture("Carotte"); 
        }
        if (evt.getKeyCode() == KeyEvent.VK_R) { 
            this.jeu.essayerDeposerNourriture(); 
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_D) { this.jeu.getAvatar().setToucheDroite(false); }
        if (evt.getKeyCode() == KeyEvent.VK_Q) { this.jeu.getAvatar().setToucheGauche(false); }
        if (evt.getKeyCode() == KeyEvent.VK_Z) { this.jeu.getAvatar().setToucheHaut(false); }
        if (evt.getKeyCode() == KeyEvent.VK_S) { this.jeu.getAvatar().setToucheBas(false); }
    }

    @Override
    public void keyTyped(KeyEvent evt) {
        // Inutilisé mais obligatoire[cite: 22]
    }

    // Lancement du test
    public static void main(String[] args) {
        fenetregrapique fenetre = new fenetregrapique();
        fenetre.setLocationRelativeTo(null); // Centre la fenêtre à l'écran
        fenetre.setVisible(true);
    }
}