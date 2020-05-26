package eniso.BenAmmarOussama.PM_ASDII;

import java.util.Scanner;

public class CheckersGameFactory {
	
	public CheckersGame createCheckersGame() {
	
		CheckersGame game;
		int size = this.getBoardSizeInput();
		Enum.GameMode mode = this.getGameModeInput();
		if (mode == Enum.GameMode.SinglePlayer) {
			Enum.Difficulty gameDifficulty = this.getGameDifficultyInput();
			game = new CheckersGame(size, mode,gameDifficulty);
		} else {
			game = new CheckersGame(size, mode);
		}
		
		return game;
	}
	
	private Enum.GameMode getGameModeInput() {
		Enum.GameMode mode = null;
		Scanner input = new Scanner(System.in);
		
		do {
			System.out.println("SinglePlayer or TwoPlayers ? (s/t):");
			String m = input.nextLine();
			if (m.equals("s")) {
				mode = Enum.GameMode.SinglePlayer;
			} else if (m.equals("t"))  {
				mode = Enum.GameMode.TwoPlayer;
			}
		} while (mode == null);
		return mode;
	}
	
	private Enum.Difficulty  getGameDifficultyInput() {
		Enum.Difficulty gameDifficulty = null;
		Scanner input = new Scanner(System.in);
		
		do {
			System.out.println("Game Difficulty: (E,M,H)");
			String dif = input.nextLine();
			if (dif.equals("E") ) {
				gameDifficulty = Enum.Difficulty.EASY;
			} else if (dif.equals("M") ) {
				gameDifficulty = Enum.Difficulty.MEDIUM;
			} else if (dif.equals("H") ) {
				gameDifficulty = Enum.Difficulty.DIFFICULT;
			}
		} while (gameDifficulty == null);
		return gameDifficulty;
	}
	
	private int getBoardSizeInput() {
		int size = 0;
		Scanner input = new Scanner(System.in);
		
		do {
			System.out.println("Board size ? (8/10):");
			String s =input.nextLine();
			if (s.equals("8")) {
				size = 8;
			} else if (s.equals("10")) {
				size = 10;
			}
		} while (size == 0);
		return size;
	}
	
	public CheckersGame loadGame() { 
		
		return (CheckersGame) FileIO.loadObjectFromFile(CheckersGame.SAVED_GAMES_FILE_PATH);
	}
	
}
