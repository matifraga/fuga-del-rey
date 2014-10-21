package Pieces;

public class King extends Piece {
	
	protected King(){
		this.name="King";
		this.owner=1;
	}
	
	@Override
	public boolean getKilled(Piece attacker1, Piece attacker2, Piece attacker3,
			Piece attacker4) {
		if(attacker1.owner==owner)
			return false;
		// throne    101
		// empty      11
		// enemy      10
		// guard king 01
		// castel     00
		int ans=0;
		if (attacker2!=null) {
			if(attacker2.owner==3)
				return false;
			ans+=1-(attacker2.owner&owner);
		}
		if (attacker3!=null){
			if(attacker3.owner==3)
				return false;
			ans+=1-(attacker3.owner&owner);
		}
		if (attacker4!=null){
			if(attacker4.owner==3)
				return false;
			ans+=1-(attacker4.owner&owner);
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

	@Override
	public Piece copy() {
		return new King();
	}
	

}
