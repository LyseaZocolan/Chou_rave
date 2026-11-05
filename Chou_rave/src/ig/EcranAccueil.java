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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Écran d'accueil du jeu "De la Terre à l'Assiette"
 */
public class EcranAccueil extends JFrame implements ActionListener { 

    private JTextField txtPseudo;
    private JRadioButton rbSanglier, rbPoule, rbVache, rbLapin;
    private ButtonGroup groupeAnimaux;
    private JButton btnJouer, btnQuitter;

    public EcranAccueil() {
        // 1. Configuration de la fenêtre principale (JFrame)
        setTitle("De la Terre à l'Assiette - Accueil");
        setSize(600, 500); // Fenêtre légèrement agrandie pour bien voir le fond
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 2. Panneau principal avec votre image de fond absolue[cite: 50]
        PanneauFond mainPanel = new PanneauFond("Z:\\Desktop\\Info_Projet\\Chou_rave\\Image Interface\\Fond Interface Graphique.jpg");
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 3. En-tête (Titres)
        JLabel lblTitre = new JLabel("De la Terre à l'Assiette", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitre.setForeground(new Color(255, 255, 255)); // Mis en blanc pour ressortir sur l'image
        
        JLabel lblSousTitre = new JLabel("Vole la nourriture au fermier !", SwingConstants.CENTER);
        lblSousTitre.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));
        lblSousTitre.setForeground(new Color(255, 255, 255)); // Mis en blanc
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false); // Rend le panneau transparent pour voir le fond
        headerPanel.add(lblTitre, BorderLayout.NORTH);
        headerPanel.add(lblSousTitre, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 4. Formulaire central (Pseudo + Animaux)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblPseudo = new JLabel("Pseudo du joueur :");
        lblPseudo.setFont(new Font("Arial", Font.BOLD, 16));
        lblPseudo.setForeground(Color.WHITE); // Texte en blanc
        formPanel.add(lblPseudo, gbc);

        gbc.gridx = 1;
        txtPseudo = new JTextField(12);
        txtPseudo.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtPseudo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblAnimal = new JLabel("Choisissez votre animal :");
        lblAnimal.setFont(new Font("Arial", Font.BOLD, 16));
        lblAnimal.setForeground(Color.WHITE); // Texte en blanc
        formPanel.add(lblAnimal, gbc);

        rbSanglier = new JRadioButton("Sanglier", true);
        rbPoule = new JRadioButton("Poule");
        rbVache = new JRadioButton("Vache");
        rbLapin = new JRadioButton("Lapin");

        JRadioButton[] animaux = {rbSanglier, rbPoule, rbVache, rbLapin};
        groupeAnimaux = new ButtonGroup();

        JPanel radioPanel = new JPanel(new GridBagLayout());
        radioPanel.setOpaque(false);
        GridBagConstraints gbcRadio = new GridBagConstraints();
        gbcRadio.anchor = GridBagConstraints.WEST;
        gbcRadio.insets = new Insets(2, 2, 2, 2);

        int row = 0;
        for (JRadioButton rb : animaux) {
            rb.setFont(new Font("Arial", Font.BOLD, 14));
            rb.setForeground(Color.WHITE); // Boutons radio en blanc
            rb.setOpaque(false); // Transparent
            groupeAnimaux.add(rb);
            gbcRadio.gridy = row++;
            radioPanel.add(rb, gbcRadio);
        }

        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(radioPanel, gbc);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 5. Boutons d'action
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        btnJouer = creerBoutonImage("/resources/bouton_play.png", "PLAY");
        btnQuitter = creerBoutonImage("/resources/bouton_quitter.png", "Quitter");

        btnJouer.addActionListener(this);
        btnQuitter.addActionListener(this);

        buttonPanel.add(btnJouer);
        buttonPanel.add(btnQuitter);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    /**
     * Méthode utilitaire pour générer un bouton.
     */
    private JButton creerBoutonImage(String cheminImage, String texteSecours) {
        JButton bouton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource(cheminImage)); 
            bouton.setIcon(new ImageIcon(img));
            bouton.setContentAreaFilled(false);
            bouton.setBorderPainted(false);
            bouton.setFocusPainted(false);
            bouton.setOpaque(false);
        } catch (Exception e) {
            // Si les images des boutons ne sont pas là, on fait des boutons normaux
            bouton.setText(texteSecours); 
            bouton.setFont(new Font("Arial", Font.BOLD, 16));
        }
        bouton.setPreferredSize(new Dimension(120, 50));
        return bouton;
    }

    private String getAnimalSelectionne() {
        if (rbSanglier.isSelected()) return "Sanglier";
        if (rbPoule.isSelected()) return "Poule";
        if (rbVache.isSelected()) return "Vache";
        if (rbLapin.isSelected()) return "Lapin";
        return "Sanglier";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnJouer) {
            String pseudo = txtPseudo.getText().trim();

            if (pseudo.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez saisir un pseudo avant de jouer !",
                        "Attention", JOptionPane.WARNING_MESSAGE); 
                return;
            }

            String animal = getAnimalSelectionne();

            JOptionPane.showMessageDialog(this,
                    "Bienvenue " + pseudo + " !\nVous avez choisi : " + animal + ".\nLancement de la partie...",
                    "Partie lancée", JOptionPane.INFORMATION_MESSAGE); 

            this.dispose(); 

        } else if (e.getSource() == btnQuitter) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EcranAccueil().setVisible(true); 
        });
    }
}

/**
 * Classe personnalisée redéfinissant JPanel pour peindre l'image de fond depuis un chemin absolu.
 */
class PanneauFond extends JPanel {
    private Image imageFond;

    public PanneauFond(String cheminAbsoluImage) {
        try {
            // Utilisation de java.io.File pour aller chercher l'image sur le Bureau[cite: 50]
            File fichierImage = new File(cheminAbsoluImage);
            if(fichierImage.exists()) {
                imageFond = ImageIO.read(fichierImage); 
            } else {
                System.err.println("Le fichier n'a pas été trouvé à l'emplacement : " + cheminAbsoluImage);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
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