package clases;

import java.awt.Point;

import Pieces.Piece;
import Pieces.PieceManager;
import Pieces.Throne;

public class Board {

	private Piece[][] board;
	private int size;
	
	public Board(int n){
		if(n<7 || n>19 || n%2==0)
			throw new IllegalArgumentException();
		this.size=n;
		this.board= new Piece[n][n];
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
				piece= PieceManager.getEmptyInstance();
				break;
			case 'N':
				piece= PieceManager.getEnemyInstance();
				break;
			case 'G':
				piece= PieceManager.getGuardInstance();
				break;
			case 'R':
				piece= PieceManager.getKingInstance();
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
		if(board[i][j].getName().equals("Empty")) //TODO: porque el checkeo?
			board[i][j]=PieceManager.getTowerInstance();		
	}
	
	private void putThrone(int i, int j) {
		if(board[i][j]==PieceManager.getEmptyInstance()) //TODO: porque el checkeo?
			board[i][j]=getThrone();
	}

	
	
	public int getSize() {
		return size;
	}

	public int value(){
		int answer=0;
		Piece piece;
		for(int i=0; i<size;i++){
			for(int j=0; j<size; j++){
				if((piece=getPiece(i, j)).getOwner()==1){
					if(piece==PieceManager.getKingInstance()){
						answer+=((i-size/2)*(i-size/2)+(j-size/2)*(j-size/2))*(20.0/size); //creo que habria que disminuir este 
							  //numero, para que no se mande tan de una a bloquear al rey
					}						
					answer+=16;
				}
				if(getPiece(i, j).getOwner()==2){
					answer-=9;
				}
			}
		}
		return answer;
	}

	public Throne getThrone(){
		return (size<12?PieceManager.getSmallThroneInstance():PieceManager.getBigThroneInstance());
	}
	
	public void move(int x1, int y1, int x2, int y2) {
		board[x2][y2]=board[x1][y1]; //TODO: validar
		removePiece(x1, y1); 
	}

	public void removePiece(int x1, int y1) {
		board[x1][y1]=(x1==size/2 && y1==size/2)?getThrone():PieceManager.getEmptyInstance();
	};
	
	public Board copy(){
		Board board=new Board(this.size);
		for(int i=0; i<this.size; i++){
			for(int j=0; j<this.size; j++){
				board.board[i][j]=this.board[i][j];
			}
		}
		return board;
	}

	/* devuelve una mascara de bits 6543210:
	 * 0: igual tablero al rotarlo 90º
	 * 1: igual tablero al rotarlo 180º
	 * 2: igual tablero al rotarlo 270º
	 * 3: simetrico respecto a x
	 * 4: simetrico respecto a y
	 * 5: simetrico respecto a la diagonal principal
	 * 6: simetrico respecto a la diagonal secundaria
	 */
	public int symmetrys() {
		int answer=0x8F; // 1111111
			
		return 0;
	}
	
	public Point xSymmetric(int i, int j){
		return new Point(size-1-i, j);
	}
	
	public Point ySymmetric(int i, int j){
		return new Point(i, size-1-j);
	}
	
	public Point firstDiagSymmetric(int i, int j){
		return new Point(j,i);
	}
	
	public Point secondDiagSymmetric(int i, int j){
		return new Point(size-1-j,size-1-i);
	}
	
	public Point rotated90(int i, int j){
		return new Point(j,size-1-i);
	}
	
	public Point rotated180(int i, int j){
		return new Point(size-1-i,size-1-j);
	}
	
	public Point rotated270(int i, int j){
		return new Point(size-1-j,i);
	}
}
