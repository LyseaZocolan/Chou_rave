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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Exemple de fenetre de jeu en utilisant uniquement des commandes
 *
 * @author guillaume.laurent
 */
public class FenetreDeJeu2 extends JFrame implements ActionListener, KeyListener {

    private BufferedImage buffer;
    private Graphics2D contexteBuffer;
    private Jeu3 jeu;
    private Timer timer;
    private JLabel jLabel1;
    private Avatar monAvatar;

    public FenetreDeJeu2() {
        // initialisation de la fenetre
        this.setTitle("TileMupping");
        this.setSize(1600, 1024);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(1600, 1024));
        this.setContentPane(this.jLabel1);
        this.pack();

        // Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.buffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(buffer));
        this.contexteBuffer = this.buffer.createGraphics();

        // Creation du jeu
        this.jeu = new Jeu3();
        
        this.addKeyListener(this);
        this.setFocusable(true);

        // Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(40, this);
        this.timer.start();
    }

    // Methode appelee par le timer et qui contient la boucle de jeu
    @Override
    public void actionPerformed(ActionEvent e) {
        this.jeu.miseAJour();
        this.jeu.rendu(contexteBuffer);
        this.jLabel1.repaint();
    }

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
    /**
     * @param args the command line argumentss
     */

}
