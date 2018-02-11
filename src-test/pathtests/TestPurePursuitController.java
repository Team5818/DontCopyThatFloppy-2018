package pathtests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.rivierarobotics.mathUtil.RigidTransformation2d;
import org.rivierarobotics.mathUtil.Vector2d;
import org.rivierarobotics.pathfollowing.Path;
import org.rivierarobotics.pathfollowing.PathSegment;
import org.rivierarobotics.pathfollowing.PurePursuitController;
import org.rivierarobotics.pathfollowing.PurePursuitController.Arc;

public class TestPurePursuitController extends JFrame{

    private static final long serialVersionUID = 1L;
    public ArrayList<PathSegment> segs = new ArrayList<PathSegment>();
    public PurePursuitController ppc;
    public Path path;
    public RigidTransformation2d pose1 = new RigidTransformation2d(new Vector2d(4, 2), Math.PI/2);
    public Vector2d closest1;
    public Vector2d look1;
    public Vector2d midpoint;
    public Arc arc1;
    public RigidTransformation2d perpBi;
    public RigidTransformation2d r2c;
    public Vector2d center;
    public static final Vector2d ORIGIN = new Vector2d(225, 225);
    public static final Vector2d RT2D_OFFSET = new Vector2d(150, 0);
    public static final double SCALE = 50;

    public TestPurePursuitController() {
        segs.add(new PathSegment(new Vector2d(0, 0), new Vector2d(0, 5)));
        path = new Path(segs);
        ppc = new PurePursuitController(path, 1);
        closest1 = path.getClosestPointOnPath(pose1.getTranslation());
        look1 = path.advancePoint(pose1.getTranslation(), 1);
        Vector2d diff = look1.subtract(pose1.getTranslation());
        midpoint = pose1.getTranslation().add(diff.scale(.5));
        double normalAngle = diff.getNormal().getAngle();
        double normalFromPose = pose1.getNormalToRotation();
        perpBi = new RigidTransformation2d(midpoint, normalAngle);
        r2c = new RigidTransformation2d(pose1.getTranslation(), normalFromPose);
        center = perpBi.getIntersection(r2c);
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void drawArc(Arc arc, Graphics2D g2, Color col) {
            int radius = (int)(arc.radius*SCALE);
            Vector2d center = arc.center.scale(SCALE).add(ORIGIN);
            g2.setColor(col);
            g2.drawOval((int)(center.getX() - radius), (int)(center.getY() - radius), radius*2, radius*2); 
    }   

    public void drawRigidTransform(RigidTransformation2d rt2d, Graphics2D g2, Color col) {
        drawVectorAsPoint(rt2d.getTranslation(),g2,col,"o");
        Vector2d beg = rt2d.getTranslation().scale(SCALE).add(ORIGIN).subtract(RT2D_OFFSET.rotate(rt2d.getRotation()));
        Vector2d end = rt2d.getTranslation().scale(SCALE).add(ORIGIN).add(RT2D_OFFSET.rotate(rt2d.getRotation()));
        Line2D lin = new Line2D.Double(beg.getX(), beg.getY(), end.getX(), end.getY());
        g2.setColor(col);
        g2.draw(lin);
    }
    
    public void drawPath(Path p, Graphics2D g2, Color col) {
        for (PathSegment seg : path.getSegs()) {
            drawPathSeg(seg, g2, col);
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
        Line2D lin = new Line2D.Double(ORIGIN.getX(), ORIGIN.getY(), drawPos.getX(), drawPos.getY());
        g.setColor(col);
        g.draw(lin);
    }

    public void drawVectorAsPoint(Vector2d vec, Graphics2D g, Color col) {
        g.setColor(col);
        vec = vec.scale(SCALE).add(ORIGIN);
        g.drawString("X", (int) vec.getX(), (int) vec.getY());
    }

    public void drawVectorAsPoint(Vector2d vec, Graphics2D g, Color col, String s) {
        g.setColor(col);
        vec = vec.scale(SCALE).add(ORIGIN);
        g.drawString(s, (int) vec.getX(), (int) vec.getY());
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        drawPath(path, g2, Color.BLACK);
        drawRigidTransform(pose1, g2, Color.BLUE);
        drawVectorAsPoint(closest1, g2, Color.BLUE);
        drawVectorAsPoint(look1, g2, Color.BLUE, "O");
        drawVectorAsPoint(midpoint,g2,Color.BLUE, "M");
        drawRigidTransform(perpBi, g2, Color.BLUE);
        drawRigidTransform(r2c, g2, Color.BLUE);
        drawVectorAsPoint(center, g2, Color.BLUE,"C");
    }

    public static void main(String[] args) {
        TestPurePursuitController test = new TestPurePursuitController();
        test.setVisible(true);

    }
}
