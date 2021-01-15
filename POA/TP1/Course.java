package TP1;

public class Course{
    public static boolean raceFinished = false;
    public Animal lievre, tortue;
    public static String winner = " ";
    public int raceLength;
    public Course(Animal lievre, Animal tortue){
        this.tortue = tortue;
        this.lievre = lievre;
        this.raceLength = 100;
    }
    public void race(){
        Thread th1 = new Thread(tortue);
        Thread th2 = new Thread(lievre);
        th1.start();
        th2.start();
    }
}