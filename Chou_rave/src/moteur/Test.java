/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package moteur;

import java.util.Scanner;

/**
 *
 * @author jbelot
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        Avatar monAvatar = new Avatar();
        boolean jeuEnCours = true;

        System.out.println("--- Début de la partie Console ---");
        System.out.println("Commandes : z (haut), s (bas), q (gauche), d (droite) | x (quitter)");
        
        while (jeuEnCours) {
            // Affichage de l'état actuel
            System.out.println("\nPosition de l'avatar : x = " + monAvatar.getX() + " | y = " + monAvatar.getY());
            System.out.print("Ton action : ");
            
            // Lecture de la saisie utilisateur
            String saisie = clavier.nextLine().toLowerCase();
            
            // On réinitialise les touches avant chaque action
            monAvatar.setToucheHaut(false);
            monAvatar.setToucheBas(false);
            monAvatar.setToucheGauche(false);
            monAvatar.setToucheDroite(false);

            // Interprétation de la commande
            switch (saisie) {
                case "z":
                    monAvatar.setToucheHaut(true);
                    break;
                case "s":
                    monAvatar.setToucheBas(true);
                    break;
                case "q":
                    monAvatar.setToucheGauche(true);
                    break;
                case "d":
                    monAvatar.setToucheDroite(true);
                    break;
                case "x":
                    jeuEnCours = false;
                    System.out.println("Fin de la partie !");
                    continue; // Passe à la fin de la boucle
                default:
                    System.out.println("Commande inconnue.");
            }

            // Si une action valide a été faite, on met à jour le modèle
            monAvatar.miseAJour();
        }
        
        clavier.close();
    }
    
    
}
