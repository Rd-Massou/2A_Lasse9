# Correction Exam Normal POA 2019:
## <span style="color:orange">Cours :</span>

Laquelle de ces propositions est fausse ?
1. La déclaration d'une méthode comme synchronized garantit qu’aucun interblocage ne peut se produire. (Faux)
1. La méthode sleep ne consomme pas de temps de processeur pendant qu’un Thread (Vrai)
sommeille.
1. Les méthodes suspend et resume de Thread sont obsolètes. (Vrai)

<span style="color:green"><strong>Justification :</strong></span>

Supposons avoir plusieurs threads, étant donné que chaque thread détient un verrou que l'autre thread veut, un blocage se produira où aucun thread ne peut obtenir le verrou qu'il veut, et aucun thread ne libère les verrous qu'il détient. Ainsi, les threads se bloquent jusqu'à l'infinie.

## <span style="color:orange">Multithreading :</span>

1. Ecrivez un programme dont le thread principal (main) lance et nomme deux nouveaux
threads. Chaque thread ainsi créé doit effectuer 10 fois les actions suivantes :

- attendre un temps aléatoire compris entre 0 et 200 ms.
- puis afficher son nom.
- le thread principal devra attendre la fin de l’exécution des deux threads qu’il a créés avant de terminer son exécution.
```java
public class Mult implements Runnable {

    public static void main(String[] args) {
        Mult ss = new Mult();
        Thread main = new Thread(ss, "Main");
        Thread th1 = new Thread(ss, "Fils A");
        Thread th2 = new Thread(ss, "Fils B");
        main.start();
        th1.start();
        th2.start();
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for(int i = 0; i<10 ;i++){
            int t = (int) Math.random() * 200;
            try{
                Thread.sleep(t);
                System.out.println(Thread.currentThread().getName());
                
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
}
```

2. Que doit-on faire pour que les deux threads affichent leurs noms en alternance ?

<span style="color:green"><strong>Il suffit de préciser les priorité et utiliser la fonction yield()</strong></span>

```java
public class Mult implements Runnable {

    public static void main(String[] args) {
        Mult ss = new Mult();
        Thread main = new Thread(ss, "Main");
        Thread th1 = new Thread(ss, "Fils A");
        Thread th2 = new Thread(ss, "Fils B");
        // Précision de la priorité
        th1.setPriority(Thread.MAX_PRIORITY); 
        th2.setPriority(Thread.MIN_PRIORITY);
        main.start();
        th1.start();
        th2.start();
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for(int i = 0; i<10 ;i++){
            int t = (int) Math.random() * 200;
            try{
                Thread.sleep(t);
                System.out.println(Thread.currentThread().getName());
                // Laisser la main à un autre Thread
                Thread.yield();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
}
```

## <span style="color:orange">Socket :</span>
On veut écrire une application client/serveur qui permet de savoir si une machine est active.
Cette application utilise des sockets TCP.
- Écrivez le programme Serveur qui attend une requête du client sur le port 8182 et lui
envoie un message (contenant l’heure locale). Le serveur doit être capable de gérer
plusieurs clients simultanément.
```java
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8182);
        try{
            while(true){
                LocalDateTime now = LocalDateTime.now();
                Socket socket = server.accept();
                // Utiliser des threads pour clients (Voir TP3 Sevrer 1 / ClientThead1)
                PrintWriter printer = new PrintWriter(socket.getOutputStream(), true);
                printer.println(now);
                socket.close();
            }
        } finally {
            server.close();
        }
    }
    
}

```
- Écrivez le programme Client qui lit une adresse au clavier et envoie une requête au serveur de la machine correspondante pour savoir si elle est active. Dans l’affirmative, il affiche le message envoyé par le serveur.
```java
public class Client {
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez l'adresse à consulter: ");
        String host = sc.nextLine();
        InetAddress adresse = InetAddress.getByName(hostname);
        Socket client = new Socket(adresse, 8182);
        InputStream in = client.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
        String serverMessage = buffer.readLine();
        if(!serverMessage.isEmpty()){
            System.out.println(serverMessage);
        }
        client.close();
    }
}
```

## <span style="color:orange">RMI :</span>

Nous disposons d’un service qui offre pour le client des opérations de gestion de son compte
courant à savoir :
```java
void debiter(double montant);
void crediter(double montant);
double getSolde();
```

<ol type="a">
  <li>On souhaite rendre chacune de ces méthodes accessibles à distance de manière à ce
qu’elles définissent l’interface entre le client et le serveur. Ecrire cette interface.
(Respectez les règles syntaxiques que doit suivre une interface Java RMI)

```java
import java.rmi.*;
public interface IServices extends Remote {
    void debiter(double montant) throws RemoteException;
    void crediter(double montant)throws RemoteException;
    double getSolde()throws RemoteException;
}
```
</li>
  <li>Déduire la classe qui matérialise le service qui offre les opérations debiter(), crediter() et
getsolde().
(Respectez les règles syntaxiques que doit suivre une implantation d’un objet serveur Java
RMI)

```java
import java.rmi.*;
import java.server.*;
public class Services extends UnicastRemoteObject implements IServices{
    private Compte compte;
    public Services(CompteBanque compte) throws RemoteException {
        this.compte = compte;
    }
    public static void debiter(double montant) throws RemoteException{
        if(montant < compte.getSolde()){
            compte.solde = compte.getSolde() - montant;
        }
    }
    public static void crediter(double montant)throws RemoteException{
        compte.solde = compte.getSolde() + montant;
    }
    public static double getSolde()throws RemoteException{
        return compte.solde;
    }
}
```

</li>
    <li>Compléter le fichier Serveur.java suivant afin de permettre l’enregistrement du service
    auprès de RMI Registry.
    
```java
import java.rmi.*;
import java.rmi.registry.*;
import java.net.InetAddress;
public class Serveur {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Serveur : Construction de l’implémentation");
            Compte cpt= new Compte(2000.0);
            System.out.println("Objet Compte enregistré dans RMIregistry");
            Service svc = new Service(cpt);
            String hostname = "MonSite.com";
            InetAddress adresse = InetAddress.getByName(hostname);
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://"+adresse+":1099/Service", svc);
            System.out.println("Attente des invocations des clients ");
        } catch (Exception e) {
            System.out.println("Erreur de liaison de l'objet Compte");
            System.out.println(e.toString());
        } // fin de catch
    } // fin du main
} // fin de la classe Serveur
```
</li>
    <li>Donner la suite de commandes ayant pour finalité le lancement du Serveur.

```shell
$ javac *.java
$ start rmiregistry
$ start java Server
```
</li>
</ol>
