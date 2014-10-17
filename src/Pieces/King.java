package Pieces;

public class King extends Piece {

	@Override
	public boolean getKilled(Piece attacker1, Piece attacker2, Piece attacker3,
			Piece attacker4) {
		if(attacker1.owner==owner)
			return false;
		int ans=(attacker2==null?0:1-(attacker2.owner&owner))+
				(attacker3==null?0:1-(attacker3.owner&owner))+
				(attacker4==null?0:1-(attacker4.owner&owner));
			
		return ans>=2;
	}

	@Override
	public boolean canStepBy(Piece piece) {
		return false;
	}

	@Override
	public boolean canJumpBy(Piece piece) {
		return false;
	}

	@Override
	public int value() {
		return 0;
	}

}
