package pieces;

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
	public boolean canBeStepBy(Piece piece) {
		return true;
	}

	@Override
	public boolean canBeJumpBy(Piece piece) {
		return true;
	}

}
