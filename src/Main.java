import Business.OlympicsManager;
import Persistence.CompetitionDAO;
import Persistence.DelegationDAO;
import Persistence.AthleteDAO;
import Presentation.Controller;
import Presentation.UIManager;

/**
 * Entry point of the Olympic Games application.
 * Initializes the required managers and data access objects
 * and starts the application through the Controller.
 */
public class Main {

    /**
     * Initializes the application components and starts the main menu.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        UIManager ui = new UIManager();
        DelegationDAO delegationDAO = new DelegationDAO();
        AthleteDAO athleteDAO = new AthleteDAO();
        CompetitionDAO competitionDAO = new CompetitionDAO();
        OlympicsManager olympicsManager = new OlympicsManager(delegationDAO,athleteDAO,competitionDAO);

        Controller controller = new Controller(olympicsManager, ui);
        controller.run();
    }

}