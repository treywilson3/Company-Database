import java.io.Console;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Trey, Farris, Casey Guest is the class where a guest can use some
 *         functionalities of the program. They can serach the database for any
 *         movie, get a random movie presented to them, or sign up for an
 *         account. Try/Catch loops are implemented in some methods to catch
 *         illegal input (when integer required but user gives string).
 */
public class Guest {

	public Guest() {
		// Present guest with choices
		choices();
	}

	/**
	 * choices() will present the guest with 3 choices and call whichever method
	 * is needed
	 */
	public void choices() {
		System.out.println("Welcome Guest!");
		System.out.println();
		while (true) {
			try {
				int guestChoice;
				Scanner in = new Scanner(System.in);
				System.out.println();
				System.out.println("1: Search for any movie in our Database");
				System.out.println("2: We will present you with a random movie!");
				System.out.println("3: Create an Account");
				System.out.println("4: Quit");

				guestChoice = in.nextInt();
				if (guestChoice == 1) {
					search();
				} else if (guestChoice == 2) {
					randomMovie();
				} else if (guestChoice == 3) {
					createAccount();
				} else if (guestChoice == 4){
					System.exit(0);
				} else {
					System.out.println("Invalid Input");
				}
			} catch (InputMismatchException e) {
				System.out.println("Wrong Input");
				System.out.println("You need to enter the correct integer");
			}
		}
	}

	/**
	 * If search() is chosen, give user opportunity to see all movie info Or let
	 * them search whatever they want to.
	 */
	public void search() {
		try {
			Scanner search = new Scanner(System.in);
			System.out.println("1: Present all Movies");
			System.out.println("2: Search any Title, Genre, or Rating");
			int searchChoice = search.nextInt();
			if (searchChoice == 1) {
				// Call Database class
				Database.memberMovieView();
			} else if (searchChoice == 2) {
				Scanner searchString = new Scanner(System.in);
				System.out.println("Enter what you want to be searched");
				String searchData = searchString.nextLine();
				// Call Database class
				Database.search(searchData);
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}
	}

	/**
	 * When called, randomMovie() will call class Databse to get a random movie
	 */
	public void randomMovie() {
		Database.randomMovie();
	}

	/**
	 * createAccount() will let the user create an account so they can become a
	 * Member
	 */
	public void createAccount() {
		String email;
		String password;
		String fname;
		String lname;
		String phone;
		String address;
		String city;
		String state;
		String zip;

		Scanner smemberEmail = new Scanner(System.in);
		System.out.println("Enter Email: ");
		email = smemberEmail.next();

		Console console = System.console();
		char memberArray[] = console.readPassword("Enter your password: ");
		password = new String(memberArray);

		Scanner sfname = new Scanner(System.in);
		System.out.println("Enter First Name: ");
		fname = sfname.next();

		Scanner slname = new Scanner(System.in);
		System.out.println("Enter Last Name: ");
		lname = slname.next();

		Scanner sphone = new Scanner(System.in);
		System.out.println("Enter Phone Number: ");
		phone = sphone.nextLine();

		Scanner saddress = new Scanner(System.in);
		System.out.println("Enter Address: ");
		address = saddress.nextLine();

		Scanner scity = new Scanner(System.in);
		System.out.println("Enter city: ");
		city = scity.nextLine();

		Scanner sstate = new Scanner(System.in);
		System.out.println("Enter State (Example Format: NC): ");
		state = sstate.next();

		Scanner szip = new Scanner(System.in);
		System.out.println("Enter Zip (5 Digit): ");
		zip = szip.next();
		// Call Database class
		Database.newUser(email, password, fname, lname, phone, address, city, state, zip);
		System.out.println("Welcome to Old Potato!");
		System.out.println("Please restart the program to login");
	}

}
