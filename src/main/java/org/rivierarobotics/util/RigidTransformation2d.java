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

package org.rivierarobotics.util;

/**
 *represents a 3 DOF pose in 2d space
 */
public class RigidTransformation2d {
    
    private Vector2d translation;
    private double rotation;//in radians
    
    /**
     * 
     * @param trans - translational component
     * @param rot - rotational component
     */
    public RigidTransformation2d(Vector2d trans, double rot) {
        translation = trans;
        rotation = rot;
    }
    
    public RigidTransformation2d(Vector2d point1, Vector2d point2) {
        translation = point1;
        rotation = point2.subtract(point1).getAngle();
    }
    
    /**
     * 
     * @return translational component of transformation
     */
    public Vector2d getTranslation() {
        return translation;
    }
    
    /**
     * 
     * @return rotational component
     */
    public double getRotation() {
        return rotation;
    }
    
    /**
     * 
     * @param other - other rt2d
     * @return composition of transformations
     */
    public RigidTransformation2d transformBy(RigidTransformation2d other) {
        return new RigidTransformation2d(translation.add(other.getTranslation()), rotation + other.getRotation());
    }
    
    /**
     * 
     * @param other - a translation
     * @return rt2d shifted by translation
     */
    public RigidTransformation2d transformBy(Vector2d other) {
        return new RigidTransformation2d(translation.add(other), rotation);
    }
    
    /**
     * 
     * @param other - a rotation
     * @return rt2d rotated (just the rotational component, not the vector)
     */
    public RigidTransformation2d transformBy(double other) {
        return new RigidTransformation2d(translation, rotation + other);
    }
    
    /**
     * 
     * @param scale - scaling factor
     * @return scaled transformation
     */
    public RigidTransformation2d scale(double scale) {
        return new RigidTransformation2d(translation.scale(scale),rotation*scale);
    }
    
    /**
     * 
     * @return transformation that brings this rt2d to 0.
     */
    public RigidTransformation2d inverse(){
        return scale(-1);
    } 
    
    public double getNormalToRotation() {
        return MathUtil.wrapAngleRad(rotation + Math.PI/2);
    }
    
    /**
     * Treat RT2Ds as lines in point-slope-form, where translation is point & angle is slope
     * @return the intersection of the two lines
     */
    public Vector2d getIntersection(RigidTransformation2d other) {
        double x1 = getTranslation().getX();
        double y1 = getTranslation().getY();
        double m1 = Math.tan(getRotation());
        double x2 = other.getTranslation().getX();
        double y2 = other.getTranslation().getY();
        double m2 = Math.tan(other.getRotation());
        
        double xInter = (y2 - y1 + x1*m1 - x2*m2)/(m1 - m2);
        double yInter = y1 + m1*(xInter - x1);
        return new Vector2d(xInter, yInter);
    }
}
