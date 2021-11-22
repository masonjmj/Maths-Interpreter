package com.group8;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Graph extends JPanel {

    ArrayList<Point2D.Double> points;
    double WIDTH;
    double HEIGHT;
    double OFFSET;
    double MAX_X;
    double MAX_Y;

    public Graph(ArrayList p, double w, double h, double o){
        points = p;
        WIDTH = w;
        HEIGHT = h;
        OFFSET = o;
        MAX_X = points.get(p.size()-1).getX();
        MAX_Y = points.get(p.size()-1).getY();
    }

    private double getX(double x){
        double newX;
        newX = OFFSET+(((WIDTH-OFFSET)/MAX_X)*x);
        return(newX);
    }

    private double getY(double y){
        double newY;
        newY = HEIGHT-(((HEIGHT-OFFSET)/MAX_Y)*y);
        return(newY);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawLine(50, 50, 50, 500);
        g2d.drawLine(50, 500, 1000, 500);

        Double x1, y1, x2, y2;
        for(int i=0; i< points.size()-3; i++){
            x1 = getX(points.get(i).getX());
            y1 = getY(points.get(i).getY());
            x2 = getX(points.get(i+1).getX());
            y2 = getY(points.get(i+1).getY());
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



