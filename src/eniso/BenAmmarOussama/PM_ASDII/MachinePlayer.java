package eniso.BenAmmarOussama.PM_ASDII;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MachinePlayer extends Player {
	
	private static final long serialVersionUID = 6529685098267757690L;
	
	private Enum.Difficulty level;
	int nbCalls = 0;
	
	public MachinePlayer(Enum.PlayerSide side, Enum.Difficulty level) {
		super(side, "MinMaxAI");
		this.level = level;
	}
	
	// Determine the machine's move in the easy level
	public Enum.MoveStatus makeRandomMove(Board board) {
		ArrayList<Move> possibleMoves = board.getAllPossibleMoves(side);
		Collections.shuffle(possibleMoves);
		Random rand = new Random();
		return board.makeMove(possibleMoves.get(rand.nextInt(possibleMoves.size())), side);
	}
	
	@Override
	public Enum.MoveStatus makeMove(Move move, Board board) {
		// TODO Auto-generated method stub
		return board.makeMove(move, side);
	}
	
	// Determine the machine's move based on the level value
	public Enum.MoveStatus makeMove(Board board) {
		if (level == Enum.Difficulty.EASY ) {
			return makeRandomMove(board);
		}
		Move m = null;
		if ( level == Enum.Difficulty.MEDIUM) {
			m = this.getBestMove(board, 4);
		}
		if (level == Enum.Difficulty.DIFFICULT ) {
			m = this.getBestMove(board, 8);
		}
		return makeMove(m,board);
	}
	
	// Get the best move to make using simple minimax algorithm
	public Move getBestMoveSimple(Board board, int depth) {
		
		ArrayList<Move> possibleMoves;
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		ArrayList<Double> boardEvaluation = new ArrayList<Double>();
		// get all the possible moves 
		possibleMoves = board.getAllPossibleMoves(getSide());
		// if there's no possible moves <==> game is finished
		if (possibleMoves == null) {
			return null;
		}
		
		// Calculate all the heuristic evalutions of after making all possible moves from the current board state
		Board auxBoard = null;
		for (Move m: possibleMoves) {
			auxBoard = new Board(board);
			auxBoard.makeMove(m, getSide());
			TreeNode root = new TreeNode(auxBoard);
			boardEvaluation.add(this.minimax(root, getSide(), depth, true));
		}
		// find the maximum evaluation value
		double maxEval = -1000; // random number
		for (int i=0; i<boardEvaluation.size(); i++) {
			if (boardEvaluation.get(i) > maxEval) {
				maxEval = boardEvaluation.get(i); 
			}
		}
		
		// Get all the moves that corresponds to the maximum evaluation value 
		for (int i=0; i<boardEvaluation.size(); i++) {
			if (boardEvaluation.get(i) == maxEval) {
				bestMoves.add(possibleMoves.get(i));
			}
		}
		// Choose a random move from all the best moves possible 
		Random rand = new Random();
		Collections.shuffle(bestMoves);
		Move m = bestMoves.get(rand.nextInt(bestMoves.size()));
		System.out.println(m);
		return m;
	}
	
	// Get the best move to make using minimax & ALpha-Beta pruning
	public Move getBestMove(Board board, int depth) {
		
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		ArrayList<Move> possibleMoves;
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		ArrayList<Double> boardEvaluation = new ArrayList<Double>();
		// get all the possible moves 
		possibleMoves = board.getAllPossibleMoves(getSide());
		// if there's no possible moves <==> game is finished
		if (possibleMoves == null) {
			return null;
		}
		// Calculate all the heuristic evalutions of after making all possible moves from the current board state
		Board auxBoard = null;
		for (Move m: possibleMoves) {
			auxBoard = new Board(board);
			auxBoard.makeMove(m, getSide());
			TreeNode root = new TreeNode(auxBoard);
			boardEvaluation.add(this.minimaxPruning(root, getSide(), depth, true, alpha, beta));
		}
		// find the maximum evaluation value
		double maxEval = -1000; // random number
		for (int i=0; i<boardEvaluation.size(); i++) {
			if (boardEvaluation.get(i) > maxEval) {
				maxEval = boardEvaluation.get(i); 
			}
		}
		// Get all the moves that corresponds to the maximum evaluation value 
		for (int i=0; i<boardEvaluation.size(); i++) {
			if (boardEvaluation.get(i) == maxEval) {
				bestMoves.add(possibleMoves.get(i));
			}
		}
		// Choose a random move from all the best moves possible 
		Random rand = new Random();
		Move m = bestMoves.get(rand.nextInt(bestMoves.size()));
		System.out.println(m);
		return m;
		
	}
	
	public double minimax(TreeNode root,Enum.PlayerSide side, int depth, boolean maxPlayer) {
		nbCalls++;
		if (depth == 0) {
			// return evaluation
			return this.getBoardEvaluation(root.getBoardStatus());
		}
		
		ArrayList<Move> possibleMoves = root.getBoardStatus().getAllPossibleMoves(side);
		
		if ( possibleMoves == null) {
			// return evaluation
			return this.getBoardEvaluation(root.getBoardStatus());
		}
		Board auxBoard = null;
		for (Move m: possibleMoves) {
			
			auxBoard = new Board(root.getBoardStatus());
			auxBoard.makeMove(m, side);
			root.addChild(new TreeNode(auxBoard));
		}
		
		if(maxPlayer) {

			double maxEval = Double.NEGATIVE_INFINITY;
			if (!root.getChildren().isEmpty()) {
				for (TreeNode child: root.getChildren()) {
					double eval = minimax(child, changePlayer(side), depth-1, false);
					maxEval = Double.max(eval, maxEval);
				}
			}
			return maxEval;
		}
		else {
			double minEval = Double.POSITIVE_INFINITY;
			if (!root.getChildren().isEmpty()) {
				for (TreeNode child: root.getChildren()) {
					double eval = minimax(child, changePlayer(side), depth-1, true);
					minEval = Double.min(eval, minEval);
				}
			}
			return minEval;
		}
	}
	
	public double minimaxPruning(TreeNode root,Enum.PlayerSide side, int depth, boolean maxPlayer, double alpha, double beta) {
		nbCalls++;
		if (depth == 0) {
			// return evaluation
			return this.getBoardEvaluation(root.getBoardStatus());
		}
		
		ArrayList<Move> possibleMoves = root.getBoardStatus().getAllPossibleMoves(side);
		if ( possibleMoves == null) {
			// return evaluation
			return this.getBoardEvaluation(root.getBoardStatus());
		}
		Board auxBoard = null;
		for (Move m: possibleMoves) {
			//System.out.println(m);
			auxBoard = new Board(root.getBoardStatus());
			auxBoard.makeMove(m, side);
			//auxBoard.displayBoard();
			root.addChild(new TreeNode(auxBoard));
		}
		
		if(maxPlayer) {
			
			double maxEval = Double.NEGATIVE_INFINITY;
			if (!root.getChildren().isEmpty()) {
				for (TreeNode child: root.getChildren()) {
					double eval = minimaxPruning(child, changePlayer(side), depth-1, !maxPlayer, alpha, beta);
					maxEval = Double.max(eval, maxEval);
					alpha = Double.max(alpha, eval);
					if ( beta <= alpha ) {
						break;
					}
				}
			}
			return maxEval;
		}
		else {
			
			double minEval = Double.POSITIVE_INFINITY;
			if (!root.getChildren().isEmpty()) {
				for (TreeNode child: root.getChildren()) {
					double eval = minimaxPruning(child, changePlayer(side), depth-1, !maxPlayer, alpha, beta);
					minEval = Double.min(eval, minEval);
					beta = Double.min(beta, eval);
					if ( beta <= alpha ) {
						break ;
					}
				}
			}
			return minEval;
		}
	}
	
	public double getBoardEvaluation(Board board) {
		double blackScore = board.getNbrNormalBlackCheckers() + board.getNbrBlackKingCheckers() * 1.7 +
				board.getNbrBlackKnightCheckers() * 2 ;
		double redScore = board.getNbrNormalRedCheckers() + board.getNbrRedKingCheckers() * 1.7 +
				board.getNbrRedKnightCheckers() * 2 ;
		if (getSide() == Enum.PlayerSide.BLACK) {
			return blackScore - redScore + board.getNbrBlackCheckers();
		}
		return redScore - blackScore + board.getNbrRedCheckers();
	}
	
	public Enum.PlayerSide changePlayer(Enum.PlayerSide side) {
		if (side == Enum.PlayerSide.BLACK) {
			return Enum.PlayerSide.RED;
		} 
		return Enum.PlayerSide.BLACK; 
	}

	
	
	
}
