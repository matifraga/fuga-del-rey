package Pieces;

public class Fighter extends Piece{

	
	
	protected Fighter(int owner){
		this.owner=owner;
		this.name=owner==2?"Enemy":"Guard";
	}
	
	@Override
	public boolean canGetKilled(Piece attacker, Piece oppositePartner, Piece sideAttacker1, Piece sideAttacker2) {
		if(oppositePartner!=null && attacker.owner!=this.owner && (oppositePartner.owner&owner)==0)
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
