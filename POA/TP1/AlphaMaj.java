package TP1;

public class AlphaMaj implements Runnable {
    public void run(){
        char character = 'A';
        for(int i = 0; i<26; i++){
            System.out.println(character);
            character ++;
        }
    }
}