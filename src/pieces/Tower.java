package pieces;

public class Tower extends Piece {

	protected Tower(){
		this.name="Tower";
		this.owner=0; //ALL ENEMY
	}
	@Override
	public boolean canGetKilled(Piece attacker, Piece oppositePartner, Piece sideAttacker1, Piece sideAttacker2) {
		return false;
	}

	@Override
	public boolean canBeStepBy(Piece piece) {
		if(piece==PieceManager.getKingInstance())
			return true;
		return false;
	}

	@Override
	public boolean canBeJumpBy(Piece piece) {
		return false;
	}

	@Override
	public int value() {
		return 0;
	}
	

}
