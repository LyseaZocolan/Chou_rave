/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chou_rave;

public class Case {
    private char symbole;

    // Constructeur pour initialiser une case avec son symbole
    public Case(char symbole) {
        this.symbole = symbole;
    }

    // Permet de lire le symbole de la case
    public char getSymbole() {
        return symbole;
    }

    // Permet de modifier le symbole de la case si besoin
    public void setSymbole(char symbole) {
        this.symbole = symbole;
    }
}