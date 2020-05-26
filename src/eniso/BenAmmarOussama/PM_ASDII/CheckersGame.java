package eniso.BenAmmarOussama.PM_ASDII;

import java.io.Serializable;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class CheckersGame implements Serializable {
	private static final long serialVersionUID = 6529685098267757690L;
	
	public static final String DOC_FILE_PATH=System.getProperty("user.home") +"\\Documents\\";
	public static final String SAVED_GAMES_FILE_PATH=System.getProperty("user.home") +"\\Documents\\Checkers\\Saved Games\\";
	public static final String REPLAYS_FILE_PATH=System.getProperty("user.home") +"\\Documents\\Checkers\\Replays\\";
	
	
	private Board board;
	private Enum.GameMode mode;
	private Enum.Difficulty difficulty ;
	private Player player1;
	private Player player2;
	Player currentPlayer ;
	private Stack<Board> gameStack;
	private Queue<Board> boardsQueue = new Queue<Board>();
	
	// Constructor to use when creating a single player game
	public CheckersGame(int size, Enum.GameMode mode, Enum.Difficulty difficulty) {
		this.board = new Board(size);
		this.mode = mode;
		this.difficulty = difficulty;
		if (difficulty == Enum.Difficulty.EASY ) {
			gameStack = new Stack<Board>(10);
		} else {
			gameStack = new Stack<Board>(10);
		}
		setUpPlayers(mode);
	}
	
	// Constructor to use when cretaing a two player game
	public CheckersGame(int size, Enum.GameMode mode) {
		this.board = new Board(size);
		this.mode = mode;
		setUpPlayers(mode);
	}
	
	// return true if the game is over
	public boolean gameIsOver() {
		return ( board.getNbrRedCheckers() == 0 || board.getNbrBlackCheckers() == 0 || 
				( board.getAllPossibleMoves(Enum.PlayerSide.RED) == null  && board.getAllPossibleMoves(Enum.PlayerSide.BLACK) == null) );
	}
	
	// Determine the winner of the game 
	public int getWinner() {
		if ( board.getNbrRedCheckers() > board.getNbrBlackCheckers() ) {
			return 1;
		}
		if ( board.getNbrRedCheckers() < board.getNbrBlackCheckers() ) {
			return 2;
		}
		return 0;
	}
	
	/* - Method that contains the game loop :
	 * 			   Step 1) Request command input 
	 * 							- Process the command if it's provided
	 * 			   Step 2) Request move input
	 * 							- make the move if it's valid, else request again
	 * 			   Step 3) Display the new state of the board
	 * 			   Step	4) Change the current player
	 * 
	 * - Evalute the game at the end of the loop ( Show results and update the statistics file )
	*/	
	public void startGame() {
		
		boolean exit = false;
		Scanner input = new Scanner(System.in);
		board.displayBoard();
		boardsQueue.enqueue(new Board(board));
		
		while (!gameIsOver()) {
			// Step 1)
			if ( currentPlayer instanceof HumanPlayer) {
				boolean repeatCommand; 
				do {
					repeatCommand = true;
					System.out.println("Command: (Save, Help, Restart, Back, Exit)\n(just press enter if you don't have any)");
					String choice = input.nextLine();
					
					if ( choice.equals("")) {
						
						repeatCommand = false ;
					} else if (choice.equals("Exit") ) {
						
						repeatCommand = false;
						exit = true;
					} else if (choice.equals("Save") ) {
						
						saveGameToFile(this);
					} else if (choice.equals("Restart") ) {
						
						board = new Board(board.getSize());
						board.displayBoard();
						currentPlayer = player1;
						repeatCommand = false;
					} else if ( choice.equals("Help") ) {
						
						ArrayList<Move> possibleMoves = board.getAllPossibleMoves(currentPlayer.getSide());
						System.out.println(possibleMoves);
					} else if ( choice.equals("Back") ) {
						
						if ( !gameStack.isEmpty() ) {
							board = new Board(gameStack.pop());
							board.displayBoard();
						}
					}
					
				} while (repeatCommand == true);
			}
			if (exit) {
				break;
			}
			
			this.doPlayerTurn(currentPlayer); // Step 2)
			board.displayBoard(); // Step 3)
			boardsQueue.enqueue(new Board(board));
			currentPlayer = this.changeCurrentPlayer(currentPlayer); // Step 4)
		}
		if (!exit) {
			evaluateGame();
		}
	}
	
	private void saveGameStatToFile(Object serObj) {
		
		FileIO.saveObjectToFile(serObj, GameStat.FILE_PATH, GameStat.FILE_NAME);
	}
	
	private void saveGameToFile(Object serObj) {
		
		FileIO.saveObjectToFile(serObj, SAVED_GAMES_FILE_PATH);
	}
	
	public CheckersGame loadGame() {
		
		Object loadedObject = FileIO.loadObjectFromFile(SAVED_GAMES_FILE_PATH);
		
		return (CheckersGame) loadedObject ; 
	}
	
	private void evaluateGame() {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Game Over!");
		GameStat gameStat = GameStat.getInstance();
		
		gameStat.addUser(player1.getName());
		gameStat.addUser(player2.getName());
		gameStat.IncrementNbGames();
		
		if (getWinner() == 1) {
			gameStat.addWin(player1.getName());
			gameStat.addLoss(player2.getName());
			System.out.println("**** "+player1.getName()+" Won ! ****");
			
		} else if ( getWinner() == 2) {
			gameStat.addWin(player2.getName());
			gameStat.addLoss(player1.getName());
			System.out.println("**** "+player2.getName()+" Won ! ****");
			
		} else {
			gameStat.addDraw(player1.getName());
			gameStat.addDraw(player2.getName());
			System.out.println("**** Draw ! ****");
			
		}
		saveGameStatToFile(gameStat);
		String answer;
		do {
			
			System.out.println("Would you like to save this game ? (yes/no)");
			answer = input.nextLine();
			if ( answer.equals("yes")) {
				
				FileIO.saveObjectToFile(boardsQueue, REPLAYS_FILE_PATH);
			} 
		} while ( !(answer.equals("yes")) && !(answer.equals("no")) );
		
	}
	
	// Request the Human player to give a move with valid positions
	private Move getPlayerMoveInput(Player player) {
		
		int end = 0, start = 0;
		Move m = null;
		Scanner input = new Scanner(System.in);
		do {
			System.out.println(player.getName()+", make your Move !  (xy xy)");
			
			start = input.nextInt();
			end = input.nextInt();
			
			Position startPosition = new Position((int)start/10, start%10);
			Position endPosition = new Position((int)end/10, end%10);
			
			if (startPosition.isValid(board.getSize()) && endPosition.isValid(board.getSize())) {
				m = new Move(startPosition, endPosition);
			}
			
		} while (m == null);
		return m;
	}
	
	// Make the move provided by the Human player, if it's invalid then request another one 
	private void doPlayerTurn(HumanPlayer player) {
		
		Enum.MoveStatus result;
		Move m;
		do {
		m = this.getPlayerMoveInput(player);
		
		result  = player.makeMove(m, board);
		System.out.println(result);
		
		} while ( result != Enum.MoveStatus.SUCCESSFUL);
	}
	
	// Make the machine player move based on the game difficulty
	private void doPlayerTurn(MachinePlayer Mplayer) {
		Enum.MoveStatus result;
		do {
			if (difficulty == Enum.Difficulty.EASY) {
				result = Mplayer.makeRandomMove(board);
			} else {
				result = Mplayer.makeMove(board);
			}
		} while (result != Enum.MoveStatus.SUCCESSFUL );
	}
	
	// Determine which "doPlayerTurn" method to use based on the type of the player
	// ( This is necessary because the currentPlayer object has a Player reference while it can point 
	//	to both types players ( Human/Machine ) )  
	private void doPlayerTurn (Player player) {
		
		if ( player instanceof HumanPlayer ) {
			doPlayerTurn( (HumanPlayer) player);
		} 
		else {
			doPlayerTurn( (MachinePlayer) player);
			gameStack.push(new Board(board));
		}
	}
	
	private Player changeCurrentPlayer(Player player) {
		if (player == player1 ) {
			return player2;
		}
		return player1;
	}
	
	private void setUpPlayers(Enum.GameMode mode) {
		String userName = "";
		Scanner input = new Scanner(System.in);
		System.out.println("Player1 username :");
		userName = input.nextLine();
		if (userName.equals("")) {
			this.player1 = new HumanPlayer(Enum.PlayerSide.RED);
		} else {
			this.player1 = new HumanPlayer(Enum.PlayerSide.RED, userName);
		}
		
		if (mode == Enum.GameMode.SinglePlayer) {
			player2 = new MachinePlayer(Enum.PlayerSide.BLACK, difficulty);
		} else {
			System.out.println("Player2 username :");
			userName = input.nextLine();
			if (userName.equals("")) {
				this.player2 = new HumanPlayer(Enum.PlayerSide.BLACK);
			} else {
				this.player2 = new HumanPlayer(Enum.PlayerSide.BLACK, userName);
			}
		}
		this.currentPlayer = player1;
		//input.close();
	}
	
	@SuppressWarnings("unchecked")
	private static Queue<Board> loadGameReplay() { 
		return (Queue<Board>) FileIO.loadObjectFromFile(REPLAYS_FILE_PATH);
	}
	
	public static void replayGame() {
		
		Queue<Board> loadedQueue = loadGameReplay();
		if (loadedQueue != null) {
			while (!loadedQueue.isEmpty()) {
				Board tempBoard = loadedQueue.dequeue();
				tempBoard.displayBoard();
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	private static boolean isFirstTimePlaying() {
		Object FirstTimePlaying = FileIO.loadObjectFromFile(System.getProperty("user.home"), "\\firstTime.java");
		if (FirstTimePlaying == null) {
			FirstTimePlaying = 0; // Random value
			FileIO.saveObjectToFile(FirstTimePlaying, System.getProperty("user.home"), "\\firstTime.java");
			return true;
		} 
		return false;
	}
	
	public static void setUpDirectories() {
		if (isFirstTimePlaying()) {
			FileIO.createDirectory(DOC_FILE_PATH+"Checkers");
			FileIO.createDirectory(DOC_FILE_PATH+"Checkers\\Saved Games");
			FileIO.createDirectory(DOC_FILE_PATH+"Checkers\\Replays");
			FileIO.createDirectory(DOC_FILE_PATH+"Checkers\\Statistics");
		}
	}

	public Board getBoard() {
		return board;
	}
	
	
}
