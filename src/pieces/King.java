package pieces;

public class King extends Piece {
	
	protected King(){
		this.name="King";
		this.owner=1;
	}
	
	@Override
	public boolean canGetKilled(Piece attacker, Piece oppositePartner, Piece sideAttacker1, Piece sideAttacker2) {
		if(attacker.owner==owner)
			return false;
		int ans=0;
		Piece[] pieces={oppositePartner,sideAttacker1,sideAttacker2};
		for (int i = 0; i < pieces.length; i++) {
			if (pieces[i]!=null) {
				if(pieces[i].owner==3)
					return false;
				ans+=1-(pieces[i].owner&owner);
			}
		}
		if(ans>=2)
			return true;
		return false;
	}

	@Override
	public boolean canBeStepBy(Piece piece) {
		return false;
	}

	@Override
	public boolean canBeJumpBy(Piece piece) {
		return false;
	}

}
