package com.group8;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Graph extends JPanel {

    ArrayList<Double> points;
    double WIDTH;
    double HEIGHT;

    public Graph(ArrayList p, int w, int h){
        points = p;
        WIDTH = w;
        HEIGHT = h;
    }

    private double getX(double x){
        double newX;
        newX = 50+(((WIDTH-50)/ points.get(points.size()-2))*x);
        return(newX);
    }

    private double getY(double y){
        double newY;
        newY = 500-(((HEIGHT-50)/ points.get(points.size()-1))*y);
        return(newY);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawLine(50, 50, 50, 500);
        g2d.drawLine(50, 500, 1000, 500);

        Double x1, y1, x2, y2;
        for(int i=0; i< points.size()-3; i+=2){
            x1 = getX(points.get(i));
            y1 = getY(points.get(i+1));
            x2 = getX(points.get(i+2));
            y2 = getY(points.get(i+3));
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
            System.out.println("Printing point"+i+" "+x1+" "+y1);
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}



