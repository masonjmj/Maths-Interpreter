package com.group8;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Graph extends JPanel {

    ArrayList<Point2D.Double> points;
    Point2D.Double maxPoint;
    Point2D.Double minPoint;
    double OFFSET;

    public Graph(ArrayList p, double o){
        points = p;
        maxPoint = new Point2D.Double(5, 5);
        minPoint = new Point2D.Double(-5,-5);
        OFFSET = o;
    }

//    private double getX(double x){
//        double newX;
//        newX = OFFSET+(((WIDTH-OFFSET)/MAX_X)*x);
//        return(newX);
//    }
//
//    private double getY(double y){
//        double newY;
//        newY = HEIGHT-(((HEIGHT-OFFSET)/MAX_Y)*y);
//        return(newY);
//    }

    private Point2D.Double mapPoint(Point2D.Double point){
        Point2D.Double newPoint = new Point2D.Double(0,0);
        newPoint.x = ((getWidth()/(maxPoint.getX()-minPoint.getX()))*(point.getX() - minPoint.getX()));
        newPoint.y = getHeight()-((getHeight()/(maxPoint.getY()-minPoint.getY()))*(point.getY() - minPoint.getY()));
        return newPoint;
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        System.out.println(getWidth()+ " " + getHeight());

//        g2d.draw(new Line2D.Double(getWidth()/2, 0, getWidth()/2, getHeight()));
//        g2d.draw(new Line2D.Double(0, getHeight()/2, getWidth(), getHeight()/2));


        double increment = Math.pow(10, Integer.toString((int)maxPoint.getX() - (int)minPoint.getX()).length() - 2);

        for (double i = minPoint.getX(); i < maxPoint.getX(); i += increment) {
            if (i == 0) {
                g2d.setStroke(new BasicStroke(5));
            }
            g2d.draw(new Line2D.Double(mapPoint(new Point2D.Double(i, minPoint.getY())), mapPoint(new Point2D.Double(i, maxPoint.getY()))));
//            if (i % 100 == 0) {
                Point2D.Double notchPoint = mapPoint(new Point2D.Double(i, 0));
                g2d.drawString(Double.toString(i), (int)notchPoint.getX(), (int)notchPoint.getY() + 14);
//            }

            g2d.setStroke(new BasicStroke(1));
        }

        double yIncrement = Math.pow(10, Integer.toString((int)maxPoint.getY() - (int)minPoint.getY()).length() - 2);

        for(double i = minPoint.getY(); i < maxPoint.getY(); i += yIncrement) {
            if (i == 0) {
                g2d.setStroke(new BasicStroke(5));
            }
            g2d.draw(new Line2D.Double(mapPoint(new Point2D.Double(minPoint.getX(), i)), mapPoint(new Point2D.Double(maxPoint.getX(), i))));

            Point2D.Double notchPoint = mapPoint(new Point2D.Double(0, i));
            g2d.drawString(Double.toString(i), (int)notchPoint.getX() - 24, (int)notchPoint.getY());

            g2d.setStroke(new BasicStroke(1));
        }


        g2d.setStroke(new BasicStroke(2.5f));
        g2d.setPaint(Color.red);
        Point2D.Double pointA;
        Point2D.Double pointB;
        for(int i=0; i< points.size()-3; i++){
            pointA = mapPoint(points.get(i));
            pointB = mapPoint(points.get(i+1));
            g2d.draw(new Line2D.Double(pointA,pointB));
        }


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}



