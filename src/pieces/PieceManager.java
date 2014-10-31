package pieces;

public class PieceManager {

	private static King kingInstance=new King();
	private static SmallThrone smallThroneInstance= new SmallThrone();
	private static BigThrone bigThroneInstance= new BigThrone();
	private static Empty emptyInstance = new Empty();
	private static Fighter guardInstance = new Fighter(1);
	private static Fighter enemyInstance = new Fighter(2);
	private static Tower towerInstance = new Tower(); 
	
	public static King getKingInstance(){
		return kingInstance;
	}
	
	public static SmallThrone getSmallThroneInstance(){
		return smallThroneInstance;
	}
	
	public static BigThrone getBigThroneInstance(){
		return bigThroneInstance;
	}
	
	public static Empty getEmptyInstance(){
		return emptyInstance;
	}

	public static Fighter getGuardInstance(){
		return guardInstance;
	}
	
	public static Fighter getEnemyInstance(){
		return enemyInstance;
	}
	
	public static Tower getTowerInstance(){
		return towerInstance;
	}
}
