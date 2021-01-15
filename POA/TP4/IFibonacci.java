package TP4;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFibonacci extends Remote {
    public int fibonacci(int param) throws RemoteException;
}
