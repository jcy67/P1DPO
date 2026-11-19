package Presentation;
import Business.ErrorType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class UIManager {

    private final Scanner s;

    public UIManager(){
        this.s = new Scanner(System.in);
    }

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

    public ArrayList<String> printDelegations(
            ArrayList<ArrayList<String>> delegations) {

        if (delegations.isEmpty()) {
            showError(ErrorType.NO_DELEGATIONS);
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

            // En la última página cambia el prompt
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

            // Paginación infinita
            start = end == items.size() ? 0 : end;
        }
    }

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


    public MAIN_MENU_OPTIONS printMenu() {

        System.out.println("\n"+ MessagesEN.HEADER_MAIN_MENU);
        System.out.println();

        System.out.println(MessagesEN.LABEL_MAIN_MENU_DELEGATIONS);
        System.out.println(MessagesEN.LABEL_MAIN_MENU_ATHLETES);
        System.out.println(MessagesEN.LABEL_MAIN_MENU_COMPETITIONS);
        System.out.println(MessagesEN.LABEL_MAIN_MENU_SIMULATE);
        System.out.println();
        System.out.println(MessagesEN.LABEL_MAIN_MENU_EXIT);

        int choice = askForInt(MessagesEN.PROMPT_CHOOSE_OPTION,0, 4);

        return switch (choice){
            case 1 -> MAIN_MENU_OPTIONS.DELEGATIONS;
            case 2 -> MAIN_MENU_OPTIONS.ATHLETES;
            case 3 -> MAIN_MENU_OPTIONS.COMPETITIONS;
            case 4 -> MAIN_MENU_OPTIONS.SIMULATE;
            default -> MAIN_MENU_OPTIONS.EXIT;
        };
    }

    public DELEGATIONS_MENU_OPTIONS printDelegationsMenu() {

        System.out.println("\n" + MessagesEN.HEADER_DELEGATIONS_MENU);
        System.out.println();

        System.out.println(MessagesEN.LABEL_DELEGATIONS_MENU_LIST);
        System.out.println(MessagesEN.LABEL_DELEGATIONS_MENU_MEDALS);
        System.out.println();
        System.out.println(MessagesEN.LABEL_DELEGATIONS_MENU_BACK);

        int choice = askForInt(MessagesEN.PROMPT_CHOOSE_OPTION,0, 2);

        return switch (choice){
            case 1 -> DELEGATIONS_MENU_OPTIONS.LIST_DELEGATIONS;
            case 2 -> DELEGATIONS_MENU_OPTIONS.SHOW_MEDALS;
            default -> DELEGATIONS_MENU_OPTIONS.BACK_FROM_DELEGATION;
        };
    }

    public ATHLETE_MENU_OPTIONS printAthletesMenu() {

        System.out.println("\n" + MessagesEN.HEADER_ATHLETES_MENU);
        System.out.println();

        System.out.println(MessagesEN.LABEL_ATHLETES_MENU_REGISTER);
        System.out.println(MessagesEN.LABEL_ATHLETES_MENU_LIST);
        System.out.println(MessagesEN.LABEL_ATHLETES_MENU_DELETE);
        System.out.println(MessagesEN.LABEL_ATHLETES_MENU_BACK);

        System.out.println();

        int choice = askForInt(MessagesEN.PROMPT_CHOOSE_OPTION, 0, 3);

        return switch (choice) {
            case 1 -> ATHLETE_MENU_OPTIONS.REGISTER_ATHLETE;
            case 2 -> ATHLETE_MENU_OPTIONS.LIST_SEARCH_ATHLETES;
            case 3 -> ATHLETE_MENU_OPTIONS.DELETE_ATHLETE;
            default -> ATHLETE_MENU_OPTIONS.BACK_FROM_ATHLETE;
        };
    }

    public COMPETITION_MENU_OPTIONS printCompetitionsMenu() {

        System.out.println("\n" + MessagesEN.HEADER_COMPETITIONS_MENU);
        System.out.println();

        System.out.println(MessagesEN.LABEL_COMPETITIONS_MENU_LOAD);
        System.out.println(MessagesEN.LABEL_COMPETITIONS_MENU_LIST);
        System.out.println(MessagesEN.LABEL_COMPETITIONS_MENU_DELETE);
        System.out.println(MessagesEN.LABEL_COMPETITIONS_MENU_BACK);

        System.out.println();

        int choice = askForInt(MessagesEN.PROMPT_CHOOSE_OPTION, 0, 3);

        return switch (choice) {
            case 1 -> COMPETITION_MENU_OPTIONS.LOAD_COMPETITION;
            case 2 -> COMPETITION_MENU_OPTIONS.LIST_COMPETITION;
            case 3 -> COMPETITION_MENU_OPTIONS.DELETE_COMPETITION;
            default -> COMPETITION_MENU_OPTIONS.BACK_FROM_COMPETITION;
        };
    }

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


    // Escoge el tipo de error que se quiere mostrar por pantalla
    public void showError(ErrorType error) {

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
            case OUT_OF_RANGE -> MessagesEN.ERROR_OUT_OF_RANGE; // No usar desde aquií por los %d, usar showMessage
            case NO_DELEGATIONS -> MessagesEN.ERROR_NO_DELEGATIONS;
            case INVALID_SIMULATION_DATA -> MessagesEN.ERROR_INVALID_SIMULATION_DATA;
        };

        System.out.println(message);
    }

    // Muestra cualquier mensaje predefinido
    public void showMessage(String message) {
        System.out.println(message);
    }

    public void waitForEnter(String message) {
        System.out.println("\n" + message);
        s.nextLine();
    }

    /**
     * Asks for an integer until the user enters one within the
     * given range, printing an error message after each invalid attempt.
     *
     * @param prompt the text to show before the range
     * @param min  minimum accepted value (inclusive)
     * @param max  maximum accepted value (inclusive)
     * @return the integer entered, between the max and the min
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

    public String askForString(String message) {

        String in;

        do{
            System.out.print(message);

            // Lee la linea como string hasta la barra del enter y recorta espacios laterales
            in = s.nextLine().trim();

            // Mira a ver si es un array de caracteres vacio o si el string esta unicamente formado por numeros
            if (in.isEmpty() || isNumber(in)){
                System.out.println(MessagesEN.ERROR_INVALID_INPUT);
                System.out.println();
            }

            // Repetira el proceso hasta que salga un String puro
        } while (in.isEmpty() || isNumber(in));


        return in;
    }

    private static boolean isNumber (String text){
        return text.matches("-?[0-9]+(\\.[0-9]+)?");
    }

    public String askForCSV(String message) {

        String in;

        do {
            System.out.print(message);
            in = s.nextLine().trim();

            // Mira a ver si es un array de caracteres vacio o si el string esta unicamente formado por numeros
            if (in.isEmpty()) {
                System.out.println(MessagesEN.ERROR_INVALID_INPUT);
            }

        } while (in.isEmpty());

        return in;
    }

    // Muestra las competiciones importadas y sus errores
    public void showImportResults(ArrayList<String> lines, ArrayList<ArrayList<ErrorType>> results) {

        for (int i = 0; i < results.size(); i++) {

            String[] parts = lines.get(i + 1).split(",", -1);
            ArrayList<ErrorType> errors = results.get(i);

            System.out.println();
            System.out.println("Competition " + (i + 1) + " of " + results.size());
            System.out.println("------------------");
            System.out.println();

            // Comprobar que existan los campos antes de mostrarlos
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

            // Mostrar si se ha importado o los errores detectados
            if (errors.isEmpty()) {
                System.out.println(MessagesEN.HEADER_COMPETITION_LOADED);

            } else {
                System.out.println(MessagesEN.ERROR_IMPORT_FAILED);

                for (ErrorType error : errors) {
                    showError(error);
                }
            }

            System.out.println();
        }
    }



//    public void showDelegations(ArrayList<Delegation> delegations){
//
//        for (Delegation delegation : delegations) {
//            System.out.println("Code: " + delegation.getCode() + " | " +
//                    "Name: " + delegation.getName() + " | " +
//                    delegation.getGoldMedals() + " | " +
//                    delegation.getSilverMedals() +  " | " +
//                    delegation.getBronzeMedals());
//        }
//    }

//
//    // Formato de fecha
//    private final String DATE_FORMAT = "yyyy-MM-dd";
//    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//
//    public void showAthletes(ArrayList<Athlete> athletes){
//
//        for (Athlete athlete : athletes) {
//            System.out.println("ID: " + athlete.getId() + " | " +
//                    "Nombre: " + athlete.getName() + " | " +
//                    sdf.format(athlete.getBirthDate()) + " | " +
//                    athlete.getDelegationCode() + " | " +
//                    athlete.getProfile() +  " | " +
//                    athlete.getSpeed() + " | " +
//                    athlete.getEndurance() +  " | " +
//                    athlete.getStrength() +  " | " +
//                    athlete.getTechnique() +  " | " +
//                    athlete.getPrecision());
//        }
//
//    }
//
//
//
//    // Formato datetime
//    private final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
//    SimpleDateFormat sdtf = new SimpleDateFormat(DATETIME_FORMAT);
//
//    public void showCompetitions(ArrayList<Competition> competitions){
//
//        for (Competition competition : competitions) {
//            System.out.println("ID: " + competition.getId() + " | " +
//                    competition.getFormat() + " | " +
//                    "Name: " + competition.getName() + " | " +
//                    sdtf.format(competition.getDateTime()) + " | " +
//                    competition.getSpeed() + " | " +
//                    competition.getEndurance() +  " | " +
//                    competition.getStrength() +  " | " +
//                    competition.getTechnique() +  " | " +
//                    competition.getPrecision() +  " | " +
//                    competition.getAthleteIds());
//
//            // si son null que no los muestre
//            if (competition.getResults() != null){
//                System.out.println(competition.getGold() +   " | " +
//                        competition.getSilver() +   " | " +
//                        competition.getBronze());
//            }
//
//        }
//
//    }
}

