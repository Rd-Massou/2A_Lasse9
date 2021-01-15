package TP4;

import java.rmi.Naming;

public class Client {
    public static void main(String[] args) throws Exception{
        IFibonacci f = (IFibonacci) Naming.lookup("rmi://127.0.0.1:4000/Fibo");
        System.out.println("f(45) = "+f.fibonacci(45));
    }
}
