package Presentation;

import Business.ErrorType;
import Business.OlympicsManager;
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

    public void run(){

//        uiManager.showDelegations(olympicsManager.getDelegationDAO());
//
//        uiManager.showAthletes(olympicsManager.getAthleteDAO());
//
        try {
            uiManager.showCompetitions(olympicsManager.getCompetitionDAO());

            loadCompetitions();
            uiManager.showCompetitions(olympicsManager.getCompetitionDAO());

            simulateOlympicGames();

        } catch (IOException e) {
            uiManager.showError(ErrorType.FILE_ERROR);

            // Gson puede provocarla
        } catch (JsonParseException e) {
            uiManager.showError(ErrorType.JSON_ERROR);
        }

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

        olympicsManager.prepareSimulation();

        int total = olympicsManager.getCompetitions().size();

        if (total == 0) {
            uiManager.showMessage(MessagesEN.ERROR_NO_COMPETITIONS);
            return;
        }

        uiManager.showMessage(MessagesEN.SIMULATION_START);

        for (int i = 0; i < total; i++) {

            ArrayList<ArrayList<Double>> results =
                    olympicsManager.simulateCompetition(
                            olympicsManager.getCompetitions().get(i)
                    );

            uiManager.showSimulationResults(
                    olympicsManager.getCompetitions().get(i),
                    olympicsManager.getSimulationAthleteNames(
                            olympicsManager.getCompetitions().get(i)
                    ),
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

        uiManager.showMessage(MessagesEN.SIMULATION_COMPLETED);
    }
}
