package Presentation;

import Business.Entities.Athlete;
import Business.Entities.Competition;
import Business.Entities.Delegation;
import Business.ErrorType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class UIManager {

    private final Scanner s;

    public UIManager(){
        this.s = new Scanner(System.in);
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
}