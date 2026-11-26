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
 public boolean creerJoueur(Avatar a, String animal) {
    String sql = "INSERT INTO Avatar (animal, pseudo, position_x, position_y) VALUES (?, ?, ?, ?)";
    try (PreparedStatement requete = connexion.prepareStatement(sql)) {
        requete.setString(1, animal);
        requete.setString(2, a.getPseudo());
        requete.setInt(3, a.getPositionX());
        requete.setInt(4, a.getPositionY());
        return requete.executeUpdate() > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}
 
    private boolean getPositionY() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}