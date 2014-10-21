package Pieces;

public class BigThrone extends Throne{

	protected BigThrone(){
		super();
	}
	
	@Override
	public boolean canJumpBy(Piece piece) {
		if(piece.getName().equals("Guard"))
			return false;
		return true;
	}


}
