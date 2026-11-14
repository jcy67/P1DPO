import Business.Entities.Athlete;
import Business.Entities.Delegation;
import Business.OlympicsManager;
import Persistence.CompetitionDAO;
import Persistence.DelegationDAO;
import Persistence.AthleteDAO;
import Presentation.Controller;
import Presentation.UIManager;


import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        try {

        DelegationDAO delegationDAO = new DelegationDAO();

        AthleteDAO athleteDAO = new AthleteDAO();
        ArrayList<Athlete> athletes = athleteDAO.readAthletes();
        for (Athlete athlete : athletes) {
            System.out.println("ID: " + athlete.getId() + " | Nombre: " + athlete.getName());
        }

        CompetitionDAO competitionDAO = new CompetitionDAO();

            UIManager ui = new UIManager();

            DelegationDAO delegationDAO = new DelegationDAO();
            AthleteDAO athleteDAO = new AthleteDAO();
            CompetitionDAO competitionDAO = new CompetitionDAO();

            OlympicsManager olympicsManager = new OlympicsManager(delegationDAO,athleteDAO,competitionDAO);

            Controller controller = new Controller(olympicsManager, ui);
            controller.run();

        } catch (FileNotFoundException e){
            System.out.println("ERROR with the files");
        }

    }

}
