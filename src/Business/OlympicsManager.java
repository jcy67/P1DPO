package Business;

import Business.Entities.Athlete;
import Business.Entities.COMPETITION_FORMAT;
import Business.Entities.Competition;
import Business.Entities.Delegation;
import Persistence.AthleteDAO;
import Persistence.CompetitionDAO;
import Persistence.DelegationDAO;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.Date;
import Business.Entities.PROFILE_TYPE;

/**
 * Manages the business logic of the Olympic Games application.
 * Handles delegations, athletes, competitions, validation
 * and competition simulations.
 */
public class OlympicsManager {

    private final DelegationDAO delegationDAO;
    private final AthleteDAO athleteDAO;
    private final CompetitionDAO competitionDAO;
    private ArrayList<Competition> competitions;

    /**
     * Creates an OlympicsManager with the required data access objects.
     *
     * @param d the delegation data access object
     * @param a the athlete data access object
     * @param c the competition data access object
     */
    public OlympicsManager(DelegationDAO d, AthleteDAO a, CompetitionDAO c) {
        this.delegationDAO = d;
        this.athleteDAO = a;
        this.competitionDAO = c;
    }


    public ArrayList<Athlete> searchAthletes(String term) throws IOException {
        ArrayList<Athlete> filtered = new ArrayList<>();
        for (Athlete a : athleteDAO.readAthletes()) {
            if (a.getName().toLowerCase().contains(term.toLowerCase())) {
                filtered.add(a);
            }
        }
        return filtered;
    }

    public Delegation getDelegationByCode(String code) throws IOException {
        for (Delegation d : delegationDAO.readDelegations()) {
            if (d.getCode().equals(code)) {
                return d;
            }
        }
        return null;
    }

    // Devuelve el error si no se puede registrar, o null si todo esta bien
    public ErrorType validateAthlete(String name, String delegationCode) throws IOException {
        for (Athlete a : athleteDAO.readAthletes()) {
            if (a.getDelegationCode().equals(delegationCode) && a.getName().equalsIgnoreCase(name)) {
                return ErrorType.ATHLETE_ALREADY_REGISTERED;
            }
        }
        return null;
    }

    public Athlete registerAthlete(String name, Date birthDate, String delegationCode, String profile) throws IOException {
        ArrayList<Athlete> athletes = athleteDAO.readAthletes();

        Athlete athlete = new Athlete(nextAthleteId(athletes), PROFILE_TYPE.valueOf(profile), name, birthDate, delegationCode);

        athletes.add(athlete);
        athleteDAO.saveAthletes(athletes);
        return athlete;
    }

    private String nextAthleteId(ArrayList<Athlete> athletes) {
        int max = 0;
        for (Athlete a : athletes) {
            int number = Integer.parseInt(a.getId().substring("ATH-".length()));
            if (number > max) {
                max = number;
            }
        }
        return String.format("ATH-%03d", max + 1);
    }

    public Delegation getBlankDelegation() {
        Delegation blank = new Delegation();
        return blank;
    }


    /**
     * Reads and sorts delegations alphabetically by name.
     *
     * @return the delegations sorted by name
     * @throws IOException if an error occurs while reading the delegations
     */
    public ArrayList<Delegation> sortDelegationsByName() throws IOException {

        ArrayList<Delegation> delegations = delegationDAO.readDelegations();

        delegations.sort(Comparator.comparing(Delegation::getName));

        return delegations;
    }

    /**
     * Reads and sorts delegations by their medal counts.
     * Prioritizes gold, silver and bronze medals, followed by name.
     *
     * @return the delegations sorted by medal counts
     * @throws IOException if an error occurs while reading the delegations
     */
    public ArrayList<Delegation> sortDelegationsByMedals() throws IOException {
        ArrayList<Delegation> delegations = delegationDAO.readDelegations();

        delegations.sort(Comparator.comparing(Delegation::getGoldMedals).reversed().thenComparing(Comparator.comparing(Delegation::getSilverMedals).reversed()).thenComparing(Comparator.comparing(Delegation::getBronzeMedals).reversed()).thenComparing(Delegation::getName));

        return delegations;
    }

