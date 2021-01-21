# Lasse9 POA:
## Threads:
### Création:
*Méthode 1:*
```java
class MonThread extends Thread{
    //attributs
    //constructeurs
    @Override
    public void run() {
        // Traitement du thread
    }
}

MonThread thread = new MonThread();
thread.start();
```
*Méthode 2: (la plus recommandée)*
```java
class MonRunnable implements Runnable{
    //attributs
    //constructeurs
    @Override
    public void run() {
        // Traitement du thread
    }
}

Runnable runnable = new MonRunnable();
Thread thread = new Thread(runnable);
thread.start;
```
### Méthodes relatives au Thread:
Il existe plusieurs méthodes qui manipule le thread. En effet il la figure ci-dessous résume tout celà.

<img src="Screen Shot 2021-01-15 at 06.22.21.png" width="800" height="300"/>

*Les états:*

Il existe plusieurs états du thread, à savoir:
- <span style="color:green"><strong>Non Actif (Créé)</strong></span> : c'est un thread crée mais qui n'est pas encore lancé donc faut utiliser **méthode start()** pour l'activer
- <span style="color:green"><strong>Prêt</strong></span> : un thread actif mais qui n'a pas le processeur pour s'executer
- <span style="color:green"><strong>Executé</strong></span> : Thread en cours d'execution donc qui occupe le processeur
- <span style="color:green"><strong>Bloqué (Endormi)</strong></span> : Un thread qui est mis en attente

*Méthodes:*
- <span style="color:orange"> <strong> start() </strong> </span>: Elle permet d'activer le thread, càd éxécuter la méthode run() défini dans notre thread.
- <span style="color:orange"> <strong> sleep(milliseconds) </strong> </span>: Elle permet de faire endormir le thread pour un nombre de millisecondes donné. **NB**: le temps du processeur n'est pas consomé par cette méthode quand le thread est endormi.
```java
Thread thread = new Thread();
thread.start();
thread.sleep(5000);
// Le thread va s'endormir pour 5 seconds puis continuer son execution
```
- <span style="color:orange"> <strong> wait() / wait(millisec) </strong> </span>: Elle permet de bloquer l'éxécution du thread (l'endormir) jusqu'a ce qu'on le réveille avec la méthode <span style="color:orange"> <strong> notify() </strong> </span> ou <span style="color:orange"> <strong> notifyAll() </strong> </span>.
```java
class MonRunnable implements Runnable{
    //attributs
    //constructeurs
    @Override
    public void run() {
        while(maCondition){
            wait();
        }
        //traitement
        notifyAll();
    }
}
```
- <span style="color:orange"> <strong> join() </strong> </span>: Elle permet d'attendre qu'une instruction ou un autre Thread termine son execution pour continuer.
```java
Thread th1 = new Thread(), th2 = new Thread();
th1.start();
th2.start();
th1.join(); // On attend que th1 termine pour continuer
```
- <span style="color:orange"> <strong> stop(), suspend(), resume(), destroy()</strong> </span>: Ces 4 méthodes sont des méthode deprecated ou obsolete i.e elles ne sont plus fonctionnelles donc à ne jamais utiliser.

*Priorité:*

C'est une notion explicite qui s'explique elle même. En effet chaque thread à une priorité de 5 i.e ```Thread.NORM_PRIORITY``` à sa création. Les priorité du thread peuvent varider de 1 jusqu'à 10 i.e de ```Thread.MIN_PRIORITY``` jusqu'à ```Thread.MAX_PRIORITY```.

Pour se faire nous utilisons ces 2 méthodes:
```java
Thread thread = new Thread();
thread.getPriority(); // Pour récupérer la priorité actuelle du thread
thread.setPriority(Thread.MAX_PRIORITY) // ou autres valeurs
```
Un Thread ayant une plus grande priorité occupe évidement le processus. Donc pour céder le processus à un autre Thread de priorité égale ou inférieure, on procède de deux manières:

- <span style="color:green"><strong>Vonlontairement</strong></span> : Soit explicitement à l'appel de la méthode <span style="color:orange"> <strong> yield() </strong> </span> soit implicitement lors d'un <span style="color:orange"> <strong> wait() </strong> </span> ou <span style="color:orange"> <strong> sleep() </strong> </span>
- <span style="color:green"> <strong> Involontairement </strong> </span>: Lorsque le Thread est bloqué par une entrée/sortie.

### Accès Conccurent:

On parle de l'accès conccurent surtout qu'on évoque la notion de la section critique. La section critique est une section de code où on touche à une ressource paratgée entre plusieurs Thread. La mal gestion de cette section provoque la corruption des données ... Il y'a deux méthode pour réglement l'accès à cette section et qui sont:

- <span style="color:orange"> <strong> synchronized </strong> </span>: ce mot clé permet de placer un lock sur un objet, une méthode ou un block de code. Donc chaque Thread qui entre dans la zone critique ferme le lock pour faire son traitement. Une fois qu'il fini, il libère le lock. 
```java
public synchronized void run(){
    try{
        while(condition){
            wait();
        }
        //traitement
        notifyAll();
    } catch (Exception e){
        e.printStackTrace();
    }
}
```
- <span style="color:orange"> <strong> ReentrantLock + Condition </strong> </span>: Ces 2 classes permet de manipuler le Lock comme on veut tout en définissant la/les condition(s) qui peuvent liberer le Lock. 
```java
private Lock lock = new ReentrantLock();
private Condition laCondition = lock.newCondition();
public void methodeName(){
    lock.lock();
    //traitement
    lock.unlock();
}
public void run(){
    lock.lock();
    try{
        while(condition){
            laCondition.await();
        }
        //traitement
        laCondition.signalAll();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        lock.unlock();
    }
}
```
## Sockets:
### Initialisation:

