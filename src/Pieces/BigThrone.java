package Pieces;

public class BigThrone extends Throne{

	@Override
	public boolean canJumpBy(Piece piece) {
		if(piece.getName()=="Guard")
			return false;
		return true;
	}

}
