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

    // Chemins vers le package "Images" (vérifie les noms exacts et extensions)

    private String cheminFond = "/Images/Fond Interface Graphique.jpg";

    private String cheminBoutonJouer = "/Images/Bouton Jouer.png";
    private String cheminBoutonQuitter = "/Images/Bouton Quitter.png";

    public EcranAccueil() {
        setTitle("De la Terre à l'Assiette - Accueil");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Fond d'écran
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
     * Crée un bouton à partir d'une image stockée dans le package Images
     */
    private JButton creerBoutonImage(String cheminRessource, String texteSecours) {
        JButton bouton = new JButton();
        try {
            URL urlImg = getClass().getResource(cheminRessource);
            if (urlImg != null) {
                Image img = ImageIO.read(urlImg);

                // Dimensions du bouton (largeur 200, hauteur 60)
                Image imgRedimensionnee = img.getScaledInstance(200, 60, Image.SCALE_SMOOTH);

                bouton.setIcon(new ImageIcon(imgRedimensionnee));
                bouton.setContentAreaFilled(false);
                bouton.setBorderPainted(false);
                bouton.setFocusPainted(false);
                bouton.setOpaque(false);
            } else {
                System.err.println("Image bouton introuvable : " + cheminRessource);
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
        try {
            URL urlImg = getClass().getResource(cheminRessource);
            if (urlImg != null) {
                imageFond = ImageIO.read(urlImg);
            } else {
                System.err.println("Image de fond introuvable : " + cheminRessource);
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