    /**
     * Checks whether the system contains registered delegations.
     *
     * @return true if delegations are available, false otherwise
     * @throws IOException if an error occurs while reading the delegations
     */
    public boolean verifyDelegations() throws IOException {
        ArrayList<Delegation> delegations = delegationDAO.readDelegations();
        return delegations != null && !delegations.isEmpty();
    }

    /**
     * Reads all registered delegations.
     *
     * @return the list of registered delegations
     * @throws IOException if an error occurs while reading the delegations
     */
    public ArrayList<Delegation> getDelegationDAO() throws IOException {
        return delegationDAO.readDelegations();
    }

    /**
     * Reads all registered athletes.
     *
     * @return the list of registered athletes
     * @throws IOException if an error occurs while reading the athletes
     */
    public ArrayList<Athlete> getAthleteDAO() throws IOException {
        return athleteDAO.readAthletes();
    }

    /**
     * Reads all registered competitions.
     *
     * @return the list of registered competitions
     * @throws IOException if an error occurs while reading the competitions
     */
    public ArrayList<Competition> getCompetitionDAO() throws IOException {
        return competitionDAO.readCompetitions();
    }

    /**
     * Reads the contents of a competition CSV file.
     *
     * @param filename the name of the CSV file to read
     * @return the lines contained in the CSV file
     * @throws IOException if an error occurs while reading the file
     */
    public ArrayList<String> readCompetitionCSV(String filename) throws IOException {
        return competitionDAO.readCSV(filename);
    }

    /**
     * Returns the currently loaded competitions.
     *
     * @return the list of loaded competitions
     */
    public ArrayList<Competition> getCompetitions() {
        return competitions;
    }

    /**
     * Converts delegation objects into rows of strings containing
     * their codes, names and medal counts.
     *
     * @param delegations the delegations to convert
     * @return the delegation information as rows of strings
     */
    private ArrayList<ArrayList<String>> delegationRows(
            ArrayList<Delegation> delegations) {

        ArrayList<ArrayList<String>> rows = new ArrayList<>();

        for (Delegation delegation : delegations) {

            ArrayList<String> row = new ArrayList<>();

            row.add(delegation.getCode());
            row.add(delegation.getName());
            row.add(String.valueOf(delegation.getGoldMedals()));
            row.add(String.valueOf(delegation.getSilverMedals()));
            row.add(String.valueOf(delegation.getBronzeMedals()));

            rows.add(row);
        }

        return rows;
    }

    /**
     * Returns delegation information sorted alphabetically by name.
     *
     * @return the sorted delegation information as rows of strings
     * @throws IOException if an error occurs while reading the delegations
     */
    public ArrayList<ArrayList<String>> getDelegationListByName() throws IOException {
        return delegationRows(sortDelegationsByName());
    }

    /**
     * Returns delegation information sorted by medal counts.
     *
     * @return the medal table as rows of strings
     * @throws IOException if an error occurs while reading the delegations
     */
    public ArrayList<ArrayList<String>> getDelegationMedalTable() throws IOException {
        return delegationRows(sortDelegationsByMedals());
    }

    /**
     * Finds the names of athletes belonging to a specific delegation.
     *
     * @param code the delegation code
     * @return the names of athletes belonging to the delegation
     * @throws IOException if an error occurs while reading the athletes
     */
    public ArrayList<String> getDelegationAthleteNames(String code)
            throws IOException {

        ArrayList<String> names = new ArrayList<>();

        for (Athlete athlete : athleteDAO.readAthletes()) {

            if (athlete.getDelegationCode().equals(code)) {
                names.add(athlete.getName());
            }
        }

        return names;
    }

    /**
     * Reads all competitions and converts their basic information
     * into rows containing the identifier, name and format.
     *
     * @return the competition information as rows of strings
     * @throws IOException if an error occurs while reading the competitions
     */
    public ArrayList<ArrayList<String>> getCompetitionList()
            throws IOException {

        competitions = competitionDAO.readCompetitions();

        ArrayList<ArrayList<String>> rows = new ArrayList<>();

        for (Competition competition : competitions) {

            ArrayList<String> row = new ArrayList<>();

            row.add(competition.getId());
            row.add(competition.getName());
            row.add(competition.getFormat().name());

            rows.add(row);
        }

        return rows;
    }

