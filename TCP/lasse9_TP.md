# Lasse9 TCP/IP partie TP:
## VLSM (Variable Lenght Subnet Mask):
<span style="color:orange">**Table de réference**:</span>
| Subnet | 1 | 2 | 4 | 8 | 16 | 32 | 64 | 128 | 256 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Host |  256 | 128 | 64 | 32 | 16 | 8 | 4 | 2 | 1 |
| Subnet Mask | /24 | /25 | /26 | /27 | /28 | /29 | /30 | /31 | /32 |

- <span style="color:orange">**Étape 1:**</span> Classer les sous-réseaux selon le nombre de machine dans l'ordre décroissant.

- <span style="color:orange">**Étape 2:**</span>
Prendre le plus grand sous-réseaux. Chercher le nombre minimal dans le tableau de réference (Host) qui peux contenir le nombre de machine du réseau. <br>**(nombre - 2) ≥ nombre de machine**

- <span style="color:orange">**Étape 3:**</span> assigner le réseaux correspondant comme dans l'exemple. Sous-réseau de 34 machines:<br>le nombre minimal dans le tablea qui satisfait ce nombre est 64 <br>-> A.B.C.0 ou A.B.C.64 ou A.B.C.128 ou A.B.C.192

- <span style="color:orange">**Étape 4:**</span> répéter cette manipulation pour chaque sous-réseau

<span style="color:green">**vidéo tutoriel (14mins):**</span>  https://www.youtube.com/watch?v=RLCd5u0sjoU

## CIDR

Pour définir une seul mask pour des réseaus differents (résumé de plusieurs réseaux). On peut le voir comme l'inverse de VLSM.

Utilisation: On ecrit les adresses IP avec la représentation binaire, on repère la partie qui est identique. C'est cette partie qui reprèsente le résumé de ces adresses.

<img src="CIDR.jpeg" width="800" height="300"/>



## Commandes pour configuration du routeur:
### Général
<ul>
<li> La configuration du mode prévilifié:

```
//Pour entrer le mode priviligié
# enable

//Pour configurer le routeur
# configure terminal 

//Pour changer le nom de votre routeur dans le terminal
# hostname XXX

//Pour securiser le mode priviligié par un mot de passe
# enable password XXX

//Pour définir une route statique de 192.168.2.0 ayant le mask 255.255.255.0 à traver une passerelle 212.217.2.17
# ip route 192.168.2.0 255.255.255.0  212.217.2.17
```
</li>
<li>La configuration de la console:

```
//Changer l'emplacement vers la line console n° X
# line console X

//securiser la line par un mot de passe
# password XXX

//Pour se connecter et revenir vers la configuration
# login
```
</li>
<li>La configuration d'une Interfance (connexion) Ethernet:

```
//Pour changer vers l'extrémité (X/X) de la cnx fast
# interface fastethernet  X/X

//Configurer l'adresse Ip et le subnet mask
# ip address  192.168.1.1  255.255.255.0 

//Pour garder la cnx à marche
# no shutdown
```
</li>
<li>La configuration d'une Interfance (connexion) Serie:

```
//Pour changer vers l'extrémité (X/X) de la cnx serie
# interface serial 0/0

//Configurer l'adresse Ip et le subnet mask
# ip address  212.217.2.18  255.255.255.252

//Réservée juste pour le routeur guideur.
# clock rate 64000 (pour le routeur DCE *)

//Pour garder la cnx à marche
# no shutdown
```
</li>
<li> Autres commandes

```
//Vérifier les routes
# show ip route

//Vérifier les protocole
# show protocols

//Voir votre configuration
# show running-config

//Sauvegarder votre configuration sur la NVRAM par :
# copy running-config startup-config

//Sauvegarder votre configuration sur un serveur tftp
# copy startup-config tftp

//Définition d'un loopback
# int loopback 0
# ip address 212.1.1.1 255.255.255.255
# ip route 192.168.0.0 255.255.0.0 loopback 0
```
</li>
</ul>

### Protocol RIP
Pour configurer le routeur selon le protocol RIP
```
//Commencer la configuration
# router rip

//Juste dans le cas de version 2
# version 2
# no auto-summary

//Ajouter les réseaux (adresse réseau) concernée par le protocole
# network A.B.C.D

//ajout d'un loopback

// Redistribution des routes statiques avec les autres routeurs 
# router rip
# redistribute static
```
### Protocol OSPF

```
//Commencer la configuration. 1 étant le numéro de processus
# Router OSPF 1

// Ajouter les réseaux (adresse réseau) concernée et les distribuer vers les areas.
# network id-reseau wild-card area 0

//ajout d'un loopback

// Partage des informations entre les routeurs
router OSPF 1
Default-information originate

//Afficher les le cout 
# sh ip ospf int
```