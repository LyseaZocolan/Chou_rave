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
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Graphics;

public class SelectionPersonnage extends JFrame implements ActionListener {

    private JTextField txtPseudo;
    private ButtonGroup groupeAnimaux;
    private JButton btnValider;
    
    // Noms des personnages 
    private String[] nomsPersonnages = {"Taupe", "Renard", "Sanglier", "Lapin", "Poule"};
    
    // Chemins images 
    private String[] cheminsImages = {
        "Z:\\Desktop\\VERT\\Info_Projet\\Images\\Images Animaux Accueil\\Selection Personnage\\taupe.png",
        "Z:\\Desktop\\VERT\\Info_Projet\\Images\\Images Animaux Accueil\\Selection Personnage\\renard.png",
        "Z:\\Desktop\\VERT\\Info_Projet\\Images\\Images Animaux Accueil\\Selection Personnage\\sanglier.png",
        "Z:\\Desktop\\VERT\\Info_Projet\\Images\\Images Animaux Accueil\\Selection Personnage\\lapin.png",
        "Z:\\Desktop\\VERT\\Info_Projet\\Images\\Images Animaux Accueil\\Selection Personnage\\poule.png"
    };

    // Textes pour la zone bleue (capacités) que vous pourrez modifier
    private String[] textesCapacites = {
        "Rapidité : +\n Capacité stockage : + \n Endurance : ++ \nCreuse des tunnels",
        "Rapidité : +++\n Capacité stockage : + \n Endurance : +++ \nVif",
        "Rapidité : ++\n Capacité stockage : ++ \n Endurance : + \nCharge les enfants.",
        "Rapidité : +++\n Capacité stockage : +++  \n Endurance : ++ \nPeut bondir",
        "Rapidité : +\n Capacité stockage : + \n Endurance : ++ \nPeut tirer des oeufs"
    };

    private JRadioButton[] radioBoutons;

    public SelectionPersonnage() {
        setTitle("Choisis ton personnage");
        setSize(950, 600); // Fenêtre agrandie pour faire rentrer la grande image et le texte
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PanneauFond mainPanel = new PanneauFond("Z:\\Desktop\\Info_Projet\\Chou_rave\\Image Interface\\Fond Interface Graphique.jpg");
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Zone des 5 cartes personnages
        JPanel cartesPanel = new JPanel(new GridLayout(1, 5, 10, 0)); 
        cartesPanel.setOpaque(false);
        
        groupeAnimaux = new ButtonGroup();
        radioBoutons = new JRadioButton[5];

        for (int i = 0; i < 5; i++) {
            // Création de la carte du personnage
            JPanel carte = new JPanel(new BorderLayout());
            carte.setBackground(new Color(0, 0, 0, 160)); // Fond sombre transparent
            carte.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));

            // Grande image en haut avec ajustement complet (X et Y)
            final int index = i; 
            
            // DÉCALAGE X : Déplace l'image horizontalement (négatif = vers la gauche)
            final int[] decalagesX = {-14, -9, -3, -4, -10}; 
            
            // ÉTIREMENT LARGEUR : Augmente la largeur de l'image
            final int[] etirementsLargeur = {20, 15, 5, 10, 22}; 
            
            // DÉCALAGE Y : Déplace l'image verticalement (négatif = vers le haut)
            final int[] decalagesY = {-7, -7, -7, -7, -7}; 
            
            // HAUTEUR DE LA CARTE : On réduit la hauteur du panneau 
            final int hauteurPanneau = 220; 
            
            JPanel imgPanel = new JPanel() {
                private Image img;
                {
                    setOpaque(false); 
                    setPreferredSize(new Dimension(0, hauteurPanneau)); // Nouvelle hauteur réduite
                    
                    try {
                        File fichierImg = new File(cheminsImages[index]);
                        if (fichierImg.exists()) {
                            img = ImageIO.read(fichierImg);
                        }
                    } catch (Exception ex) {
                        System.err.println("Erreur image : " + cheminsImages[index]);
                    }
                }

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (img != null) {
                        int x = decalagesX[index];
                        int y = decalagesY[index];
                        int largeur = getWidth() + etirementsLargeur[index];
                        
                        // On compense la hauteur dessinée pour ne pas écraser l'image.
                        // Si le panneau fait 220 et qu'on remonte de -30, l'image dessinée fait 250.
                        int hauteur = getHeight() - y; 
                        
                        g.drawImage(img, x, y, largeur, hauteur, this);
                    } else {
                        g.setColor(Color.RED);
                        g.drawString("Image manquante", 20, 125);
                    }
                }
            };
            carte.add(imgPanel, BorderLayout.NORTH);

            // Texte des capacités au centre
            JTextArea txtCapacites = new JTextArea(textesCapacites[i]);
            txtCapacites.setFont(new Font("Arial", Font.BOLD, 12));
            txtCapacites.setForeground(new Color(200, 230, 255)); // Bleu très clair pour la lisibilité
            txtCapacites.setOpaque(false); // Fond transparent
            txtCapacites.setEditable(false);
            txtCapacites.setFocusable(false);
            txtCapacites.setLineWrap(true);
            txtCapacites.setWrapStyleWord(true);
            txtCapacites.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Marges internes
            carte.add(txtCapacites, BorderLayout.CENTER);

            // Bouton radio de sélection en bas
            radioBoutons[i] = new JRadioButton("Sélectionner");
            radioBoutons[i].setFont(new Font("Arial", Font.BOLD, 14));
            radioBoutons[i].setForeground(Color.WHITE);
            radioBoutons[i].setOpaque(false);
            radioBoutons[i].setHorizontalAlignment(SwingConstants.CENTER);
            radioBoutons[i].setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            
            groupeAnimaux.add(radioBoutons[i]);
            carte.add(radioBoutons[i], BorderLayout.SOUTH);
            
            cartesPanel.add(carte);
        }
        
        radioBoutons[2].setSelected(true); // Sanglier par défaut
        mainPanel.add(cartesPanel, BorderLayout.CENTER);

        // 2. Zone du bas : Pseudo et Validation
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0, 0, 0, 180));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblPseudo = new JLabel("Pseudo : ");
        lblPseudo.setFont(new Font("Arial", Font.BOLD, 18));
        lblPseudo.setForeground(Color.WHITE);
        bottomPanel.add(lblPseudo);

        txtPseudo = new JTextField(15);
        txtPseudo.setFont(new Font("Arial", Font.PLAIN, 18));
        bottomPanel.add(txtPseudo);

        // Espace vide entre le champ texte et le bouton
        bottomPanel.add(new JLabel("    "));

        btnValider = new JButton("PLAY / REJOINDRE LOBBY");
        btnValider.setFont(new Font("Arial", Font.BOLD, 16));
        btnValider.addActionListener(this);
        bottomPanel.add(btnValider);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    private String getPersonnageSelectionne() {
        for (int i = 0; i < 5; i++) {
            if (radioBoutons[i].isSelected()) {
                return nomsPersonnages[i];
            }
        }
        return nomsPersonnages[2]; // Sanglier par défaut
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnValider) {
            String pseudo = txtPseudo.getText().trim();

            if (pseudo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Saisis un pseudo !", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String perso = getPersonnageSelectionne();
            
            SalleAttente attente = new SalleAttente(pseudo, perso);
            attente.setVisible(true);
            this.dispose();
        }
    }
}