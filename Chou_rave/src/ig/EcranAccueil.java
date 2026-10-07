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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Écran d'accueil du jeu "Chou-rave"
 * Réalisé par le Product Owner
 */
public class EcranAccueil extends JFrame implements ActionListener {

    // Composants de l'interface
    private JTextField txtPseudo;
    private JRadioButton rbSanglier;
    private JRadioButton rbRenard;
    private JRadioButton rbTaupe;
    private JRadioButton rbLapin;
    private ButtonGroup groupeAnimaux;
    private JButton btnJouer;
    private JButton btnQuitter;

    public EcranAccueil() {
        // Configuration de la fenêtre principale (JFrame)
        setTitle("Chou-rave - Accueil");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer à l'écran
        setResizable(false);

        // Panneau principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(235, 245, 235)); // Fond vert très clair
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Titre
        JLabel lblTitre = new JLabel("Chou-rave", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitre.setForeground(new Color(34, 112, 34));
        
        JLabel lblSousTitre = new JLabel("Vole la nourriture au fermier !", SwingConstants.CENTER);
        lblSousTitre.setFont(new Font("Arial", Font.ITALIC, 14));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(lblTitre, BorderLayout.NORTH);
        headerPanel.add(lblSousTitre, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 2. Formulaire central (Pseudo + Choix de l'animal)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champ Pseudo
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblPseudo = new JLabel("Pseudo du joueur :");
        lblPseudo.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(lblPseudo, gbc);

        gbc.gridx = 1;
        txtPseudo = new JTextField(12);
        txtPseudo.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtPseudo, gbc);

        // Sélection de l'animal
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblAnimal = new JLabel("Choisissez votre animal :");
        lblAnimal.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(lblAnimal, gbc);

        // Boutons radio pour les 4 animaux
        rbSanglier = new JRadioButton("Sanglier", true); // Sélectionné par défaut
        rbRenard = new JRadioButton("Renard");
        rbTaupe = new JRadioButton("Taupe");
        rbLapin = new JRadioButton("Lapin");

        // Style des radio buttons
        JRadioButton[] animaux = {rbSanglier, rbRenard, rbTaupe, rbLapin};
        groupeAnimaux = new ButtonGroup();

        JPanel radioPanel = new JPanel(new GridBagLayout());
        radioPanel.setOpaque(false);
        GridBagConstraints gbcRadio = new GridBagConstraints();
        gbcRadio.anchor = GridBagConstraints.WEST;
        gbcRadio.insets = new Insets(2, 2, 2, 2);

        int row = 0;
        for (JRadioButton rb : animaux) {
            rb.setFont(new Font("Arial", Font.PLAIN, 13));
            rb.setOpaque(false);
            groupeAnimaux.add(rb);
            gbcRadio.gridy = row++;
            radioPanel.add(rb, gbcRadio);
        }

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(radioPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 3. Panneau des boutons (Play / Quitter)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        btnJouer = new JButton("PLAY");
        btnJouer.setFont(new Font("Arial", Font.BOLD, 16));
        btnJouer.setBackground(new Color(46, 139, 87));
        btnJouer.setForeground(Color.WHITE);
        btnJouer.setPreferredSize(new Dimension(120, 40));
        btnJouer.addActionListener(this);

        btnQuitter = new JButton("Quitter");
        btnQuitter.setFont(new Font("Arial", Font.PLAIN, 14));
        btnQuitter.setPreferredSize(new Dimension(100, 40));
        btnQuitter.addActionListener(this);

        buttonPanel.add(btnJouer);
        buttonPanel.add(btnQuitter);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Ajout du panneau à la fenêtre
        setContentPane(mainPanel);
    }

    /**
     * Méthode renvoyant le nom de l'animal sélectionné
     */
    private String getAnimalSelectionne() {
        if (rbSanglier.isSelected()) return "Sanglier";
        if (rbRenard.isSelected()) return "Renard";
        if (rbTaupe.isSelected()) return "Taupe";
        if (rbLapin.isSelected()) return "Lapin";
        return "Cochon";
    }

    /**
     * Gestion des événements de clics sur les boutons
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnJouer) {
            String pseudo = txtPseudo.getText().trim();

            if (pseudo.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez saisir un pseudo avant de jouer !",
                        "Attention",
                        JOptionPane.WARNING_MESSAGE); //
                return;
            }

            String animal = getAnimalSelectionne();

            // Message de confirmation avant de lancer le jeu / la salle d'attente
            JOptionPane.showMessageDialog(this,
                    "Bienvenue " + pseudo + " !\nVous avez choisi : " + animal + ".\nLancement de la partie...",
                    "Partie lancée",
                    JOptionPane.INFORMATION_MESSAGE); //

            // TODO : Passer à l'écran de jeu ou à la salle d'attente (salle de lobby)
            // Exemple : 
            // FenetreDeJeu jeu = new FenetreDeJeu(pseudo, animal);
            // jeu.setVisible(true);

            // Fermeture de la fenêtre d'accueil
            this.dispose();

        } else if (e.getSource() == btnQuitter) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // Lancement dans le Thread Event Dispatcher (EDT) recommandé par Swing[cite: 34]
        SwingUtilities.invokeLater(() -> {
            EcranAccueil accueil = new EcranAccueil();
            accueil.setVisible(true); //
        });
    }
}
