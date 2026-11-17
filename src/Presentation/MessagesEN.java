package Presentation;

public class MessagesEN {

    public static final String MENU_LABEL_ADMIT_POU = "Admit a Pou";
    public static final String MENU_LABEL_RELEASE_POU = "Release a Pou";
    public static final String MENU_LABEL_ACCESS_POU_RECORDS = "Access a Pou's records";
    public static final String MENU_LABEL_LIST_POUS_IN_DAYCARE = "List the Pous currently in the daycare";
    public static final String MENU_LABEL_CARE_FOR_POUS = "Care for a Pou";
    public static final String MENU_LABEL_EXIT = "Exit";

    public static final String MENU_INTRO_ADMIT_POU = "Let's admit a Pou!";
    public static final String MENU_INTRO_RELEASE_POU = "Let's release a Pou!";
    public static final String MENU_INTRO_ACCESS_POU_RECORDS = "Let's look up a Pou's records!";
    public static final String MENU_INTRO_LIST_POUS_IN_DAYCARE = "Let's see who's in the daycare!";
    public static final String MENU_INTRO_CARE_FOR_POUS = "Let's care for a Pou!";
    public static final String MENU_INTRO_EXIT = "Bye bye, Poukie!";

    public static final String CARE_LABEL_CHECK_ON = "Check on the Pou";
    public static final String CARE_LABEL_FEED = "Feed the Pou";
    public static final String CARE_LABEL_PLAY = "Play with the Pou";
    public static final String CARE_LABEL_SLEEP = "Put the Pou to sleep";
    public static final String CARE_LABEL_BACK = "Back to the main menu";

    public static final String FOOD_LABEL_HEALTHY = "Healthy food";
    public static final String FOOD_LABEL_FAST_FOOD = "Fast food";

    public static final String MOOD_LABEL_THRIVING = "thriving";
    public static final String MOOD_LABEL_FINE = "fine";
    public static final String MOOD_LABEL_GRUMPY = "grumpy";
    public static final String MOOD_LABEL_MISERABLE = "miserable";


    public static final String PROMPT_ASK_FOR_CSV = "Enter the name of the CSV file: ";
    public static final String PROMPT_POU_NAME = "Whats your Pou's name? ";
    public static final String PROMPT_HOUSEHOLD_NAME = "Ohh such a beautiful name! What about the Daycare.Entities.Household name? ";
    public static final String PROMPT_MEMBER_NAME = "Who is handing the Pou over? ";
    public static final String PROMPT_COLLECTOR_NAME = "Who is collecting the Pou? ";
    public static final String PROMPT_MEMBER_NAMES = "Who are the members of your household? (member1, member2, ..., memberN)\nRemember they will be the only people allowed to drop off/collect your pookie: ";
    public static final String PROMPT_FOOD_POINTS = "Food points to give";
    public static final String PROMPT_SLEEP_HOURS = "Hours to sleep";
    public static final String PROMPT_METER_FOOD = "How much has this pou eaten today?";
    public static final String PROMPT_METER_HEALTH = "How healthy and strong is it feeling today?";
    public static final String PROMPT_METER_FUN = "How much fun has it had today?";
    public static final String PROMPT_METER_ENERGY = "How much energy does it have?";
    public static final String PROMPT_CHOOSE_OPTION = "Choose an option";
    public static final String PROMPT_CONTINUE = "Press Enter to continue...";
    public static final String PROMPT_IS_POU_NEW = "Is this the Pou's first time at the daycare? (y/n): ";
    public static final String PROMPT_POU_QUERY = "Pou's name (or \"name, household\"): ";
    public static final String PROMPT_PICK_POU = "Which Pou? (id number or name): ";
    public static final String PROMPT_CHOOSE_AMONG_POUS = "We have %d Pous called %s. Which one do you mean?";
    public static final String PROMPT_NEXT_SIMULATION = "<Press ENTER to simulate the next competition>";
    public static final String PROMPT_FINISH_SIMULATION = "<Press ENTER to finish the simulation>";

