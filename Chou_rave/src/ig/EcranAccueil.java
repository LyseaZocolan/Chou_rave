/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ig;

/**
 *
 * @author ntessier
 */


import chou_rave.JoueurSql;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class EcranAccueil extends JFrame implements ActionListener {

    private JButton btnJouer;
    private JButton btnQuitter;
    private JTextField txtPseudo;
    private JoueurSql bdd = new JoueurSql();

    // TODO : Modifiez ces chemins avec ceux de votre ordinateur
    private String cheminFond = "Z:\\Desktop\\VERT\\Info_Projet\\Images\\Ecran accueil.jpg";
    private String cheminBoutonJouer = "Z:\\Desktop\\VERT\\Info_Projet\\Images\\Bouton Jouer.png";
    private String cheminBoutonQuitter = "Z:\\Desktop\\VERT\\Info_Projet\\Images\\Bouton Quitter.png";

    public EcranAccueil() {
        setTitle("De la Terre à l'Assiette - Accueil");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Fond d'écran
        PanneauFond mainPanel = new PanneauFond(cheminFond);
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 10, 20));

        

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panneau des boutons au centre/bas
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        
        btnJouer = creerBoutonImage(cheminBoutonJouer, "JOUER");
        btnJouer.addActionListener(this);
        buttonPanel.add(btnJouer);
        
        btnQuitter = creerBoutonImage(cheminBoutonQuitter, "QUITTER");
        btnQuitter.addActionListener(this);
        buttonPanel.add(btnQuitter);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    /**
     * Méthode pour créer un bouton à partir d'une image stockée sur le disque
     */
    private JButton creerBoutonImage(String cheminAbsolu, String texteSecours) {
        JButton bouton = new JButton();
        try {
            File fichierImg = new File(cheminAbsolu);
            if (fichierImg.exists()) {
                Image img = ImageIO.read(fichierImg);
                
                // Redimensionnement de l'image du bouton (Largeur 200, Hauteur 60)
                Image imgRedimensionnee = img.getScaledInstance(200, 60, Image.SCALE_SMOOTH); 
                
                bouton.setIcon(new ImageIcon(imgRedimensionnee));
                bouton.setContentAreaFilled(false);
                bouton.setBorderPainted(false);
                bouton.setFocusPainted(false);
                bouton.setOpaque(false);
            } else {
                // Si l'image n'est pas trouvée, on met du texte classique
                bouton.setText(texteSecours); 
                bouton.setFont(new Font("Arial", Font.BOLD, 20));
            }
        } catch (Exception e) {
            bouton.setText(texteSecours); 
            bouton.setFont(new Font("Arial", Font.BOLD, 20));
        }
        bouton.setPreferredSize(new Dimension(200, 60)); 
        return bouton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnJouer) {
            SelectionPersonnage selection = new SelectionPersonnage();
            selection.setVisible(true); 
            this.dispose(); 
        } else if (e.getSource() == btnQuitter) {
            System.exit(0); // Ferme complètement le programme
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EcranAccueil().setVisible(true));
    }
}

/**
 * Classe interne pour gérer l'image de fond
 */
class PanneauFond extends JPanel {
    private Image imageFond;

    public PanneauFond(String cheminAbsoluImage) {
        try {
            File fichierImage = new File(cheminAbsoluImage);
            if(fichierImage.exists()) {
                imageFond = ImageIO.read(fichierImage); 
            }
        } catch (Exception e) {
            System.err.println("Erreur image de fond : " + e.getMessage());
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