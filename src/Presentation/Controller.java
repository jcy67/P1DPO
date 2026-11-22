package Presentation;

import Business.OlympicsManager;
import Business.ERROR_TYPE;

import com.google.gson.JsonParseException;
import java.io.IOException;

import java.util.ArrayList;

/**
 * Controls the execution of the Olympic Games application.
 * Handles user menu selections and coordinates interactions
 * between the user interface and the business logic.
 */
public class Controller {

    private final UIManager uiManager;
    private final OlympicsManager olympicsManager;

    /**
     * Creates a Controller with the required managers.
     *
     * @param om the manager responsible for the business logic
     * @param ui the manager responsible for user interaction
     */
    public Controller(OlympicsManager om, UIManager ui) {
        this.uiManager = ui;
        this.olympicsManager = om;
    }

    /**
     * Starts the application and manages the main menu.
     * Executes the selected options and handles exceptions
     * that may occur during program execution.
     */
    public void run() {

        uiManager.printWelcome();
        uiManager.showMessage(MessagesEN.MESSAGE_VERIFYING_FILES);

        try {

            String invalidFile = olympicsManager.verifyStartupFiles();

            if (invalidFile != null) {
                uiManager.showMessage(
                        String.format(MessagesEN.ERROR_STARTUP_FILE, invalidFile)
                );
                uiManager.showMessage(MessagesEN.MESSAGE_SHUTTING_DOWN);
                return;
            }

        } catch (IOException e) {
            uiManager.showError(ERROR_TYPE.FILE_ERROR);
            uiManager.showMessage(MessagesEN.MESSAGE_SHUTTING_DOWN);
            return;

        } catch (JsonParseException e) {
            uiManager.showError(ERROR_TYPE.JSON_ERROR);
            uiManager.showMessage(MessagesEN.MESSAGE_SHUTTING_DOWN);
            return;
        }

        uiManager.showMessage(MessagesEN.MESSAGE_FILES_OK);
        uiManager.showMessage(MessagesEN.MESSAGE_STARTING_PROGRAM);

        boolean running = true;

        while (running) {

            try {

                MAIN_MENU_OPTION option = uiManager.printMenu();

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
                uiManager.showError(ERROR_TYPE.FILE_ERROR);

            } catch (JsonParseException e) {
                uiManager.showError(ERROR_TYPE.JSON_ERROR);

            } catch (NumberFormatException e) {
                uiManager.showError(ERROR_TYPE.NOT_A_NUMBER);

            } catch (IllegalArgumentException e) {
                uiManager.showMessage(e.getMessage());

            } catch (IllegalStateException e) {
                uiManager.showError(ERROR_TYPE.INVALID_SIMULATION_DATA);

            }
        }
    }


    /**
     * Displays and manages the delegations submenu.
     * Allows the user to list delegations, view medal counts
     * or return to the main menu.
     *
     * @throws IOException if an error occurs while accessing delegation data
     */
    private void manageDelegations() throws IOException {

        DELEGATIONS_MENU_OPTION option;

        do {

            option = uiManager.printDelegationsMenu();

            switch (option) {

                case LIST_DELEGATIONS -> listDelegations();

                case SHOW_MEDALS -> {
                    showMedals();
                    uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                }

                case BACK_FROM_DELEGATION -> {
                }
            }

        } while (option != DELEGATIONS_MENU_OPTION.BACK_FROM_DELEGATION);
    }

    /**
     * Displays the list of delegations and allows the user
     * to select one to view its information and athletes.
     *
     * @throws IOException if an error occurs while reading delegation or athlete data
     */
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

    /**
     * Displays the medal table of all delegations,
     * sorted by the number of medals won.
     *
     * @throws IOException if an error occurs while reading delegation data
     */
    private void showMedals() throws IOException {

        uiManager.printMedalTable(olympicsManager.getDelegationMedalTable());
    }


