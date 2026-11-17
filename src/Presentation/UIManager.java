package Presentation;

import Business.Entities.Athlete;
import Business.Entities.Competition;
import Business.Entities.Delegation;
////
import Persistence.CompetitionDAO;
import Persistence.DelegationDAO;
////
import Business.ErrorType;


import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class UIManager {

    private final Scanner s;

    public UIManager(){
        this.s = new Scanner(System.in);
    }

    public static Integer askForIntOrEnter(String prompt, int min, int max) {
        boolean valid = false;
        Integer finalResult = null;

        while (!valid) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                finalResult = null;
                valid = true;
            } else {
                try {
                    int value = Integer.parseInt(input);
                    if (value >= min && value <= max) {
                        finalResult = value;
                        valid = true;
                    } else {
                        System.out.println(String.format(MessagesEN.ERROR_OUT_OF_RANGE, min, max));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(MessagesEN.ERROR_NOT_A_NUMBER);
                }
            }
        }
        return finalResult;
    }

    public int printDelegations(ArrayList<Delegation> delegations, int max) {

        System.out.println(MessagesEN.DELEGATIONS_QUANTITY);
        int size = askForInt(MessagesEN.PROMPT_CHOOSE_OPTION, 1, delegations.size());
        System.out.println();

        int start = 0;
        Integer choice = null;

        while (choice == null) {
            int end = Math.min(start + size, delegations.size());

            for (int i = start; i < end; i++) {
                Delegation d = delegations.get(i);
                System.out.println("    " + (i + 1) + ") " + d.getCode() + " " + d.getName());
            }

            System.out.println();
            choice = askForIntOrEnter(MessagesEN.PROMPT_CHOOSE_OR_ENTER, 0, delegations.size());
            System.out.println();

            if (choice == null) {
                start = end;
            }
        }
        choice--;
        return choice;
    }

    public void printDelegationStats (ArrayList<Athlete> athletes, Delegation delegation) {
        System.out.printf("-| %s | %s |-\n", delegation.getCode(), delegation.getName());
        System.out.println("\t" + MessagesEN.DELEGATIONS_GOLD_MEDALS + delegation.getGoldMedals());
        System.out.println("\t" + MessagesEN.DELEGATIONS_SILVER_MEDALS + delegation.getSilverMedals());
        System.out.println("\t" + MessagesEN.DELEGATIONS_BRONZE_MEDALS + delegation.getBronzeMedals());

        int numathletes = 0;
        for (Athlete a : athletes) {
            if (a.getDelegationCode().equals(delegation.getCode())) {
                numathletes++;
            }
        }

        System.out.printf("\n\t%d athletes\n", numathletes);
        System.out.println("\t" + MessagesEN.SEPARATOR);

        for (Athlete a : athletes) {
            if (a.getDelegationCode().equals(delegation.getCode())) {
                System.out.println("\t" + a.getName());
            }
        }
        System.out.println();
    }

    public void printMedalTable(ArrayList<Delegation> delegations) {

        String body = "| %-10s | %-75s | %4s | %6s | %6s |%n";

        System.out.println(MessagesEN.TABLE_BORDER);
        System.out.printf(body, "Code", "Delegation", "Gold", "Silver", "Bronze");
        System.out.println(MessagesEN.TABLE_BORDER);

        for (Delegation d : delegations) {
            System.out.printf(body, d.getCode(), d.getName(), d.getGoldMedals(), d.getSilverMedals(), d.getBronzeMedals());
        }
        System.out.println(MessagesEN.TABLE_BORDER);
    }

    public static MAIN_MENU_OPTIONS printMenu() {
        System.out.println(MessagesEN.MAIN_MENU_HEADER);
        System.out.println();
        System.out.println(MessagesEN.MAIN_MENU_DELEGATIONS);
        System.out.println(MessagesEN.MAIN_MENU_ATHLETES);
        System.out.println(MessagesEN.MAIN_MENU_COMPETITIONS);
        System.out.println(MessagesEN.MAIN_MENU_SIMULATE);
        System.out.println();
        System.out.println(MessagesEN.MAIN_MENU_EXIT);

        int choice = askForInt(MessagesEN.PROMPT_CHOOSE_OPTION,0, 4);

        return switch (choice){
            case 1 -> MAIN_MENU_OPTIONS.DELEGATIONS;
            case 2 -> MAIN_MENU_OPTIONS.ATHLETES;
            case 3 -> MAIN_MENU_OPTIONS.COMPETITIONS;
            case 4 -> MAIN_MENU_OPTIONS.SIMULATE;
            default -> MAIN_MENU_OPTIONS.EXIT;
        };
    }

    public static DELEGATIONS_MENU_OPTIONS printDelegationsMenu() {
        System.out.println(MessagesEN.DELEGATIONS_MENU_HEADER);
        System.out.println();
        System.out.println(MessagesEN.DELEGATIONS_MENU_LIST);
        System.out.println(MessagesEN.DELEGATIONS_MENU_MEDALS);
        System.out.println();
        System.out.println(MessagesEN.DELEGATIONS_MENU_BACK);

        int choice = askForInt(MessagesEN.PROMPT_CHOOSE_OPTION,0, 2);

        return switch (choice){
            case 1 -> DELEGATIONS_MENU_OPTIONS.LIST_DELEGATIONS;
            case 2 -> DELEGATIONS_MENU_OPTIONS.SHOW_MEDALS;
            default -> DELEGATIONS_MENU_OPTIONS.BACK;
        };
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
    public static int askForInt(String prompt, int min, int max) {
        while (true) {
            System.out.printf(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println(String.format(MessagesEN.ERROR_OUT_OF_RANGE, min, max));
                    continue;
                }
                return value;
            } catch (NumberFormatException notANumber) {
                System.out.println(MessagesEN.ERROR_NOT_A_NUMBER);
            }
        }
    }




    public void showDelegations(ArrayList<Delegation> delegations){

        for (Delegation delegation : delegations) {
            System.out.println("Code: " + delegation.getCode() + " | " +
                    "Name: " + delegation.getName() + " | " +
                    delegation.getGoldMedals() + " | " +
                    delegation.getSilverMedals() +  " | " +
                    delegation.getBronzeMedals());
        }
    }


    // Formato de fecha
    private final String DATE_FORMAT = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    public void showAthletes(ArrayList<Athlete> athletes){

        for (Athlete athlete : athletes) {
            System.out.println("ID: " + athlete.getId() + " | " +
                    "Nombre: " + athlete.getName() + " | " +
                    sdf.format(athlete.getBirthDate()) + " | " +
                    athlete.getDelegationCode() + " | " +
                    athlete.getProfile() +  " | " +
                    athlete.getSpeed() + " | " +
                    athlete.getEndurance() +  " | " +
                    athlete.getStrength() +  " | " +
                    athlete.getTechnique() +  " | " +
                    athlete.getPrecision());
        }

    }



    // Formato datetime
    private final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    SimpleDateFormat sdtf = new SimpleDateFormat(DATETIME_FORMAT);

    public void showCompetitions(ArrayList<Competition> competitions){

        for (Competition competition : competitions) {
            System.out.println("ID: " + competition.getId() + " | " +
                    competition.getFormat() + " | " +
                    "Name: " + competition.getName() + " | " +
                    sdtf.format(competition.getDateTime()) + " | " +
                    competition.getSpeed() + " | " +
                    competition.getEndurance() +  " | " +
                    competition.getStrength() +  " | " +
                    competition.getTechnique() +  " | " +
                    competition.getPrecision() +  " | " +
                    competition.getAthleteIds());

            // si son null que no los muestre
            if (competition.getResults() != null){
                System.out.println(competition.getGold() +   " | " +
                        competition.getSilver() +   " | " +
                        competition.getBronze());
            }

        }

    }


    public String askForCSV(String message) {

        String in;

        do {
            System.out.print(message);
            in = s.nextLine().trim();

            // Mira a ver si es un array de caracteres vacio o si el string esta unicamente formado por numeros
            if (in.isEmpty()) {
                System.out.println(MessagesEN.MESSAGE_INVALID_INPUT);
            }

        } while (in.isEmpty());

        return in;
    }

    public String askForString(String message) {

        String in;

        do{
            System.out.print(message);

            // Lee la linea como string hasta la barra del enter y recorta espacios laterales
            in = s.nextLine().trim();

            // Mira a ver si es un array de caracteres vacio o si el string esta unicamente formado por numeros
            if (in.isEmpty() || isNumber(in)){
                System.out.println(MessagesEN.MESSAGE_INVALID_INPUT);
                System.out.println();
            }

            // Repetira el proceso hasta que salga un String puro
        } while (in.isEmpty() || isNumber(in));


        return in;
    }

    private static boolean isNumber (String text){
        return text.matches("-?[0-9]+(\\.[0-9]+)?");
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
            if (parts.length >= 9) {

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
                System.out.println(MessagesEN.MESSAGE_IMPORT_FAILED);

                for (ErrorType error : errors) {
                    showError(error);
                }
            }

            System.out.println();
        }
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
        };

        System.out.println(message);
    }

    // Muestra cualquier mensaje predefinido
    public void showMessage(String message) {
        System.out.println(message);
    }


    public void showSimulationResults(
            Competition competition,
            ArrayList<String> athleteNames,
            ArrayList<ArrayList<Double>> results,
            int number,
            int total) {

        System.out.println("\nCompetition " + number + " of " + total);
        System.out.println("------------------");

        System.out.println("\n-| " + competition.getId()
                + " | " + competition.getName() + " |-");

        System.out.println("\nFormat: " + competition.getFormat());
        System.out.println("Simulation format: TIMED");

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

    public void waitForEnter(String message) {
        System.out.println("\n" + message);
        s.nextLine();
    }
}