    /**
     * Returns the detailed information of a competition.
     * Includes its identifier, name, format, date, attribute weights
     * and medal winners, if available.
     *
     * @param id the identifier of the competition
     * @return the competition details as a list of strings
     * @throws IllegalStateException if the competition cannot be found
     */
    public ArrayList<String> getCompetitionDetails(String id) {

        Competition competition = findCompetition(id);

        ArrayList<String> info = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        info.add(competition.getId());
        info.add(competition.getName());
        info.add(competition.getFormat().name());
        info.add(sdf.format(competition.getDateTime()));

        info.add(String.valueOf(competition.getSpeed()));
        info.add(String.valueOf(competition.getEndurance()));
        info.add(String.valueOf(competition.getStrength()));
        info.add(String.valueOf(competition.getTechnique()));
        info.add(String.valueOf(competition.getPrecision()));

        if (competition.getResults() == null) {
            info.add(null);
            info.add(null);
            info.add(null);
        } else {
            info.add(competition.getGold());
            info.add(competition.getSilver());
            info.add(competition.getBronze());
        }

        return info;
    }

    /**
     * Returns the names of athletes participating in a competition,
     * together with the names of their delegations.
     *
     * @param id the identifier of the competition
     * @return a list containing athlete names and their delegations
     * @throws IOException if an error occurs while reading athlete or delegation data
     * @throws IllegalStateException if the competition cannot be found
     */
    public ArrayList<String> getCompetitionParticipantNames(String id)
            throws IOException {

        Competition competition = findCompetition(id);

        ArrayList<Athlete> athletes = athleteDAO.readAthletes();
        ArrayList<Delegation> delegations = delegationDAO.readDelegations();

        if (delegations == null) {
            throw new IOException("Could not read delegations.");
        }

        ArrayList<String> names = new ArrayList<>();

        for (String athleteId : competition.getAthleteIds()) {

            for (Athlete athlete : athletes) {

                if (athlete.getId().equals(athleteId)) {

                    for (Delegation delegation : delegations) {

                        if (delegation.getCode().equals(athlete.getDelegationCode())) {

                            names.add(
                                    athlete.getName() + " - " +
                                            delegation.getName()
                            );

                            break;
                        }
                    }

                    break;
                }
            }
        }

        return names;
    }

    /**
     * Deletes a competition from the system.
     * Saves the updated competition list and recalculates
     * the medal counts of all delegations.
     *
     * @param id the identifier of the competition to delete
     * @throws IOException if an error occurs while saving or updating the data
     * @throws IllegalStateException if the competition cannot be found
     */
    public void deleteCompetition(String id) throws IOException {

        Competition competition = findCompetition(id);

        competitions.remove(competition);

        competitionDAO.saveCompetitions(competitions);

        updateDelegationMedals();
    }

    /**
     * Returns the identifiers of all currently loaded competitions.
     *
     * @return the list of competition identifiers
     */
    public ArrayList<String> getCompetitionIds() {

        ArrayList<String> ids = new ArrayList<>();

        for (Competition competition : competitions) {
            ids.add(competition.getId());
        }

        return ids;
    }

    /**
     * Searches for a competition using its identifier.
     *
     * @param id the identifier of the competition to find
     * @return the competition matching the identifier
     * @throws IllegalStateException if the competition cannot be found
     */
    private Competition findCompetition(String id) {

        for (Competition competition : competitions) {

            if (competition.getId().equals(id)) {
                return competition;
            }
        }

        throw new IllegalStateException();
    }

