package clases;

import Pieces.BigThrone;
import Pieces.Empty;
import Pieces.Fighter;
import Pieces.King;
import Pieces.Piece;
import Pieces.SmallThrone;
import Pieces.Tower;

public class Board {

	private Piece[][] board;
	private int size;
	
	public Board(int n){
		if(n<7 || n>19 || n%2==0)
			throw new IllegalArgumentException();
		size=n;
		board= new Piece[n][n];
	}
	
	public Piece getPiece(int i,int j){
		if(i<0 || i>size || j<0 || j>size)
			return null;
		return board[i][j];
	}
	
	public void fillRow(String str, int n){
		int length=str.length();
		Piece piece;
		for(int i=0;i<length;i++){
			switch (str.charAt(i)) {
			case '0':
				piece= new Empty();
				break;
			case 'N':
				piece= new Fighter(2);
				break;
			case 'G':
				piece= new Fighter(1);
				break;
			case 'R':
				piece= new King();
				break;
			default:
				throw new IllegalArgumentException();
			}
			board[n][i]=piece;
		}
	}
	
	public void putTowersAndThrone(){
		putTower(0,0);
		putTower(0,size-1);
		putTower(size-1,0);
		putTower(size-1,size-1);
		putThrone(size/2,size/2);
		
	}
	
	private void putTower(int i, int j) {
		if(board[i][j].getName().equals("Empty"))
			board[i][j]=new Tower();		
	}
	
	private void putThrone(int i, int j) {
		if(board[i][j].getName().equals("Empty"))
			board[i][j]=(size<12?new SmallThrone():new BigThrone());
		
	}

	
	
	public int getSize() {
		return size;
	}

	public int value(){return 0;};
}
