package game;

import java.awt.Point;

import pieces.Piece;
import pieces.PieceManager;
import pieces.Throne;

public class Board {

	private Piece[][] board;
	private int size;
	private Point kingPosition;
	private int enemyCount=0, guardCount=0;
	
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
	
	/*Devuelve si se cargo un rey o no*/
	public boolean fillRow(String str, int n){
		int length=str.length();
		boolean isAKing=false;
		Piece piece;
		for(int i=0;i<length;i++){
			switch (str.charAt(i)) {
			case '0':
				piece= PieceManager.getEmptyInstance();
				break;
			case 'N':
				piece= PieceManager.getEnemyInstance();
				enemyCount++;
				break;
			case 'G':
				piece= PieceManager.getGuardInstance();
				guardCount++;
				break;
			case 'R':
				piece= PieceManager.getKingInstance();
				kingPosition=new Point(n, i);
				isAKing=true;
				guardCount++;
				break;
			default:
				throw new IllegalArgumentException("Hay un caracter desconocido");
			}
			board[n][i]=piece;
		}
		return isAKing;
	}
	
	public void putTowersAndThrone(){
		putTower(0,0);
		putTower(0,size-1);
		putTower(size-1,0);
		putTower(size-1,size-1);
		putThrone(size/2,size/2);
	}
	
	private void putTower(int row, int col) {
		if(board[row][col]==PieceManager.getEmptyInstance())
			board[row][col]=PieceManager.getTowerInstance();		
	}
	
	private void putThrone(int row, int col) {
		if(board[row][col]==PieceManager.getEmptyInstance())
			board[row][col]=getThrone();
	}

	
	public void enemyKilled(){
		enemyCount--;
	}
	
	public void guardKilled(){
		guardCount--;
	}
	
	public void setKingPosition(int row, int col){
		kingPosition.x=row;
		kingPosition.y=col;
	}
	
	public int getSize() {
		return size;
	}

	public int value1(){
		return 16*guardCount-9*enemyCount;
	}
	
	public int value2(){
		int answer=0;
		int kingRow=kingPosition.x, kingCol=kingPosition.y;		
		answer+=((kingRow-size/2)*(kingRow-size/2)+(kingCol-size/2)*(kingCol-size/2))*(20.0/size);
		answer+=16*guardCount;
		answer-=9*enemyCount;
		return answer;
	}
	
	public int value3(){ 
		int answer=0, enemysTopLeft=0, enemysTopRight=0, enemysBotLeft=0, enemysBotRight=0;
		int kingRow=kingPosition.x, kingCol=kingPosition.y;
		
		answer+=((kingRow-size/2)*(kingRow-size/2)+(kingCol-size/2)*(kingCol-size/2))*(20.0/size);
		
		for(int auxRow=0; auxRow<=kingRow; auxRow++){
			for(int auxCol=0; auxCol<=kingCol; auxCol++){
				if(getPiece(auxRow, auxCol).getOwner()==2)
					enemysTopLeft++;
			}
		}
		
		for(int auxRow=kingRow; auxRow<size; auxRow++){
			for(int auxCol=0; auxCol<=kingCol; auxCol++){
				if(getPiece(auxRow, auxCol).getOwner()==2)
					enemysBotLeft++;
			}
		}
		
		for(int auxRow=0; auxRow<=kingRow; auxRow++){
			for(int auxCol=kingCol; auxCol<size; auxCol++){
				if(getPiece(auxRow, auxCol).getOwner()==2)
					enemysTopRight++;
			}
		}
		
		for(int auxRow=kingRow; auxRow<size; auxRow++){
			for(int auxCol=kingCol; auxCol<size; auxCol++){
				if(getPiece(auxRow, auxCol).getOwner()==2)
					enemysBotRight++;
			}
		}
	answer+=16*guardCount-9*(enemyCount)-10*Math.min(Math.min(enemysBotRight, enemysBotLeft), Math.min(enemysTopRight, enemysTopLeft));
	return answer; 
	}

	public Throne getThrone(){
		return (size<12?PieceManager.getSmallThroneInstance():PieceManager.getBigThroneInstance());
	}
	
