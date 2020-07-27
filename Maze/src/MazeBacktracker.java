import java.util.ArrayList;

public class MazeBacktracker {
	static boolean checkData = false;
	static final int sampleSize = 200;

	public static int[][] backtrack(int[][] board) {
		ArrayList<Integer> bestPath = new ArrayList<Integer>();
		for (int numIteration = 0; numIteration < sampleSize; numIteration++) {
			System.out.println(numIteration + "/" + sampleSize);
			ArrayList<Integer> visited = new ArrayList<>();
			ArrayList<Integer> path = new ArrayList<>();
			ArrayList<pathData> boardLayout = new ArrayList<>();
			int startPath = -1;
			int endPath = -1;

			// create each path option
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[y].length; x++) {
					boardLayout.add(new pathData(num(y, x, board), board));
				}
			}
			// check data

			if (checkData) {
				for (int i = 0; i < boardLayout.size(); i++) {
					System.out.print(boardLayout.get(i).pathNum() + ": " + boardLayout.get(i).wall() + " : ");
					for (int j = 0; j < boardLayout.get(i).pathOptions.size(); j++) {
						System.out.print(boardLayout.get(i).pathOptions.get(j) + " ");
					}
					System.out.println();

				}
			}

			// find start and end
			for (int i = 0; i < boardLayout.size(); i++) {
				if (boardLayout.get(i).start()) {
					startPath = i;
					// put path in path and visited list
					path.add(i);
					visited.add(i);
				} else if (boardLayout.get(i).end()) {
					endPath = i;
				}
			}
			// while end of path does not equal endPath num
			boolean impossible = false;
			while (!impossible && path.get(path.size() - 1) != endPath) {
				// System.out.println(path);
				// if path doesn't end, find another path
				if (!currentPathEnd(path, boardLayout)) {
					// if found path
					int pickedPath = pickRandomPath(path, boardLayout, visited);
					if (pickedPath != -1) {
						visited.add(pickedPath);
						path.add(pickedPath);
						// else remove and backtrack
					} else {
						// System.out.println("backtrack " + path.get(path.size() - 1));
						path.remove(path.size() - 1);
					}
					// else remove from path, but keep as visited
				} else {
					// System.out.println("backtrack " + path.get(path.size() - 1));
					path.remove(path.size() - 1);

				}
				// check for impossible maze
				if (path.size() - 1 == -1) {
					System.out.println();
					System.out.println("impossible maze");
					impossible = true;
				}
			}

			//if smaller than best path, or best path has no spots
			if(bestPath.size() == 0 || path.size() < bestPath.size()) {
				bestPath = path;
			}
		}
		int[][] visualizeBoard = board;

		// replace 1's with -1s
		/*for (int i = 0; i < visualizeBoard.length; i++) {
			for (int j = 0; j < visualizeBoard[i].length; j++) {
				if (visualizeBoard[i][j] == 1) {
					visualizeBoard[i][j] = -1;
				}
			}
		}*/

		// draw from 2 to end +1 each time
		int count = 0;
		for (int i : bestPath) {
			visualizeBoard[i / board.length][i % board.length] = count;
			count++;
			// visualizeBoard[i / board.length][i % board.length]++;
		}
		// set endpoint as -2
		if (bestPath.size() != 0) {
			visualizeBoard[bestPath.get(bestPath.size() - 1) / board.length][bestPath.get(bestPath.size() - 1) % board.length] = -2;
			//set startpoint as -3
			visualizeBoard[bestPath.get(0) / board.length][bestPath.get(0) % board.length] = -3;
		}
		/*
		 * for (int i = 0; i < visualizeBoard.length; i++) { System.out.println();
		 * System.out.println(); System.out.println(); for (int j = 0; j <
		 * visualizeBoard[i].length; j++) { System.out.print("\t" + visualizeBoard[i][j]
		 * + " "); } }
		 * 
		 * System.out.println(); System.out.println("completed in " + path.size() +
		 * " steps");
		 */

		return visualizeBoard;

	}

	public static boolean currentPathEnd(ArrayList<Integer> path, ArrayList<pathData> boardLayout) {
		// System.out.println(boardLayout.get(path.get(path.size() -
		// 1)).pathOptions.size());
		return boardLayout.get(path.get(path.size() - 1)).pathOptions.size() < 1;
	}

	public static int pickRandomPath(ArrayList<Integer> path, ArrayList<pathData> boardLayout,
			ArrayList<Integer> visited) {
		// test if valid path exists
		boolean exists = false;
		// check every option if path exists
		for (int i = 0; i < boardLayout.get(path.get(path.size() - 1)).pathOptions.size(); i++) {
			// if current option doesn't exist in visited
			if (!visited.contains(boardLayout.get(path.get(path.size() - 1)).pathOptions.get(i))) {
				exists = true;
				// System.out.println("checked " + boardLayout.get(path.get(path.size() -
				// 1)).pathOptions.get(i));
			}
		}
		// if exists, use path
		if (exists) {
			int ran = (int) (Math.random() * boardLayout.get(path.get(path.size() - 1)).pathOptions.size());
			while (visited.contains(boardLayout.get(path.get(path.size() - 1)).pathOptions.get(ran))) {
				ran = (int) (Math.random() * boardLayout.get(path.get(path.size() - 1)).pathOptions.size());
				// System.out.println("stuck");
			}
			// System.out.println("ran " + ran + " array " +
			// boardLayout.get(path.get(path.size() - 1)).pathOptions);
			int pathPicked = (boardLayout.get(path.get(path.size() - 1)).pathOptions.get(ran));
			boardLayout.get(path.get(path.size() - 1)).pathOptions.remove(ran);

			return pathPicked;
		} else {
			return -1;
		}
	}

	public static int num(int y, int x, int[][] board) {
		return y * board.length + x;
	}

	public static int findValue(int boardNum, int[][] board) {
		if (boardNum < 0) {
			return 1;
		} else {
			// System.out.println(boardNum);
			return board[(int) (boardNum / board.length)][boardNum % (board[0].length)];
		}

	}

	public static void displayResult(int[][] board, ArrayList<Integer> path) {

	}
}
