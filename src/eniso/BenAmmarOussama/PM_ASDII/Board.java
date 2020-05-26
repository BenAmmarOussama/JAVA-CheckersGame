package eniso.BenAmmarOussama.PM_ASDII;


import java.util.Arrays;
import java.io.Serializable;
import java.util.ArrayList;
 

public class Board implements Serializable {
	private static final long serialVersionUID = 6529685098267757690L;
	
	Enum.PieceType[][] board;
	private int size;
	private int nbrNormalRedCheckers;
	private int nbrNormalBlackCheckers;
	private int nbrRedKingCheckers = 0;
	private int nbrBlackKingCheckers = 0;
	private int nbrRedKnightCheckers = 0;
	private int nbrBlackKnightCheckers = 0;
	
	public Board(int size) {
		
		board = new Enum.PieceType[size][size];
		this.size = size;
		if (size == 8) {
			this.nbrNormalBlackCheckers = 12;
			this.nbrNormalRedCheckers = 12;
		} else {
			this.nbrNormalBlackCheckers = 20;
			this.nbrNormalRedCheckers = 20;
		}
		setUpBoard();
	}
	
	// Copy Constructor
	public Board(Board anotherBoard) {
	
		Enum.PieceType[][] newBoard = new Enum.PieceType[anotherBoard.getSize()][anotherBoard.getSize()];
        for(int i = 0; i < anotherBoard.getSize(); i++)
        {
            for(int j = 0; j< anotherBoard.getSize(); j++)
            {
                newBoard[i][j] = anotherBoard.getPiece(i, j);
            }
        }
		this.board = newBoard;
		this.size = anotherBoard.getSize();
		this.nbrNormalBlackCheckers = anotherBoard.getNbrBlackCheckers();
		this.nbrNormalRedCheckers = anotherBoard.getNbrNormalRedCheckers();
		this.nbrBlackKingCheckers = anotherBoard.getNbrBlackKingCheckers();
		this.nbrRedKingCheckers = anotherBoard.getNbrRedKingCheckers();
		this.nbrBlackKnightCheckers = anotherBoard.getNbrBlackKnightCheckers();
		this.nbrRedKnightCheckers = anotherBoard.getNbrRedKnightCheckers();
		
	}
	
	// get piece using position
	public Enum.PieceType getPiece(Position p) {
		return getPiece(p.getRow(), p.getCol());
	}
	
	// get piece using row & col
	public Enum.PieceType getPiece(int row, int col) {
		return board[row][col];
	}
	
	/* Set up the Board with pieces in their starting positions */
	private void setUpBoard() {
        for (int row = 0; row < this.size; row++) {
           for (int col = 0; col < this.size; col++) {
                if ( row % 2 != col % 2 ) {
                    if (row < (this.size / 2)-1)
                        board[row][col] = Enum.PieceType.BLACK;
                    else if (row > (this.size / 2))
                        board[row][col] = Enum.PieceType.RED;
                    else
                        board[row][col] = Enum.PieceType.EMPTY;
                }
                else {
                    board[row][col] = Enum.PieceType.EMPTY;
                }
            }
        }
    }

	public Enum.PieceType[][] getBoard() {
		return board;
	}

	public int getSize() {
		return size;
	}

	public int getNbrNormalRedCheckers() {
		return nbrNormalRedCheckers;
	}

	public int getNbrNormalBlackCheckers() {
		return nbrNormalBlackCheckers;
	}

	public int getNbrRedKingCheckers() {
		return nbrRedKingCheckers;
	}

	public int getNbrBlackKingCheckers() {
		return nbrBlackKingCheckers;
	}

	public int getNbrRedKnightCheckers() {
		return nbrRedKnightCheckers;
	}

	public int getNbrBlackKnightCheckers() {
		return nbrBlackKnightCheckers;
	}
	
	public int getNbrRedCheckers() {
		return getNbrNormalRedCheckers() + getNbrRedKingCheckers() + getNbrRedKnightCheckers() ;
	}
	
	public void setNbrRedCheckers(int nbrRedCheckers) {
		this.nbrRedKingCheckers = nbrRedCheckers;
	}

