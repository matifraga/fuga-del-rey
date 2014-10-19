package clases;

import Pieces.BigThrone;
import Pieces.Empty;
import Pieces.Fighter;
import Pieces.King;
import Pieces.Piece;
import Pieces.SmallThrone;
import Pieces.Throne;
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
		if(i<0 || i>=size || j<0 || j>=size)
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
			board[i][j]=getThrone();
		
	}

	
	
	public int getSize() {
		return size;
	}

	public int value(){return 0;}

	public Throne getThrone(){
		return (size<12?new SmallThrone():new BigThrone());
	}
	
	public void move(int x1, int y1, int x2, int y2) {
		board[x2][y2]=board[x1][y1];
		removePiece(x1, y1);
	}

	public void removePiece(int x1, int y1) {
		board[x1][y1]=(x1==size/2 && y1==size/2)?getThrone():new Empty();
	};
	
	public Board copy(){
		Board board=new Board(this.size);
		for(int i=0; i<this.size; i++){
			for(int j=0; j<this.size; j++){
				board.board[i][j]=this.board[i][j].copy();
			}
		}
		return board;
	}
}
