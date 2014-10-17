package clases;

import Pieces.Piece;

public class Board {

	private Piece[][] board;
	private int size;
	
	public Board(int n){
		if(n<7 || n>19 || n%2==0)
			throw new IllegalArgumentException();
	}
	
	public int value(){return 0;};
}
