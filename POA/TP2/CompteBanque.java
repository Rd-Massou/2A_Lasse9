package TP2;

public class CompteBanque {
    public int solde;

    public CompteBanque(int solde){
        this.solde = solde;
    }

    public int getSolde(){
        return solde;
    }

    public void retirer(int somme){
        if(somme <= solde){
            System.out.println("Vous avez retiré " + somme + "Dh avec succes!");
            solde -= somme;
        } else {
            System.out.println("Opération impossible, solde insuffisant!");
        }
    }
}