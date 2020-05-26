package eniso.BenAmmarOussama.PM_ASDII;

public class HumanPlayer extends Player {
	
	private static final long serialVersionUID = 6529685098267757690L;

	public HumanPlayer(Enum.PlayerSide side) {
		super(side);
	}
	
	public HumanPlayer(Enum.PlayerSide side, String name) {
		super(side, name);
	}
	
	public Enum.MoveStatus makeMove(Move move, Board board) {
		return board.makeMove(move, side);
	}
}
