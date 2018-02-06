package org.rivierarobotics.mathUtil;

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
}
