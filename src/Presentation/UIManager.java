package Presentation;

import Business.Entities.Athlete;
import Business.Entities.Competition;
import Business.Entities.Delegation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UIManager {

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


}
