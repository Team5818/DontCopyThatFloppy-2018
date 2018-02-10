package pathtests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.rivierarobotics.mathUtil.Vector2d;
import org.rivierarobotics.pathfollowing.Path;
import org.rivierarobotics.pathfollowing.PathSegment;

public class TestPath extends JFrame{
    private static final long serialVersionUID = 1L;
    public ArrayList<PathSegment> segs = new ArrayList<PathSegment>();
    public Path path;
    public Vector2d point1 = new Vector2d(2,2);
    public Vector2d closest1;
    public Vector2d point2 = new Vector2d(6,3);
    public Vector2d closest2;
    public Vector2d point3 = new Vector2d(5,8);
    public Vector2d closest3;
    public Vector2d point4 = new Vector2d(-3, -3);
    public Vector2d closest4;
    public static final Vector2d ORIGIN = new Vector2d(225,225);
    public static final double SCALE = 50;

    public TestPath(){
        segs.add(new PathSegment(new Vector2d(0,2), new Vector2d(5,5)));
        segs.add(new PathSegment(new Vector2d(5,5), new Vector2d(5,2)));
        segs.add(new PathSegment(new Vector2d(5,2), new Vector2d(7,7)));
        segs.add(new PathSegment(new Vector2d(7,7), new Vector2d(3,6)));
        segs.add(new PathSegment(new Vector2d(3,6), new Vector2d(3,9)));
        path = new Path(segs);
        closest1 = path.getClosestPointOnPath(point1);
        closest2 = path.getClosestPointOnPath(point2);
        closest3 = path.getClosestPointOnPath(point3);
        closest4 = path.getClosestPointOnPath(point4);
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void drawPath(Path p, Graphics2D g2, Color col) {
        for(PathSegment seg :path.getSegs()) {
            drawPathSeg(seg,g2,col);
        }
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
        drawPath(path,g2,Color.BLACK);
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
        TestPath test = new TestPath();
        test.setVisible(true);
    }
}
