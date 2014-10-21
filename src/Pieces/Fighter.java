package Pieces;

public class Fighter extends Piece{

	
	
	protected Fighter(int owner){
		this.owner=owner;
		this.name=owner==2?"Enemy":"Guard";
	}
	
	@Override
	public boolean getKilled(Piece attacker1, Piece attacker2, Piece attacker3,
			Piece attacker4) {
		if(attacker2!=null && attacker1.owner!=this.owner && (attacker2.owner&owner)==0)
			return true;		
		return false;
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