	public void setNbrBlackCheckers(int nbrBlackCheckers) {
		this.nbrBlackKingCheckers = nbrBlackCheckers;
	}

	public void setNbrNormalRedCheckers(int nbrNormalRedCheckers) {
		this.nbrNormalRedCheckers = nbrNormalRedCheckers;
	}

	public void setNbrNormalBlackCheckers(int nbrNormalBlackCheckers) {
		this.nbrNormalBlackCheckers = nbrNormalBlackCheckers;
	}

	public int getNbrBlackCheckers() {
		return getNbrNormalBlackCheckers() + getNbrBlackKingCheckers() + getNbrBlackKnightCheckers();
	}
	
    /* Display the current state of the board */
	public void displayBoard() {
		char[] lineArray = new char[6 * this.size];
		Arrays.fill(lineArray, '-');
		String lineSeperator = new String(lineArray);
		System.out.print("   ");
		for (int i = 0; i< this.size; i++) {
			System.out.print("  "+i+"   ");
		}
		System.out.println("\n  "+lineSeperator);
		for (int i=0; i<this.board.length; i++ ) {
			System.out.print(i+" |  ");
			for (int j=0; j<this.board.length; j++) {
				if (this.board[i][j] == Enum.PieceType.EMPTY) {
					System.out.print(" "+"  |  ");
				} else {
					if ( this.board[i][j] == Enum.PieceType.BLACK ) System.out.print("B" +"  |  "); 	
					else if ( this.board[i][j] == Enum.PieceType.BLACK_KING  ) System.out.print("BK" +"  |  ");
					else if ( this.board[i][j] == Enum.PieceType.BLACK_KNIGHT  ) System.out.print("bK" +"  |  ");
					else if ( this.board[i][j] == Enum.PieceType.RED  ) System.out.print("R" +"  |  ");
					else if ( this.board[i][j] == Enum.PieceType.RED_KING  ) System.out.print("RK" +"  |  ");
					else if ( this.board[i][j] == Enum.PieceType.RED_KNIGHT ) System.out.print("rK" +"  |  ");
				}
			}
			System.out.print('\n');
			System.out.println("  "+lineSeperator);
		}
		System.out.println("Red :"+this.getNbrRedCheckers()+" ** Black :"+this.getNbrBlackCheckers());
		System.out.println("\n          *******************************\n          *******************************\n");
	}
	
	
	// find the position to jump over if the jump move is valid
    private Position getJumpedPosition(Move move) {

        Position position = new Position((move.getStart().getRow() + move.getEnd().getRow()) / 2,
                		        (move.getStart().getCol() + move.getEnd().getCol()) / 2);
        return position;
    }
    
    // get the piece to jump over if the jump move is valid
    private Enum.PieceType getJumpedPiece(Position jumpedPosition) {
    	return getPiece(jumpedPosition);
    }
    
    // Check whether the piece type matches the player side or not
    private boolean isOwnPiece(Enum.PlayerSide player, Position pos) {
    	
    	Enum.PieceType pieceType = getPiece(pos);
        if ( pieceType == Enum.PieceType.EMPTY ) {
        	return false;
        }
        if (player == Enum.PlayerSide.BLACK ) {
        	if ( pieceType == Enum.PieceType.BLACK || pieceType == Enum.PieceType.BLACK_KNIGHT || pieceType == Enum.PieceType.BLACK_KING ) {
        		return true;
        	} else {
        		return false;
        	}
        }
        else if ( player == Enum.PlayerSide.RED ) {
        	if ( pieceType == Enum.PieceType.RED || pieceType == Enum.PieceType.RED_KING || pieceType == Enum.PieceType.RED_KNIGHT ) {
        		return true;
        	} else {
        		return false;
        	}
        }
        else 
        	return false;
    }
	
    // Check if the piece is an opponent's piece
	private boolean isOpponentPiece(Enum.PlayerSide player, Position position) {
		if (this.getPiece(position) == Enum.PieceType.EMPTY ) {
			return false;
		} else 
			return !isOwnPiece(player, position);
	}
	
