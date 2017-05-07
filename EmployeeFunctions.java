import java.io.Console;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

/**
 * 
 */

/**
 * @author Trey, Farris, Casey Employees Class needs to be verified before it
 *         can be instantiated Meaning only verified employees can access this
 *         class EmployeesFunctions does not have nearly as much power as the
 *         CEOFunctions Because they do not need the access that the CEO needs.
 *         Try/Catch loops are implemented in some methods to catch illegal
 *         input (when integer required but user gives string).
 */
public class EmployeeFunctions {
	private String employeeEmail, employeePassword;

	public EmployeeFunctions(String employeeEmail, String employeePassword) {
		this.employeeEmail = employeeEmail;
		this.employeePassword = employeePassword;
		// if verified run choices
		choices();
	}

	/**
	 * Choices lets employees decide what they want to do and will call
	 * whichever method is needed.
	 */
	public void choices() {
		System.out.println("Welcome!");
		System.out.println();
		while (true) {
			try {
				int employeeChoice;
				Scanner in = new Scanner(System.in);
				System.out.println();
				System.out.println("1: Add/Delete Functions");
				System.out.println("2: Business Stats");
				System.out.println("3: Settings");
				System.out.println("4: Exit");

				employeeChoice = in.nextInt();
				if (employeeChoice == 1) {
					addFunctions();
				} else if (employeeChoice == 2) {
					businessStats();
				} else if (employeeChoice == 3) {
					settings();
				} else if (employeeChoice == 4) {
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
	 * addFunctions is one of the choices(). It is where all adding and deleting
	 * functions can be chosen.
	 */
	public void addFunctions() {
		try {
			Scanner sfunctions = new Scanner(System.in);
			System.out.println("1: Add/Delete Movies");
			System.out.println("2: Add/Delete Members");
			System.out.println("3: Add/Delete Actors");
			int functions = sfunctions.nextInt();
			if (functions == 1) {
				addDeleteMovies();
			} else if (functions == 2) {
				addDeleteMembers();
			} else if (functions == 3) {
				addDeleteActors();
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}
	}

	/**
	 * businessStats() is where the employees can choose to find out any stored
	 * information about the business.
	 */
	public void businessStats() {
		try {
			Scanner sbusinessStats = new Scanner(System.in);
			System.out.println("1: Movie Stats");
			System.out.println("2: Company Stats");
			int businessStats = sbusinessStats.nextInt();
			if (businessStats == 1) {
				movieStats();
			} else if (businessStats == 2) {
				companyStats();
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}
	}

	/**
	 * Movie stats is a choice from business stats employees is given choices to
	 * learn more info about movie stats
	 */
	public void movieStats() {
		try {
			Scanner smovieStats = new Scanner(System.in);
			System.out.println("1: Movies that HAVE NOT been watched");
			System.out.println("2: Movies that HAVE been Watched AND Favorited by members");
			System.out.println("3: Movies that have been Watched AND/OR Favorited by members");
			int movieStats = smovieStats.nextInt();
			if (movieStats == 1) {
				Database.moviesNotWatched();
			} else if (movieStats == 2) {
				Database.moviesWatchedAndFavorited();
			} else if (movieStats == 3){
				Database.combinedWatchedandFavorited();
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}
	}

	/**
	 * companyStats() is a choice from business stats employees is given choices
	 * to learn more about the business
	 */
	public void companyStats() {
		try {
			Scanner scompanyStats = new Scanner(System.in);
			System.out.println("1: How many members are there");
			System.out.println("2: How many employees are there");
			System.out.println("3: How many movies are there");
			System.out.println("4: All Employees Information");
			int companyStats = scompanyStats.nextInt();
			if (companyStats == 1) {
				Database.getMembers();
			} else if (companyStats == 2) {
				Database.getEmployees();
			} else if (companyStats == 3) {
				Database.getAllMovies();
			} else if (companyStats == 4) {
				Database.employeeView();
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}
	}

	/**
	 * addDeleteActors will allow the employees to either add or delete Actors,
	 * or to add an actor to an existing movie.
	 */
	public void addDeleteActors() {
		try {
			Scanner choice1 = new Scanner(System.in);
			System.out.println("1: Add New Actor");
			System.out.println("2: Add Actor to Movie");
			System.out.println("3: Delete Actor");
			int decision = choice1.nextInt();
			if (decision == 1) {
				addActor();
			} else if (decision == 2) {
				addActorToMovie();
			} else if (decision == 3) {
				deleteActor();
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}

	}

	/**
	 * deleteActor() is called from addDeleteActors and will allow the employees
	 * to delete an actor
	 */
	public void deleteActor() {
		Scanner dactorID = new Scanner(System.in);
		System.out.println("Enter Actor ID to delete char(5)");
		String deleteActorID = dactorID.next();
		Database.deleteActor(deleteActorID);
	}

	/**
	 * addActor() is called from addDeleteActors and will allow the employees to
	 * add an actor
	 */
	public void addActor() {
		Scanner sactorID = new Scanner(System.in);
		System.out.println("Enter Actor ID char(5)");
		String actorID = sactorID.next();

		Scanner sactorFname = new Scanner(System.in);
		System.out.println("Enter Actor Fname");
		String actorFname = sactorFname.next();

		Scanner sactorLname = new Scanner(System.in);
		System.out.println("Enter Actor Lname");
		String actorLname = sactorLname.next();
		Database.addActor(actorID, actorFname, actorLname);
	}
	/**
	 * add an actor id to a movie id
	 */
	public void addActorToMovie() {
		Scanner movie_id = new Scanner(System.in);
		Scanner actor_id = new Scanner(System.in);
		String m_id, a_id;
		System.out.println("Enter Movie ID: ");
		m_id = movie_id.nextLine();
		System.out.println("Enter Actor ID: ");
		a_id = actor_id.nextLine();
		Database.addActorToMovie(m_id, a_id);
	}

	/**
	 * addDeleteMovies() gives employees choice to either add or delete a movie
	 * and then calls the correct method.
	 */
	public void addDeleteMovies() {
		try {
			int employeeChoice;
			Scanner in = new Scanner(System.in);
			System.out.println("1: Add Movie");
			System.out.println("2: Delete Movies");

			employeeChoice = in.nextInt();
			if (employeeChoice == 1) {
				addMovie();
			} else if (employeeChoice == 2) {
				deleteMovie();
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}

	}

	/**
	 * addMovie() is called from addDeleteMovies() and will add movie once
	 * employees gives all information.
	 */
	public void addMovie() {
		// add movie
		Scanner smovieID = new Scanner(System.in);
		System.out.println("Enter Movie ID char(5): ");
		String movieID = smovieID.next();
		// smovieID.close();

		Scanner stitle = new Scanner(System.in);
		System.out.println("Enter Title varchar(40): ");
		String title = stitle.nextLine();
		// stitle.close();

		Scanner srating = new Scanner(System.in);
		System.out.println("Enter Rating int(11): ");
		String rating = srating.nextLine();
		// srating.close();

		Scanner sgenre = new Scanner(System.in);
		System.out.println("Enter Genre varchar(15): ");
		String genre = sgenre.nextLine();
		// sgenre.close();

		Scanner sreleaseDate = new Scanner(System.in);
		System.out.println("Enter Release Date char(4): ");
		String releaseDate = sreleaseDate.nextLine();
		// sreleaseDate.close();
		Database.addMovie(movieID, title, rating, genre, releaseDate);
	}

	/**
	 * deleteMovie() is called from addDeleteMovies() and will add movie once
	 * employees gives all information.
	 */
	public void deleteMovie() {
		Scanner delete = new Scanner(System.in);
		System.out.println("Enter Movie ID to be deleted char(5): ");
		String deleteMovieID = delete.next();
		Database.deleteMovie(deleteMovieID);
	}

	/**
	 * addDeleteMembers() gives employees choice to add or delete members and
	 * will then call the correct method
	 */
	public void addDeleteMembers() {
		try {
			int empChoice;
			Scanner empScanner = new Scanner(System.in);
			System.out.println("1: Add Member");
			System.out.println("2: Delete Member");
			empChoice = empScanner.nextInt();
			if (empChoice == 1) {
				addMember();
			} else if (empChoice == 2) {
				deleteMember();
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}

	}

	/**
	 * addMember() is called from addDeleteMember() and will allow the employees
	 * to add a member.
	 */
	public void addMember() {
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
		System.out.println("Enter Member Email varchar(50): ");
		email = smemberEmail.next();

		Scanner smemberPassword = new Scanner(System.in);
		System.out.println("Enter Member Password varchar(200)");
		password = smemberPassword.next();

		Scanner sfname = new Scanner(System.in);
		System.out.println("Enter First Name var(20): ");
		fname = sfname.next();

		Scanner slname = new Scanner(System.in);
		System.out.println("Enter Last Name var(20): ");
		lname = slname.next();

		Scanner sphone = new Scanner(System.in);
		System.out.println("Enter Phone Number var(20): ");
		phone = sphone.nextLine();

		Scanner saddress = new Scanner(System.in);
		System.out.println("Enter Address var(40): ");
		address = saddress.nextLine();

		Scanner scity = new Scanner(System.in);
		System.out.println("Enter city varchar(20): ");
		city = scity.nextLine();

		Scanner sstate = new Scanner(System.in);
		System.out.println("Enter State char(2): ");
		state = sstate.next();

		Scanner szip = new Scanner(System.in);
		System.out.println("Enter Zip char(5): ");
		zip = szip.next();

		Database.newUser(email, password, fname, lname, phone, address, city, state, zip);
	}

	/**
	 * deleteMember() is called from addDeleteMembers() and will allow the
	 * employees to delete Members
	 */
	public void deleteMember() {
		String deleteMember;
		System.out.println("Enter member ID to delete");
		Scanner delete = new Scanner(System.in);
		deleteMember = delete.next();
		Database.deleteMember(deleteMember);
	}

	/**
	 * settings() allows the employees to decide to see their personal
	 * information or to update their information.
	 */
	public void settings() {
		try {
			int employeeChoice;
			Scanner empChoice = new Scanner(System.in);
			System.out.println("1: Your information");
			System.out.println("2: Update Email Address");
			System.out.println("3: Update Password");
			employeeChoice = empChoice.nextInt();
			if (employeeChoice == 1) {
				Database.getEmployeeInformation(employeeEmail);
			} else if (employeeChoice == 2) {
				updateEmailAddress();
			} else if (employeeChoice == 3) {
				updatePassword();
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}
	}

	/**
	 * updateEmailAddress() is called from settings() and will allow the
	 * employees to update their email address
	 */
	public void updateEmailAddress() {
		Scanner eemail = new Scanner(System.in);
		String newEmployeeEmail;
		System.out.println("Enter New Email Address: ");
		newEmployeeEmail = eemail.next();
		Database.updateEmployeeEmailAddress(employeeEmail, newEmployeeEmail);
		employeeEmail = newEmployeeEmail;
	}

	/**
	 * updatePassword() is called from settings() and will allow the employees
	 * to update their password
	 */
	public void updatePassword() {
		Console console = System.console();
		char employeeArray[] = console.readPassword("Enter your password: ");
		String password = new String(employeeArray);
		Database.updateEmployeePassword(employeeEmail, password);
	}

}
