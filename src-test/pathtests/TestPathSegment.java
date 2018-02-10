package pathtests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.rivierarobotics.mathUtil.Vector2d;
import org.rivierarobotics.pathfollowing.PathSegment;


public class TestPathSegment extends JFrame{
    
    private static final long serialVersionUID = 1L;
    public PathSegment segment = new PathSegment(new Vector2d(0,2), new Vector2d(5,5));
    public Vector2d point1 = new Vector2d(2,2);
    public Vector2d closest1 = segment.getClosestPoint(point1);
    public Vector2d point2 = new Vector2d(5,1);
    public Vector2d closest2 = segment.getClosestPoint(point2);
    public Vector2d point3 = new Vector2d(8,8);
    public Vector2d closest3 = segment.getClosestPoint(point3);
    public Vector2d point4 = new Vector2d(-3, -3);
    public Vector2d closest4 = segment.getClosestPoint(point4);
    public static final Vector2d ORIGIN = new Vector2d(225,225);
    public static final double SCALE = 50;

    public TestPathSegment(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void drawPathSeg(PathSegment seg, Graphics2D g2, Color col) {
        Vector2d beg = seg.getBeginning().scale(SCALE).add(ORIGIN);
        Vector2d end = seg.getEnd().scale(SCALE).add(ORIGIN);
        Line2D lin = new Line2D.Double(beg.getX(), beg.getY(), end.getX(), end.getY());
        g2.setColor(col);
        g2.draw(lin);
    }
    
    public void drawVectorFromOrigin(Vector2d vec, Graphics2D g, Color col) {
        Vector2d drawPos = vec.scale(SCALE).add(ORIGIN);
        Line2D lin = new Line2D.Double(ORIGIN.getX(),ORIGIN.getY(),drawPos.getX(), drawPos.getY());
        g.setColor(col);
        g.draw(lin);
    }
    
    public void drawVectorAsPoint(Vector2d vec, Graphics2D g, Color col) {
        g.setColor(col);
        vec = vec.scale(SCALE).add(ORIGIN);
        g.drawString("X", (int)vec.getX(), (int)vec.getY());
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        drawPathSeg(segment,g2,Color.BLACK);
        drawVectorAsPoint(point1, g2, Color.BLUE);
        drawVectorAsPoint(closest1, g2, Color.BLUE);
        drawVectorAsPoint(point2, g2, Color.GREEN);
        drawVectorAsPoint(closest2, g2, Color.GREEN);
        drawVectorAsPoint(point3, g2, Color.RED);
        drawVectorAsPoint(closest3, g2, Color.RED);
        drawVectorAsPoint(point4, g2, Color.MAGENTA);
        drawVectorAsPoint(closest4, g2, Color.MAGENTA);
    }

    public static void main(String[] args) {
        TestPathSegment test = new TestPathSegment();
        test.setVisible(true);
    }
}
