package Presentation;

public class MessagesEN {



    public static final String ATHLETE_DETAILS_HEADER = "Athlete details";
    public static final String ATHLETE_TITLE_FORMAT = "-| %s | %s |-%n";
    public static final String ATHLETE_DELEGATION_FORMAT = "Delegation: %s %s%n";
    public static final String ATHLETE_BIRTHDATE_FORMAT = "Date of birth: %s%n";
    public static final String ATHLETE_PROFILE_FORMAT = "Profile: %s%n";
    public static final String ATHLETE_ATTRIBUTES_HEADER = "Attributes";
    public static final String ATHLETE_ATTRIBUTE_FORMAT = "%-12s%d%n";




    // HEADERS
    public static final String HEADER_MAIN_MENU =
            "What do you want to do:";

    public static final String HEADER_DELEGATIONS_MENU =
            "Delegations submenu:";

    public static final String HEADER_ATHLETES_MENU =
            "Athletes submenu:";

    public static final String HEADER_COMPETITIONS_MENU =
            "Competitions submenu:";

    public static final String HEADER_COMPETITION_LOADED =
            "Competition loaded successfully.\n";

    public static final String HEADER_SIMULATION_START =
            "\nSimulate Olympic Games\n" + "----------------------\n" + "Starting Olympic Games simulation...";

    public static final String HEADER_SIMULATION_COMPLETED =
            "\nOlympic Games simulation completed";



    // LABELS - MAIN MENU
    public static final String LABEL_MAIN_MENU_DELEGATIONS =
            "1) Delegations";

    public static final String LABEL_MAIN_MENU_ATHLETES =
            "2) Athletes";

    public static final String LABEL_MAIN_MENU_COMPETITIONS =
            "3) Competitions";

    public static final String LABEL_MAIN_MENU_SIMULATE =
            "4) Simulate the Games";

    public static final String LABEL_MAIN_MENU_EXIT =
            "0) Exit";



    // LABELS - DELEGATIONS
    public static final String LABEL_DELEGATIONS_MENU_LIST =
            "1) Show list of delegations";

    public static final String LABEL_DELEGATIONS_MENU_MEDALS =
            "2) Show medals by delegation";

    public static final String LABEL_DELEGATIONS_MENU_BACK =
            "0) Back";



    // LABELS - ATHLETES
    public static final String LABEL_ATHLETES_MENU_REGISTER =
            "1) Register athlete";

    public static final String LABEL_ATHLETES_MENU_LIST =
            "2) List/Search athletes";

    public static final String LABEL_ATHLETES_MENU_DELETE =
            "3) Delete athlete";

    public static final String LABEL_ATHLETES_MENU_BACK =
            "0) Back";



    // LABELS - COMPETITIONS
    public static final String LABEL_COMPETITIONS_MENU_LOAD =
            "1) Load competitions from CSV";

    public static final String LABEL_COMPETITIONS_MENU_LIST =
            "2) List competitions";

    public static final String LABEL_COMPETITIONS_MENU_DELETE =
            "3) Delete competition";

    public static final String LABEL_COMPETITIONS_MENU_BACK =
            "0) Back";



    // PROMPTS
    public static final String PROMPT_CHOOSE_OPTION =
            "Choose an option: ";

    public static final String PROMPT_CHOOSE_OR_ENTER =
            "Choose an option or press ENTER to see next page: ";

    public static final String PROMPT_DELEGATIONS_QUANTITY =
            "How many delegations would you like to see per page?";

    public static final String PROMPT_ASK_FOR_CSV =
            "Enter the name of the CSV file: ";

    public static final String PROMPT_CONTINUE =
            "Press ENTER to continue...";

    public static final String PROMPT_NEXT_SIMULATION =
            "<Press ENTER to simulate the next competition>";

    public static final String PROMPT_FINISH_SIMULATION =
            "<Press ENTER to finish the simulation>";

    public static final String PROMPT_SEARCH_ATHLETES = "Enter a search term or press ENTER to show all athletes: ";

    public static final String PROMPT_CHOOSE_ATHLETE = "Choose an athlete: ";



    // MESSAGES
    public static final String MESSAGE_OPENING_FILE =
            "Opening file...";

    public static final String MESSAGE_VALIDATING_COMPETITIONS =
            "Validating file...";

    public static final String MESSAGE_GOODBYE =
            "We hope to see you again!";

