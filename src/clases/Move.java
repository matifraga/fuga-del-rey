package clases;

public class Move {

	private int xOrigin,xDest,yOrigin,yDest,value;
	
	public Move(){};
	
	public Move(int value){
		this.value=value;
	}
	
	public Move(int x1, int y1, int x2, int y2){
		this.xOrigin=x1;
		this.yOrigin=y1;
		this.xDest=x2;
		this.yDest=y2;
	}
	
	public Move(int x1, int y1, int x2, int y2,int value){
		this.xOrigin=x1;
		this.yOrigin=y1;
		this.xDest=x2;
		this.yDest=y2;
		this.value=value;
	}

	public int getXOrigin() {
		return xOrigin;
	}

	public int getXDest() {
		return xDest;
	}

	public int getYOrigin() {
		return yOrigin;
	}

	public int getYDest() {
		return yDest;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString(){
		return "("+xOrigin+","+yOrigin+")("+xDest+","+yDest+") "+value;
	}
}