    /**
     * Returns the basic information of a competition for its simulation.
     * Includes the identifier, name and format.
     *
     * @param id the identifier of the competition
     * @return the competition information as a list of strings
     * @throws IllegalStateException if the competition cannot be found
     */
    public ArrayList<String> getSimulationCompetitionInfo(String id) {

        Competition competition = findCompetition(id);

        ArrayList<String> info = new ArrayList<>();

        info.add(competition.getId());
        info.add(competition.getName());
        info.add(competition.getFormat().name());

        return info;
    }

    /**
     * Imports competitions from CSV lines.
     * Validates each competition and saves the valid ones.
     * Stores the validation errors separately for each CSV line.
     *
     * @param infoLines the lines containing the competition data
     * @return the validation errors for each competition in the CSV file
     * @throws IOException if an error occurs while reading or saving the data
     */
    public ArrayList<ArrayList<ERROR_TYPE>> importCompetitions(ArrayList<String> infoLines) throws IOException {

        ArrayList<ArrayList<ERROR_TYPE>> errorsPerLine = new ArrayList<>();

        ArrayList<Competition> competitionsRegistered = getCompetitionDAO();
        ArrayList<Athlete> athletesRegistered = getAthleteDAO();

        boolean changes = false;

        for (int i = 1; i < infoLines.size(); i++) {

            String line = infoLines.get(i);

            ArrayList<ERROR_TYPE> errors = validateCompetition(line, athletesRegistered, competitionsRegistered);
            errorsPerLine.add(errors);

            if (errors.isEmpty()) {

                Competition competition = Competition.createFromLine(line);

                competitionsRegistered.add(competition);
                changes = true;
            }
        }

        // Save the competitions only if changes have been made.
        if (changes) {
            competitionDAO.saveCompetitions(competitionsRegistered);
        }

        return errorsPerLine;
    }

