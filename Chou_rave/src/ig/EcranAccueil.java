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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class EcranAccueil extends JFrame implements ActionListener {

    private JButton btnJouer;
    private JButton btnQuitter;

    // Si tu veux ton image "Chou-Rave!", remplace par "/resources/Ecran accueil.jpg"
    private String cheminFond = "/resources/Ecran accueil.jpg";
    private String cheminBoutonJouer = "/resources/Bouton Jouer.png";
    private String cheminBoutonQuitter = "/resources/Bouton Quitter.png";
    // Chemins vers le package "Images" (vérifie les noms exacts et extensions)

   

    

    public EcranAccueil() {
        setTitle("De la Terre à l'Assiette - Accueil");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PanneauFond mainPanel = new PanneauFond(cheminFond);
        mainPanel.setLayout(new BorderLayout(10, 10));
        // Le 3ème chiffre (15) gère la hauteur des boutons par rapport au bas de la fenêtre
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));

        // Panneau des boutons en bas, côte à côte
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
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
     * Charge une image depuis le package resources (marche sur tous les PC,
     * même sans Clean and Build préalable).
     */
    public static Image chargerImage(String cheminRessource) {
        try {
            // 1. Méthode standard (quand le projet est compilé / en JAR)
            URL urlImg = EcranAccueil.class.getResource(cheminRessource);
            if (urlImg != null) {
                return ImageIO.read(urlImg);
            }
            // 2. Secours en chemin relatif dans le dossier src du projet
            File fichierLocal = new File("src" + cheminRessource);
            if (fichierLocal.exists()) {
                return ImageIO.read(fichierLocal);
            }
             // 3. Chemin absolu (ancienne méthode)
            File fichierAbsolu = new File(cheminRessource);
            if (fichierAbsolu.exists()) {
                return ImageIO.read(fichierAbsolu);
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement image : " + cheminRessource);
        }
        System.err.println("Image introuvable : " + cheminRessource);
        return null;
    }

    private JButton creerBoutonImage(String cheminRessource, String texteSecours) {
        JButton bouton = new JButton();
        Image img = chargerImage(cheminRessource);
        if (img != null) {
            Image imgRedimensionnee = img.getScaledInstance(200, 60, Image.SCALE_SMOOTH);
            bouton.setIcon(new ImageIcon(imgRedimensionnee));
            bouton.setContentAreaFilled(false);
            bouton.setBorderPainted(false);
            bouton.setFocusPainted(false);
            bouton.setOpaque(false);
        } else {
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
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EcranAccueil().setVisible(true));
    }
}

/**
 * Panneau de fond réutilisé par EcranAccueil et SelectionPersonnage
 */
class PanneauFond extends JPanel {
    private Image imageFond;

    public PanneauFond(String cheminRessource) {
        this.imageFond = EcranAccueil.chargerImage(cheminRessource);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageFond != null) {
            g.drawImage(imageFond, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}