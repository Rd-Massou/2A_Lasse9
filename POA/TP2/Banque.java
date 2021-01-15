package TP2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banque {
    private final double[] comptes;
    private Lock bankLock = new ReentrantLock();
    private Condition soldeSuffisant = bankLock.newCondition();

    public Banque(int compteCount, double solde){
        comptes = new double[compteCount];
        for(int i=0; i<compteCount; i++){
            comptes[i] = solde;
        }
    }

    public synchronized double soldeTotal(){
        double total = 0;
        for(double amount: comptes){
            total += amount;
        }
        return Math.round(total);
    }

    public synchronized void transferer(int from, int to, double amount) throws InterruptedException {
        while (amount > comptes[from]) {
            wait();
        }
        comptes[from] -= amount;
        comptes[to] += amount;
        notifyAll();
        System.out.printf("Le compte émetteur %d a transféré au compte destinataire %d un montant de: %.2f Dh\n", (from + 1), (to + 1), amount);
    }

    public double soldeTotalV2(){
        bankLock.lock();
        double total = 0;
        for(double amount: comptes){
            total += amount;
        }
        bankLock.unlock();
        return Math.round(total);
    }

    public void transfererV2(int from, int to, double amount) throws InterruptedException {
        bankLock.lock();
        try{
            while (amount > comptes[from]) {
                soldeSuffisant.await();
            }
            comptes[from] -= amount;
            comptes[to] += amount;
            soldeSuffisant.signalAll();
            System.out.printf("Le compte émetteur %d a transféré au compte destinataire %d un montant de: %.2f Dh\n", (from + 1), (to + 1), amount);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bankLock.unlock();
        }
    }

    public int size(){
        return comptes.length;
    }

    public double[] getAccounts(){
        return comptes;
    }

}