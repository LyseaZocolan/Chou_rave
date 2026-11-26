/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ig;

/**
 *
 * @author ntessier
 */


import chou_rave.Avatar;
import chou_rave.JoueurSql;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SelectionPersonnage extends JFrame implements ActionListener {

    private JTextField txtPseudo;
    private ButtonGroup groupeAnimaux;
    private JButton btnValider;
    private JoueurSql joueurSql = new JoueurSql();

    // Noms des personnages (utilisés pour le transfert vers la salle d'attente)
    private String[] nomsPersonnages = {"Taupe", "Renard", "Sanglier", "Lapin", "Poule"};

    // Chemins relatifs vers le package "resources"
    private String cheminFond = "/resources/Fond Interface Graphique.jpg";
    private String[] cheminsImages = {
        "/resources/Taupe.jpg",
        "/resources/Renard.jpg",
        "/resources/Sanglier.jpg",
        "/resources/Lapin.jpg",
        "/resources/Poule.jpg"
    };

    // Textes des capacités (zone bleue)
    private String[] textesCapacites = {
        "Rapidité : +\nCapacité stockage : +\nEndurance : ++\nCreuse des tunnels",
        "Rapidité : +++\nCapacité stockage : +\nEndurance : +++\nVif",
        "Rapidité : ++\nCapacité stockage : ++\nEndurance : +\nCharge les enfants.",
        "Rapidité : +++\nCapacité stockage : +++\nEndurance : ++\nPeut bondir",
        "Rapidité : +\nCapacité stockage : +\nEndurance : ++\nPeut tirer des oeufs"
    };

    private JRadioButton[] radioBoutons;

    public SelectionPersonnage() {
        setTitle("Choisis ton personnage");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PanneauFond mainPanel = new PanneauFond(cheminFond);
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Zone des 5 cartes personnages
        JPanel cartesPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        cartesPanel.setOpaque(false);

        groupeAnimaux = new ButtonGroup();
        radioBoutons = new JRadioButton[5];

        // Réglages individuels d'alignement (Ordre : 0.Taupe, 1.Renard, 2.Sanglier, 3.Lapin, 4.Poule)
        final int[] decalagesX = {-14, -6, -2, -5, -10};
        final int[] etirementsLargeur = {20, 10, 5, 10, 20};
        final int[] decalagesY = {-8, -5, -8, -8, -8};
        final int hauteurPanneau = 220;
        
       
       

        for (int i = 0; i < 5; i++) {
            JPanel carte = new JPanel(new BorderLayout());
            carte.setBackground(new Color(0, 0, 0, 160));
            carte.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));

            // ZONE ROUGE : Grande image en haut chargée depuis resources
            final int index = i;
            JPanel imgPanel = new JPanel() {
                private Image img;
                {
                    setOpaque(false);
                    setPreferredSize(new Dimension(0, hauteurPanneau));
                    img = EcranAccueil.chargerImage(cheminsImages[index]);
                }

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (img != null) {
                        int x = decalagesX[index];
                        int y = decalagesY[index];
                        int largeur = getWidth() + etirementsLargeur[index];
                        int hauteur = getHeight() - y;

                        g.drawImage(img, x, y, largeur, hauteur, this);
                    } else {
                        g.setColor(Color.RED);
                        g.drawString("Image manquante", 20, 110);
                    }
                }
            };
            carte.add(imgPanel, BorderLayout.NORTH);

            // ZONE BLEUE : Texte des capacités au centre
            JTextArea txtCapacites = new JTextArea(textesCapacites[i]);
            txtCapacites.setFont(new Font("Arial", Font.BOLD, 12));
            txtCapacites.setForeground(new Color(200, 230, 255));
            txtCapacites.setOpaque(false);
            txtCapacites.setEditable(false);
            txtCapacites.setFocusable(false);
            txtCapacites.setLineWrap(true);
            txtCapacites.setWrapStyleWord(true);
            txtCapacites.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
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
        return nomsPersonnages[2];
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

        Avatar avatar = new Avatar();
        avatar.setPseudo(pseudo);
        avatar.setX(0);
        avatar.setY(0);

       if (joueurSql.creerJoueur(avatar, perso)) {
            SalleAttente attente = new SalleAttente(pseudo, perso);
            attente.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Impossible d'enregistrer le joueur.",
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
}