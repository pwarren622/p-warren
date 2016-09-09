/**
 * AutoPricer.java
 * 
 * Automates pricing for a t-shirt order given order size, various costs, and a chosen 
 * percentage mark-up. Assumes user enters valid data.
 * 
 * @author Peter Warren
 */

import java.text.DecimalFormat;
import java.util.Scanner;
public class AutoPricer {
	
	/**
	 * A matrix of per item printing costs ($) dependent on both the number of colors in 
	 * the print(rows) and the size of the order (columns) (split into small (1-19 shirts) 
	 * medium (20-39 shirts) and large (40+ shirts).
	 * Higher row numbers correspond with more colors and therefore higher per item print costs.
	 * Higher column numbers correspond with larger order size and therefore smaller per item
	 * print costs.
	 * Note: matrix simplified for demonstration purposes.
	 */
	private static double [][] PRINTING_MATRIX = {{3, 2, 1}, {4, 3, 2}, {5, 4, 3}};
	
	/**
	 * Cost and revenue instance variables
	 */
	private double totalCost, costPerItem, totalOrderRevenue, revPerItem;
	
	/**
	 * Constructs new pricer for the order with specified size, qualities and costs
	 * @param numShirts - the quantity of shirts in the order (also the number of prints)
	 * @param costPerShirt - the cost of the physical shirt
	 * @param numColorsFront - the number of colors in the print on the front of the shirt
	 * @param numColorsBack - the number of colors in the print on the back of the shirt
	 * @param shippingCost - cost to ship shirts to printer
	 * @param setUpCost - cost to set up the screen printing 
	 */
	public AutoPricer(int numShirts, double costPerShirt, int numColorsFront,
			int numColorsBack, double shippingCost, double setUpCost){
		
		/**
		 * Find column of print matrix (0/1/2) corresponding to order size (small/medium/large)
		 */
		int numShirtsCol = AutoPricer.getNumShirtCol(numShirts); 
		
		/**
		 * Matrix rows corresponding to number of colors in front and back prints
		 * Row of -1 indicates a print of zero colors (no print)
		 */
		int numColorsFrontRow = numColorsFront - 1;
		int numColorsBackRow = numColorsBack - 1;
		
		/**
		 * Find front and back printing costs for individual shirt
		 */
		double frontPrintCost = AutoPricer.getPrintCost(numShirtsCol, numColorsFrontRow); 
		double backPrintCost = AutoPricer.getPrintCost(numShirtsCol, numColorsBackRow);
		
		/**
		 * Find total front and back printing costs and total cost of the physical shirts
		 */
		double totalFrontCost = numShirts * frontPrintCost;
		double totalBackCost = numShirts * backPrintCost;
		double totalShirtCost = numShirts * costPerShirt;
		
		
		/**
		 * Find total cost of producing the order
		 */
		totalCost = totalFrontCost + totalBackCost + totalShirtCost + shippingCost
				+ setUpCost;
		costPerItem = totalCost / numShirts; // cost of producing individual shirt
		totalOrderRevenue = totalCost; // total revenue = total cost when there is no markup
		revPerItem = costPerItem; // revenue per item = cost per item when there is no markup
	}
	
	/**
	 * Determine the revenue of one shirt given a markup (also the shirt price)
	 * @param markUp - entered as a percentage of the item's cost
	 * @return - revPerItem - the revenue from one shirt (the shirt price)
	 */
	public double getRevenuePer(double markUp){
		double pricePer = costPerItem * (1 + (markUp / 100));
		revPerItem = pricePer;
		return revPerItem;	
	}
	
	/**
	 * Determine the total revenue of the order (also the order price)
	 * @param markUp - entered as a percentage of the order's cost
	 * @return - totalOrderRevenue - the total revenue from the order (order price)
	 */
	public double getTotalRevenue(double markUp){
		double totalPrice = totalCost * (1 + (markUp / 100));
		totalOrderRevenue = totalPrice;
		return totalOrderRevenue; 
	}
	
	/**
	 * Get the total cost of the order
	 * @return - totalCost - total order cost
	 */
	public double getTotalCost(){
		return totalCost;
	}
	
	/**
	 * Find the total profits of the order
	 * @return - profits (revenue - cost)
	 */
	public double getTotalProfits(){
		return (totalOrderRevenue - totalCost);
	}
	
	/**
	 * Helper method to find the appropriate column of PRINTING_MATRIX based on the 
	 * size of the order. Column 1: small (0-19). Column 2: medium (20-39).
	 * Column 3: large (40+)
	 * @param numShirts - number of shirts in the order
	 * @return - numShirtCol - printing matrix column corresponding to order size
	 */
	private static int getNumShirtCol(int numShirts){
		int numShirtCol;
		if (numShirts < 20){
			numShirtCol = 0;
		}
		else if (numShirts < 40){
			numShirtCol = 1;
		}
		else {
			numShirtCol = 2;
		}
		return numShirtCol;
	}
	
	/**
	 * Helper method to find printing cost per item based on the size of the printing job 
	 * and number of colors in the print
	 * @param numShirtsCol - column of printing matrix correspond to size of the printing job
	 * @param numColorsRow - row of printing matrix corresponding to number of colors in print
	 * @return - printCost - print cost per item
	 */
	private static double getPrintCost(int numShirtsCol, int numColorsRow){
		double printCost;
		if (numColorsRow > -1){
			printCost = PRINTING_MATRIX[numColorsRow][numShirtsCol];
		}
		else {
			printCost = 0;
		}
		return printCost;
	}
	
	/**
	 * Create user interface. Assumes user inputs valid data.
	 */
	public static void main(String args[]){
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		DecimalFormat df = new DecimalFormat("0.00"); // to convert values to $#.##
	
		int numShirts, numColorsFront, numColorsBack;
		double costPerShirt, shippingCost, setupCost, markUp;
		
		System.out.println("Number of shirts (must be positive integer): ");
		numShirts = input.nextInt();
		
		System.out.println("Cost per shirt ($): ");
		costPerShirt = input.nextDouble();
		
		System.out.println("Number of colors on front (integer 0-3): ");
		numColorsFront = input.nextInt();
		
		System.out.println("Number of colors on back (integer 0-3): ");
		numColorsBack = input.nextInt();
		
		System.out.println("Setup cost ($): ");
		setupCost = input.nextDouble();
		
		System.out.println("Shipping cost ($): ");
		shippingCost = input.nextDouble();
		
		AutoPricer pricer = new AutoPricer(numShirts, costPerShirt, numColorsFront,
				numColorsBack, shippingCost, setupCost);
		
		char newMarkup = 'y'; 
		
		while (newMarkup != 'n') { // execute if user wants to test another markup
			System.out.println("Markup? (%): ");
			markUp = input.nextDouble();
			
			
			/**
			 * Print revenue, cost, and profit data under current order assumptions
			 */
			System.out.println("Price per shirt: $" + df.format(pricer.getRevenuePer(markUp)) +
					" | Total order revenue: $" + df.format(pricer.getTotalRevenue(markUp)) + 
					" | Total order cost: $" + df.format(pricer.getTotalCost()) +
					" | Total profits: $" + df.format(pricer.getTotalProfits()));
			System.out.println("New markup? ('y' or 'n'): ");
			
			input.nextLine();
			newMarkup = input.nextLine().charAt(0);
		}
	}
}
