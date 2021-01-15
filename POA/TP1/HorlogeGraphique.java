package TP1;

import java.awt.Font;
import javax.swing.*;

class Horloge implements Runnable{
    public int seconds, minutes, hours;
    public String s="", m="", h="";
    public HorlogeGraphique horlogeGraphique;
    public Horloge(HorlogeGraphique horlogeGraphique, int seconds, int minutes, int hours){
        this.horlogeGraphique = horlogeGraphique;
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
    }
    public Horloge(HorlogeGraphique horlogeGraphique){
        this.horlogeGraphique = horlogeGraphique;
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
    }
    public void run(){
        while(true){
            try {
                Thread.sleep(1000);
                seconds++;
                if(seconds == 60){
                    minutes++;
                    seconds = 0;
                }
                if(minutes == 60){
                    hours++;
                    minutes = 0;
                }
                if(hours == 24){
                    hours = 0;
                }
                s =(seconds<10 ? "0":"") + String.valueOf(seconds);
                m =(minutes<10 ? "0":"") + String.valueOf(minutes);
                h =(hours<10 ? "0":"") + String.valueOf(hours);
                this.horlogeGraphique.setText(h + ":" + m + ":" + s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

public class HorlogeGraphique extends JLabel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel label = new JLabel();
    public HorlogeGraphique(){
        this.setHorizontalAlignment(JLabel.CENTER);
        Font font = this.getFont();
        this.setFont(new Font(font.getName(),font.getStyle(),30));
        Runnable horloge = new Horloge(this, 58, 59, 23);
        Thread th = new Thread(horloge);
        th.start();
    }
    public static void main(String[] args) {
    JFrame frame = new JFrame("Horloge Graphique");
    frame.setSize(200, 200);
    frame.setContentPane(new HorlogeGraphique());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    }
}