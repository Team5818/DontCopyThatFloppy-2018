/*
 * This file is part of DontCopyThatFloppy-2018, licensed under the GNU General Public License (GPLv3).
 *
 * Copyright (c) Riviera Robotics <https://github.com/Team5818>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pathtests;

import org.rivierarobotics.pathfollowing.Path;
import org.rivierarobotics.pathfollowing.PathSegment;
import org.rivierarobotics.util.Vector2d;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class TestPath extends JFrame {

    private static final long serialVersionUID = 1L;
    public ArrayList<PathSegment> segs = new ArrayList<PathSegment>();
    public Path path;
    public Vector2d point1 = new Vector2d(0, 2);
    public Vector2d closest1;
    public Vector2d look1;
    public Vector2d point2 = new Vector2d(0, 5);
    public Vector2d closest2;
    public Vector2d look2;
    public Vector2d point3 = new Vector2d(5, 4);
    public Vector2d closest3;
    public Vector2d look3;
    public Vector2d point4 = new Vector2d(-3, -3);
    public Vector2d closest4;
    public Vector2d look4;
    public static final Vector2d ORIGIN = new Vector2d(225, 225);
    public static final double SCALE = 50;

    public TestPath() {
        segs.add(new PathSegment(new Vector2d(0, 0), new Vector2d(-.55, 2.65)));
        segs.add(new PathSegment(new Vector2d(-.55, 2.65), new Vector2d(3.88, 6)));
        segs.add(new PathSegment(new Vector2d(3.88, 6), new Vector2d(8, 5.121)));
        path = new Path(segs);
        closest1 = path.getClosestPointOnPath(point1);
        look1 = path.advancePoint(point1, 1);
        closest2 = path.getClosestPointOnPath(point2);
        look2 = path.advancePoint(point2, 1);
        closest3 = path.getClosestPointOnPath(point3);
        look3 = path.advancePoint(point3, 1);
        closest4 = path.getClosestPointOnPath(point4);
        look4 = path.advancePoint(point4, 1);
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        drawVectorAsPoint(point1, g2, Color.BLUE);
        drawVectorAsPoint(closest1, g2, Color.BLUE);
        drawVectorAsPoint(look1, g2, Color.BLUE, "O");
        drawVectorAsPoint(point2, g2, Color.GREEN);
        drawVectorAsPoint(closest2, g2, Color.GREEN);
        drawVectorAsPoint(look2, g2, Color.GREEN, "O");
        drawVectorAsPoint(point3, g2, Color.RED);
        drawVectorAsPoint(closest3, g2, Color.RED);
        drawVectorAsPoint(look3, g2, Color.RED, "O");
        drawVectorAsPoint(point4, g2, Color.MAGENTA);
        drawVectorAsPoint(closest4, g2, Color.MAGENTA);
        drawVectorAsPoint(look4, g2, Color.MAGENTA, "O");
    }

    public static void main(String[] args) {
        TestPath test = new TestPath();
        test.setVisible(true);

    }
}
