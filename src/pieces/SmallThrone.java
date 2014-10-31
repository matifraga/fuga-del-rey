package pieces;

public class SmallThrone extends Throne {
	
	protected SmallThrone(){
		super();
	}
	
	@Override
	public boolean canBeJumpBy(Piece piece) {
		return true;
	}

	
}
