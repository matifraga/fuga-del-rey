package pieces;

public abstract class Throne extends Piece{

	protected Throne(){
		this.name="Throne";
		this.owner=5;
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
	public abstract boolean canBeJumpBy(Piece piece);

}
