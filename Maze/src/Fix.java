import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Fix{
	public static int num = 0;
	public static void main(String[] args) throws FileNotFoundException{
		// TODO Auto-generated method stub

		while (1 == 1) {
			Scanner in = new Scanner(new File("C:/Users/s-lame/OneDrive - Bellevue School District/Eclipse/Maze/Board/" + num + ".txt"));
			int[][] board = new int[20][20];
			for(int y = 0; y < board.length; y++) {
				String temp = in.nextLine();
				System.out.println(temp);
				Scanner lineScanner = new Scanner(temp).useDelimiter(",");
				for(int x = 0; x < board.length; x++) {
					board[y][x] = Integer.parseInt(lineScanner.next());
				}
			}
			
			try {
				// print board to file
				PrintStream out = new PrintStream(
						new File("C:/Users/s-lame/OneDrive - Bellevue School District/Eclipse/Maze/Board/" + num + ".txt"));
				for (int[] y : board) {
					String num = "";
					for (int x : y) {
						if(x >= 0) {
							num+= "" + 0 + ",";
						} else {
							num += "" + x + ",";
						}

					}
					out.println(num.substring(0, num.length() - 1));
				}
				out.println("\n");
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
				System.exit(1);
			}
			num++;
		}
	}
}