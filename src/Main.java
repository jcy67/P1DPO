import Business.OlympicsManager;
import Persistence.CompetitionDAO;
import Persistence.DelegationDAO;
import Persistence.AthleteDAO;
import Presentation.Controller;
import Presentation.UIManager;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        try {

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
