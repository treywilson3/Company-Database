import java.io.Console;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Trey, Farris, Casey MemberFunctions is only available to verified
 *         Members It is where all member choices can be made. Lots of the
 *         methods will end up calling class Database to get information or to
 *         update information regarding the database. Try/Catch loops are
 *         implemented in some methods to catch illegal input (when integer
 *         required but user gives string).
 */
public class MemberFunctions {
	private String memberEmail, memberPassword;

	/**
	 * Constructor will call choices to present choices to member
	 * 
	 * @param memberEmail
	 *            - the member's email
	 * @param memberPassword
	 *            - the members password
	 */
	public MemberFunctions(String memberEmail, String memberPassword) {
		this.memberEmail = memberEmail;
		this.memberPassword = memberPassword;
		// if verified run choices
		choices();
	}

	/**
	 * Choices() presents the member with multiple choices it will then call
	 * whichever method needs to be called (the member decides).
	 */
	public void choices() {
		System.out.println("Welcome!");
		System.out.println();
		while (true) {
			try {
				int memberChoice;
				Scanner in = new Scanner(System.in);
				System.out.println();
				System.out.println("1: Search for any movie in our Database");
				System.out.println("2: Add movie to: To Watch, Watched, Favorite Movies");
				System.out.println("3: We will present you with a random movie!");
				System.out.println("4: Stored Data (To Watch, Watch History, Favorite Movies, Recommendations)");
				System.out.println("5: Settings (Update Member Information)");
				System.out.println("6: Movie Stats");
				System.out.println("7: Quit");

				memberChoice = in.nextInt();
				if (memberChoice == 1) {
					search();
				} else if (memberChoice == 2) {
					addingChoices();
				} else if (memberChoice == 3) {
					randomMovie();
				} else if (memberChoice == 4) {
					storedData();
				} else if (memberChoice == 5) {
					settings();
				} else if (memberChoice == 6) {
					movieStats();
				} else if (memberChoice == 7) {
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
	 * movieStats() presents the member with more choices regarding stats on
	 * movies. It will then call the correct method
	 */
	public void movieStats() {
		try {
			System.out.println("1: Top 10 Watched Movies");
			System.out.println("2: Top 10 Rated Movies");
			System.out.println("3: Most Popular Actors");
			System.out.println("4: All Movies with a Rate Grade");

			Scanner memberMovieStats = new Scanner(System.in);
			int memberMovieStatsChoice = memberMovieStats.nextInt();
			if (memberMovieStatsChoice == 1) {
				top10WatchedMovies();
			} else if (memberMovieStatsChoice == 2) {
				top10WRatedMovies();
			} else if (memberMovieStatsChoice == 3) {
				mostPopularActors();
			} else if (memberMovieStatsChoice == 4) {
				rateGrade();
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}
	}

	/**
	 * rateGrade() is called from movieStats() and it calls class Database
	 */
	public void rateGrade() {
		Database.rateGrade();

	}

	/**
	 * top10WRatedMovies() is called from movieStats() and it calls class
	 * Database
	 */
	public void top10WRatedMovies() {
		Database.top10RatedMovies();

	}

	/**
	 * mostPopularActors() is called from movieStats() and it calls class
	 * Database
	 */
	public void mostPopularActors() {
		Database.mostPopularActors();

	}

	/**
	 * top10WatchedMovies() is called from movieStats() and it calls class
	 * Database
	 */

	public void top10WatchedMovies() {
		Database.top10WatchedMovies();

	}

	/**
	 * addingChoices() presents the member with choices of which list to add to
	 * and will then call the correct method
	 */
	public void addingChoices() {
		try {
			int memberAddChoice;
			Scanner memberAdd = new Scanner(System.in);
			System.out.println("1: Add Movie to: To Watch");
			System.out.println("2: Add Movie to: Watch History");
			System.out.println("3: Add movie to: Favorite Movies");
			memberAddChoice = memberAdd.nextInt();

			if (memberAddChoice == 1) {
				addToWatch();
			} else if (memberAddChoice == 2) {
				addWatched();
			} else if (memberAddChoice == 3) {
				addFavoriteMovies();
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}
	}

	/**
	 * addToWatch is where the user can add to their toWatch list
	 */
	public void addToWatch() {
		Scanner movieWatch = new Scanner(System.in);
		String movieToWatch;
		System.out.println("Enter Movie Title that you want to add to: To Watch: ");
		movieToWatch = movieWatch.nextLine();
		Database.addToWatch(memberEmail, movieToWatch);

	}

	/**
	 * addWatched is where the member can add to their watched list
	 */
	public void addWatched() {
		Scanner scannerWatched = new Scanner(System.in);
		Scanner scannerDayWatched = new Scanner(System.in);
		String movieWatched;
		String dayWatched;
		System.out.println("Enter Movie Title that you have watched: ");
		movieWatched = scannerWatched.nextLine();
		System.out.println("Enter Date Watched: (yyyy-MM-dd)");
		dayWatched = scannerDayWatched.nextLine();
		Database.addWatchHistory(memberEmail, movieWatched, dayWatched);
	}

	/**
	 * addFavoriteMovies is where the member can add to their favorite movies
	 * list
	 */
	public void addFavoriteMovies() {
		Scanner movieFavScan = new Scanner(System.in);
		String movieFav;
		System.out.println("Enter Movie Title that you want to add to: Favorite Movies: ");
		movieFav = movieFavScan.nextLine();
		Database.addFavoriteMovie(memberEmail, movieFav);
	}

	/**
	 * search() allows the user to search anything in our movie table or present
	 * them with all movies in our database.
	 */
	public void search() {
		try {
			Scanner search = new Scanner(System.in);
			System.out.println("1: Present all Movies");
			System.out.println("2: Search any Title, Genre, or Rating");
			int searchChoice = search.nextInt();
			if (searchChoice == 1) {
				Database.memberMovieView();
			} else if (searchChoice == 2) {
				Scanner searchString = new Scanner(System.in);
				System.out.println("Enter what you want to be searched");
				String searchData = searchString.nextLine();
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
	 * randomMovie() will call class Database and give the member a randomly
	 * chosen movie.
	 */
	public void randomMovie() {
		Database.randomMovie();
	}

	/**
	 * Stored data is where multiple member's list are able to be seen and also
	 * their membership information.
	 */
	public void storedData() {
		try {
			int memberChoice;
			Scanner in = new Scanner(System.in);
			System.out.println("1: To Watch List");
			System.out.println("2: Watch History");
			System.out.println("3: Favorite Movies");
			System.out.println("4: Recommendations");
			System.out.println("5: Get Member Information");

			memberChoice = in.nextInt();
			if (memberChoice == 1) {
				// to watch list
				Database.toWatch(memberEmail);
			} else if (memberChoice == 2) {
				// watch history
				Database.watchHistory(memberEmail);
			} else if (memberChoice == 3) {
				// favorite movies
				Database.favoriteMovies(memberEmail);
			} else if (memberChoice == 4) {
				Database.recommendations(memberEmail);
			} else if (memberChoice == 5) {
				Database.getMemberInformation(memberEmail);
			} else {
				System.out.println("Invalid Input");
			}
		} catch (InputMismatchException e) {
			System.out.println("Wrong Input");
			System.out.println("You need to enter the correct integer");
		}
	}

	/**
	 * settings() lets the member decide to update multiple aspects of their
	 * membership
	 */
	public void settings() {
		try {
			int memberChoice;
			Scanner in = new Scanner(System.in);
			System.out.println("1: Update Phone");
			System.out.println("2: Update Address");
			System.out.println("3: Update Email Address");
			System.out.println("4: Update Password");

			memberChoice = in.nextInt();
			if (memberChoice == 1) {
				updatePhone();
			} else if (memberChoice == 2) {
				updateAddress();
			} else if (memberChoice == 3) {
				updateEmailAddress();
			} else if (memberChoice == 4) {
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
	 * updatePhone() allows the user to update their phone number
	 */
	public void updatePhone() {
		Scanner mphone = new Scanner(System.in);
		String memberPhoneNumber;
		System.out.println("Enter New Phone Number: ");
		memberPhoneNumber = mphone.next();
		Database.updateMemberPhone(memberEmail, memberPhoneNumber);
	}

	/**
	 * updateAddress() allows the member to update their address
	 */
	public void updateAddress() {
		Scanner scanner = new Scanner(System.in);
		String memberAddress;
		System.out.println("Enter New Address: ");
		memberAddress = scanner.nextLine();
		Scanner scanner2 = new Scanner(System.in);
		String city;
		System.out.println("Enter New City: ");
		city = scanner2.nextLine();
		Scanner scanner3 = new Scanner(System.in);
		String state;
		System.out.println("Enter New State: ");
		state = scanner3.nextLine();
		Scanner scanner4 = new Scanner(System.in);
		String zip;
		System.out.println("Enter New Zip: ");
		zip = scanner4.nextLine();
		Database.updateMemberAddress(memberEmail, memberAddress, city, state, zip);
	}

	/**
	 * updateEmailAddress() allows the member to update their email address
	 */
	public void updateEmailAddress() {
		Scanner memail = new Scanner(System.in);
		String newMemberEmail;
		System.out.println("Enter New Email Address: ");
		newMemberEmail = memail.next();
		Database.updateMemberEmailAddress(memberEmail, newMemberEmail);
		memberEmail = newMemberEmail;

	}

	/**
	 * updatePassword allows member to update their password
	 */
	public void updatePassword() {
		Console console = System.console();
		char memberArray[] = console.readPassword("Enter your password: ");
		String password = new String(memberArray);
		Database.updateMemberPassword(memberEmail, password);
	}

}
