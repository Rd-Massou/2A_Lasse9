package TP3;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server2 {

    public static String pageContent(String name){
        String content = "";
        File page = new File(name);
        Scanner sc = null;
        try {
            sc = new Scanner(page);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNext()){
            content += sc.nextLine();
        }
        sc.close();
        return content;
    }
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        try{
            while(true){
                System.out.println("Waiting for client's connexion ...");
                Socket client = serverSocket.accept();
                System.out.println("Client connected !!");
                PrintWriter printer = new PrintWriter(client.getOutputStream(), true);
                String content = pageContent("index.html"), header = "HTTP/1.0 200 OK\n\n" + content;
                printer.println(header);
                printer.close();
                client.close();
            }
        } finally {
            serverSocket.close();
        }
    }
}