	// Get all possible normal moves from a given position and player side
	public ArrayList<Move> getPossibleNormalMoves(Position startPosition, Enum.PlayerSide side){
		/* 
		 * 1) Check if
		 *		- the position is not empty 
		 *		- the player is trying to move his own piece 
		*/
		Enum.PieceType pieceType = getPiece(startPosition);
		if ( pieceType == Enum.PieceType.EMPTY || isOpponentPiece(side, startPosition)) {
			return null;
		}
		
		/* 
		 * 2) add all possible positions that the piece can move to 
		*/
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ArrayList<Position> possibleEndPositions = new ArrayList<Position>(); 
		
		int startRow = startPosition.getRow();
		int startCol = startPosition.getCol();
		int endRow, endCol;
		
		// if it's a normal piece (RED or BLACK) then we have 2 possible moves
		if ( pieceType == Enum.PieceType.RED || pieceType == Enum.PieceType.BLACK ) {
			endRow = pieceType == Enum.PieceType.BLACK ? startRow+1 : startRow-1;
			endCol = startCol + 1;
			possibleEndPositions.add(new Position(endRow, endCol));
			endCol = startCol - 1;
			possibleEndPositions.add(new Position(endRow, endCol));
		}
		
		// if it's a king piece (RED_KING or BLACK_KING) then we only have 4 possible moves
		// if it's a knight piece (RED_KNIGHT or BLACK_KNIGHT) it has the same 4 possible moves 
		// plus 4 others that will be added later 
		
		if ( pieceType == Enum.PieceType.RED_KING || pieceType == Enum.PieceType.BLACK_KING
				|| pieceType == Enum.PieceType.RED_KNIGHT || pieceType == Enum.PieceType.BLACK_KNIGHT) {
					
			possibleEndPositions.add(new Position(startRow + 1, startCol - 1));
			possibleEndPositions.add(new Position(startRow + 1, startCol + 1));
			possibleEndPositions.add(new Position(startRow - 1, startCol - 1));
			possibleEndPositions.add(new Position(startRow - 1, startCol + 1));

		}
		
		// if it's a knight piece (RED_KNIGHT or BLACK_KNIGHT) then we add the remaining 4 possible moves (up, down, left, right)
		if ( pieceType == Enum.PieceType.RED_KNIGHT || pieceType == Enum.PieceType.BLACK_KNIGHT ) {
					
			possibleEndPositions.add(new Position(startRow, startCol - 1));
			possibleEndPositions.add(new Position(startRow, startCol + 1));
			possibleEndPositions.add(new Position(startRow + 1, startCol));
			possibleEndPositions.add(new Position(startRow - 1, startCol));
					
		}
		
		/*
		 * 3) iterate through all the possible end positions and keep only the ones that make a valid move :
		 *   										- the endPosition is valid (inside the board) and empty
		 */
		for (Position endPosition: possibleEndPositions) {
			if ( endPosition.isValid(size) && this.getPiece(endPosition) == Enum.PieceType.EMPTY ) {
				possibleMoves.add(new Move(startPosition, endPosition));
			}
		}
		return possibleMoves;
	}
	
