package TP1;

public class Animal implements Runnable{
    public String animalName;
    public int speed, distance;
    public Animal(String name, int speed){
        animalName = name;
        this.speed = speed;
        this.distance = 0;
    }
    public void run(){
        while(!Course.raceFinished){
            try {
                if(this.distance >= 100){
                    Course.raceFinished = true;
                    Course.winner = this.animalName;
                    System.out.println("Course terminée, le vainquer est: " + Course.winner);
                    break;
                } else {
                    this.distance += this.speed;
                    System.out.println(this.animalName + " a parcouru " + this.distance + "m." );
                    if(this.animalName.equals("Lièvre") && this.distance == 50){
                        System.out.println("Le Lièvre dort !!");
                        Thread.sleep(2000);
                        System.out.println("Le Lièvre s'est réveillé !!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}