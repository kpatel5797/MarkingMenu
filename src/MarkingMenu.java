// Parts of code are taken from the Oracle's Java source code

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;

public class MarkingMenu extends JPopupMenu{
 
    Shape mainCircle;
    Shape secondCircle;
    Dimension d;
    int numItems = 0;
    int secondNumItems = 0;
    Point center;
    Point secondCenter;
    Vector<String> names = new Vector<String>();
    Vector<String> secondNames = new Vector<String>();
    Vector<JMenuItem> menuItems = new Vector<JMenuItem>();
    private int pointX[];
    private int pointY[];
    private int secondPointX[];
    private int secondPointY[];
    boolean secondary = false;
    JMenu secondMenu = null;

    public MarkingMenu() {
        d = getPreferredSize();
        mainCircle = new Ellipse2D.Double(100, 100, 200, 200);
        center = new Point((int)d.getHeight()/2, (int)d.getWidth()/2);
        setOpaque(false);
        setVisible(false);
        addListeners();
    }

    @Override
    public void show(Component invoker, int x, int y) {
        super.show(invoker, x-200, y-200);
    }

    @Override
    public JMenuItem add(JMenuItem j){
        if(numItems+1 > 8) {
            System.err.println("Cannot have more than 8 items in the Marking Menu!!!");
        }
        else {
            numItems = numItems + 1;
            names.add(j.getText());
            menuItems.add(j);
        }
        pieDivision(numItems);
        return new JMenuItem();
    }