    /**
     * Displays and manages the athletes submenu.
     * Allows the user to register, list and search athletes.
     *
     * @throws IOException if an error occurs while accessing athlete data
     */
    private void manageAthletes() throws IOException {

        ATHLETES_MENU_OPTION option;

        do {

            option = uiManager.printAthletesMenu();

            switch (option) {

                case REGISTER_ATHLETE -> {
                    registerAthlete();
                    uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                }

                case LIST_SEARCH_ATHLETES -> {
                    String term = uiManager.askSearchTerm();
                    ArrayList<ArrayList<String>> found = olympicsManager.searchAthletes(term);

                    int chosen = uiManager.chooseAthlete(found);
                    if (chosen != 0) {
                        String id = found.get(chosen - 1).get(0);
                        uiManager.printAthlete(olympicsManager.getAthleteDetails(id));
                        uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                    }
                }

                // Athlete deletion has not been implemented because the assignment
                // does not specify whether medals earned by a deleted athlete should be preserved,
                // whether competition results should be removed or reassigned,
                // or whether delegation medal counts should be updated immediately.
                // Feature methods and messages have been commented.
                case DELETE_ATHLETE ->  uiManager.showMessage(MessagesEN.MESSAGE_NOT_IMPLEMENTED);


                case BACK_FROM_ATHLETE -> {
                }
            }

        } while (option != ATHLETES_MENU_OPTION.BACK_FROM_ATHLETE);
    }

    /**
     * Registers a new athlete using data provided by the user.
     * Displays the registered athlete without passing entity objects to Presentation.
     *
     * @throws IOException if the athlete or delegation files cannot be accessed
     */
    private void registerAthlete() throws IOException {

        String name = uiManager.askAthleteName();
        String birthDate = uiManager.askBirthDate();

        ArrayList<String> delegation = uiManager.printDelegations(
                olympicsManager.getDelegationListByName()
        );
        if (delegation == null) {
            return;
        }
        String delegationCode = delegation.get(0);

        String profile = uiManager.askProfile();
        if (profile == null) {
            return; // 0 = Cancel
        }

        ERROR_TYPE error = olympicsManager.validateAthlete(name, delegationCode);
        if (error != null) {
            uiManager.showError(error);
            return;
        }

        String athleteId = olympicsManager.registerAthlete(
                name, birthDate, delegationCode, profile
        );
        uiManager.showRegisteredAthlete(olympicsManager.getAthleteDetails(athleteId));
    }


//    private void deleteAthlete() throws IOException {
//
//        uiManager.showMessage(MessagesEN.HEADER_DELETE_ATHLETE);
//
//        String term = uiManager.askSearchTerm();
//        ArrayList<ArrayList<String>> found = olympicsManager.searchAthletes(term);
//
//        int chosen = uiManager.chooseAthlete(found);
//        if (chosen == 0) {
//            return;
//        }
//
//        String id = found.get(chosen - 1).get(0);
//        ArrayList<String> details = olympicsManager.getAthleteDetails(id);
//
//        uiManager.printAthlete(details);
//        uiManager.showMessage(MessagesEN.MESSAGE_DELETE_ATHLETE_WARNING);
//
//        boolean confirm = uiManager.askForConfirmation(
//                MessagesEN.PROMPT_CONFIRM_DELETE_ATHLETE
//        );
//
//        if (confirm) {
//            olympicsManager.deleteAthlete(id);
//
//            uiManager.showMessage(String.format(
//                    MessagesEN.HEADER_ATHLETE_DELETED, details.get(1)
//            ));
//        } else {
//            uiManager.showMessage(MessagesEN.MESSAGE_ATHLETE_DELETE_CANCELLED);
//        }
//
//        uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
//    }

    /**
     * Displays and manages the competitions submenu.
     * Allows the user to load, list and delete competitions,
     * or return to the main menu.
     *
     * @throws IOException if an error occurs while accessing competition data
     */
    private void manageCompetitions() throws IOException {

        COMPETITIONS_MENU_OPTION option;

        do {

            option = uiManager.printCompetitionsMenu();

            switch (option) {

                case LOAD_COMPETITION -> {
                    loadCompetitions();
                    uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
                }

                case LIST_COMPETITION -> listCompetitions();

                case DELETE_COMPETITION -> deleteCompetition();

                case BACK_FROM_COMPETITION -> {
                }
            }

        } while (option != COMPETITIONS_MENU_OPTION.BACK_FROM_COMPETITION);
    }

