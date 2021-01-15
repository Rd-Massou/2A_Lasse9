package TP2;

public class SanjiEtRobinJob implements Runnable{
    private CompteBanque compte;

    public SanjiEtRobinJob(CompteBanque compte){
        this.compte = compte;
    }

    public synchronized void demandeRetrait(int somme){
        if (compte.getSolde() < somme){
            System.out.println("Pas assez d’argent pour "+Thread.currentThread().getName());
        } else {
            try {
                System.out.println(Thread.currentThread().getName() + " est sur le point de retirer.");
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " s'est reveillé.");
                compte.retirer(somme);
                System.out.println(Thread.currentThread().getName() + " à compléter le retrait! Nouveau solde: " + compte.getSolde());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while(true){
            demandeRetrait(200);
            if (compte.getSolde() <= 0) {
                System.out.println(Thread.currentThread().getName() + " est à découvert");
                break;
            }
        }
    }
}