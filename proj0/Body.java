import java.lang.Math;

public class Body {
    private static final double G = 6.67e-11; // the gravitational constant

    public double xxPos;  // Its current x position
    public double yyPos;  // Its current y position
    public double xxVel;  // Its current velocity in the x direction
    public double yyVel;  // Its current velocity in the y direction
    public double mass;   // Its mass
    public String imgFileName;  // The name of the file that corresponds to the image that depicts the body

    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Body(Body b) {
        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.imgFileName = b.imgFileName;
    }
    
    /**
     * calculates the distance between two Bodys
     * 
     * @param b   a single Body
     * @return    a double equal to the distance between the supplied body and the body that is doing the calculation
     */
    public double calcDistance(Body b) {
        double dx = this.xxPos - b.xxPos;
        double dy = this.yyPos - b.yyPos;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    /**
     * calculates the force exerted on this body by the given body
     * 
     * @param b   a single Body
     * @return    the total force
     */
    public double calcForceExertedBy(Body b) {
        return this.mass * b.mass * G / Math.pow(this.calcDistance(b), 2);
    }

    /**
     * calculates the force exerted by the given body in the X direction
     * 
     * @param b   a single Body
     * @return    the force in the X direction
     */
    public double calcForceExertedByX(Body b) {
        double dx = b.xxPos - this.xxPos;
        return this.calcForceExertedBy(b) / this.calcDistance(b) * dx;
    }

    /**
     * calculates the force exerted by the given body in the Y direction
     * 
     * @param b   a single Body
     * @return    the force in the Y direction
     */
    public double calcForceExertedByY(Body b) {
        double dy = b.yyPos - this.yyPos;
        return this.calcForceExertedBy(b) / this.calcDistance(b) * dy;
    }

    /**
     * calculates the net X force exerted by all bodies in that array upon the current Body
     * 
     * @param bodies  an array of Bodies
     * @return       the net X force
     */
    public double calcNetForceExertedByX(Body[] bodies) {
        double netForceX = 0.0;
        for (Body b: bodies) {
            if (this.equals(b)) {
                continue;
            }

            netForceX = netForceX + this.calcForceExertedByX(b);
        }
        return netForceX;
    }

   /**
     * calculates the net Y force exerted by all bodies in that array upon the current Body
     * 
     * @param bodies  an array of Bodies
     * @return       the net Y force
     */
    public double calcNetForceExertedByY(Body[] bodies) {
        double netForceY = 0.0;
        for (Body b: bodies) {
            if (this.equals(b)) {
                continue;
            }

            netForceY = netForceY + this.calcForceExertedByY(b);
        }
        return netForceY;
    }

    /**
     * update the body’s position and velocity instance variables in a small period of time
     * 
     * @param dt  a small period of time
     * @param fx  an x-force
     * @param fy  a y-force
     */
    public void update(double dt, double fx, double fy) {
        // 1 Calculate the acceleration using the provided x- and y-forces.
        double ax = fx / this.mass;
        double ay = fy / this.mass;
        // 2 Calculate the new velocity by using the acceleration and current velocity.
        this.xxVel = this.xxVel + dt * ax;
        this.yyVel = this.yyVel + dt * ay;
        // 3 Calculate the new position by using the velocity computed in step 2 and the current position.
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "./images/" + this.imgFileName);
    }
}
