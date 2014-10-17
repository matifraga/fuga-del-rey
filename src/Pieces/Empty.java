package Pieces;

public class Empty extends Piece {

	public Empty(){
		this.name="Empty";
		this.owner=3;
	}
	@Override
	public boolean getKilled(Piece attacker1, Piece attacker2, Piece attacker3,
			Piece attacker4) {		
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
