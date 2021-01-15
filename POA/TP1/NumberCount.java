package TP1;

public class NumberCount implements Runnable{
    public void run(){
        for(int i = 0; i<60; i++){
            System.out.println(i);
        }
    }
}