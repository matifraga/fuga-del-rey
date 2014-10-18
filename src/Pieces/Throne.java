package Pieces;

public abstract class Throne extends Piece{

	public Throne(){
		this.name="Throne";
		this.owner=5;
	}
	@Override
	public boolean getKilled(Piece attacker1, Piece attacker2, Piece attacker3,
			Piece attacker4) {
		return false;
	}

	@Override
	public boolean canStepBy(Piece piece) {
		System.out.println(piece.getName());
		if(piece.getName().equals("King"))
			return true;
		return false;
	}

	@Override
	public abstract boolean canJumpBy(Piece piece);

	@Override
	public int value() {
		return 0;
	}

}
