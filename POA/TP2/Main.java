package TP2;

public class Main {

    public static void Exo1(){
        CompteBanque compte = new CompteBanque(2000);
        Runnable robin = new SanjiEtRobinJob(compte), sanji = new SanjiEtRobinJob(compte);
        Thread th1 = new Thread(robin, "Robin"), th2 = new Thread(sanji, "Sanji");
        th1.start();
        th2.start();
    }
    public static void Exo2_1(){
        Banque banque = new Banque(100, 1000);
        for (int depuis = 0; depuis < 100; depuis ++) {
            Runnable r = new Transfert(banque, depuis, 1000);
            new Thread(r).start();
        }
        System.out.println("Total dans la banque: "+ banque.soldeTotal());
    }
    public static void Exo2_2(){
        Banque banque = new Banque(100, 1000);
        for (int depuis = 0; depuis < 100; depuis ++) {
            Runnable r = new TransfertV2(banque, depuis, 1000);
            new Thread(r).start();
        }
        System.out.println("Total dans la banque: "+ banque.soldeTotalV2());
    }
    public static void main(String[] args) {
        Exo2_2();
    }
}