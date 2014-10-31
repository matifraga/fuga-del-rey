package pieces;

public class BigThrone extends Throne{

	protected BigThrone(){
		super();
	}
	
	@Override
	public boolean canBeJumpBy(Piece piece) {
		if(piece==PieceManager.getGuardInstance())
			return false;
		return true;
	}


}
