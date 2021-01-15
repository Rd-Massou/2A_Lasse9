package TP1;

public class Main {
    public static void Exo1() {
        Runnable counter = new NumberCount(), alpha1 = new AlphaMaj(), alpha2 = new AlphaMin();
        Thread th1 = new Thread(counter), th2 = new Thread(alpha1), th3 = new Thread(alpha2);
        th1.start();
        th2.start();
        th3.start();
    }

    public static void Exo3() {
        Animal tortue = new Animal("Tortue", 2), lievre = new Animal("Lièvre", 10);
        Course course = new Course(lievre, tortue);
        course.race();
    }

    public static void main(String[] args) {
        Exo3();
    }
}