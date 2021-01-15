package TP2;

public class TransfertV2 implements Runnable{
    private Banque banque;
    private int depuis;
    private double amount;

    public TransfertV2(Banque banque, int depuis, double amount){
        this.banque = banque;
        this.depuis = depuis;
        this.amount = amount;
    }

    public void run(){
        try {
            int sendTo = (int) (Math.random()*100);
            amount = Math.random()*1000.00;
            banque.transfererV2(depuis, sendTo, amount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
