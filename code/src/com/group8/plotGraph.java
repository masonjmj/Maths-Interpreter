package com.group8;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class plotGraph extends JFrame {

    ArrayList<Point2D.Double> points = new ArrayList<>();


    public void addPoint(Double x, Double y){
        points.add(new Point2D.Double(x,y));
    }

   /* public void printPoints(){
        for(int i=0; i<points.size(); i++){
            System.out.println(points.get(i));
        }
    }*/

    public void initUI() {

        add(new Graph(points, 1000, 500,50));

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
