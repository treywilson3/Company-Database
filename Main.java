import java.io.Console;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Trey, Farris, Casey Main will determine which class to make. The user
 *         is given 3 choices: Member, Employee, Guest Whichever is chosen is
 *         where they will go. Class main will also verify member or employee
 *         credentials by calling verfication functions in Class Database.
 */
public class Main {

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// Console console = System.console();
	// boolean verify = true;
	// while (verify) {
	// // Ask questions
	// Scanner in = new Scanner(System.in);
	// System.out.println("1: Member Functions");
	// System.out.println("2: Employee Functions");
	// System.out.println("3: Guest Functions");
	// int userMainMenuChoice = in.nextInt();
	// // Member
	// if (userMainMenuChoice == 1) {
	// String memberEmail, memberPassword;
	// System.out.println("Enter your Email: ");
	// memberEmail = in.next();
	// System.out.println("Enter your Password: ");
	//// If in IDE you have to use bottom
	//// memberPassword = in.next();
	//// if (Database.verifiedMember(memberEmail, memberPassword) ==
	//// true) {
	//// new MemberFunctions(memberEmail, memberPassword);
	//// verify = false;
	//// } else {
	//// System.out.println("Invalid User Info");
	//// }
	// // If using console you can use below
	// char memberArray[] = console.readPassword("Enter your secret password:");
	// memberPassword = new String(memberArray);
	// if (Database.verifiedMember(memberEmail, memberPassword) == true) {
	// new MemberFunctions(memberEmail, memberPassword);
	// verify = false;
	// } else {
	// System.out.println("Invalid User Info");
	// }
	// // Employee
	// } else if (userMainMenuChoice == 2) {
	// String employeeEmail, employeePassword;
	// System.out.println("Enter your Email: ");
	// employeeEmail = in.next();
	// System.out.println("Enter your Password: ");
	// // If using IDE use below
	//// employeePassword = in.next();
	//// if (Database.ceo(employeeEmail, employeePassword) == true) {
	//// new CEOFunctions(employeeEmail, employeePassword);
	//// verify = false;
	//// } else if (Database.verifiedEmployee(employeeEmail,
	//// employeePassword) == true) {
	//// new EmployeeFunctions(employeeEmail, employeePassword);
	//// verify = false;
	//// } else {
	//// System.out.println("Invalid Employee Info");
	//// }
	//// If using command line use below
	// char employeeArray[] = console.readPassword("Enter your secret
	// password:");
	// employeePassword = new String(employeeArray);
	// if (Database.ceo(employeeEmail, employeePassword) == true) {
	// new CEOFunctions(employeeEmail, employeePassword);
	// verify = false;
	// } else if (Database.verifiedEmployee(employeeEmail, employeePassword) ==
	// true) {
	// new EmployeeFunctions(employeeEmail, employeePassword);
	// verify = false;
	// } else {
	// System.out.println("Invalid Employee Info");
	// }
	// // Guest
	// } else if (userMainMenuChoice == 3) {
	// new Guest();
	// } else {
	// System.out.println("Invalid Input");
	// }
	// }
	// }
	public static void main(String[] args) {
		Console console = System.console();
		boolean verify = true;
		while (verify) {
			try {
				// Ask questions
				Scanner in = new Scanner(System.in);
				System.out.println("1: Sign In");
				System.out.println("2: Guest");
				int userMainMenuChoice = in.nextInt();
				if (userMainMenuChoice == 1) {
					String email, password;
					System.out.println("Enter your Email: ");
					email = in.next();
					// If in IDE you have to use bottom
					// System.out.println("Enter your Password: ");
					// password = in.next();
					// if (Database.verifiedMember(email, password) == true) {
					// new MemberFunctions(email, password);
					// verify = false;
					// } else if (Database.ceo(email, password) == true) {
					// new CEOFunctions(email, password);
					// verify = false;
					// } else if (Database.verifiedEmployee(email, password) ==
					// true) {
					// new EmployeeFunctions(email, password);
					// verify = false;
					// } else {
					// System.out.println("Invalid Info");
					// }
					// If using console you can use below
					char memberArray[] = console.readPassword("Enter your password: ");
					password = new String(memberArray);
					if (Database.verifiedMember(email, password) == true) {
						new MemberFunctions(email, password);
						verify = false;
					} else if (Database.ceo(email, password) == true) {
						new CEOFunctions(email, password);
						verify = false;
					} else if (Database.verifiedEmployee(email, password) == true) {
						new EmployeeFunctions(email, password);
						verify = false;
					} else {
						System.out.println("Invalid Info");
					}
					// Guest
				} else if (userMainMenuChoice == 2) {
					new Guest();
					verify = false;
				} else {
					System.out.println("Invalid Input");
				}
			} catch (InputMismatchException e) {
				System.out.println("Wrong Input");
				System.out.println("You need to enter the correct integer");
			}
		}
	}
}
