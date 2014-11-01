package pieces;

public abstract class Piece {

	protected String name;
	protected int owner;
	
	public abstract boolean canGetKilled(Piece attacker, Piece oppositePartner, Piece sideAttacker1, Piece sideAttacker2);
	public abstract boolean canBeStepBy(Piece piece);
	public abstract boolean canBeJumpBy(Piece piece);
	
	public String getName(){
		return name;
	}
	
	public int getOwner(){
		return owner;
	}
}
