package Lindenmayer;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyTurtle implements Tortue {
	public HashMap<String,Double> bondingBox; 
    public State currentState;
    public Stack<State> statesHistory;
    public double step;
    public double angleUnitaire;
    
    
	public MyTurtle(JSONObject  jSonObj){
		currentState = new State();
		statesHistory = new Stack<State>();
		bondingBox = new HashMap<String, Double>();
		startTurtle(jSonObj);
		}
	
	// BoundingBox
	public void updateBoundingBox(double X, double Y) {
		if(X < bondingBox.get("Xmin")) bondingBox.put("Xmin", X);
		if(X > bondingBox.get("Xmax")) bondingBox.put("Xmax", X);
		if(Y < bondingBox.get("Ymin")) bondingBox.put("Ymin", Y);
		if(Y > bondingBox.get("Ymax")) bondingBox.put("Ymax", Y);
	}
	
	public void setBoundingBoxStart() {
		
		double X = currentState.getPosX();
		double Y = currentState.getPosY();
		
		this.bondingBox.put("Xmin", X);
		this.bondingBox.put("Xmax", X);
		this.bondingBox.put("Ymin", Y);
		this.bondingBox.put("Ymax", Y);
	}
	
	public Double[] boundingBoxToArray() {
		Double[] boundingBoxArray = new Double[4];
		boundingBoxArray[0]= this.bondingBox.get("Xmin");
		boundingBoxArray[1]= this.bondingBox.get("Xmax");
		boundingBoxArray[2]= this.bondingBox.get("Ymin");
		boundingBoxArray[3]= this.bondingBox.get("Ymax");
		return boundingBoxArray;
	}
	
	// position and movement.
	public void setPosition(double posX ,double posY, double angle) {
		currentState.setState(posX, posY, angle);
	}
	
    @Override
    public void draw() {
    	 
    	double angle = currentState.getAngle();
    	double nouveauX = currentState.getPosX() + step*Math.cos(0.0174533*angle);
    	double nouveauY = currentState.getPosY() + step*Math.sin(0.0174533*angle);
        currentState.setState(nouveauX,nouveauY,angle);
        
        System.out.println(currentState.getPosX()+ " "+ currentState.getPosY() + " lineto");
        EPS_creator.writeEPSFile(currentState.getPosX()+ " "+ currentState.getPosY() + " lineto"); 
    	updateBoundingBox(nouveauX,nouveauY);
        }

    @Override
    public void move() {
    	double angle = currentState.getAngle();
    	double nouveauX = currentState.getPosX() + step*Math.cos(0.0174533*angle);
        double nouveauY = currentState.getPosY() + step*Math.sin(0.0174533*angle);
        currentState.setState(nouveauX,nouveauY,angle);
        System.out.println(currentState.getPosX()+ " "+ currentState.getPosY() + " moveto");
        EPS_creator.writeEPSFile(currentState.getPosX()+ " "+ currentState.getPosY() + " moveto"); 
        updateBoundingBox(nouveauX,nouveauY);
         
    }

    @Override
    public void turnR() {
        double nouvelAngle = currentState.getAngle() + angleUnitaire;
        if(nouvelAngle < 0) nouvelAngle = 360 + nouvelAngle;
        if(nouvelAngle > 360) nouvelAngle = nouvelAngle%360;
        currentState.setAngle(nouvelAngle);
    }

    @Override
    public void turnL() {
    	double nouvelAngle = currentState.getAngle() - angleUnitaire;
    	if(nouvelAngle < 0) nouvelAngle = 360 + nouvelAngle;
    	if(nouvelAngle > 360) nouvelAngle = nouvelAngle%360;
        currentState.setAngle(nouvelAngle);
    }

    public void push() {
    	
    	System.out.println("currentpoint");
    	EPS_creator.writeEPSFile("currentpoint");
        statesHistory.push(currentState.cloneState(currentState.posX, currentState.posY, currentState.angle));
        System.out.println("stroke");
    	EPS_creator.writeEPSFile("stroke");
    	System.out.println(statesHistory.peek().posX + " " + statesHistory.peek().posY + " newpath moveto");
    	EPS_creator.writeEPSFile(statesHistory.peek().posX + " " + statesHistory.peek().posY + " newpath moveto");

    }

    public void pop() {
        //statesHistory.push(currentState.cloneState(currentState.posX, currentState.posY, currentState.angle));
    	System.out.println("stroke");
    	EPS_creator.writeEPSFile("stroke");
    	System.out.println(statesHistory.peek().posX + " " + statesHistory.peek().posY + " newpath moveto");
    	EPS_creator.writeEPSFile(statesHistory.peek().posX + " " + statesHistory.peek().posY +" newpath moveto");
    	statesHistory.pop();
    }

    public void stay() {
    	// Ne sert �......RIEN
    }
 
    public Point2D getPosition() {
        return new Point2D.Double(currentState.getPosX() , currentState.getPosY());
    }

    public double getAngle() {
        return currentState.getAngle();
    }

    public void init(Point2D position, double angle_deg) {
    	currentState.setState(position.getX(), position.getY(), angle_deg);
    	statesHistory.push(currentState.cloneState(currentState.posX, currentState.posY, currentState.angle));
    	setBoundingBoxStart();
    }
    
    public void setUnits(double pas, double delta) {
    	step = pas;
    	angleUnitaire = delta;
    }
    
    /*
     * Seletione la derniere ligne du JSONOBject  <"start"> avec les info d'initialization de la Tortue.  
     */
    public void startTurtle(JSONObject jSonObj) {
    	JSONObject system_params = jSonObj.getJSONObject("parameters");
    	JSONArray startJSON = system_params.getJSONArray("start"); 
    	double start[] = new double[3];
    	for(int n=0; n<3; n++){
    		start[n] = startJSON.getDouble(n);
    	}
    	init(new Point2D.Double(start[0],start[1]),start[2]);	
    	double unit_step = system_params.getDouble("step");
    	double unit_angle = system_params.getDouble("angle");
    	setUnits(unit_step, unit_angle);
    }
    
    
	
   // State Class
    static class State {
    	
    	public double posX;
    	public double posY;
    	public double angle;
        
        public State(){
    	} 
        
        public void setState(double posX ,double posY, double angle) {
        	this.posX = posX;
        	this.posY = posY;
        	this.angle = angle;
        }
        
        public State cloneState(double posX ,double posY, double angl) {
          State clon = new State();
          clon.setState(posX, posY, angl);
        return clon;
    	}
        
        public double getPosX() {
			return posX;
		}

		public void setPosX(double posX) {
			this.posX = posX;
		}

		public double getPosY() {
			return posY;
		}

		public void setPosY(double posY) {
			this.posY = posY;
		}

		public double getAngle() {
			return angle;
		}

		public void setAngle(double angle) {
			this.angle = angle;
		}
            
    }
	
}


