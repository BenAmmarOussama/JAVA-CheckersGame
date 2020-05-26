package eniso.BenAmmarOussama.PM_ASDII;

import java.util.ArrayList;

// this class represents the tree data structure that will be used in the minimax algorithm
public class TreeNode {
	
		private Board BoardStatus; // the value of the node
		private ArrayList<TreeNode> children; // the children of the node
		
		public TreeNode(Board b) {
			this.BoardStatus = b;
			children = new ArrayList<TreeNode>();
		}

		public Board getBoardStatus() {
			return BoardStatus;
		}

		public ArrayList<TreeNode> getChildren() {
			return children;
		}
		
		
		public void addChild(TreeNode child) {
			this.children.add(child);
		}
	
}
