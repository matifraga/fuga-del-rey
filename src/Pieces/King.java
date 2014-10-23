package Pieces;

public class King extends Piece {
	
	protected King(){
		this.name="King";
		this.owner=1;
	}
	
	@Override
	public boolean canGetKilled(Piece attacker, Piece oppositePartner, Piece sideAttacker1, Piece sideAttacker2) {
		if(attacker.owner==owner)
			return false;
		//TODO: INFORME
		// throne    101
		// empty      11
		// enemy      10
		// guard king 01
		// castle     00
		int ans=0;
		Piece[] pieces={oppositePartner,sideAttacker1,sideAttacker2};
		for (int i = 0; i < pieces.length; i++) {
			if (pieces[i]!=null) {
				if(pieces[i].owner==3)
					return false;
				ans+=1-(pieces[i].owner&owner);
			}
		}
		//int ans=(attacker2!=null || attacker2.owner==3)?-10:(1-(attacker2.owner&owner)) +
		//		  (attacker3!=null || attacker3.owner==3)?-10:(1-(attacker3.owner&owner)) +
		//		  (attacker4!=null || attacker4.owner==3)?-10:(1-(attacker4.owner&owner));
		
		if(ans>=2)
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
