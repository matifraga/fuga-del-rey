package Pieces;

public abstract class Piece {

	protected String name;
	protected int owner;
	
	public abstract boolean getKilled(Piece attacker1, Piece attacker2, Piece attacker3, Piece attacker4);
	public abstract boolean canStepBy(Piece piece);
	public abstract boolean canJumpBy(Piece piece);
	public abstract int value();
	
	public String getName(){
		return name;
	}
	
	public int getOwner(){
		return owner;
	}
}