    public static final String HEADER_ADMITTED = "Pou admitted:";
    public static final String HEADER_COLLECTED = "Pou collected:";
    public static final String HEADER_CARE = "Pou status:";
    public static final String MAIN_MENU_HEADER = "=== POU DAYCARE - FRONT DESK ===";
    public static final String CARE_MENU_HEADER = "=== CARING FOR A POU ===";
    public static final String FOOD_TYPE_HEADER = "Which type of food?";
    public static final String HEADER_POUS_IN_DAYCARE = "Pous currently in the daycare:";
    public static final String HEADER_ALL_RECORDS = "All Pous on record:";
    public static final String MESSAGE_WELCOME = "Welcome to the Pou Daycare front desk!";
    public static final String MESSAGE_OPENING = "Welcome to the Pou Daycare front desk!";
    public static final String MESSAGE_OPENING_FILE = "Opening file...";
    public static final String MESSAGE_VALIDATING_COMPETITIONS = "Validating file...";
    public static final String HEADER_COMPETITION_LOADED = "Competition loaded successfully.\n";
    public static final String SIMULATION_START = "\nSimulate Olympic Games\n" + "----------------------\n" + "Starting Olympic Games simulation...";
    public static final String SIMULATION_COMPLETED = "\nOlympic Games simulation completed";

    public static final String MESSAGE_NO_POUS_IN_DAYCARE = "There are no Pous in the daycare right now.";
    public static final String MESSAGE_NO_RECORDS = "There are no records to show.";
    public static final String MESSAGE_NOT_A_NUMBER = "That is not a valid number.";
    public static final String MESSAGE_VALUE_OUT_OF_RANGE = "The value must be between %d and %d.";
    public static final String MESSAGE_ASSUMED_POU = "Only one Pou is called %s - assuming %s.";
    public static final String MESSAGE_INVALID_INPUT = "Invalid input. Please try again.";
    public static final String MESSAGE_IMPORT_FAILED = "Error: The competition could not be loaded.";

    // ERROR TYPES
    public static final String ERROR_INVALID_FIELDS = "Invalid number of CSV fields.";
    public static final String ERROR_EMPTY_ID = "Competition identifier cannot be empty.";
    public static final String ERROR_INVALID_ID = "Invalid competition identifier. Expected format: CMP-XXXX.";
    public static final String ERROR_DUPLICATE_ID = "Competition identifier already exists.";
    public static final String ERROR_INVALID_FORMAT = "Invalid competition format. Use TIMED, SCORED or MEASURED.";
    public static final String ERROR_EMPTY_NAME = "Competition name cannot be empty.";
    public static final String ERROR_INVALID_DATE = "Invalid date. Expected format: yyyy-MM-dd HH:mm.";
    public static final String ERROR_INVALID_WEIGHT = "Competition weights must be between 0.0 and 1.0.";
    public static final String ERROR_INVALID_WEIGHT_SUM = "The sum of competition weights must equal 1.0.";
    public static final String ERROR_INVALID_ATHLETE = "One or more athlete identifiers do not exist.";
    public static final String ERROR_DUPLICATE_ATHLETE = "An athlete cannot participate more than once in the same competition.";
    public static final String ERROR_NOT_ENOUGH_ATHLETES = "A competition must have at least 3 different athletes.";
    public static final String ERROR_FILE = "Error reading or writing the file.";
    public static final String ERROR_JSON = "Invalid JSON format.";
    public static final String ERROR_NO_COMPETITIONS = "Error: There are no competitions to simulate.";


    public static final String LABEL_NONE_OF_THEM = "None of them";
    public static final String PRESENCE_IN_DAYCARE = "in the daycare";
    public static final String PRESENCE_LAST_SEEN_PREFIX = "last seen on ";
    public static final String PRESENCE_NOT_ADMITTED = "not admitted yet";

    public static final String INPUT_YES = "y";
    public static final String INPUT_NO = "n";
}
