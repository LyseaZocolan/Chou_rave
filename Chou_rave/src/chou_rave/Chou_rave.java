/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chou_rave;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author lzocolan
 */
public class Chou_rave {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        EcranAccueil ecran = new EcranAccueil();
        JeuMap carte = new JeuMap();
        //avatars
        
        Avatar avatar1 = new Avatar();
        Avatar avatar2 = new Avatar();
        Avatar avatar3 = new Avatar();
        Avatar avatar4 = new Avatar();
        
        ArrayList<Avatar> listeAvatar = new ArrayList<>();
        listeAvatar.add(avatar1);
        listeAvatar.add(avatar2);
        listeAvatar.add(avatar3);
        listeAvatar.add(avatar4);
               
        
        for(Avatar joueur : listeAvatar){
            ArrayList<JeuCoordonnees> terrier = new ArrayList<>();
            int a = 500;
            int b = 500;
            for (int x = 0; x <= a; x++) {
                for (int y = 0; y <= b; y++) {
                    terrier.add(new JeuCoordonnees(x, y));
            }
            Random random = new Random();
            int indexAleatoire = random.nextInt(terrier.size());
            JeuCoordonnees coordHasard = terrier.get(indexAleatoire);
            int coorx = coordHasard.getX();
            int coory = coordHasard.getY();
            joueur.setX(coorx);
            joueur.setY(coory);
        }
    }
    
}
