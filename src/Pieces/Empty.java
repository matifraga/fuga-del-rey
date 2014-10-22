package Pieces;

public class Empty extends Piece {

	protected Empty(){
		this.name="Empty";
		this.owner=3;
	}
	@Override
	public boolean canGetKilled(Piece attacker, Piece oppositePartner, Piece sideAttacker1, Piece sideAttacker2) {		
		return false;
	}

	@Override
	public boolean canStepBy(Piece piece) {
		return true;
	}

	@Override
	public boolean canJumpBy(Piece piece) {
		return true;
	}

	@Override
	public int value() {
		return 0;
	}
	

}
