import java.util.ArrayList;

public class pathData {
	private boolean start = false;
	private boolean end = false;
	private int num;
	private boolean wall;
	public ArrayList<Integer> pathOptions = new ArrayList<>();
	
	public pathData(int num, int[][] board) {
		this.num = num;
		//test if wall
		if(MazeBacktracker.findValue(num, board) == -1) {
			wall = true;
		} else {
			//else check connected paths
			wall= false;
			pathOptions = check(board);
			
		}
		//test if start
		if(MazeBacktracker.findValue(num, board) == -2) {
			start = true;
		}else if(MazeBacktracker.findValue(num, board) == -3) {
			//test if end
			end = true;
		}
		
	}
	
	public int pathNum() {
		return num;
	}
	
	public boolean wall() {
		return wall;
	}
	public boolean start() {
		return start;
	}
	public boolean end() {
		return end;
	}
	
	private ArrayList<Integer> check(int[][] board) {
		ArrayList<Integer> pathsFound = new ArrayList<Integer>();
		//check up
		if(num-board[0].length >= 0) {
			if(MazeBacktracker.findValue(num-board[0].length, board) == 0 || MazeBacktracker.findValue(num-board[0].length, board) == -2 || MazeBacktracker.findValue(num-board[0].length, board) == -3) {
				pathsFound.add(num-board[0].length);
			}
		}
		//check left
		if(num%(board[0].length) != 0) {
			if(MazeBacktracker.findValue(num-1, board) == 0 || MazeBacktracker.findValue(num-1, board) == -2 || MazeBacktracker.findValue(num-1, board) == -3) {
				pathsFound.add(num-1);
			}
		}
		//check right
		if(num%(board[0].length) != board[0].length-1) {
			if(MazeBacktracker.findValue(num+1, board) == 0 || MazeBacktracker.findValue(num+1, board) == -2 || MazeBacktracker.findValue(num+1, board) == -3) {
				pathsFound.add(num+1);
			}
		}
		//check below
		if(num+board[0].length < board.length*board[0].length) {
			if(MazeBacktracker.findValue(num+board[0].length, board) == 0 || MazeBacktracker.findValue(num+board[0].length, board) == -2 || MazeBacktracker.findValue(num+board[0].length, board) == -3) {
				pathsFound.add(num+board[0].length);
			}
		}
		return pathsFound;
	}
}
