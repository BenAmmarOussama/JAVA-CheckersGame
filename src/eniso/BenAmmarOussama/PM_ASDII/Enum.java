package eniso.BenAmmarOussama.PM_ASDII;

public class Enum {
	
	public enum PlayerSide { RED, BLACK }
	
	public enum Difficulty { EASY, MEDIUM, DIFFICULT }
	
	public enum GameMode { SinglePlayer, TwoPlayer }
	
	public enum MoveStatus { SUCCESSFUL, ADDITIONAL_JUMP, MOVE_FAILED }
	
	public enum PieceType { EMPTY, 
							RED, RED_KING,  RED_KNIGHT,
							BLACK, BLACK_KING, BLACK_KNIGHT }
	
}
