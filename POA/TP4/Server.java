package TP4;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IFibonacci {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Server() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws Exception {
        Server s = new Server();
        LocateRegistry.createRegistry(4000);
        Naming.rebind("rmi://localhost:4000/Fibo", s);
        System.out.println("Le serveur est prêt");
    }

    public int fibonacci(int param) throws RemoteException{
        if (param <= 1){
            return param;
        } else {
            return fibonacci(param - 1) + fibonacci(param - 2);
        }
    }
}
