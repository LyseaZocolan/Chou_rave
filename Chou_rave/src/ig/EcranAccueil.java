/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ig;

/**
 *
 * @author ntessier
 */

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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class EcranAccueil extends JFrame implements ActionListener { 

    private JButton btnJouer;
    private JButton btnQuitter; // Ajout du bouton quitter

    public EcranAccueil() {
        setTitle("De la Terre à l'Assiette - Accueil");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Fond d'écran
        PanneauFond mainPanel = new PanneauFond("Z:\\Desktop\\Info_Projet\\Chou_rave\\Image Interface\\Fond Interface Graphique.jpg");
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));

        // Titre
        JLabel lblTitre = new JLabel("De la Terre à l'Assiette", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 40));
        lblTitre.setForeground(Color.WHITE);
        
        // Sous-titre
        JLabel lblSousTitre = new JLabel("Vole la nourriture au fermier !", SwingConstants.CENTER);
        lblSousTitre.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
        lblSousTitre.setForeground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(lblTitre, BorderLayout.NORTH);
        headerPanel.add(lblSousTitre, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panneau des boutons au centre/bas
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        
        btnJouer = creerBoutonImage("/resources/bouton_play.png", "JOUER");
        btnJouer.addActionListener(this);
        buttonPanel.add(btnJouer);
        
        btnQuitter = creerBoutonImage("/resources/bouton_quitter.png", "QUITTER");
        btnQuitter.addActionListener(this);
        buttonPanel.add(btnQuitter);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    private JButton creerBoutonImage(String chemin, String texteSecours) {
        JButton bouton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource(chemin)); 
            bouton.setIcon(new ImageIcon(img));
            bouton.setContentAreaFilled(false);
            bouton.setBorderPainted(false);
            bouton.setFocusPainted(false);
            bouton.setOpaque(false);
        } catch (Exception e) {
            bouton.setText(texteSecours); 
            bouton.setFont(new Font("Arial", Font.BOLD, 20));
        }
        bouton.setPreferredSize(new Dimension(150, 60));
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

class PanneauFond extends JPanel {
    private Image imageFond;

    public PanneauFond(String cheminAbsoluImage) {
        try {
            File fichierImage = new File(cheminAbsoluImage);
            if(fichierImage.exists()) {
                imageFond = ImageIO.read(fichierImage); 
            }
        } catch (Exception e) {
            System.err.println("Erreur image : " + e.getMessage());
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