public class NBody {
    private static String suffix = "./images/";
    private static int scale = 100;

    public static double readRadius(String path) {
        In in = new In(path);
        in.readInt();
        return in.readDouble();
    }

    public static Body[] readBodies(String path) {
        In in = new In(path);
        int number = in.readInt();
        in.readDouble();

        Body[] bodys = new Body[number];
        
        for (int i = 0; i < number; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();

            bodys[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return bodys;
    }

    private static void draw(Body[] bodies) {
        StdDraw.clear();
        
        StdDraw.picture(0, 0, suffix + "starfield.jpg");

        for (Body b: bodies) {
            b.draw();
        }

        StdDraw.show();
    }

    private static void printResult(double radius, Body[] bodies){
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (Body b: bodies) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", 
            b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
        }
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);

        int waitTimeMilliseconds = 10;

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-1 * radius, radius);

        for (double t = 0; t <= T; t += dt) {
            // 1 Create an xForces array and yForces array.
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];
            // 2 Calculate the net x and y forces for each Body, storing these in the xForces and yForces arrays respectively.
            for (int i = 0; i < bodies.length; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            // 3 call update on each of the Bodys.
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].update(dt, xForces[i], yForces[i]);
            }
            // 4 Draw the background image and all of the Bodys.
            draw(bodies);
            // 5 Pause the animation for 10 milliseconds.
            StdDraw.pause(waitTimeMilliseconds);
        }

        printResult(radius, bodies);
    }
}