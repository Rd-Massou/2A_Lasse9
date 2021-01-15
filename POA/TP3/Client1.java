package TP3;

import java.io.*;
import java.net.Socket;

public class Client1 {
    public static void main(String[] args) throws IOException{
        Socket client = new Socket("localhost", 4000);
        InputStream in = client.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
        String proverb = buffer.readLine();
        System.out.println("Todays proverb: " + proverb);
        client.close();
    }
}