    public void pieDivision(int n) {
        double angle = 0;
        if(secondary == false) {
            pointX = new int[n];
            pointY = new int[n];
            for(int i=0; i<n; i++){
                angle = i*(360/n);
                pointX[i] = (int)((center.getX() + 100 * Math.cos(Math.toRadians(angle))));
                pointY[i] = (int)((center.getY() + 100 * Math.sin(Math.toRadians(angle))));
            }
        }
        else {
            secondPointX = new int[n];
            secondPointY = new int[n];
            for(int i=0; i<n; i++){
                angle = i*(360/n);
                secondPointX[i] = (int)((secondCenter.getX() + 100 * Math.cos(Math.toRadians(angle))));
                secondPointY[i] = (int)((secondCenter.getY() + 100 * Math.sin(Math.toRadians(angle))));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
    	// super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fill(mainCircle);
        g2.setColor(Color.BLACK);
        g2.draw(mainCircle);

        if(numItems > 1) {
        for(int i=0; i<numItems; i++) {
                g2.setColor(Color.BLACK);
                g2.draw(new Line2D.Double(center.getX(), center.getY(), pointX[i], pointY[i]));
            }
        }
        if(numItems == 1) {
            g.drawString(names.get(0), 50, 100);
        }
        else if(numItems == 2) {
            g.drawString(names.get(0), ((pointX[0]+pointX[1])/2)-50, ((pointY[0]+pointY[1])/2)-50);
            g.drawString(names.get(1), ((pointX[0]+pointX[1])/2)-50, ((pointY[0]+pointY[1])/2)+50);
        }
        else {
            for(int i=0; i<numItems; i++) {
                int x1 = pointX[i];
                int y1 = pointY[i];
                int x2;
                int y2;
                if(i+1 == numItems){
                    x2 = pointX[0];
                    y2 = pointY[0];
                }
                else {
                    x2 = pointX[i+1];
                    y2 = pointY[i+1];
                }
                g.drawString(names.get(i), ((x1+x2)/2)-10, ((y1+y2)/2)+10);
            }
        }
        if(secondary == true) {
            g2.setColor(Color.WHITE);
            g2.fill(secondCircle);
            g2.setColor(Color.BLACK);
            g2.draw(secondCircle);
            int n = secondMenu.getItemCount();
            if(n > 1) {
                for(int i=0; i<n; i++) {
                        g2.setColor(Color.BLACK);
                        g2.draw(new Line2D.Double(secondCenter.getX(), secondCenter.getY(), secondPointX[i], secondPointY[i]));
                    }
                }
                if(n == 1) {
                    g.drawString(secondNames.get(0), 50, 100);
                }
                else if(n == 2) {
                    g.drawString(secondNames.get(0), ((secondPointX[0]+secondPointX[1])/2)-50, ((secondPointY[0]+secondPointY[1])/2)-50);
                    g.drawString(secondNames.get(1), ((secondPointX[0]+secondPointX[1])/2)-50, ((secondPointY[0]+secondPointY[1])/2)+50);
                }
                else {
                    for(int i=0; i<n; i++) {
                        int x1 = secondPointX[i];
                        int y1 = secondPointY[i];
                        int x2;
                        int y2;
                        if(i+1 == n){
                            x2 = secondPointX[0];
                            y2 = secondPointY[0];
                        }
                        else {
                            x2 = secondPointX[i+1];
                            y2 = secondPointY[i+1];
                        }
                        g.drawString(secondNames.get(i), ((x1+x2)/2)-10, ((y1+y2)/2)+10);
                    }
                }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    public void addListeners() {
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(secondary == true && secondCircle.contains(e.getX(), e.getY())) {
                    int sector = whichSector(e.getX(), e.getY(), secondPointX, secondPointY, secondNumItems, false);
                    setVisible(false);
                    secondMenu.getItem(sector).doClick();
                }
                else if(mainCircle.contains(e.getX(), e.getY())) {
                    secondary = false;
                    int sector = whichSector(e.getX(), e.getY(), pointX, pointY, numItems, true);
                    secondNumItems = ((JMenu)menuItems.get(sector)).getItemCount();
                    if(secondNumItems > 0) {
                        if(numItems+1 > 8) {
                            System.err.println("Cannot have more than 8 items in the Marking Menu!!!");
                        }
                        else {
                            secondMenu = ((JMenu)menuItems.get(sector));
                            secondary = true;
                            secondCenter = new Point(e.getX(), e.getY());
                            secondCircle = new Ellipse2D.Double(e.getX()-100, e.getY()-100, 200, 200);
                            secondNames.clear();
                            for(int i=0; i<secondNumItems; i++) {
                                secondNames.add(secondMenu.getItem(i).getText());
                            }
                            pieDivision(secondNumItems);
                            repaint();
                        }
                    }
                    else {
                        (menuItems.get(sector)).doClick();
                    }
                }
                else {
                    setVisible(false);
                    secondary = false;
                }
            }
        });
    }

    public int whichSector(int x, int y, int ptX[], int ptY[], int n, boolean circle) {
        int sector = 0;
        if(n == 1) {
            sector = 0;
        }
        else if(n == 2) {
            if(y < ptY[0]) {
                sector = 0;
            }
            else {
                sector = 1;
            }
        }
        else {
            for(int i=0; i<n; i++) {
                int x1 = ptX[i];
                int y1 = ptY[i];
                int x2;
                int y2;
                if(i+1 == n){
                    x2 = ptX[0];
                    y2 = ptY[0];
                }
                else {
                    x2 = ptX[i+1];
                    y2 = ptY[i+1];
                }

                int X[];
                int Y[];
                if(circle == true) {
                    X = new int[]{200, x1, x2};
                    Y = new int[]{200, y1, y2};
                }
                else {
                    X = new int[]{(int)secondCenter.getX(), x1, x2};
                    Y = new int[]{(int)secondCenter.getY(), y1, y2};
                }
                Polygon p = new Polygon(X, Y, 3);
                Line2D l1= new Line2D.Double(x1, y1, x2, y2);
                Line2D l2 = new Line2D.Double(200, 200, x, y);
                if(p.contains(x, y) || l1.intersectsLine(l2)) {
                    sector = i;
                }
            }
        }
        return sector;
    }
}