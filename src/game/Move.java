package game;

public class Move {

	protected int xOrigin,xDest,yOrigin,yDest,value;
	
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
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString(){
		return "("+xOrigin+","+yOrigin+")("+xDest+","+yDest+") "+value;
	}
	
	public int getValue(){
		return value;
	}
	
	public String moveString(){
		return "("+xOrigin+","+yOrigin+")("+xDest+","+yDest+")";
	}
}
