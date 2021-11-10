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
        WIDTH = (w-100)/(double) p.get(p.size()-1);
        HEIGHT = (h-100)/(double) p.get(p.size()-2);;
    }
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawLine(50, 50, 50, 500);
        g2d.drawLine(50, 500, 1000, 500);

        Double x1, y1, x2, y2;
        for(int i=0; i< points.size()-3; i++){
            x1 = (WIDTH*points.get(i))+50;
            y1 = 500-(HEIGHT*points.get(i+1));
            x2 = (WIDTH*points.get(i+2))+50;
            y2 = 500-(HEIGHT*points.get(i+3));
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}



