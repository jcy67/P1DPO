import Business.OlympicsManager;
import Persistence.CompetitionDAO;
import Persistence.DelegationDAO;
import Persistence.AthleteDAO;
import Presentation.Controller;
import Presentation.UIManager;

public class Main {

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