    public static final String MESSAGE_NOT_IMPLEMENTED =
            "This functionality is not implemented yet.";

    public static final String MESSAGE_DELEGATIONS_GOLD_MEDALS =
            "Gold medals: ";

    public static final String MESSAGE_DELEGATIONS_SILVER_MEDALS =
            "Silver medals: ";

    public static final String MESSAGE_DELEGATIONS_BRONZE_MEDALS =
            "Bronze medals: ";



    // ERRORS
    public static final String ERROR_INVALID_INPUT =
            "Invalid input. Please try again.";

    public static final String ERROR_NOT_A_NUMBER =
            "Error: Please enter a number.";

    public static final String ERROR_OUT_OF_RANGE =
            "Error: Please enter a number between %d and %d.";

    public static final String ERROR_INVALID_FIELDS =
            "Invalid number of CSV fields.";

    public static final String ERROR_EMPTY_ID =
            "Competition identifier cannot be empty.";

    public static final String ERROR_INVALID_ID =
            "Invalid competition identifier. Expected format: CMP-XXXX.";

    public static final String ERROR_DUPLICATE_ID =
            "Competition identifier already exists.";

    public static final String ERROR_INVALID_FORMAT =
            "Invalid competition format. Use TIMED, SCORED or MEASURED.";

    public static final String ERROR_EMPTY_NAME =
            "Competition name cannot be empty.";

    public static final String ERROR_INVALID_DATE =
            "Invalid date. Expected format: yyyy-MM-dd HH:mm.";

    public static final String ERROR_INVALID_WEIGHT =
            "Competition weights must be between 0.0 and 1.0.";

    public static final String ERROR_INVALID_WEIGHT_SUM =
            "The sum of competition weights must equal 1.0.";

    public static final String ERROR_INVALID_ATHLETE =
            "One or more athlete identifiers do not exist.";

    public static final String ERROR_DUPLICATE_ATHLETE =
            "An athlete cannot participate more than once in the same competition.";

    public static final String ERROR_NOT_ENOUGH_ATHLETES =
            "A competition must have at least 3 different athletes.";

    public static final String ERROR_IMPORT_FAILED =
            "Error: The competition could not be loaded.";

    public static final String ERROR_FILE =
            "Error reading or writing the file.";

    public static final String ERROR_JSON =
            "Invalid JSON format.";

    public static final String ERROR_NO_COMPETITIONS =
            "Error: There are no competitions to simulate.";

    public static final String ERROR_NO_DELEGATIONS =
            "There are no delegations available.";

    public static final String ERROR_INVALID_SIMULATION_DATA =
            "Error: Invalid athlete or delegation data in the simulation.";


    // FORMATTING
    public static final String SEPARATOR = "----------";

    public static final String TABLE_BORDER =
            "+------------+-----------------------------------------------------------------------------+------+--------+--------+";



    public static final String ATHLETES_LIST_BACK = "    0) Back";
    public static final String NO_ATHLETES_FOUND = "No athletes found.";
    public static final String PROMPT_ATHLETE_NAME = "Athlete name: ";
    public static final String PROMPT_BIRTHDATE = "Date of birth (DD/MM/YYYY): ";

    public static final String PROMPT_CHOOSE_PROFILE = "Choose a profile: ";
    public static final String HEADER_SELECT_PROFILE = "Select the athlete's profile:";
    public static final String LABEL_PROFILE_BALANCED = "1) Balanced";
    public static final String LABEL_PROFILE_POWER = "2) Power";
    public static final String LABEL_PROFILE_ENDURANCE = "3) Endurance";
    public static final String LABEL_PROFILE_TECHNICAL = "4) Technical";
    public static final String LABEL_PROFILE_CANCEL = "0) Cancel";
    public static final String MESSAGE_ATHLETE_REGISTERED = "Athlete registered successfully.";
    public static final String MESSAGE_ATHLETE_STORED = "The athlete has been stored successfully.";
    public static final String ERROR_EMPTY_ATHLETE_NAME = "Error: The athlete name can't be empty.";
    public static final String ERROR_INVALID_BIRTHDATE = "Error: The date of birth is not valid.\nPlease enter a valid date using the format DD/MM/YYYY.";
    public static final String ERROR_ATHLETE_ALREADY_REGISTERED = "Error: An athlete with that name already exists in this delegation.";

}

