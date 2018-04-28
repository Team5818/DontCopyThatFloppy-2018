package org.rivierarobotics.pathfollowing;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;

public class WiggleModifier {    
    public static Trajectory addWiggle(Trajectory path, WiggleConfig conf) {
        int numSegs = path.segments.length;
        double period = (double)numSegs/conf.numCycles;
        Trajectory modified = path.copy();
        for(int i = 0; i < numSegs; i++) {
            Segment s = modified.segments[i];
            s.heading += conf.amplitude*Math.sin(i*2*Math.PI/period)*Math.abs(s.velocity)/conf.maxVel;
        }
        return modified;
    }
    
    public static class WiggleConfig{
        public double numCycles;
        public double maxVel;
        public double amplitude;
        
        public WiggleConfig(int nc, double mv, double amp) {
            numCycles = nc;
            maxVel = mv;
            amplitude = amp;
        }
    }
    
}
