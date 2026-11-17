package Presentation;

import Business.Entities.Athlete;
import Business.Entities.Delegation;
import Business.OlympicsManager;
import Persistence.DelegationDAO;

import java.util.ArrayList;

public class Controller {

    private final UIManager uiManager;
    private final OlympicsManager olympicsManager;

    public Controller(OlympicsManager om, UIManager ui) {
        this.uiManager = ui;
        this.olympicsManager = om;
    }

    public void manageDelegations() {
        DELEGATIONS_MENU_OPTIONS options = uiManager.printDelegationsMenu();
        switch (options) {
            case LIST_DELEGATIONS -> listDelegations();
            case SHOW_MEDALS -> showMedals();
        }
    }

    public void showMedals() {
        ArrayList<Delegation> delegations = olympicsManager.sortDelegationsByMedals();
        uiManager.printMedalTable(delegations);
    }

    public void listDelegations() {
        ArrayList<Delegation> delegations = olympicsManager.sortDelegationsByName();
        int delegation = uiManager.printDelegations(delegations, delegations.size());
        ArrayList<Athlete> athletes = olympicsManager.getAthleteDAO();
        uiManager.printDelegationStats(athletes, delegations.get(delegation));
    }

    public void run(){

        boolean running = true;
        while(running){
            MAIN_MENU_OPTIONS options = UIManager.printMenu();

            switch (options){
                case DELEGATIONS -> manageDelegations();
                case EXIT -> running = false;
            }
        }

    }
}
