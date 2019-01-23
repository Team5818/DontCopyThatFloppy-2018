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
