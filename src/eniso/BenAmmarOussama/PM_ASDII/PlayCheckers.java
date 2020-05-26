package eniso.BenAmmarOussama.PM_ASDII;

import java.util.Scanner;

public class PlayCheckers {

	public static void main(String[] args) {
		CheckersGame.setUpDirectories();
		String choice;
		do {
			Scanner input = new Scanner(System.in);
			System.out.println("\n\t*************"
							  +"\n\t1)New Game\n"
							   +"\t2)Load Game\n"
							   +"\t3)Watch a saved Game\n"
							   +"\t4)Statistics\n"
							   +"\t5)Exit \n"
							   +"\t*************"
					          );
			choice = input.nextLine();
			CheckersGameFactory gameFactory = new CheckersGameFactory();
			CheckersGame game;
			switch (choice) {
				case "1":
					
					game = gameFactory.createCheckersGame();
					game.startGame();
					break;
				case "2":
					
					CheckersGame loadedGame = gameFactory.loadGame();
					loadedGame.startGame();
					break;
				case "3":
					
					CheckersGame.replayGame();
				case "4":
					
					GameStat gameStatistics = GameStat.getInstance();
					gameStatistics.display();
					break;
				case "5":
					
					break;
					
			}
		} while (!choice.equals("5"));
		

	}

}
