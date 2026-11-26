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
import java.util.ArrayList;
import java.util.List;
import chou_rave.FenetreDeJeu; // Remplace "chou_rave" par le nom exact du package de FenetreDeJeu


public class SalleAttente extends JFrame implements ActionListener {

    private String pseudo;
    private String personnage;
    private String perso;
    
    private String cheminFond = "/resources/Fond Interface Graphique.jpg";

    private DefaultListModel<String> modeleJoueurs;
    private JList<String> listeJoueurs;
    private JLabel lblStatut;
    private JButton btnRetour;
    private JButton btnLancer;

    public SalleAttente(String pseudo, String perso) {
        this.perso = perso;
        this.personnage = perso;
        this.pseudo = pseudo;

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

        // LIGNE DE TEST : Ajoute 3 faux joueurs pour atteindre 4/4 et lancer la partie (à commenter quand le multi sera prêt)
        ajouterJoueursTest();

        listeJoueurs = new JList<>(modeleJoueurs);
        listeJoueurs.setFont(new Font("Arial", Font.BOLD, 16));
        listeJoueurs.setForeground(Color.WHITE);
        listeJoueurs.setBackground(new Color(30, 30, 30));

        JScrollPane scrollListe = new JScrollPane(listeJoueurs);
        scrollListe.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        lobbyPanel.add(scrollListe, BorderLayout.CENTER);

        lblStatut = new JLabel("", SwingConstants.CENTER);
        lblStatut.setFont(new Font("Arial", Font.ITALIC, 14));
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

        // Vérifie si on a bien 4 joueurs pour activer le bouton "LANCER LA PARTIE"
        mettreAJourEtatPartie();

        setContentPane(mainPanel);
    }

    /**
     * Ajoute 3 joueurs fictifs avec des personnages différents du tien pour tester le lancement.
     */
    private void ajouterJoueursTest() {
        String[] tousLesPersos = {"Taupe", "Renard", "Sanglier", "Lapin", "Poule"};
        int nbAjoutes = 0;
        for (String p : tousLesPersos) {
            if (!p.equalsIgnoreCase(personnage) && nbAjoutes < 3) {
                nbAjoutes++;
                modeleJoueurs.addElement("JoueurTest_" + nbAjoutes + " (" + p + ") - Prêt");
            }
        }
    }

    /**
     * Active le bouton Lancer uniquement quand 4 joueurs sont présents dans le lobby.
     */
    private void mettreAJourEtatPartie() {
        int nbJoueurs = modeleJoueurs.size();
        boolean partieComplete = (nbJoueurs >= 4);

        btnLancer.setEnabled(partieComplete);
        if (partieComplete) {
            lblStatut.setText("Partie complète (" + nbJoueurs + "/4) - Prêt à lancer !");
            lblStatut.setForeground(new Color(140, 255, 140));
        } else {
            lblStatut.setText("En attente des autres joueurs (" + nbJoueurs + "/4)...");
            lblStatut.setForeground(new Color(200, 230, 255));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRetour) {
            SelectionPersonnage selection = new SelectionPersonnage();
            selection.setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnLancer) {
            if (modeleJoueurs.size() >= 4) {
                // On transforme la liste de la salle d'attente en List<String> attendue par FenetreDeJeu
                List<String> listeNomsJoueurs = new ArrayList<>();
                for (int i = 0; i < modeleJoueurs.size(); i++) {
                    listeNomsJoueurs.add(modeleJoueurs.getElementAt(i));
                }

                // On crée la fenêtre de jeu et on appelle demarrer() (indispensable pour le Timer et le clavier)
                FenetreDeJeu fenetreJeu = new FenetreDeJeu(perso);
                fenetreJeu.demarrer();
                this.dispose(); // Ferme la salle d'attente
            }
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