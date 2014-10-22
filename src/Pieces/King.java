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
		if (oppositePartner!=null) {
			if(oppositePartner.owner==3)
				return false;
			ans+=1-(oppositePartner.owner&owner);
		}
		if (sideAttacker1!=null){
			if(sideAttacker1.owner==3)
				return false;
			ans+=1-(sideAttacker1.owner&owner);
		}
		if (sideAttacker2!=null){
			if(sideAttacker2.owner==3)
				return false;
			ans+=1-(sideAttacker2.owner&owner);
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
