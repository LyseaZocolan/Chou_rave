
package chou_rave;



public class JeuMap {

    private int lignes;
    private int colonnes;
    private Case[][] grille; // Java reconnait automatiquement la classe Case car elle est dans le même package !

    // Constructeur de la map avec des symboles spécifiques pour les bords
    public JeuMap(int lignes, int colonnes) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.grille = new Case[lignes][colonnes];
        
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                
                // Si on est sur la ligne du haut ou du bas
                if (i == 0 || i == lignes - 1) {
                    grille[i][j] = new Case('-');
                } 
                // Si on est sur la colonne de gauche ou de droite
                else if (j == 0 || j == colonnes - 1) {
                    grille[i][j] = new Case('!');
                } 
                // Sinon, c'est l'intérieur de la map
                else {
                    grille[i][j] = new Case('.');
                }
                
            }
        }
    }

    public void afficher() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                System.out.print(grille[i][j].getSymbole() + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        JeuMap maMap = new JeuMap(5, 10);
        
        maMap.afficher();
    }
}