package Lindenmayer;

import java.awt.geom.Point2D;

public interface Tortue{ 
		public void draw();
	    public void move();
	    public void turnR();
	    public void turnL();
	    public void push();
	    public void pop();
	    public void stay();
	    
	    public void init(Point2D position, double angle_deg);
	    public Point2D getPosition();
	    public double getAngle();
	    public void setUnits(double step, double delta);
	}

