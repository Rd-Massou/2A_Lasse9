# Correction Exam Normal POA 2020:
## <span style="color:orange">Cours :</span>
- Pourquoi les méthodes stop(), suspend() et resume() sont deprecated ?

**->** <span style="color:green"><strong>Car leurs usage provoque souvent des situation d'interblocage</strong></span>
- C'est quoi la différence entre RMI (Remote Method Invocation) et les sockets?

**->** <span style="color:green"><strong>RMI est un protocol qui permet l'accès à un Objet Distant (OD) et ses méthodes. Les socket sont un moyen de communication entre les différent processus via un réseau.</strong></span>

## <span style="color:orange">Exercice 1:</span>
<ol><li>Écrire la classe Fourchettes que doivent se partager les philosophes. Elle doit contenir:
    <ul type="">
    <li>Le nombre et le tableau d'occupation des fourchettes</li>
    <li>Le(s) constructeur de la classe</li>
    <li>Les deux fonctions dédiés pour prendre et libérer les fourchette</li>
    </ul>

```java
public class Fourchettes {
    public int nombreDeFourchettes;
    private int tabOccup;
    private Lock forkLock = new ReentrantLock();
    private Condition forkDispo = forkLock.newCondition();
    public Fourchettes(int nombreDeFourchettes, int[] tabOccup){
        this.nombreDeFourchettes = nombreDeFourchettes;
        this.tabOccup = tabOccup;
    }
    public void prendre(int i)throws InterruptedException{
        forkLock.lock();
        try{
            while(tabOccup[i] == 1 ){
                forkDispo.await();
            }
            tabOccup[i] = 1;
            forkDispo.signalAll();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void liberer(int i) { 
        tab[i] = 0;
    }
}
```

</li>
<li> Écrire la classe Philosophe dédié pour représenter les philosophes. Elle doit contenir:
    <ul type="">
    <li>Identificateur du philosophe</li>
    <li>Un lien ver la fourchette</li>
    <li>Le nombre de bouchées à ingurgiter (avaler)</li>
    <li>Le temps minimum pour avaler une bouchée, un temps variable pour manger, un temps minimun pour réfléchir et un temps pour divaguer</li>
    <li>Le(s) constructeurs de la classe</li>
    <li>La fonction principale du philosophe: cycle sur manger, penser. Pour manger, il prend la fourchette de droite et celle de gauche (la fourchette i et (i+1)%n). Après, le philosophe garde les fourchettes un certain temps et les dépose ensuite et la boucle se termine lorsque le philosophe a terminé ses bouchées.<br>NB: Vous pouvez utiliser les méthodes Random() et nextInt().</li>
    </ul>

```java
Pas sur de la réponse ...
```

</li>
<li>La classe Dinner qui crée un objet de type Fourchettes, n Philosophe lié à cet objet (n=3) et les « démarre »

```java
public class Dinner {
    public static void main(String[] args) {
        int[] tab = {0,0,0};
        Fourchettes forks = new Fourchettes(3, tab);
        Philosophe philo1 = new Philosophe(forks);
        Philosophe philo2 = new Philosophe(forks);
        Philosophe philo3 = new Philosophe(forks);
        new Thread(philo1).start();
        new Thread(philo2).start();
        new Thread(philo3).start();
    }
}
```
</li>
</ol>

## <span style="color:orange">Exercice 2:</span>
On veux écrire une application client/serveur qui permet de savoir si une machine est active. Cette application utilise des sockets TCP:
1. Ecrivez un programme Serveur qui attends une requête du client sur le port 7554 et lui envoie un proverbe (parmis une liste existante) au client, chaque fois que celui-ci s'y connecte Le serveur, le serveur doit être capable de gérer plusieurs clients simultanément. <span style="color:green"><strong>(Voir TP3: Server1)</strong></span>
1. Écrivez le programme Client qui lit une adresse au clavier et envoie une requête au serveur de la machine correspondante pour savoir si elle est active. Dans l’affirmative, il affiche le message envoyé par le serveur.
```java
public class Client {
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez l'adresse à consulter: ");
        String host = sc.nextLine();
        InetAddress adresse = InetAddress.getByName(hostname);
        Socket client = new Socket(adresse, 7554);
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
## <span style="color:orange">Exercice 3:</span>
Nous disposons d'un service qui représente un contrôleur de température **TemperatureSensor** qui offre les opérations de gestion de température d'un système industriel. Les méthodes offertes par ce services sont les suivantes:
```java
void augmenterTemp(double tempVal);
void diminuerTemp(double tempVal);
double lire_temp();
```
<ol>
<li>Quelles sonts les étapes nécessaires pour le développement d'une application distribuées avec RMI (à partir de JDK 1.5) ?
<span style="color:green"><strong>(Voir Étapes 1-2-3 du lasse9)</strong></span>

<li>On souhaite rendre chacune de ces méthodes accessible à distance de manière à ce qu'elles définissent l'interface **TemperatureSensorInterface** entre le client et le serveur. Ecrire cette Interface.

```java
import java.rmi.*;
public interface TemperatureSensorInterface extends Remote {
    public void augmenterTemp(double tempVal) throws RemoteException;
    public void diminuerTemp(double tempVal) throws RemoteException;
    public double lire_temp() throws RemoteException;
}
```
</li>
<li>Déduire la classe **TemperatureSensor** qui matérialise le service qui offre les opérations augmenterTemp(), diminuerTemp() et lire_temp().

```java
import java.rmi.*;
import java.rmi.server.*;
public class TemperatureSensor extends UnicastRemoteObject implements  TemperatureSensorInterface {
    private double temperature;
    public TemperatureSensor(double temperature){
        this.temperature = temperature;
    }
    public void augmenterTemp(double tempVal) throws RemoteException {
        temperature += tempVal;
    }
    public void diminuerTemp(double tempVal) throws RemoteException{
        temperature. -= tempVal;
    }
    public double lire_temp() throws RemoteException{
        return temperature;
    }
}
```
</li>
<li>Donner le programme serveur Serveur.java qui doit être installé sur la machine services.ensias.ma sachant que le service de noms doit être activé sur le port 5789

```java
import java.rmi.*;
import java.rmi.registry.*;
import java.net.InetAddress;
public class Serveur {
    public static void main(String[] args) throws Exception {
        try {
            TemperatureSensor tempSr= new TemperatureSensor(37.0);
            String hostname = "ensias.ma";
            InetAddress adresse = InetAddress.getByName(hostname);
            LocateRegistry.createRegistry(5798);
            Naming.rebind("rmi://"+adresse+":5798/serviceTemp", tempSr);
            System.out.println("Le serveur est prêt !");
        } catch (Exception e) {
            System.out.println("Erreur de liaison de l'objet TemperatureSensor");
            System.out.println(e.toString());
        }
    }
}
```
</li>
<li>
Le programme du client Client.java qui doit être lancé à partir d'une autre machine.

```java
import java.rmi.*;
import java.net.InetAddress;
public class Client {
    public static void main(String[] args) throws Exception{
        String hostname = "ensias.ma";
        InetAddress adresse = InetAddress.getByName(hostname);
        TemperatureSensorInterface Temp = (TemperatureSensorInterface) Naming.lookup("rmi://"+adresse+":5798/serviceTemp");
        System.out.println("Température actuelle: "+Temp.lire_temp());
        Temp.augmenterTemp(0.5);
        Temp.diminuerTemp(19.5);
        System.out.println("Nouvelle température: "+Temp.lire_temp());
    }
}
```
</li>
</ol>