	// Get all possible jump moves from a given position and player side
	public ArrayList<Move> getPossibleJumpMoves(Position startPosition, Enum.PlayerSide side) {
		/* 
		 * 1) Check if
		 *		- the position is not empty 
		 *		- the player is trying to move his own piece 
		*/
		Enum.PieceType pieceType = getPiece(startPosition);
		if ( pieceType == Enum.PieceType.EMPTY || isOpponentPiece(side, startPosition)) {
			return null;
		}
		
		/* 
		 * 2) add all possible positions that the piece can jump to 
		*/
		ArrayList<Move> possibleJumpMoves = new ArrayList<Move>();
		ArrayList<Position> possibleEndPositions = new ArrayList<Position>(); 
		
		int startRow = startPosition.getRow();
		int startCol = startPosition.getCol();
		int endRow, endCol;
		Move jumpMove;
		
		if ( pieceType == Enum.PieceType.RED || pieceType == Enum.PieceType.BLACK ) {
			endRow = pieceType == Enum.PieceType.BLACK ? startRow+2 : startRow-2;
			endCol = startCol + 2;
			possibleEndPositions.add(new Position(endRow, endCol));
			endCol = startCol - 2;
			possibleEndPositions.add(new Position(endRow, endCol));
		}
		
		if ( pieceType == Enum.PieceType.RED_KING || pieceType == Enum.PieceType.BLACK_KING
				|| pieceType == Enum.PieceType.RED_KNIGHT || pieceType == Enum.PieceType.BLACK_KNIGHT) {
					
			possibleEndPositions.add(new Position(startRow + 2, startCol - 2));
			possibleEndPositions.add(new Position(startRow + 2, startCol + 2));
			possibleEndPositions.add(new Position(startRow - 2, startCol - 2));
			possibleEndPositions.add(new Position(startRow - 2, startCol + 2));
		}
		
		/*
		 * 3) iterate through all the possible end positions and keep only the ones that make a valid move :
		 *   										- the endPosition is valid (inside the board) and empty
		 *											- the piece to jump over is an opponent piece
		 */
		for (Position endPosition: possibleEndPositions) {
			jumpMove = new Move(startPosition, endPosition);
			if ( endPosition.isValid(size) && this.getPiece(endPosition) == Enum.PieceType.EMPTY
					&& this.isOpponentPiece(side, this.getJumpedPosition(jumpMove))) {
				possibleJumpMoves.add(jumpMove);
			}
		}
		return possibleJumpMoves;	
	}
	
	// Get all possible moves for a given player side
	public ArrayList<Move> getAllPossibleMoves(Enum.PlayerSide side) {
		Enum.PieceType man;
		Enum.PieceType king;
		Enum.PieceType knight;
		// get the piece types that corresponds to the player side
		if (side == Enum.PlayerSide.RED) {
			man = Enum.PieceType.RED;
			king = Enum.PieceType.RED_KING;
			knight = Enum.PieceType.RED_KNIGHT;
		} else {
			man = Enum.PieceType.BLACK;
			king = Enum.PieceType.BLACK_KING;
			knight = Enum.PieceType.BLACK_KNIGHT;
		}
		
		/*
		 * 1) Get all posiible jump moves for the player's pieces
		 * ( we have to start with with the jump moves because making a jump move is mandatory if one is available )
		 * */
		ArrayList<Move> possibleJumpMoves = new ArrayList<Move>();
		for (int row=0; row<size; row++) {
			for (int col=0; col<size; col++) {
				Enum.PieceType piece = getPiece(row, col);
				if (piece == man || piece == king || piece == knight) {
					Position startPosition = new Position(row, col);
					possibleJumpMoves.addAll(getPossibleJumpMoves(startPosition, side));
				}
			}
		}
		
		if (!possibleJumpMoves.isEmpty()) {
			return possibleJumpMoves;
		}
		// if we reach this part of the code then the player has no possible jump moves
		
		/*
		 *  2) Get all posiible normal moves for the player's pieces
		 */
		ArrayList<Move> possibleNormalMoves = new ArrayList<Move>();
		for (int row=0; row<size; row++) {
			for (int col=0; col<size; col++) {
				Enum.PieceType piece = getPiece(row, col);
				if (piece == man || piece == king || piece == knight) {
					Position startPosition = new Position(row, col);
					possibleNormalMoves.addAll(getPossibleNormalMoves(startPosition, side));
				}
			}
		}
		
		if (!possibleNormalMoves.isEmpty()) {
			return possibleNormalMoves;
		}
		
		// if we reach this part of the code then the player has no possible moves (jump or normal)
		return null;
	}
	
