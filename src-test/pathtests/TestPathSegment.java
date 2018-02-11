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
    public static final double LOOKAHEAD = .5;
    public PathSegment segment = new PathSegment(new Vector2d(0,2), new Vector2d(5,5));
    public Vector2d point1 = new Vector2d(1,4);
    public Vector2d closest1 = segment.getClosestPoint(point1);
    public Vector2d look1 = segment.advancePoint(point1, LOOKAHEAD);
    public Vector2d point2 = new Vector2d(5,1);
    public Vector2d closest2 = segment.getClosestPoint(point2);
    public Vector2d look2 = segment.advancePoint(point2, LOOKAHEAD);
    public Vector2d point3 = new Vector2d(8,8);
    public Vector2d closest3 = segment.getClosestPoint(point3);
    public Vector2d look3 = segment.advancePoint(point3, LOOKAHEAD);
    public Vector2d point4 = new Vector2d(-3, -3);
    public Vector2d closest4 = segment.getClosestPoint(point4);
    public Vector2d look4 = segment.advancePoint(point4, LOOKAHEAD);
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

    public void drawVectorAsPoint(Vector2d vec, Graphics2D g, Color col, String s) {
        g.setColor(col);
        vec = vec.scale(SCALE).add(ORIGIN);
        g.drawString(s, (int)vec.getX(), (int)vec.getY());
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        drawPathSeg(segment,g2,Color.BLACK);
        drawVectorAsPoint(point1, g2, Color.BLUE);
        drawVectorAsPoint(closest1, g2, Color.BLUE);
        drawVectorAsPoint(look1, g2, Color.BLUE,"O");
        drawVectorAsPoint(point2, g2, Color.GREEN);
        drawVectorAsPoint(closest2, g2, Color.GREEN);
        drawVectorAsPoint(look2, g2, Color.GREEN,"O");
        drawVectorAsPoint(point3, g2, Color.RED);
        drawVectorAsPoint(closest3, g2, Color.RED);
        drawVectorAsPoint(look3, g2, Color.RED,"O");
        drawVectorAsPoint(point4, g2, Color.MAGENTA);
        drawVectorAsPoint(closest4, g2, Color.MAGENTA);
        drawVectorAsPoint(look4, g2, Color.MAGENTA,"O");
    }

    public static void main(String[] args) {
        TestPathSegment test = new TestPathSegment();
        test.setVisible(true);
    }
}
