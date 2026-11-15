import Business.Entities.Athlete;
import Business.Entities.Competition;
import Business.Entities.Delegation;
import Business.OlympicsManager;
import Persistence.CompetitionDAO;
import Persistence.DelegationDAO;
import Persistence.AthleteDAO;
import Presentation.Controller;
import Presentation.UIManager;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        UIManager ui = new UIManager();
        DelegationDAO delegationDAO = new DelegationDAO();
        AthleteDAO athleteDAO = new AthleteDAO();
        CompetitionDAO competitionDAO = new CompetitionDAO();
        OlympicsManager olympicsManager = new OlympicsManager(delegationDAO,athleteDAO,competitionDAO);

        Controller controller = new Controller(olympicsManager, ui);


        ArrayList<Delegation> delegations = delegationDAO.readDelegations();
        for (Delegation delegation : delegations) {
            System.out.println("Code: " + delegation.getCode() + " | " +
                    "Name: " + delegation.getName() + " | " +
                    delegation.getGoldMedals() + " | " +
                    delegation.getSilverMedals() +  " | " +
                    delegation.getBronzeMedals());
        }


        ArrayList<Athlete> athletes = athleteDAO.readAthletes();
        // Formato de fecha
        final String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

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

        ArrayList<Competition> competitions = competitionDAO.readCompetitions();
        // Formato datetime
        final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdtf = new SimpleDateFormat(DATETIME_FORMAT);

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

        controller.run();
    }

}