package Presentation;

public class MessagesEN {


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

    public static final String HEADER_AVAILABLE_COMPETITIONS =
            "Available competitions";

    public static final String HEADER_COMPETITION_DETAILS =
            "Competition details";

    public static final String HEADER_ATTRIBUTE_WEIGHTS =
            "Attribute weights";

    public static final String HEADER_COMPETITION_RESULT =
            "Competition result";

    public static final String HEADER_PARTICIPANTS =
            "Participants";

    public static final String HEADER_DELETE_COMPETITION =
            "Delete a competition";

    public static final String HEADER_COMPETITION_DELETED =
            "The competition \"%s\" has been removed from the system.";


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

    public static final String LABEL_BACK =
            "0) Back";



    // PROMPTS
    public static final String PROMPT_CHOOSE_OPTION =
            "Choose an option: ";

    public static final String PROMPT_CHOOSE_OR_ENTER =
            "Choose an option or press ENTER to see next page: ";

    public static final String PROMPT_CHOOSE_OR_RESTART =
            "Choose an option or press ENTER to restart the list: ";

    public static final String PROMPT_DELEGATIONS_QUANTITY =
            "How many delegations would you like to see per page?";

    public static final String PROMPT_ASK_FOR_CSV =
            "Enter the name of the CSV file: ";

    public static final String PROMPT_CONTINUE =
            "<Press ENTER to continue>";

    public static final String PROMPT_NEXT_SIMULATION =
            "<Press ENTER to simulate the next competition>";

    public static final String PROMPT_FINISH_SIMULATION =
            "<Press ENTER to finish the simulation>";

    public static final String PROMPT_COMPETITIONS_QUANTITY =
            "How many competitions would you like to see per page?";

    public static final String PROMPT_CONFIRM_DELETE_COMPETITION =
            "Are you sure you want to delete this competition? (Y/N): ";




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

    public static final String MESSAGE_COMPETITION_NOT_SIMULATED =
            "This competition has not been simulated yet.";

    public static final String MESSAGE_DELETE_COMPETITION_WARNING =
            "\nDeleting this competition will also remove its results and update any related\n" +
                    "information stored in the system.\n";

    public static final String MESSAGE_COMPETITION_DELETE_CANCELLED =
            "Competition deletion cancelled.";


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

    public static final String ERROR_NO_AVAILABLE_COMPETITIONS =
            "There are no competitions available.";


    // FORMATTING
    public static final String SEPARATOR = "----------";

    public static final String TABLE_BORDER =
            "+------------+-----------------------------------------------------------------------------+------+--------+--------+";

}

