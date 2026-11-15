package Presentation;

import Business.OlympicsManager;

public class Controller {

    private final UIManager uiManager;
    private final OlympicsManager olympicsManager;

    public Controller(OlympicsManager om, UIManager ui) {
        this.uiManager = ui;
        this.olympicsManager = om;
    }

    public void run(){

        uiManager.showDelegations(olympicsManager.getDelegationDAO());

        uiManager.showAthletes(olympicsManager.getAthleteDAO());

        uiManager.showCompetitions(olympicsManager.getCompetitionDAO());

    }
}
