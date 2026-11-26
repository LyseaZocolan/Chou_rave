/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chou_rave;

/**
 *
 * @author lzocolan
 */
import java.sql.*;
import outils.OutilsJDBC;
import moteur.Joueur;

public class JoueurSql {
    private String adresseBase;
    private String user;
    private String motdepasse;
    private Connection connexion; //lui c'est l'état de la connexion, autant en faire aussi un attribut.

   
 public JoueurSql(){
        this.adresseBase = "jdbc:mariadb://nemrod.ens2m.fr:3306/2026-2027_s1_vs1_tp2_chou_rave";
        this.user = "etudiant";
        this.motdepasse = "YTDTvj9TR3CDYCmP";
	
	//Vous avez vu que, avant de faire une requête, il fallait se connecter à la BD, ce que je te propose c'est de te connecter/déco UNE seule fois, et pas à 
	//chaque fois que tu fais une requête : La connection à la BD prend du TEMPS, si tu fais plusieurs co/déco, ça va être long :)
	try {
	
	this.connexion = DriverManager.getConnection(this.adresseBase, this.user, this.motdepasse);
	
	} catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
 public void creerJoueur(Avatar J){
       //Voilà un exemple (va utiliser INSERT dans sa requête SQL), admettons qu'on a un Joueur J caractérisé par : son nom, son score, sa position X, sa position Y. On va ajouter cerre
	//ligne à notre table JOUEUR !
        try {
            PreparedStatement requete = connexion.prepareStatement("INSERT INTO Joueur VALUES (?, ?, ?, ?)");
            requete.setString(1, J.getPseudo());
            requete.setInt(2, J.getScore());
            requete.setInt(3, J.getPositionX());
            requete.setBoolean(4, getPositionY());
            System.out.println(requete);
            int nombreDAjouts = requete.executeUpdate();
            System.out.println(nombreDAjouts + " enregistrement(s) ajoute(s)");

            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }

    }

    private boolean getPositionY() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}