	// Make the necessary updates to the board after making a given move for a given player side 
	public Enum.MoveStatus makeMove(Move move, Enum.PlayerSide side) {
		
		ArrayList<Move> possibleMoves;
		if ( move == null ) {
			return Enum.MoveStatus.MOVE_FAILED;
		}
		
		Position startPosition = move.getStart();
		int startRow = startPosition.getRow();
		int startCol = startPosition.getCol();
		Position endPosition = move.getEnd();
		int endRow = endPosition.getRow();
		int endCol = endPosition.getCol();
		Enum.PieceType pieceType = this.getPiece(startPosition);
		boolean isJumpMove = false;
		boolean isNormalMove = false;
		
		/* 
		 * 1) Check if
		 *		- the position is not empty 
		 *		- the player is trying to move his own piece 
		*/
		if (!isOwnPiece(side, startPosition) || pieceType == Enum.PieceType.EMPTY ) {
			return Enum.MoveStatus.MOVE_FAILED;
		}
		
		/*
		 * 2) Get all the possible moves for the given player side and check if the given move is among them
		*/
		possibleMoves = getAllPossibleMoves(side);
		
		// Check there's a possible move to begin with
		if (!possibleMoves.isEmpty()) {
			// Check if the given move is among the possible moves
			if (possibleMoves.contains(move)) {
				// At this point we know that move is either a jump move or a normal move ( since it's among the possible moves ) 
				// Check if the given move is a jump move 
				if (move.isJumpMove()) {
					// Make the necessary updates to the board after making the jump move ( Not all the updates )
					isJumpMove = true; // mark that we made a jump move
					board[startRow][startCol] = Enum.PieceType.EMPTY;
					board[endRow][endCol] = pieceType;
					Position jumpedPosition = this.getJumpedPosition(move);
					Enum.PieceType jumpedPiece = this.getJumpedPiece(jumpedPosition);
				
					if (jumpedPiece == Enum.PieceType.RED) {
						this.nbrNormalRedCheckers--;
					} else if (jumpedPiece == Enum.PieceType.RED_KING) {
						this.nbrRedKingCheckers--;
					} else if (jumpedPiece == Enum.PieceType.RED_KNIGHT) {
						this.nbrRedKnightCheckers--;
					} else if (jumpedPiece == Enum.PieceType.BLACK) {
						this.nbrNormalBlackCheckers--;
					} else if (jumpedPiece == Enum.PieceType.BLACK_KING) {
						this.nbrBlackKingCheckers--;
					} else if (jumpedPiece == Enum.PieceType.BLACK_KNIGHT) {
						this.nbrBlackKnightCheckers--;
					}
					board[jumpedPosition.getRow()][jumpedPosition.getCol()] = Enum.PieceType.EMPTY;
				} 
				// If the game is not a jump move then it must be a normal move 
				else {
					// Make the necessary updates to the board after making a normal move ( Not all the updates )
					isNormalMove = true; // mark that ze made a normal move
					board[startRow][startCol] = Enum.PieceType.EMPTY;
					board[endRow][endCol] = pieceType;
				}
			} 
		} 
			
		// Check if there's pieces to upgrade after making a move 
		if ( isJumpMove || isNormalMove ) {
			if ( endRow == 0 && side == Enum.PlayerSide.RED && pieceType == Enum.PieceType.RED ) {
				if (endCol == 0 || endCol == size - 1) {
					board[endRow][endCol] = Enum.PieceType.RED_KNIGHT;
					this.nbrRedKnightCheckers++;
					this.nbrNormalRedCheckers--;
				} else {
					board[endRow][endCol] = Enum.PieceType.RED_KING;
					this.nbrRedKingCheckers++;
					this.nbrNormalRedCheckers--;
				}
			}
			
			if ( endRow == size - 1 && side == Enum.PlayerSide.BLACK && pieceType == Enum.PieceType.BLACK ) {
				if (endCol == 0 || endCol == size - 1) {
					board[endRow][endCol] = Enum.PieceType.BLACK_KNIGHT;
					this.nbrBlackKnightCheckers++;
					this.nbrNormalBlackCheckers--;
				} else {
					board[endRow][endCol] = Enum.PieceType.BLACK_KING;
					this.nbrBlackKingCheckers++;
					this.nbrNormalBlackCheckers--;
				}
			}
		}
		
		if (isJumpMove) {
			// Check if there's an additional jump move
			ArrayList<Move> additionalJumpMoves = this.getPossibleJumpMoves(endPosition, side);
			if (additionalJumpMoves.isEmpty()) 
				return Enum.MoveStatus.SUCCESSFUL;
			return Enum.MoveStatus.ADDITIONAL_JUMP; 
		} else if (isNormalMove) {
			return Enum.MoveStatus.SUCCESSFUL;
		} else {
			return Enum.MoveStatus.MOVE_FAILED;
		}
		
	}
	
}
