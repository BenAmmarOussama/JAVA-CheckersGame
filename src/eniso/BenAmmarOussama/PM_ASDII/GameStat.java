package eniso.BenAmmarOussama.PM_ASDII;


import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

// This class is used to hold statistics about the game
// Only one instance of this class can exist that's why the SINGLETON design pattern is implemented 

public class GameStat implements Serializable {
	private static final long serialVersionUID = 6529685098267757690L;
	
	public static final String FILE_PATH = System.getProperty("user.home") +"\\Documents\\Checkers\\Statistics\\";
	public static final String FILE_NAME = "Game Stat.java";
	
	public static GameStat INSTANCE ;
	Hashtable<String, int[]> playersStat;
	private int nbGames;
	
	
	private GameStat() {
		this.nbGames = 0;
		this.playersStat = new Hashtable<String, int[]>(); // the keys will be mapped to an array of size 3 :
	}													   //           - First value corresponds to the number of wins
														   //           - First value corresponds to the number of draws
														   //           - First value corresponds to the number of losses 
	
	
	// Copy Constructor
	private GameStat(GameStat anotherObject) {
		this.nbGames = anotherObject.getNbGames();
		//this.playersStat = anotherObject.getPlayersStat();
		this.playersStat = new Hashtable<String, int[]>();
		Enumeration<String> keys = anotherObject.getPlayersStat().keys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			int[] oldValue =  anotherObject.getPlayersStat().get(key);
			int[] newValue = new int[3];
			newValue[0] = oldValue [0];
			newValue[1] = oldValue [1];
			newValue[2] = oldValue [2];
			this.playersStat.put(key, newValue);
		}
	}
	
	public static GameStat getInstance() {
		// load the game statistics object from file, if the file doesn't exist the function will return null
		INSTANCE = loadGameStatFromFile();
		if (INSTANCE == null) {
			return new GameStat();
		}
		return new GameStat(INSTANCE);
	}
	
	private static GameStat loadGameStatFromFile() {
		
		return (GameStat) FileIO.loadObjectFromFile(FILE_PATH, FILE_NAME);
	}
	
	@Override
	public String toString() {
		String output = "";
		Enumeration<String> keys = this.playersStat.keys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			int[] value =  this.playersStat.get(key);
			output += key+" : \n"+
					"\tWin: "+value[0]+" ** Draw: "+value[1]+" ** Loss: "+value[2]+"\n";
		}
		return output+"  nbGames: "+this.getNbGames()+"\n";
	}
	
	// Add a user to the players statistics hashtable 
	public void addUser(String name) {
		if ( !this.playersStat.containsKey(name)) {
			int[] a = {0, 0, 0};
			this.playersStat.put(name, a);
		}
		
	}
	
	private void updateValue(String name, int index) {
		int[] value = this.playersStat.get(name);
		try {
			value[index] = value[index] + 1; 
			playersStat.put(name, value);
		} 
		catch (NullPointerException e) {
			System.out.println("Error: username doesn't exist");
		}
	}
	
	public void addWin(String name) {
		updateValue(name, 0);
	}
	
	public void addDraw(String name) {
		updateValue(name, 1);
	}
	
	public void addLoss(String name) {
		updateValue(name, 2);
	}
	
	public void display() {
		System.out.println(this);
	}
	
	public Hashtable<String, int[]> getPlayersStat() {
		return playersStat;
	}

	public void setPlayersStat(Hashtable<String, int[]> playersStat) {
		this.playersStat = playersStat;
	}

	public int getNbGames() {
		return nbGames;
	}

	public void IncrementNbGames() {
		this.nbGames += 1;
	}

	
}
