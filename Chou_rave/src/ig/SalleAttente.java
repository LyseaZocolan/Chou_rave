/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ig;

/**
 *
 * @author ntessier
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Écran de la Salle d'attente
 * Développé par le PO
 */
public class SalleAttente extends JFrame {

    private JLabel lblStatut;
    private Timer timerVerification;

    // Constructeur qui récupère le pseudo et l'animal du joueur depuis l'écran d'accueil
    public SalleAttente(String pseudo, String animal) {
        setTitle("Salle d'attente - De la Terre à l'Assiette");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer à l'écran
        setResizable(false);

        // 1. Panneau principal avec l'image de fond absolue
        PanneauFondLobby mainPanel = new PanneauFondLobby("Z:\\Desktop\\Info_Projet\\Chou_rave\\Image Interface\\Fond Interface Graphique.jpg");
        mainPanel.setLayout(new GridBagLayout()); // Idéal pour tout centrer

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // 2. Texte principal "Salle d'attente"
        JLabel lblTitre = new JLabel("Salle d'attente", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 45));
        lblTitre.setForeground(Color.WHITE);
        
        // Texte récapitulatif du joueur
        JLabel lblJoueur = new JLabel("En attente des autres joueurs pour " + pseudo + " (" + animal + ")...", SwingConstants.CENTER);
        lblJoueur.setFont(new Font("Arial", Font.ITALIC, 16));
        lblJoueur.setForeground(new Color(230, 230, 230));

        // 3. Petit rond qui tourne (Spinner personnalisé)
        SpinnerChargement spinner = new SpinnerChargement();

        // 4. Compteur de joueurs connectés
        lblStatut = new JLabel("Joueurs connectés : 1 / 4", SwingConstants.CENTER);
        lblStatut.setFont(new Font("Arial", Font.BOLD, 20));
        lblStatut.setForeground(Color.YELLOW);

        // Ajout des composants au centre de l'image
        gbc.gridy = 0; mainPanel.add(lblTitre, gbc);
        gbc.gridy = 1; mainPanel.add(lblJoueur, gbc);
        gbc.gridy = 2; mainPanel.add(spinner, gbc);
        gbc.gridy = 3; mainPanel.add(lblStatut, gbc);

        setContentPane(mainPanel);

        // 5. Lancement de la vérification régulière (Timer)
        demarrerVerificationJoueurs();
    }

    /**
     * Utilisation d'un Timer pour vérifier toutes les secondes si les 4 joueurs sont là.
     */
    private void demarrerVerificationJoueurs() {
        // Variable pour simuler l'arrivée des joueurs (à remplacer par la BDD)
        final int[] joueursActuels = {1}; 

        // Le Timer s'exécute toutes les 1000 millisecondes (1 seconde)
        timerVerification = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // TODO SCRUM MASTER : Intégration JDBC
                // C'est ici que le Scrum Master doit faire sa requête SQL : 
                // SELECT COUNT(*) FROM dresseurs;
                
                // --- SIMULATION POUR LE TEST VISUEL ---
                if (joueursActuels[0] < 4) {
                    //joueursActuels[0]++; // Un joueur arrive (simulation)
                    lblStatut.setText("Joueurs connectés : " + joueursActuels[0] + " / 4");
                }

                // Quand les 4 joueurs sont là
                if (joueursActuels[0] >= 4) {
                    timerVerification.stop(); 
                    
                    JOptionPane.showMessageDialog(SalleAttente.this, 
                            "La partie est complète ! Lancement du jeu...", 
                            "C'est parti !", JOptionPane.INFORMATION_MESSAGE); 
                    
                    // TODO DEVS AVATAR/PLATEFORME : Lancer le "vrai" jeu ici (FenetreDeJeuGUI)
                    
                    
                    dispose(); // Fermer la salle d'attente
                }
            }
        });
        timerVerification.start();
    }
}

/**
 * Composant graphique créant un "rond qui tourne" pour faire patienter.
 */
class SpinnerChargement extends JPanel {
    private int angle = 0;

    public SpinnerChargement() {
        setOpaque(false); // Fond transparent
        setPreferredSize(new Dimension(80, 80));

        // Timer très rapide (50ms) pour fluidifier l'animation de rotation
        Timer timerAnimation = new Timer(50, e -> {
            angle -= 15; // Fait tourner le cercle
            if (angle <= -360) angle = 0;
            repaint(); // Redessine le composant
        });
        timerAnimation.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Lissage des bords du cercle
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(6)); // Épaisseur du trait
        
        // Arc de cercle (pas complet, pour voir la rotation)
        g2d.drawArc(10, 10, 60, 60, angle, 280); 
    }
}

/**
 * Recopie de la classe PanneauFond pour la salle d'attente (Chemin absolu).
 */
class PanneauFondLobby extends JPanel {
    private Image imageFond;

    public PanneauFondLobby(String cheminAbsolu) {
        try {
            File fichierImage = new File(cheminAbsolu); 
            if(fichierImage.exists()) {
                imageFond = ImageIO.read(fichierImage); 
            }
        } catch (Exception e) {
            System.err.println("Image introuvable");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageFond != null) {
            g.drawImage(imageFond, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}