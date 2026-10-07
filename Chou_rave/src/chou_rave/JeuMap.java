package chou_rave;

public class JeuMap {

    public static class Case {
        private char symbole;

        public Case(char symbole) {
            this.symbole = symbole;
        }

        public char getSymbole() {
            return symbole;
        }

        public void setSymbole(char symbole) {
            this.symbole = symbole;
        }
    }

    private int lignes;
    private int colonnes;
    private Case[][] grille;

    // Constructeur de la map avec des bords fermés
    public JeuMap(int lignes, int colonnes) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.grille = new Case[lignes][colonnes];
        
        // On parcourt chaque case de la grille
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                
                // Si on est sur le bord haut (i == 0), bas (i == lignes - 1),
                // gauche (j == 0) ou droite (j == colonnes - 1)
                if (i == 0 || i == lignes - 1 || j == 0 || j == colonnes - 1) {
                    grille[i][j] = new Case('#'); // On met un mur '#'
                } else {
                    grille[i][j] = new Case('.'); // Sinon, c'est vide '.' à l'intérieur
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
        System.out.println("Ma map avec des bords fermes :");
        maMap.afficher();
    }
}