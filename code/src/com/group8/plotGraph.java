package com.group8;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class plotGraph extends JFrame {

    ArrayList<Double> points = new ArrayList<>();


    public void addPoint(Double x, Double y){
        points.add(x);
        points.add(y);
    }

   /* public void printPoints(){
        for(int i=0; i<points.size(); i++){
            System.out.println(points.get(i));
        }
    }*/

    public void initUI() {

        add(new Graph(points, 1000, 500));

        setTitle("Graph");
        setSize(1050, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

  /*  public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                plotGraph ex = new plotGraph();
                ex.setVisible(true);
            }
        });
    }*/
}