    /**
     * Validates a competition from a CSV line.
     * Checks its identifier, format, name, date, attribute weights
     * and participating athletes.
     *
     * @param line the CSV line containing the competition information
     * @param athletes the registered athletes used for validation
     * @param competitions the existing competitions used to check duplicate identifiers
     * @return a list of validation errors, or an empty list if the competition is valid
     */
    public ArrayList<ERROR_TYPE> validateCompetition(String line, ArrayList<Athlete> athletes, ArrayList<Competition> competitions) {

        ArrayList<ERROR_TYPE> errors = new ArrayList<>();

        String[] parts = line.split(",", -1);

        // Check if the number of fields is valid.
        if (parts.length != 10) {
            errors.add(ERROR_TYPE.INVALID_FIELDS);
            return errors;
        }

        // Validate the competition identifier.
        if (parts[0].trim().isEmpty()) {
            errors.add(ERROR_TYPE.EMPTY_ID);

            // Check if the competition identifier has the correct format.
        } else if (!parts[0].matches("CMP-[0-9]{3,4}")) {
            errors.add(ERROR_TYPE.INVALID_ID);
        }

        for (Competition competition : competitions) {
            if (competition.getId().equals(parts[0])) {
                errors.add(ERROR_TYPE.DUPLICATE_ID);
                break;
            }
        }

        // Validate the competition format.
        boolean validFormat = false;

        for (COMPETITION_FORMAT format : COMPETITION_FORMAT.values()) {
            if (format.name().equals(parts[1])) {
                validFormat = true;
                break;
            }
        }

        if (!validFormat) {
            errors.add(ERROR_TYPE.INVALID_FORMAT);
        }

        // Validate the competition name.
        if (parts[2].trim().isEmpty()) {
            errors.add(ERROR_TYPE.EMPTY_NAME);
        }

        // Validate the competition date.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // Use strict mode to reject invalid dates, such as February 30.
        sdf.setLenient(false);

        // Convert the text into a Date without throwing a ParseException.
        Date date = sdf.parse(parts[3], new ParsePosition(0));

        if (date == null || !sdf.format(date).equals(parts[3])) {
            errors.add(ERROR_TYPE.INVALID_DATE);
        }

        // Validate the attribute weights.
        double total = 0;
        boolean validateWeights = true;

        for (int i = 4; i < 9; i++) {

            String value = parts[i].trim();

            // Check if the value is a valid double.
            if (!value.matches("[0-9]+(\\.[0-9]+)?")) {
                validateWeights = false;
                continue;
            }

            double weight = Double.parseDouble(value);

            if (weight < 0 || weight > 1) {
                validateWeights = false;
            }

            total += weight;
        }

        if (!validateWeights) {
            errors.add(ERROR_TYPE.INVALID_WEIGHT);

            // Check that the sum of the weights equals 1.
        } else if (Math.abs(total - 1.0) > 0.000001) {
            errors.add(ERROR_TYPE.INVALID_WEIGHT_SUM);
        }

        // Validate the participating athletes.
        ArrayList<String> uniqueIds = new ArrayList<>();
        String[] idsCsv = parts[9].split("\\|", -1);

        boolean invalidAthlete = false;
        boolean duplicateAthlete = false;

        for (String athleteId : idsCsv) {

            String id = athleteId.trim();

            boolean exists = false;

            // Check whether the athlete exists.
            for (Athlete athlete : athletes) {
                if (athlete.getId().equals(id)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                invalidAthlete = true;
            }

            // Check whether the athlete is duplicated.
            if (uniqueIds.contains(id)) {
                duplicateAthlete = true;
            } else {
                uniqueIds.add(id);
            }
        }

        // Add the detected validation errors.
        if (invalidAthlete) {
            errors.add(ERROR_TYPE.INVALID_ATHLETE);
        }

        if (duplicateAthlete) {
            errors.add(ERROR_TYPE.DUPLICATE_ATHLETE);
        }

        if (uniqueIds.size() < 3) {
            errors.add(ERROR_TYPE.NOT_ENOUGH_ATHLETES);
        }

        return errors;
    }

    /**
     * Simulates a competition identified by its ID.
     *
     * @param id the identifier of the competition to simulate
     * @return the ranked results containing each athlete's luck and time
     * @throws IllegalStateException if the competition cannot be found
     *         or its simulation data is invalid
     */
    public ArrayList<ArrayList<Double>> simulateCompetition(String id) {
        return simulateCompetition(findCompetition(id));
    }

    /**
     * Simulates an Olympic competition by calculating the time of each athlete.
     * Ranks the athletes from the lowest to the highest completion time
     * and assigns gold, silver and bronze medals to the top three athletes.
     *
     * @param competition the competition to simulate
     * @return a list containing the luck factor and calculated time
     *         of each athlete, ordered by their ranking
     * @throws IllegalStateException if the competition has invalid
     *         participants or missing delegation data
     */
    public ArrayList<ArrayList<Double>> simulateCompetition(Competition competition) {

        ArrayList<Athlete> ranking = new ArrayList<>();
        ArrayList<ArrayList<Double>> results = new ArrayList<>();

        // Validate the participating athletes.
        if (competition.getAthletes() == null ||
                competition.getAthletes().size() < 3 ||
                competition.getAthletes().size() != competition.getAthleteIds().size()) {
            throw new IllegalStateException();
        }

        for (Athlete athlete : competition.getAthletes()) {

            if (athlete.getDelegation() == null) {
                throw new IllegalStateException();
            }

            double luck = Math.random();
            double time = calculateTime(athlete, competition, luck);

            // Find the ranking position according to the lowest completion time.
            int position = 0;

            while (position < results.size() &&
                    results.get(position).get(1) <= time) {
                position++;
            }

            // Store the luck factor and calculated time.
            ArrayList<Double> athleteResults = new ArrayList<>();
            athleteResults.add(luck);
            athleteResults.add(time);

            // Insert the athlete and their results at the corresponding position.
            ranking.add(position, athlete);
            results.add(position, athleteResults);
        }

        // Update the athlete ranking.
        competition.setAthletes(ranking);

        // Assign the medals to the top three athletes.
        competition.setResults(
                ranking.get(0),
                ranking.get(1),
                ranking.get(2)
        );

        return results;
    }

    /**
     * Calculates an athlete's completion time using their weighted attributes
     * and a random luck factor according to the competition formula.
     *
     * @param athlete the athlete participating in the competition
     * @param competition the competition being simulated
     * @param luck the random luck factor between 0.0 and 1.0
     * @return the calculated completion time
     */
    private double calculateTime(Athlete athlete, Competition competition, double luck) {

        double weightedAttributes = (athlete.getSpeed() / 100.0) * competition.getSpeed() +
                (athlete.getEndurance() / 100.0) * competition.getEndurance() +
                (athlete.getStrength() / 100.0) * competition.getStrength() +
                (athlete.getTechnique() / 100.0) * competition.getTechnique() +
                (athlete.getPrecision() / 100.0) * competition.getPrecision();

        return 1.0 - luck * weightedAttributes;
    }

    /**
     * Loads competitions, athletes and delegations from their JSON files.
     * Associates each athlete with their delegation and each competition
     * with its participating athletes.
     *
     * @throws IOException if an error occurs while reading the files
     */
    public void loadRelations() throws IOException {

        competitions = competitionDAO.readCompetitions();

        ArrayList<Athlete> athletes = athleteDAO.readAthletes();
        ArrayList<Delegation> delegations = delegationDAO.readDelegations();

        // Associate each athlete with their delegation.
        for (Athlete athlete : athletes) {

            for (Delegation delegation : delegations) {

                if (athlete.getDelegationCode().equals(delegation.getCode())) {
                    athlete.setDelegation(delegation);
                    break;
                }
            }
        }

        // Associate each competition with its participating athletes.
        for (Competition competition : competitions) {

            ArrayList<Athlete> participants = new ArrayList<>();

            for (String id : competition.getAthleteIds()) {

                for (Athlete athlete : athletes) {

                    if (athlete.getId().equals(id)) {
                        participants.add(athlete);
                        break;
                    }
                }
            }

            competition.setAthletes(participants);
        }
    }

    /**
     * Recalculates the medal counts of all delegations based on
     * the competition results and saves the updated data.
     *
     * @throws IOException if an error occurs while reading or saving the files
     */
    public void updateDelegationMedals() throws IOException {

        ArrayList<Delegation> delegations = delegationDAO.readDelegations();
        ArrayList<Athlete> athletes = athleteDAO.readAthletes();

        // Reset all medal counts.
        for (Delegation delegation : delegations) {
            delegation.resetMedals();
        }

        // Go through all competitions.
        for (Competition competition : competitions) {

            if (competition.getResults() == null) {
                continue;
            }

            // Find the medal-winning athletes.
            for (Athlete athlete : athletes) {

                // Find the athlete's delegation.
                for (Delegation delegation : delegations) {

                    if (athlete.getDelegationCode().equals(delegation.getCode())) {

                        if (athlete.getId().equals(competition.getGold())) {
                            delegation.setGoldMedals(delegation.getGoldMedals() + 1);
                        }

                        if (athlete.getId().equals(competition.getSilver())) {
                            delegation.setSilverMedals(delegation.getSilverMedals() + 1);
                        }

                        if (athlete.getId().equals(competition.getBronze())) {
                            delegation.setBronzeMedals(delegation.getBronzeMedals() + 1);
                        }
                    }
                }
            }
        }

        // Save the updated delegations.
        delegationDAO.saveDelegations(delegations);
    }

    /**
     * Saves the current competition results to the JSON file.
     *
     * @throws IOException if an error occurs while saving the file
     */
    public void saveSimulationResults() throws IOException {
        competitionDAO.saveCompetitions(competitions);
    }

    /**
     * Returns the names of the athletes participating in a competition,
     * together with the names of their delegations, ordered by ranking.
     *
     * @param id the identifier of the competition
     * @return a list containing athlete names and their delegations
     * @throws IllegalStateException if the competition cannot be found
     */
    public ArrayList<String> getSimulationAthleteNames(String id) {

        Competition competition = findCompetition(id);

        ArrayList<String> names = new ArrayList<>();

        for (Athlete athlete : competition.getAthletes()) {

            names.add(
                    athlete.getName() + " - " +
                            athlete.getDelegation().getName()
            );
        }

        return names;
    }


}
