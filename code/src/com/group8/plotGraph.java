package com.group8;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class plotGraph extends JFrame {

    ArrayList<Point2D.Double> points = new ArrayList<>();
    Point2D.Double maxPoint = new Point2D.Double(10, 10);
    Point2D.Double minPoint = new Point2D.Double(-10, -10);
    Double xInc =1.0;
    Double yInc = 1.0;


    public void setMaxPoint(Double x, Double y) {
        maxPoint.setLocation(x,y);
    }

    public void setMinPoint(Double x, Double y) {
        minPoint.setLocation(x,y);
    }

    public void setIncrement(Double x, Double y){
        xInc = x;
        yInc = y;
    }

    public double getMaxX(){
        return maxPoint.getX();
    }

    public double getMaxY(){
        return maxPoint.getY();
    }

    public void addPoint(Double x, Double y){
        points.add(new Point2D.Double(x,y));
    }

   /* public void printPoints(){
        for(int i=0; i<points.size(); i++){
            System.out.println(points.get(i));
        }
    }*/

    public void initUI() {
//        System.out.println(xInc+" "+yInc);
//        System.out.println(minPoint + " " + maxPoint);
        getContentPane().removeAll();
        getContentPane().repaint();
        Graph g = new Graph(points, maxPoint, minPoint, xInc, yInc);
        add(g);
        setTitle("Graph");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
