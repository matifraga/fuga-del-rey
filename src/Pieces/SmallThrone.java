package Pieces;

public class SmallThrone extends Throne {

	@Override
	public boolean canJumpBy(Piece piece) {
		return true;
	}

	@Override
	public Piece copy() {
		return new SmallThrone();
	}

}