L'initionlisation des sockets se fait comme suit:
- <span style="color:orange"> <strong>Côté client</strong> </span>:
```java
int port = 4040; // les port dispo varient de 1024 -> 65535
String myHost = "localhost"; // ou n'import quelle autre host de votre serveur :D
Socket socket = new Socket(myHost,port);
// traitement (Input/Output)
socket.close();
```
- <span style="color:orange"> <strong>Côté serveur</strong> </span>:
```java
port = 4040;
ServerSocket server = new ServerSocket(port);
try{
    while(true){
        Socket client = server.accept(); // pour accepter une socket qui veux communiquer avec le serveur
        //traitement (Input/Output)
        client.close();
    }
} catch (Exception e) {
    e.printStackTrace();
} finally {
    server.close();
}
```
### Input/Output (communication):
Pour assurer une comminication entre le client et le serveur, nous utilisons les classes suivantes:

- <span style="color:orange"> <strong>BufferedReader</strong> </span>(input)
```java
// Input : Lecture de donnée reçu
Socket socket = new Socket(8080);
BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.InputStream()));
String message = buffer.readLine();
System.out.println(message);
```
- <span style="color:orange"> <strong>PrintWriter</strong> </span>(output)
```java
// Output : Ecriture de donnée à envoyer
ServerSocket server = new ServerSocket(8080);
try{
    Socket client = server.accept();
    PrintWriter printer = new PrintWriter(client.getOutputStream(), true);
    printer.println(proverb);
    printer.close();
    client.close();
} catch (Exception e) {
    e.printStackTrace();
} finally {
    server.close();
}
```

## Proxy RMI:
<ol> <li> <span style="color:green"> <strong>Étape 1 </span>: Définition de l'interface de l'objet distant :</strong> 
<ul> <li> interface héritant de java.rmi.Remote </li>
<li> Utiliser pour les méthodes : "throws java.rmi.RemoteException" </li>
<li> paramètres de type simple, objets Sérialisables (implements Serializable) ou Distants (implements Remote)</li>
</ul>

```java
import java.rmi.*;
public interface IMyInterface extends Remote {
    public int myMethode(int param) throws RemoteException;
}
```
<li><span style="color:green"> <strong>Étape 2</span>: Ecrire une implémentation de l'interface définie</strong> 
<ul>
    <li>Classe héritant de java.rmi.server.UnicastRemoteObject et implémentant l'interface précédente.</li>
    <li>Ecrire un main permettant l'enregistrement auprès du Naming</li>
</ul>

```java
import java.rmi.*;
import java.rmi.server.*;
public class MyClass extends UnicastRemoteObject implements IMyInterface {
    // joue le rôle de l'object distant 
    // Attributs
    public MyClass() throws RemoteException {
        super();
    }
    /*
    public MyClass(Args) throws RemoteException {
        Attributs = Args
    }
    */
    // Implémenter la méthode déclarée dans l'interface
    public int MyMethode(int param) throws RemoteException{
        int resultat = param**3;
        return resultat;
    }
}
```
</li>
<li><span style="color:green"> <strong>Étape 3 </span>: Génération de la classe stub nécessaire au client et Ecriture du programme client </strong> 
<ul>
    <li>Utilisation du Naming pour trouver l'objet distant</li>
    <li>appel(s) de méthodes.</li>
</ul>

**<span style="color:Orange">Class Server (stub):</span>**
```java
import java.rmi.*;
import java.rmi.registry.*;
import java.net.InetAddress;
public class Server {
    public static void main(String[] args) throws Exception {
        try{
            /* Pour la sécurité, facultatif dans l'exam
            System.setProperty("java.security.policy","client.policy");
            */
            MyClass s = new MyClass();
            int port = 4000; // port par défault: 1099
            LocateRegistry.createRegistry(port);
            String hostname = "MonSite.com";
            InetAddress adresse = InetAddress.getByName(hostname); 
            Naming.rebind("rmi://"+adresse+":"+port+"/MyAlias", s);
            /* enregistrer l'objet distant dans l'enrée indiquée
            On utilise rebind au lieu de bind pour éviter AlreadyBoundException
            lorsque l'entrée existe déja.
            */
            System.out.println("Le serveur est prêt");
        } catch (Exception e) {
            System.out.println("Erreur de liaison de l'objet TemperatureSensor");
            System.out.println(e.toString());
        }
    }
}
```
**<span style="color:Orange">Programme Client:</span>**
```java
import java.rmi.*;
import java.net.InetAddress;
public class Client {
    public static void main(String[] args) throws Exception{
        // appel à l'objet distant:
        int port = 4000; // port par défault: 1099
        String hostname = "MonSite.com";
        InetAddress adresse = InetAddress.getByName(hostname); 
        IMyInterface f = (IMyInterface) Naming.lookup("rmi://"+adresse+":"+port+"/MyAlias");
        // Utilisation :
        System.out.println("f(45) = "+f.MyMethode(45));
    }
}
```
</li>
<li><span style="color:green"> <strong>Étape 4 </span>: Écrire le policy file (policy.file)</strong> 

```
grant{
    permission java.net.SocketPermission
    "*:1024-65535", "connect";
};
```
</li>
<li><span style="color:green"> <strong>Étape 5 </span>: Compilation + Lancement du Serveur + Excution du client.</strong> 

```shell
$ javac *.java
$ start rmiregistry
$ start java Server
$ java –Djava.security.policy=client.policy Client
```
</li>
</ol>


# Bon courage :blush::blush:
