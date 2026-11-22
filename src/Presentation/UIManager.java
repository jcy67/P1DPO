package Presentation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import Business.ERROR_TYPE;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Manages the user interface of the Olympic Games application.
 * Displays menus, requests user input and presents information
 * about delegations, athletes, competitions and simulations.
 */
public class UIManager {

    private final Scanner s;

    /**
     * Creates a UIManager and initializes the scanner for user input.
     */
    public UIManager() {
        this.s = new Scanner(System.in);
    }

    /**
     * Displays the application banner and welcome message.
     */
    public void printWelcome() {

        System.out.println("    __     __   ____  ____      ___   __   _  _  ____  ____     __     __   ____");
        System.out.println("   (  )   / _\\ (___ \\/ _  \\    / __) / _\\ ( \\/ )(  __)/ ___)   (  )   / _\\ (  _ \\");
        System.out.println("   / (_/\\/    \\ / __/) _  (   ( (_ \\/    \\/ \\/ \\ ) _) \\___ \\   / (_/\\/    \\ ) _ (");
        System.out.println("   \\____/\\_/\\_/(____)\\____/    \\___/\\_/\\_/\\_)(_/(____)(____/   \\____/\\_/\\_/(____/");

        System.out.println();

        System.out.println("Welcome to LA28 Games Lab.");
        System.out.println("The Olympics Simulator.");
        System.out.println();
    }

    /**
     * Displays the details of a newly registered athlete.
     *
     * @param athleteInfo the athlete information prepared by OlympicsManager
     */
    public void showRegisteredAthlete(ArrayList<String> athleteInfo) {
        System.out.println();
        System.out.println(MessagesEN.MESSAGE_ATHLETE_REGISTERED);
        System.out.println();
        printAthlete(athleteInfo);
        System.out.println(MessagesEN.MESSAGE_ATHLETE_STORED);
    }

    /**
     * Displays the available athlete profiles and asks the user to select one.
     *
     * @return the selected profile, or null if the user cancels
     */
    public String askProfile() {
        System.out.println();
        System.out.println(MessagesEN.HEADER_SELECT_PROFILE);
        System.out.println();
        System.out.println(MessagesEN.LABEL_PROFILE_BALANCED);
        System.out.println(MessagesEN.LABEL_PROFILE_POWER);
        System.out.println(MessagesEN.LABEL_PROFILE_ENDURANCE);
        System.out.println(MessagesEN.LABEL_PROFILE_TECHNICAL);
        System.out.println();
        System.out.println(MessagesEN.LABEL_PROFILE_CANCEL);

        int choice = askForInt(MessagesEN.PROMPT_CHOOSE_PROFILE, 0, 4);

        return switch (choice) {
            case 1 -> "BALANCED";
            case 2 -> "POWER";
            case 3 -> "ENDURANCE";
            case 4 -> "TECHNICAL";
            default -> null;
        };
    }

    /**
     * Asks the user for a search term to filter athletes by name.
     *
     * @return the search term, or an empty string to show all athletes
     */
    public String askSearchTerm() {
        System.out.print(MessagesEN.PROMPT_SEARCH_ATHLETES);
        return s.nextLine().trim();
    }

    /**
     * Displays the athlete details supplied by the business layer.
     * The list contains ID, name, delegation code, delegation name,
     * birth date, profile and the five attributes.
     *
     * @param info the athlete information to display
     */
    public void printAthlete(ArrayList<String> info) {
        System.out.println(MessagesEN.ATHLETE_DETAILS_HEADER);
        System.out.println(MessagesEN.SEPARATOR);
        System.out.println();
        System.out.printf(MessagesEN.ATHLETE_TITLE_FORMAT, info.get(0), info.get(1));
        System.out.println();
        System.out.printf(MessagesEN.ATHLETE_DELEGATION_FORMAT, info.get(2), info.get(3));
        System.out.printf(MessagesEN.ATHLETE_BIRTHDATE_FORMAT, info.get(4));
        System.out.printf(MessagesEN.ATHLETE_PROFILE_FORMAT, info.get(5));
        System.out.println();
        System.out.println(MessagesEN.ATHLETE_ATTRIBUTES_HEADER);
        System.out.println(MessagesEN.SEPARATOR);
        System.out.printf(MessagesEN.ATHLETE_ATTRIBUTE_FORMAT, "Speed:", Integer.parseInt(info.get(6)));
        System.out.printf(MessagesEN.ATHLETE_ATTRIBUTE_FORMAT, "Endurance:", Integer.parseInt(info.get(7)));
        System.out.printf(MessagesEN.ATHLETE_ATTRIBUTE_FORMAT, "Strength:", Integer.parseInt(info.get(8)));
        System.out.printf(MessagesEN.ATHLETE_ATTRIBUTE_FORMAT, "Technique:", Integer.parseInt(info.get(9)));
        System.out.printf(MessagesEN.ATHLETE_ATTRIBUTE_FORMAT, "Precision:", Integer.parseInt(info.get(10)));
        System.out.println();
    }

    /**
     * Asks the user to enter a non-empty athlete name.
     * Repeats the request until a valid name is entered.
     *
     * @return the entered athlete name
     */
    public String askAthleteName() {
        String name = "";
        while (name.isEmpty()) {
            System.out.print(MessagesEN.PROMPT_ATHLETE_NAME);
            name = s.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println(MessagesEN.ERROR_EMPTY_ATHLETE_NAME);
            }
        }
        return name;
    }

    /**
     * Asks for a valid birth date and returns its text representation.
     *
     * @return the birth date in dd/MM/yyyy format
     */
    public String askBirthDate() {
        while (true) {
            System.out.print(MessagesEN.PROMPT_BIRTHDATE);
            String input = s.nextLine().trim();
            if (parseBirthDate(input) != null) {
                return input;
            }
            System.out.println(MessagesEN.ERROR_INVALID_BIRTHDATE);
        }
    }

    /**
     * Parses and validates a birth date using the dd/MM/yyyy format.
     *
     * @param input the birth date entered by the user
     * @return the parsed date, or null if the date is invalid
     */
    private Date parseBirthDate(String input) {
        if (!input.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            return format.parse(input);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Displays the matching athletes and lets the user choose one or go back.
     *
     * @param found rows containing athlete ID, name and delegation name
     * @return a one-based selection, or 0 to return
     */
    public int chooseAthlete(ArrayList<ArrayList<String>> found) {
        if (found.isEmpty()) {
            System.out.println(MessagesEN.NO_ATHLETES_FOUND);
            return 0;
        }
        for (int i = 0; i < found.size(); i++) {
            ArrayList<String> athlete = found.get(i);
            System.out.println("    " + (i + 1) + ") " + athlete.get(1) + " - " + athlete.get(2));
        }
        System.out.println();
        System.out.println(MessagesEN.ATHLETES_LIST_BACK);
        return askForInt(MessagesEN.PROMPT_CHOOSE_ATHLETE, 0, found.size());
    }


    /**
     * Asks the user to enter an integer within the specified range
     * or press ENTER without entering a value.
     *
     * @param prompt the message displayed before reading the input
     * @param min the minimum accepted value (inclusive)
     * @param max the maximum accepted value (inclusive)
     * @return the entered integer, or null if ENTER is pressed
     * @throws NumberFormatException if the input is not a valid integer
     * @throws IllegalArgumentException if the value is outside the allowed range
     */
    public Integer askForIntOrEnter(String prompt, int min, int max) {

        System.out.print(prompt);

        String input = s.nextLine().trim();

        if (input.isEmpty()) {
            return null;
        }

        int finalResult = Integer.parseInt(input);

        if (finalResult < min || finalResult > max) {
            throw new IllegalArgumentException(
                    String.format(MessagesEN.ERROR_OUT_OF_RANGE, min, max)
            );
        }

        return finalResult;
    }

    /**
     * Asks the user to confirm an action by entering Y or N.
     * Repeats the request until a valid answer is entered.
     *
     * @param prompt the confirmation message to display
     * @return true if the user enters Y, false if the user enters N
     */
    public boolean askForConfirmation(String prompt) {

        while (true) {

            System.out.print(prompt);

            String answer = s.nextLine().trim();

            if (answer.equalsIgnoreCase("Y")) {
                return true;
            }

            if (answer.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println(MessagesEN.ERROR_INVALID_INPUT);
        }
    }

    /**
     * Displays a paginated list of delegations and allows
     * the user to select one or return to the previous menu.
     *
     * @param delegations the delegation information to display
     * @return the selected delegation, or null if the user goes back
     *         or no delegations are available
     */
    public ArrayList<String> printDelegations(
            ArrayList<ArrayList<String>> delegations) {

        if (delegations.isEmpty()) {
            showError(ERROR_TYPE.NO_DELEGATIONS);
            return null;
        }

        ArrayList<String> items = new ArrayList<>();

        for (ArrayList<String> delegation : delegations) {
            items.add(delegation.get(0) + " " + delegation.get(1));
        }

        Integer choice = printPaginatedList(
                items,
                MessagesEN.PROMPT_DELEGATIONS_QUANTITY,
                MessagesEN.PROMPT_CHOOSE_OR_ENTER
        );

        if (choice == null) {
            return null;
        }

        return delegations.get(choice);
    }

    /**
     * Displays a paginated list of items and allows the user
     * to select an item, move to the next page or return.
     * Restarts from the first page after reaching the last page.
     *
     * @param items the items to display
     * @param quantityPrompt the message asking for the page size
     * @param selectionPrompt the message asking the user to select an item
     * @return the index of the selected item, or null if the user goes back
     */
    private Integer printPaginatedList(
            ArrayList<String> items,
            String quantityPrompt,
            String selectionPrompt) {

        System.out.println(quantityPrompt);

        int pageSize = askForInt(
                MessagesEN.PROMPT_CHOOSE_OPTION,
                1,
                items.size()
        );

        int start = 0;

        while (true) {

            int end = Math.min(start + pageSize, items.size());

            System.out.println();

            for (int i = start; i < end; i++) {
                System.out.println("    " + (i + 1) + ") " + items.get(i));
            }

            System.out.println("\n    " + MessagesEN.LABEL_BACK);
            System.out.println();

            // Change the prompt on the last page.
            String prompt = selectionPrompt;

            if (end == items.size()) {
                prompt = MessagesEN.PROMPT_CHOOSE_OR_RESTART;
            }

            Integer choice = askForIntOrEnter(
                    prompt,
                    0,
                    items.size()
            );

            if (choice != null) {

                if (choice == 0) {
                    return null;
                }

                return choice - 1;
            }

            // Infinite pagination.
            start = end == items.size() ? 0 : end;
        }
    }

    /**
     * Displays a paginated list of available competitions.
     * Allows the user to select a competition or return
     * to the previous menu.
     *
     * @param competitions the competition information to display
     * @param header the header displayed above the competition list
     * @return the identifier of the selected competition,
     *         or null if the user goes back or the list is empty
     */
    public String printCompetitions(
            ArrayList<ArrayList<String>> competitions,
            String header) {

        if (competitions.isEmpty()) {
            showMessage(MessagesEN.ERROR_NO_AVAILABLE_COMPETITIONS);
            return null;
        }

        System.out.println("\n" + header);
        System.out.println("----------------------");

        ArrayList<String> items = new ArrayList<>();

        for (ArrayList<String> competition : competitions) {
            items.add(competition.get(1) + " - " + competition.get(2));
        }

        Integer choice = printPaginatedList(
                items,
                MessagesEN.PROMPT_COMPETITIONS_QUANTITY,
                MessagesEN.PROMPT_CHOOSE_OR_ENTER
        );

        if (choice == null) {
            return null;
        }

        return competitions.get(choice).get(0);
    }

    /**
     * Displays the detailed information of a competition.
     * Shows its identifier, name, format, date, attribute weights,
     * medal results and participating athletes.
     *
     * @param info the competition details, including its weights and results
     * @param participants the names of the participating athletes
     *                     and their delegations
     */
    public void printCompetitionDetails(
            ArrayList<String> info,
            ArrayList<String> participants) {

        System.out.println("\n" + MessagesEN.HEADER_COMPETITION_DETAILS);
        System.out.println("-------------------");

        System.out.println("\n-| " + info.get(0) +
                " | " + info.get(1) + " |-");

        System.out.println("\nFormat: " + info.get(2));
        System.out.println("Date and time: " + info.get(3));

        System.out.println("\n" + MessagesEN.HEADER_ATTRIBUTE_WEIGHTS);
        System.out.println("-----------------");

        System.out.printf(java.util.Locale.US,
                "Speed:      %.2f%n", Double.parseDouble(info.get(4)));

        System.out.printf(java.util.Locale.US,
                "Endurance:  %.2f%n", Double.parseDouble(info.get(5)));

        System.out.printf(java.util.Locale.US,
                "Strength:   %.2f%n", Double.parseDouble(info.get(6)));

        System.out.printf(java.util.Locale.US,
                "Technique:  %.2f%n", Double.parseDouble(info.get(7)));

        System.out.printf(java.util.Locale.US,
                "Precision:  %.2f%n", Double.parseDouble(info.get(8)));

        System.out.println("\n" + MessagesEN.HEADER_COMPETITION_RESULT);
        System.out.println("------------------");

        if (info.get(9) == null) {

            System.out.println(MessagesEN.MESSAGE_COMPETITION_NOT_SIMULATED);

        } else {

            System.out.println("Gold:   " + info.get(9));
            System.out.println("Silver: " + info.get(10));
            System.out.println("Bronze: " + info.get(11));
        }

        System.out.println("\n" + MessagesEN.HEADER_PARTICIPANTS);
        System.out.println("------------\n");

        for (int i = 0; i < participants.size(); i++) {
            System.out.println(
                    "    " + (i + 1) + ") " + participants.get(i)
            );
        }

        System.out.println();
    }

    /**
     * Displays the selected delegation's information.
     * Shows its medal counts and the names of its athletes.
     *
     * @param delegation the delegation information
     * @param athleteNames the names of athletes belonging to the delegation
     */
    public void printDelegationStats(
            ArrayList<String> delegation,
            ArrayList<String> athleteNames) {

        System.out.printf(
                "-| %s | %s |-%n",
                delegation.get(0),
                delegation.get(1)
        );

        System.out.println("\t" +
                MessagesEN.MESSAGE_DELEGATIONS_GOLD_MEDALS +
                delegation.get(2));

        System.out.println("\t" +
                MessagesEN.MESSAGE_DELEGATIONS_SILVER_MEDALS +
                delegation.get(3));

        System.out.println("\t" +
                MessagesEN.MESSAGE_DELEGATIONS_BRONZE_MEDALS +
                delegation.get(4));

        System.out.println("\n\t" + athleteNames.size() + " athletes");
        System.out.println("\t" + MessagesEN.SEPARATOR);

        for (String name : athleteNames) {
            System.out.println("\t" + name);
        }

        System.out.println();
    }

    /**
     * Displays a table containing the delegations and their medal counts.
     * Shows each delegation's code, name, gold, silver and bronze medals.
     *
     * @param delegations the delegation information to display
     */
    public void printMedalTable(
            ArrayList<ArrayList<String>> delegations) {

        String body = "| %-10s | %-75s | %4s | %6s | %6s |%n";

        System.out.println(MessagesEN.TABLE_BORDER);

        System.out.printf(
                body,
                "Code", "Delegation", "Gold", "Silver", "Bronze"
        );

        System.out.println(MessagesEN.TABLE_BORDER);

        for (ArrayList<String> delegation : delegations) {

            System.out.printf(
                    body,
                    delegation.get(0),
                    delegation.get(1),
                    delegation.get(2),
                    delegation.get(3),
                    delegation.get(4)
            );
        }

        System.out.println(MessagesEN.TABLE_BORDER);
    }

    /**
     * Displays the main menu and asks the user to select an option.
     *
     * @return the selected main menu option
     */
    public MAIN_MENU_OPTION printMenu() {

        System.out.println("\n" + MessagesEN.HEADER_MAIN_MENU);
        System.out.println();

        System.out.println(MessagesEN.LABEL_MAIN_MENU_DELEGATIONS);
        System.out.println(MessagesEN.LABEL_MAIN_MENU_ATHLETES);
        System.out.println(MessagesEN.LABEL_MAIN_MENU_COMPETITIONS);
        System.out.println(MessagesEN.LABEL_MAIN_MENU_SIMULATE);
        System.out.println();
        System.out.println(MessagesEN.LABEL_MAIN_MENU_EXIT);

        int choice = askForInt(MessagesEN.PROMPT_CHOOSE_OPTION, 0, 4);

        return switch (choice) {
            case 1 -> MAIN_MENU_OPTION.DELEGATIONS;
            case 2 -> MAIN_MENU_OPTION.ATHLETES;
            case 3 -> MAIN_MENU_OPTION.COMPETITIONS;
            case 4 -> MAIN_MENU_OPTION.SIMULATE;
            default -> MAIN_MENU_OPTION.EXIT;
        };
    }

    /**
     * Displays the delegations submenu and asks the user
     * to select an option.
     *
     * @return the selected delegation menu option
     */
    public DELEGATIONS_MENU_OPTION printDelegationsMenu() {

        System.out.println("\n" + MessagesEN.HEADER_DELEGATIONS_MENU);
        System.out.println();

        System.out.println(MessagesEN.LABEL_DELEGATIONS_MENU_LIST);
        System.out.println(MessagesEN.LABEL_DELEGATIONS_MENU_MEDALS);
        System.out.println();
        System.out.println(MessagesEN.LABEL_DELEGATIONS_MENU_BACK);

        int choice = askForInt(MessagesEN.PROMPT_CHOOSE_OPTION, 0, 2);

        return switch (choice) {
            case 1 -> DELEGATIONS_MENU_OPTION.LIST_DELEGATIONS;
            case 2 -> DELEGATIONS_MENU_OPTION.SHOW_MEDALS;
            default -> DELEGATIONS_MENU_OPTION.BACK_FROM_DELEGATION;
        };
    }

    /**
     * Displays the athletes submenu and asks the user to select an option.
     *
     * @return the selected athlete menu option
     */
    public ATHLETES_MENU_OPTION printAthletesMenu() {

        System.out.println("\n" + MessagesEN.HEADER_ATHLETES_MENU);
        System.out.println();

        System.out.println(MessagesEN.LABEL_ATHLETES_MENU_REGISTER);
        System.out.println(MessagesEN.LABEL_ATHLETES_MENU_LIST);
        System.out.println(MessagesEN.LABEL_ATHLETES_MENU_DELETE);
        System.out.println(MessagesEN.LABEL_ATHLETES_MENU_BACK);

        System.out.println();

        int choice = askForInt(MessagesEN.PROMPT_CHOOSE_OPTION, 0, 3);

        return switch (choice) {
            case 1 -> ATHLETES_MENU_OPTION.REGISTER_ATHLETE;
            case 2 -> ATHLETES_MENU_OPTION.LIST_SEARCH_ATHLETES;
            case 3 -> ATHLETES_MENU_OPTION.DELETE_ATHLETE;
            default -> ATHLETES_MENU_OPTION.BACK_FROM_ATHLETE;
        };
    }

    /**
     * Displays the competitions submenu and asks the user to select an option.
     *
     * @return the selected competition menu option
     */
    public COMPETITIONS_MENU_OPTION printCompetitionsMenu() {

        System.out.println("\n" + MessagesEN.HEADER_COMPETITIONS_MENU);
        System.out.println();

        System.out.println(MessagesEN.LABEL_COMPETITIONS_MENU_LOAD);
        System.out.println(MessagesEN.LABEL_COMPETITIONS_MENU_LIST);
        System.out.println(MessagesEN.LABEL_COMPETITIONS_MENU_DELETE);
        System.out.println(MessagesEN.LABEL_COMPETITIONS_MENU_BACK);

        System.out.println();

        int choice = askForInt(MessagesEN.PROMPT_CHOOSE_OPTION, 0, 3);

        return switch (choice) {
            case 1 -> COMPETITIONS_MENU_OPTION.LOAD_COMPETITION;
            case 2 -> COMPETITIONS_MENU_OPTION.LIST_COMPETITION;
            case 3 -> COMPETITIONS_MENU_OPTION.DELETE_COMPETITION;
            default -> COMPETITIONS_MENU_OPTION.BACK_FROM_COMPETITION;
        };
    }

    /**
     * Displays the results of a simulated Olympic competition.
     * Shows the competition information, athlete rankings, luck factors,
     * completion times and medal winners.
     *
     * @param competitionInfo the competition identifier, name and format
     * @param athleteNames the names of the athletes in ranking order
     * @param results the luck factor and completion time of each athlete
     * @param number the current competition number
     * @param total the total number of competitions
     */
    public void showSimulationResults(
            ArrayList<String> competitionInfo,
            ArrayList<String> athleteNames,
            ArrayList<ArrayList<Double>> results,
            int number,
            int total) {

        System.out.println("\nCompetition " + number + " of " + total);
        System.out.println("------------------");

        System.out.println("\n-| " + competitionInfo.get(0)
                + " | " + competitionInfo.get(1) + " |-");

        System.out.println("\nFormat: " + competitionInfo.get(2));

        System.out.println("\nSimulating competition...");

        System.out.println("\nResults");
        System.out.println("-------");

        for (int i = 0; i < results.size(); i++) {

            double luck = results.get(i).get(0);
            double time = results.get(i).get(1);

            System.out.println("\n    " + (i + 1) + ") " + athleteNames.get(i));

            System.out.printf(java.util.Locale.US,
                    "       Luck: %.2f%n", luck);

            System.out.printf(java.util.Locale.US,
                    "       Time: %.2f seconds%n", time);
        }

        System.out.println("\nMedal results");
        System.out.println("-------------");

        System.out.println("\n    Gold:   " + athleteNames.get(0));
        System.out.println("    Silver: " + athleteNames.get(1));
        System.out.println("    Bronze: " + athleteNames.get(2));
    }

    /**
     * Displays the error message corresponding to the given error type.
     *
     * @param error the type of error to display
     */
    public void showError(ERROR_TYPE error) {

        String message = switch (error) {
            case INVALID_FIELDS -> MessagesEN.ERROR_INVALID_FIELDS;
            case EMPTY_ID -> MessagesEN.ERROR_EMPTY_ID;
            case INVALID_ID -> MessagesEN.ERROR_INVALID_ID;
            case DUPLICATE_ID -> MessagesEN.ERROR_DUPLICATE_ID;
            case INVALID_FORMAT -> MessagesEN.ERROR_INVALID_FORMAT;
            case EMPTY_NAME -> MessagesEN.ERROR_EMPTY_NAME;
            case INVALID_DATE -> MessagesEN.ERROR_INVALID_DATE;
            case INVALID_WEIGHT -> MessagesEN.ERROR_INVALID_WEIGHT;
            case INVALID_WEIGHT_SUM -> MessagesEN.ERROR_INVALID_WEIGHT_SUM;
            case INVALID_ATHLETE -> MessagesEN.ERROR_INVALID_ATHLETE;
            case DUPLICATE_ATHLETE -> MessagesEN.ERROR_DUPLICATE_ATHLETE;
            case NOT_ENOUGH_ATHLETES -> MessagesEN.ERROR_NOT_ENOUGH_ATHLETES;
            case FILE_ERROR -> MessagesEN.ERROR_FILE;
            case JSON_ERROR -> MessagesEN.ERROR_JSON;
            case NOT_A_NUMBER -> MessagesEN.ERROR_NOT_A_NUMBER;
            case OUT_OF_RANGE -> MessagesEN.ERROR_OUT_OF_RANGE; // Use showMessage() instead because of the %d placeholders.
            case NO_DELEGATIONS -> MessagesEN.ERROR_NO_DELEGATIONS;
            case INVALID_SIMULATION_DATA -> MessagesEN.ERROR_INVALID_SIMULATION_DATA;
            case ATHLETE_ALREADY_REGISTERED -> MessagesEN.ERROR_ATHLETE_ALREADY_REGISTERED;
        };

        System.out.println(message);
    }

    /**
     * Displays a given message on the screen.
     *
     * @param message the message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays a message and waits for the user to press ENTER.
     *
     * @param message the message to display
     */
    public void waitForEnter(String message) {
        System.out.println("\n" + message);
        s.nextLine();
    }

    /**
     * Asks the user to enter an integer within the specified range.
     * Throws an exception if the entered value is invalid.
     *
     * @param prompt the message displayed before reading the input
     * @param min the minimum accepted value (inclusive)
     * @param max the maximum accepted value (inclusive)
     * @return the integer entered by the user
     * @throws NumberFormatException if the input is not a valid integer
     * @throws IllegalArgumentException if the value is outside the allowed range
     */
    public int askForInt(String prompt, int min, int max) {

        System.out.print(prompt);

        int value = Integer.parseInt(s.nextLine().trim());

        if (value < min || value > max) {
            throw new IllegalArgumentException(
                    String.format(MessagesEN.ERROR_OUT_OF_RANGE, min, max)
            );
        }
        return value;
    }

    /**
     * Asks the user to enter a non-empty string that is not entirely numeric.
     * Repeats the request until a valid string is entered.
     *
     * @param message the message displayed before reading the input
     * @return the valid string entered by the user
     */
    public String askForString(String message) {

        String in;

        do {
            System.out.print(message);

            // Read the input line and remove leading and trailing spaces.
            in = s.nextLine().trim();

            // Check whether the string is empty or contains only a number.
            if (in.isEmpty() || isNumber(in)) {
                System.out.println(MessagesEN.ERROR_INVALID_INPUT);
                System.out.println();
            }

            // Repeat until a valid string is entered.
        } while (in.isEmpty() || isNumber(in));

        return in;
    }

    /**
     * Checks whether a string represents an integer or decimal number.
     *
     * @param text the string to check
     * @return true if the string represents a number, false otherwise
     */
    private static boolean isNumber(String text) {
        return text.matches("-?[0-9]+(\\.[0-9]+)?");
    }

    /**
     * Asks the user to enter a non-empty CSV filename.
     * Repeats the request until a non-empty value is entered.
     *
     * @param message the message displayed before reading the input
     * @return the CSV filename entered by the user
     */
    public String askForCSV(String message) {

        String in;

        do {
            System.out.print(message);
            in = s.nextLine().trim();

            // Check whether the input is empty.
            if (in.isEmpty()) {
                System.out.println(MessagesEN.ERROR_INVALID_INPUT);
            }

        } while (in.isEmpty());

        return in;
    }

    /**
     * Displays the results of importing competitions from a CSV file.
     * Shows the information of each competition and indicates whether
     * it was imported successfully or which validation errors occurred.
     *
     * @param lines the lines read from the CSV file
     * @param results the validation errors associated with each competition
     */
    public void showImportResults(ArrayList<String> lines, ArrayList<ArrayList<ERROR_TYPE>> results) {

        for (int i = 0; i < results.size(); i++) {

            String[] parts = lines.get(i + 1).split(",", -1);
            ArrayList<ERROR_TYPE> errors = results.get(i);

            System.out.println();
            System.out.println("Competition " + (i + 1) + " of " + results.size());
            System.out.println("------------------");
            System.out.println();

            // Check that the required fields exist before displaying them.
            if (parts.length >= 10) {

                System.out.println("Identifier: " + parts[0]);
                System.out.println("Name: " + parts[2]);
                System.out.println("Format: " + parts[1]);
                System.out.println("Start date and time: " + parts[3]);

                System.out.print("Weights: ");

                for (int j = 4; j < 9; j++) {
                    System.out.print(parts[j].trim());

                    if (j < 8) {
                        System.out.print(", ");
                    }
                }

                System.out.println();

                System.out.print("Athletes: ");

                for (int j = 9; j < parts.length; j++) {
                    System.out.print(parts[j].trim());

                    if (j < parts.length - 1) {
                        System.out.print(", ");
                    }
                }

                System.out.println();
            }

            System.out.println();

            // Display whether the competition was imported or which errors occurred.
            if (errors.isEmpty()) {
                System.out.println(MessagesEN.HEADER_COMPETITION_LOADED);

            } else {
                System.out.println(MessagesEN.ERROR_IMPORT_FAILED);

                for (ERROR_TYPE error : errors) {
                    showError(error);
                }
            }

            System.out.println();
        }
    }

}