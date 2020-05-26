package eniso.BenAmmarOussama.PM_ASDII;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

abstract public class Player implements Serializable {
	
	private static final long serialVersionUID = 6529685098267757690L;
	private static int playerNumber = 10; //Number used to generate the user name in case it wasn't provided 
	
	protected Enum.PlayerSide side;
	private  String name;
	
	
	public Player(Enum.PlayerSide side, String name) {
		this.side = side;
		this.name = name;
	}
	
	public Player(Enum.PlayerSide side) {
		this(side, "Player"+playerNumber);
		playerNumber++;
	}
	
	abstract public Enum.MoveStatus makeMove(Move move, Board board);
	
	@Override
	public String toString() {
		return name + "(" + side + ")";
	}
	
	public Enum.PlayerSide getSide() {
		return side; 
	}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) { 
  
        // If the object is compared with itself then return true   
        if (obj == this) { 
            return true; 
        } 
        // Check if obj is an instance of Position or not 
        if (!(obj instanceof Player)) { 
            return false; 
        } 
        // Type cast obj to "Player" so that we can compare the name & side attributes   
        Player p = (Player) obj; 
        return ( this.name == p.getName() && this.side == p.getSide()  ); 
    }
	
}
