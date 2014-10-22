package clases;

public class Move {

	private int x1,x2,y1,y2,value;
	
	public Move(){};
	
	public Move(int value){
		this.value=value;
	}
	
	public Move(int x1, int y1, int x2, int y2){
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
	}
	
	public Move(int x1, int y1, int x2, int y2,int value){
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		this.value=value;
	}

	public int getX1() {
		return x1;
	}

	public int getX2() {
		return x2;
	}

	public int getY1() {
		return y1;
	}

	public int getY2() {
		return y2;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString(){
		return "("+x1+","+y1+")("+x2+","+y2+") "+value;
	}
}