    /**
     * Loads competitions from a CSV file.
     * Reads the file, validates the competition data
     * and displays the import results and any detected errors.
     *
     * @throws IOException if an error occurs while reading or saving the data
     */
    private void loadCompetitions() throws IOException {

        String filename = uiManager.askForCSV(MessagesEN.PROMPT_ASK_FOR_CSV);
        uiManager.showMessage(MessagesEN.MESSAGE_OPENING_FILE);

        ArrayList<String> lines = olympicsManager.readCompetitionCSV(filename);
        uiManager.showMessage(MessagesEN.MESSAGE_VALIDATING_COMPETITIONS);

        ArrayList<ArrayList<ERROR_TYPE>> errors = olympicsManager.importCompetitions(lines);
        uiManager.showImportResults(lines, errors);
    }

    /**
     * Displays the list of available competitions and allows
     * the user to select one to view its detailed information
     * and participating athletes.
     *
     * @throws IOException if an error occurs while reading competition or athlete data
     */
    private void listCompetitions() throws IOException {

        String id = uiManager.printCompetitions(
                olympicsManager.getCompetitionList(),
                MessagesEN.HEADER_AVAILABLE_COMPETITIONS
        );

        if (id == null) {
            return;
        }

        uiManager.printCompetitionDetails(
                olympicsManager.getCompetitionDetails(id),
                olympicsManager.getCompetitionParticipantNames(id)
        );

        uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
    }

    /**
     * Allows the user to select and delete a competition.
     * Displays its details, requests confirmation and removes it
     * from the system if the user confirms the operation.
     *
     * @throws IOException if an error occurs while reading or saving competition data
     */
    private void deleteCompetition() throws IOException {

        String id = uiManager.printCompetitions(
                olympicsManager.getCompetitionList(),
                MessagesEN.HEADER_DELETE_COMPETITION
        );

        if (id == null) {
            return;
        }

        ArrayList<String> details = olympicsManager.getCompetitionDetails(id);

        uiManager.printCompetitionDetails(
                details,
                olympicsManager.getCompetitionParticipantNames(id)
        );

        uiManager.showMessage(
                MessagesEN.MESSAGE_DELETE_COMPETITION_WARNING
        );

        boolean confirm = uiManager.askForConfirmation(
                MessagesEN.PROMPT_CONFIRM_DELETE_COMPETITION
        );

        if (confirm) {

            olympicsManager.deleteCompetition(id);

            uiManager.showMessage(
                    String.format(
                            MessagesEN.HEADER_COMPETITION_DELETED,
                            details.get(1)
                    )
            );

        } else {

            uiManager.showMessage(
                    MessagesEN.MESSAGE_COMPETITION_DELETE_CANCELLED
            );
        }

        uiManager.waitForEnter(MessagesEN.PROMPT_CONTINUE);
    }

    /**
     * Simulates all available Olympic competitions.
     * Skips competitions with fewer than three athletes.
     * Displays the results, saves them to persistence
     * and updates the delegation medal counts.
     *
     * @throws IOException if an error occurs while reading or saving the data
     * @throws IllegalStateException if the simulation contains invalid data
     */
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

            ArrayList<String> info =
                    olympicsManager.getSimulationCompetitionInfo(id);

            if (results.isEmpty()) {

                uiManager.showMessage(
                        String.format(
                                MessagesEN.MESSAGE_COMPETITION_SKIPPED,
                                info.get(1)
                        )
                );

            } else {

                uiManager.showSimulationResults(
                        info,
                        olympicsManager.getSimulationAthleteNames(id),
                        results,
                        i + 1,
                        total
                );

            }

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
