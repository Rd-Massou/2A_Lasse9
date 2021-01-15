package TP3;

import java.net.*;
import java.io.*;

public class Client1Thread extends Thread {
    private Socket client;
    private String proverb;
    public Client1Thread(Socket client, String proverb) throws IOException{
        this.client = client;
        this.proverb = proverb;
    }
    public void run(){
        try{
            PrintWriter printer = new PrintWriter(client.getOutputStream(), true);
            printer.println(proverb);
            printer.close();
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
