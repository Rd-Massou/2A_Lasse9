package TP3;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server1 {

    private static ArrayList<String> proverbs;

    public static void init(){
        try {
            File myProverbs = new File("proverbs.txt");
            Scanner myReader = new Scanner(myProverbs);
            while (myReader.hasNextLine()) {
                proverbs.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String getProverb(){
        int random = (int)(Math.random()*proverbs.size());
        return proverbs.get(random);
    }

    public static void withoutThread()throws IOException {
        proverbs = new ArrayList<>();
        init();
        ServerSocket serverSocket = new ServerSocket(4000);
        try{
            while(true){
                System.out.println("Waiting for client connexion ...");
                Socket client = serverSocket.accept();
                System.out.println("Client connected !");
                try {
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    out.println(getProverb());
                } finally {
                    client.close();
                }
            }
        } finally {
            serverSocket.close();
        }
    }

    public static void withThread()throws IOException {
        proverbs = new ArrayList<>();
        init();
        ServerSocket serverSocket = new ServerSocket(4000);
        try{
            while(true){
                System.out.println("Waiting for client connexion ...");
                Socket client = serverSocket.accept();
                System.out.println("Client connected !");
                Client1Thread thr = new Client1Thread(client, getProverb());
                thr.start();
            }
        } finally {
            serverSocket.close();
        }
    }

    public static void main(String[] args) {
        try{
            withThread();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}