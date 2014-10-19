package Pieces;

public class Tower extends Piece {

	public Tower(){
		this.name="Tower";
		this.owner=0; //ALL ENEMY
	}
	@Override
	public boolean getKilled(Piece attacker1, Piece attacker2, Piece attacker3, Piece attacker4) {
		return false;
	}

	@Override
	public boolean canStepBy(Piece piece) {
		if(piece.getName().equals("King"))
			return true;
		return false;
	}

	@Override
	public boolean canJumpBy(Piece piece) {
		return false;
	}

	@Override
	public int value() {
		return 0;
	}
	@Override
	public Piece copy() {
		return new Tower();
	}

}
