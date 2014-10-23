package Pieces;

public class BigThrone extends Throne{

	protected BigThrone(){
		super();
	}
	
	@Override
	public boolean canJumpBy(Piece piece) {
		if(piece==PieceManager.getGuardInstance())
			return false;
		return true;
	}


}
