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
}
