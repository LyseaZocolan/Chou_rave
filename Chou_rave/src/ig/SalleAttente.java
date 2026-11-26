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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class SalleAttente extends JFrame implements ActionListener {

    private String pseudo;
    private String personnage;

    private String cheminFond = "/resources/Fond Interface Graphique.jpg";

    private DefaultListModel<String> modeleJoueurs;
    private JList<String> listeJoueurs;
    private JButton btnRetour;
    private JButton btnLancer;

    public SalleAttente(String pseudo, String personnage) {
        this.pseudo = pseudo;
        this.personnage = personnage;

        setTitle("De la Terre à l'Assiette - Salle d'attente");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PanneauFondLobby mainPanel = new PanneauFondLobby(cheminFond);
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // 1. En-tête
        JLabel lblTitre = new JLabel("SALLE D'ATTENTE", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitre.setForeground(Color.WHITE);
        lblTitre.setOpaque(true);
        lblTitre.setBackground(new Color(0, 0, 0, 160));
        lblTitre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.ORANGE, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(lblTitre, BorderLayout.NORTH);

        // 2. Zone centrale : Profil du joueur à gauche + Liste des joueurs à droite
        JPanel centrePanel = new JPanel(new BorderLayout(15, 0));
        centrePanel.setOpaque(false);

        // Panneau récapitulatif du joueur (avec son image depuis resources)
        JPanel profilPanel = new JPanel(new BorderLayout(10, 10));
        profilPanel.setBackground(new Color(0, 0, 0, 170));
        profilPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.ORANGE, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        profilPanel.setPreferredSize(new Dimension(240, 0));

        JLabel lblInfoJoueur = new JLabel(
                "<html><center>Joueur : " + pseudo + "<br>Personnage : " + personnage + "</center></html>",
                SwingConstants.CENTER
        );
        lblInfoJoueur.setFont(new Font("Arial", Font.BOLD, 16));
        lblInfoJoueur.setForeground(Color.WHITE);
        profilPanel.add(lblInfoJoueur, BorderLayout.NORTH);

        // Chargement automatique de l'image du personnage choisi (ex: /resources/Sanglier.png)
        JLabel lblImagePerso = new JLabel();
        lblImagePerso.setHorizontalAlignment(SwingConstants.CENTER);
        Image imgPerso = EcranAccueil.chargerImage("/resources/" + personnage + ".png");
        if (imgPerso != null) {
            Image imgRedim = imgPerso.getScaledInstance(170, 220, Image.SCALE_SMOOTH);
            lblImagePerso.setIcon(new ImageIcon(imgRedim));
        } else {
            lblImagePerso.setText("Image introuvable");
            lblImagePerso.setForeground(Color.RED);
        }
        profilPanel.add(lblImagePerso, BorderLayout.CENTER);

        centrePanel.add(profilPanel, BorderLayout.WEST);

        // Panneau liste des joueurs connectés
        JPanel lobbyPanel = new JPanel(new BorderLayout(10, 10));
        lobbyPanel.setBackground(new Color(0, 0, 0, 170));
        lobbyPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.ORANGE, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblListeTitre = new JLabel("Joueurs dans la partie :", SwingConstants.LEFT);
        lblListeTitre.setFont(new Font("Arial", Font.BOLD, 18));
        lblListeTitre.setForeground(Color.ORANGE);
        lobbyPanel.add(lblListeTitre, BorderLayout.NORTH);

        modeleJoueurs = new DefaultListModel<>();
        modeleJoueurs.addElement(pseudo + " (" + personnage + ") - Prêt");

        listeJoueurs = new JList<>(modeleJoueurs);
        listeJoueurs.setFont(new Font("Arial", Font.BOLD, 16));
        listeJoueurs.setForeground(Color.WHITE);
        listeJoueurs.setBackground(new Color(30, 30, 30));

        JScrollPane scrollListe = new JScrollPane(listeJoueurs);
        scrollListe.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        lobbyPanel.add(scrollListe, BorderLayout.CENTER);

        JLabel lblStatut = new JLabel("En attente des autres joueurs...", SwingConstants.CENTER);
        lblStatut.setFont(new Font("Arial", Font.ITALIC, 14));
        lblStatut.setForeground(new Color(200, 230, 255));
        lobbyPanel.add(lblStatut, BorderLayout.SOUTH);

        centrePanel.add(lobbyPanel, BorderLayout.CENTER);
        mainPanel.add(centrePanel, BorderLayout.CENTER);

        // 3. Zone du bas : Boutons Retour et Lancer
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        bottomPanel.setBackground(new Color(0, 0, 0, 180));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        btnRetour = new JButton("CHANGER DE PERSONNAGE");
        btnRetour.setFont(new Font("Arial", Font.BOLD, 15));
        btnRetour.addActionListener(this);
        bottomPanel.add(btnRetour);

        btnLancer = new JButton("LANCER LA PARTIE");
        btnLancer.setFont(new Font("Arial", Font.BOLD, 15));
        btnLancer.addActionListener(this);
        bottomPanel.add(btnLancer);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRetour) {
            SelectionPersonnage selection = new SelectionPersonnage();
            selection.setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnLancer) {
            // Remplace ce message par l'ouverture de votre fenêtre de jeu (ex: new FenetreDeJeu()...)
            JOptionPane.showMessageDialog(
                    this,
                    "Lancement de la partie pour " + pseudo + " (" + personnage + ") !",
                    "Démarrage",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}

class PanneauFondLobby extends JPanel {
    private Image imageFond;

    public PanneauFondLobby(String cheminRessource) {
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