package eniso.BenAmmarOussama.PM_ASDII;

import java.io.Serializable;

public class Position implements Serializable {
	
	private static final long serialVersionUID = 6529685098267757690L;
	private int row;
	private int col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	// Default constructor
	public Position() {
		this(0,0);
	}
	
	// Copy Constructor
	public Position(Position p) {
		this(p.getRow(), p.getCol());
	}
	
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	// Check if the position is inside the board
	public boolean isValid(int boardSize) {
		return (( this.row < boardSize && this.row >= 0 ) 
				&& ( this.col < boardSize && this.col >= 0));
	}
	
	
	@Override
	public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
        // Check if o is an instance of Position or not 
        if (!(o instanceof Position)) { 
            return false; 
        } 
        // Type cast o to "Position" so that we can compare the row & column attributes   
        Position p = (Position) o; 
          
        return ( this.row == p.getRow() && this.col == p.getCol()  ); 
    }
	
	@Override
	public String toString() {
		return "("+ row +" , "+ col +")";
	}
}
