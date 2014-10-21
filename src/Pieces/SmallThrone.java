package Pieces;

public class SmallThrone extends Throne {
	
	protected SmallThrone(){
		super();
	}
	
	@Override
	public boolean canJumpBy(Piece piece) {
		return true;
	}

	
}
