package Pieces;

public class BigThrone extends Throne{

	@Override
	public boolean canJumpBy(Piece piece) {
		if(piece.getName().equals("Guard"))
			return false;
		return true;
	}

	@Override
	public Piece copy() {
		return new BigThrone();
	}

}
