import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that determines the solutions to the queens problem based on the number
 * of queens the user selects
 * @author Peter Warren on 2/21/16
 *
 */
public class NQueensSolver {

	/**
	 * Determine and add the n queen position solutions to the given list of solutions using
	 * backtracking
	 * @param n - number of queens
	 * @param qIndex - index number of the current queen
	 * @param qPositions - array of the current positions of the queens on the board
	 * @param solutions - list of all complete solutions
	 */
	public static void solve(int n, int qIndex, int [] qPositions, ArrayList<int []> solutions){
		
		if (!(qPositions[n - 1] == 0)){
			solutions.add(qPositions.clone()); // add solution to solution set
		}
		
		else {
			for (int pos = 1; pos <= n; pos++){
				if (validPosition(pos, qIndex, qPositions)){
					qPositions[qIndex] = pos;
					solve(n, qIndex + 1, qPositions, solutions);
					qPositions[qIndex] = 0;
				}
			}
		}
	}
	
	/**
	 * Determine if the position of the current queen is valid relative to the queens placed
	 * before it
	 * @param pos - position of current queen
	 * @param qIndex - index of the current queen
	 * @param qPositions - array of current positions of queens on the board
	 * @return - true if current position of the current queen is valid
	 */
	private static boolean validPosition(int pos, int qIndex, int [] qPositions){
		for (int oldQIndex = 0; oldQIndex < qIndex; oldQIndex++){
			if (qPositions[oldQIndex] == pos){
				return false;
			}
			
			if (Math.abs(pos - qPositions[oldQIndex]) == Math.abs(qIndex - oldQIndex)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Create interface that allows user to select n queens for n queens problem
	 * If n is less than or equal to 8, display the solutions.
	 * If n is greater than 8 display the number of solutions.
	 */
	public static void main(String [] args){
		ArrayList<int []> solutions = new ArrayList<int []>();
		Scanner input = new Scanner(System.in);
		int n = 1;
		
		do {
			System.out.println("Enter number of queens: ");
			n = input.nextInt();
			if (n > 0){
				int [] qPositions = new int[n];
				solve(n, 0, qPositions, solutions);
				if (n <= 8){
					System.out.println(solutions.size() + " solutions:");
					for (int [] solution : solutions){
						String sol = "[";
						for (int pos : solution){
							sol += pos + ", ";
						}
						sol = sol.substring(0, sol.lastIndexOf(",")) + "]";
						System.out.println(sol);
					}
				}
				else {
					System.out.println(solutions.size() + " solutions.");
				}
				solutions.clear();
			}
		}
		while (n > 0);
	}
}
