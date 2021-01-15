package TP1;

public class AlphaMin implements Runnable {
    public void run(){
        char character = 'a';
        for(int i = 0; i<26; i++){
            System.out.println(character);
            character++;
        }
    }
}