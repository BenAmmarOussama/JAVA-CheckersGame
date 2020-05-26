package eniso.BenAmmarOussama.PM_ASDII;

import java.io.Serializable;

public class Move implements Serializable {
	private static final long serialVersionUID = 6529685098267757690L;
	private Position start;
	private Position end;
	
	// Constructor using the row and column coordinates 
	public Move(int startRow, int startCol, int endRow, int endCol) {
		start = new Position(startRow, startCol);
		end = new Position(endRow, endCol);
	}
	
	// Constructor using Position objects
	public Move(Position start, Position end) {
		this.start = start;
		this.end = end;
	}
	
	// Copy constructor
	public Move(Move m) {
		start = m.getStart();
		end = m.getEnd();
	}
	
	// Check if the move is a jump move
	public boolean isJumpMove() {
		return ( Math.abs( getStart().getRow() - getEnd().getRow() ) == 2 
				&& Math.abs( getStart().getCol() - getEnd().getCol() ) == 2);
	}
	
	@Override
	public String toString() {
		return "\nStart: " + start.toString()+" End: "+ end.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		// If the object is compared with itself then return true
		if (obj == this) {
			return true;
		}
		// Check if obj is an instance of Move or not
        if(!(obj instanceof Move)) {
            return false;
        }
     // Type cast obj to "Move" so that we can compare the start & end attributes 
        Move m = (Move) obj;
        return (this.getStart().equals(m.getStart()) 
        		             && this.getEnd().equals(m.getEnd()) );

    }
	 
	public Position getStart() {
		return start;
	}
	
	public Position getEnd() {
		return end;
	}

}
