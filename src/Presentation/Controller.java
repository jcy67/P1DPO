package Presentation;

import Business.OlympicsManager;
import Business.ErrorType;

import com.google.gson.JsonParseException;
import java.io.IOException;

import java.util.ArrayList;

public class Controller {

    private final UIManager uiManager;
    private final OlympicsManager olympicsManager;

    public Controller(OlympicsManager om, UIManager ui) {
        this.uiManager = ui;
        this.olympicsManager = om;
    }

    public void run() {

        boolean running = true;

        while (running) {

            try {

                MAIN_MENU_OPTIONS option = uiManager.printMenu();

                switch (option) {

                    case DELEGATIONS -> manageDelegations();

                    case ATHLETES -> manageAthletes();

                    case COMPETITIONS -> manageCompetitions();

                    case SIMULATE -> simulateOlympicGames();

                    case EXIT -> {
                        uiManager.showMessage(MessagesEN.MESSAGE_GOODBYE);
                        running = false;
                    }
                }

            } catch (IOException e) {
                uiManager.showError(ErrorType.FILE_ERROR);

            } catch (JsonParseException e) {
                uiManager.showError(ErrorType.JSON_ERROR);

            } catch (NumberFormatException e) {
                uiManager.showError(ErrorType.NOT_A_NUMBER);

            } catch (IllegalArgumentException e) {
                uiManager.showMessage(e.getMessage());

            } catch (IllegalStateException e){
                uiManager.showError(ErrorType.INVALID_SIMULATION_DATA);

            }
        }
    }

    private void manageDelegations() throws IOException {

        DELEGATIONS_MENU_OPTIONS option;

        do {

            option = uiManager.printDelegationsMenu();

            switch (option) {

                case LIST_DELEGATIONS -> {
                    listDelegations();
                    uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                }

                case SHOW_MEDALS -> {
                    showMedals();
                    uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                }

                case BACK_FROM_DELEGATION -> {
                }
            }

        } while (option != DELEGATIONS_MENU_OPTIONS.BACK_FROM_DELEGATION);
    }

    private void listDelegations() throws IOException {

        ArrayList<String> delegation = uiManager.printDelegations(
                olympicsManager.getDelegationListByName()
        );

        if (delegation == null) {
            return;
        }

        uiManager.printDelegationStats(
                delegation,
                olympicsManager.getDelegationAthleteNames(delegation.get(0))
        );
    }

    private void showMedals() throws IOException {

        uiManager.printMedalTable(olympicsManager.getDelegationMedalTable());
    }


    private void manageAthletes() {

        ATHLETE_MENU_OPTIONS option;

        do {

            option = uiManager.printAthletesMenu();

            switch (option) {

                case REGISTER_ATHLETE -> {
                    uiManager.showMessage("Register athlete - Not implemented yet.");
                    uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                }

                case LIST_SEARCH_ATHLETES -> {
                    uiManager.showMessage("List/Search athletes - Not implemented yet.");
                    uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                }

                case DELETE_ATHLETE -> {
                    uiManager.showMessage("Delete athlete - Not implemented yet.");
                    uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                }

                case BACK_FROM_ATHLETE -> {
                }
            }

        } while (option != ATHLETE_MENU_OPTIONS.BACK_FROM_ATHLETE);
    }

    private void manageCompetitions() throws IOException {

        COMPETITION_MENU_OPTIONS option;

        do {

            option = uiManager.printCompetitionsMenu();

            switch (option) {

                case LOAD_COMPETITION -> {
                    loadCompetitions();
                    uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                }

                case LIST_COMPETITION -> {
                    uiManager.showMessage("Show competitions - Not implemented yet.");
                    uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                }

                case DELETE_COMPETITION -> {
                    uiManager.showMessage("Delete competition - Not implemented yet.");
                    uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                }

                case BACK_FROM_COMPETITION -> {
                }
            }

        } while (option != COMPETITION_MENU_OPTIONS.BACK_FROM_COMPETITION);
    }

    private void loadCompetitions() throws IOException{

        String filename = uiManager.askForCSV(MessagesEN.PROMPT_ASK_FOR_CSV);
        uiManager.showMessage(MessagesEN.MESSAGE_OPENING_FILE);

        ArrayList<String> lines = olympicsManager.readCompetitionCSV(filename);
        uiManager.showMessage(MessagesEN.MESSAGE_VALIDATING_COMPETITIONS);

        ArrayList<ArrayList< ErrorType >> errors= olympicsManager.importCompetitions(lines);
        uiManager.showImportResults(lines, errors);

    }

    private void simulateOlympicGames() throws IOException {

        olympicsManager.loadRelations();

        ArrayList<String> competitionIds =
                olympicsManager.getCompetitionIds();

        int total = competitionIds.size();

        if (total == 0) {
            uiManager.showMessage(MessagesEN.ERROR_NO_COMPETITIONS);
            return;
        }

        uiManager.showMessage(MessagesEN.HEADER_SIMULATION_START);

        for (int i = 0; i < total; i++) {

            String id = competitionIds.get(i);

            ArrayList<ArrayList<Double>> results =
                    olympicsManager.simulateCompetition(id);

            uiManager.showSimulationResults(
                    olympicsManager.getSimulationCompetitionInfo(id),
                    olympicsManager.getSimulationAthleteNames(id),
                    results,
                    i + 1,
                    total
            );

            if (i < total - 1) {
                uiManager.waitForEnter(MessagesEN.PROMPT_NEXT_SIMULATION);
            } else {
                uiManager.waitForEnter(MessagesEN.PROMPT_FINISH_SIMULATION);
            }
        }

        olympicsManager.saveSimulationResults();
        olympicsManager.updateDelegationMedals();

        uiManager.showMessage(MessagesEN.HEADER_SIMULATION_COMPLETED);
    }
}