	public void move(int xOrigin, int yOrigin, int xDest, int yDest) {
		if(board[xOrigin][yOrigin]==PieceManager.getKingInstance()){
			setKingPosition(xDest, yDest);
		}
		board[xDest][yDest]=board[xOrigin][yOrigin];
		removePiece(xOrigin, yOrigin); 
	}

	public void removePiece(int row, int col) {
		board[row][col]=(row==size/2 && col==size/2)?getThrone():PieceManager.getEmptyInstance();
	};
	
	public Board copy(){
		Board board=new Board(this.size);
		for(int row=0; row<this.size; row++){
			for(int col=0; col<this.size; col++){
				board.board[row][col]=this.board[row][col];
			}
		}
		board.enemyCount=this.enemyCount;
		board.guardCount=this.guardCount;
		board.kingPosition=this.kingPosition;
		return board;
	}

	/* devuelve una mascara de bits 654321:
	 * 1: igual tablero al rotarlo 90 grados a la derecha
	 * 2: igual tablero al rotarlo 180 grados
	 * 3: simetrico respecto a x
	 * 4: simetrico respecto a y
	 * 5: simetrico respecto a la diagonal principal
	 * 6: simetrico respecto a la diagonal secundaria
	 */
	/*Queda para emprolijar codigo*/
	public int symmetrys() {
		int answer=0x3F; /*0111 1111*/
		Point point;	
		
		for(int row=0; row<size; row++)
			for(int col=0; col<size; col++){
				point=rotated90(row, col);
				if(board[row][col]!=board[point.x][point.y]){
					row=size; col=size;
					answer-=1;
				}
			}
		
		for(int row=0; row<=size/2; row++)
			for(int col=0; col<size; col++){
				point=rotated180(row, col);
				if(board[row][col]!=board[point.x][point.y]){
					row=size; col=size; 
					answer-=2;
				}
			}
		
		for(int row=0; row<size/2; row++)
			for(int col=0; col<size; col++){
				point=xSymmetric(row, col);
				if(board[row][col]!=board[point.x][point.y]){
					row=size; col=size;
					answer-=4;
				}
			}
		
		for(int row=0; row<size; row++)
			for(int col=0; col<size/2; col++){
				point=ySymmetric(row, col);
				if(board[row][col]!=board[point.x][point.y]){
					row=size; col=size;
					answer-=8;
				}
			}
		
		for(int row=0; row<size; row++)
			for(int col=row+1; col<size; col++){
				point=firstDiagSymmetric(row, col);
				if(board[row][col]!=board[point.x][point.y]){
					row=size; col=size;
					answer-=16;
				}
			}
		
		for(int row=0; row<size; row++)
			for(int col=0; col<size-row-1; col++){
				point=secondDiagSymmetric(row, col);
				if(board[row][col]!=board[point.x][point.y]){
					row=size; col=size; 
					answer-=32;
				}
			}
		return answer;
	}
	
	//Devuelve la posicion en el tablero simetrica a (i,j) respecto del eje central horizontal
	public Point xSymmetric(int i, int j){
		return new Point(size-1-i, j);
	}
	
	//Devuelve la posicion en el tablero simetrica a (i,j) respecto del eje central vertical
	public Point ySymmetric(int i, int j){
		return new Point(i, size-1-j);
	}
	
	//Devuelve la posicion en el tablero simetrica a (i,j) respecto a la diagonal principal
	public Point firstDiagSymmetric(int i, int j){
		return new Point(j,i);
	}
	
	//Devuelve la posicion en el tablero simetrica a (i,j) respecto a la diagonal secundaria
	public Point secondDiagSymmetric(int i, int j){
		return new Point(size-1-j,size-1-i);
	}
	
	//Devuelve la posicion en el tablero donde quedaria la casilla (i,j) al rotar el tablero 90 grados a la derecha
	public Point rotated90(int i, int j){
		return new Point(j,size-1-i);
	}
	
	//Devuelve la posicion en el tablero donde quedaria la casilla (i,j) al rotar el tablero 180 grados a la derecha
	public Point rotated180(int i, int j){
		return new Point(size-1-i,size-1-j);
	}
	
	//Devuelve la posicion en el tablero donde quedaria la casilla (i,j) al rotar el tablero 270 grados a la derecha
	public Point rotated270(int i, int j){
		return new Point(size-1-j,i);
	}
}
