package TP2;

public class Transfert implements Runnable{
    private Banque banque;
    private int depuis;
    private double amount;

    public Transfert(Banque banque, int depuis, double amount){
        this.banque = banque;
        this.depuis = depuis;
        this.amount = amount;
    }

    public void run(){
        try {
            int sendTo = (int) (Math.random()*100);
            amount = Math.random()*1000.00;
            banque.transferer(depuis, sendTo, amount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
