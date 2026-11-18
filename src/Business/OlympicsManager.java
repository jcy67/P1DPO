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


public class OlympicsManager {


    private final DelegationDAO delegationDAO;
    private final AthleteDAO athleteDAO;
    private final CompetitionDAO competitionDAO;
    private ArrayList<Competition> competitions;

    public OlympicsManager(DelegationDAO d, AthleteDAO a, CompetitionDAO c) {
        this.delegationDAO = d;
        this.athleteDAO = a;
        this.competitionDAO = c;
    }

    public ArrayList<Delegation> sortDelegationsByName() throws IOException{
        ArrayList<Delegation> delegations = delegationDAO.readDelegations();

        delegations.sort(Comparator.comparing(Delegation::getName));

        return delegations;
    }

    public ArrayList<Delegation> sortDelegationsByMedals() throws IOException{
        ArrayList<Delegation> delegations = delegationDAO.readDelegations();

        delegations.sort(Comparator.comparing(Delegation::getGoldMedals).reversed().thenComparing(Comparator.comparing(Delegation::getSilverMedals).reversed()).thenComparing(Comparator.comparing(Delegation::getBronzeMedals).reversed()).thenComparing(Delegation::getName));

        return delegations;
    }

    public boolean verifyDelegations() throws IOException {
        ArrayList<Delegation> delegations = delegationDAO.readDelegations();
        return delegations != null && !delegations.isEmpty();
    }

    public ArrayList<Delegation> getDelegationDAO() throws IOException {
        return delegationDAO.readDelegations();
    }

    public ArrayList<Athlete> getAthleteDAO() throws IOException {
        return athleteDAO.readAthletes();
    }

    public ArrayList<Competition> getCompetitionDAO() throws IOException {
        return competitionDAO.readCompetitions();
    }

    public ArrayList<String> readCompetitionCSV(String filename) throws IOException {
        return competitionDAO.readCSV(filename);
    }

    public ArrayList<Competition> getCompetitions() {
        return competitions;
    }

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

    public ArrayList<ArrayList<String>> getDelegationListByName() throws IOException {
        return delegationRows(sortDelegationsByName());
    }

    public ArrayList<ArrayList<String>> getDelegationMedalTable() throws IOException{
        return delegationRows(sortDelegationsByMedals());
    }

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

    public ArrayList<String> getCompetitionIds() {

        ArrayList<String> ids = new ArrayList<>();

        for (Competition competition : competitions) {
            ids.add(competition.getId());
        }

        return ids;
    }

    private Competition findCompetition(String id) {

        for (Competition competition : competitions) {

            if (competition.getId().equals(id)) {
                return competition;
            }
        }

        throw new IllegalStateException();
    }

    public ArrayList<ArrayList<Double>> simulateCompetition(String id) {
        return simulateCompetition(findCompetition(id));
    }

    public ArrayList<String> getSimulationCompetitionInfo(String id) {

        Competition competition = findCompetition(id);

        ArrayList<String> info = new ArrayList<>();

        info.add(competition.getId());
        info.add(competition.getName());
        info.add(competition.getFormat().name());

        return info;
    }

    // Por cada linea del csv se guarda los errores al importar en cada linea
    public ArrayList<ArrayList<ErrorType>> importCompetitions(ArrayList<String> infoLines) throws IOException {

        ArrayList<ArrayList<ErrorType>> errorsPerLine = new ArrayList<>();

        ArrayList<Competition> competitionsRegistered = getCompetitionDAO();
        ArrayList<Athlete> athletesRegistered = getAthleteDAO();


        boolean changes = false;

        for (int i = 1; i < infoLines.size(); i++){

            String line = infoLines.get(i);

            ArrayList<ErrorType> errors = validateCompetition(line, athletesRegistered,competitionsRegistered);
            errorsPerLine.add(errors);

            if (errors.isEmpty()){

                Competition competition = Competition.createFromLine(line);

                competitionsRegistered.add(competition);
                changes = true;
            }


        }

        // Si no cambia, no lo vuelvo a guardar, solo si cambia
        if (changes){
            competitionDAO.saveCompetitions(competitionsRegistered);

        }

        return errorsPerLine;

    }

    public ArrayList<ErrorType> validateCompetition(String line,ArrayList<Athlete> athletes, ArrayList<Competition> competitions) {

        ArrayList<ErrorType> errors = new ArrayList<>();

        String[] parts= line.split(",", -1);

        // Mas largo o corto de lo permitido
        if (parts.length != 10) {
            errors.add(ErrorType.INVALID_FIELDS);
            return errors;
        }


        // Comprobaciones Id
        if (parts[0].trim().isEmpty()) {
            errors.add(ErrorType.EMPTY_ID);

            // Comprueba si es el formato correcto para el id de la competicion
        } else if (!parts[0].matches("CMP-[0-9]{3,4}")) {
        errors.add(ErrorType.INVALID_ID);
        }

        for (Competition competition: competitions){
            if (competition.getId().equals(parts[0])){
                errors.add(ErrorType.DUPLICATE_ID);
                break;
            }
        }


        // Comprobacion formato
        boolean validFormat = false;

        for (COMPETITION_FORMAT format: COMPETITION_FORMAT.values()){
            if (format.name().equals(parts[1])){
                validFormat = true;
                break;
            }
        }
        if (!validFormat){
            errors.add(ErrorType.INVALID_FORMAT);
        }


        // Comprobacion nombre
        if (parts[2].trim().isEmpty()){
            errors.add(ErrorType.EMPTY_NAME);
        }


        // Comprobaciones fecha
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // modo estricto (por si pone 30 de febrero, que lo anule)
        sdf.setLenient(false);

        // Convierte el texto en Date sin lanzar ParseException
        Date date = sdf.parse(parts[3], new ParsePosition(0));

        if (date == null || !sdf.format(date).equals(parts[3])){
            errors.add(ErrorType.INVALID_DATE);
        }


        // Comprabacion pesos
        double total = 0;
        boolean validateWeights = true;

        for (int i = 4; i < 9 ; i++){

            String value = parts[i].trim();

            // Comprueba si es un double, por si hay otro tipo de caracteres
            if (!value.matches("[0-9]+(\\.[0-9]+)?")) {
                validateWeights = false;
                continue;
            }

            double weight = Double.parseDouble(value);
            if ( weight < 0 || weight > 1){
                validateWeights = false;
            }
            total += weight;

        }
        if (!validateWeights){
            errors.add(ErrorType.INVALID_WEIGHT);

        // Comprobamos que sume 1
        } else if (Math.abs(total - 1.0) > 0.000001) {
            errors.add(ErrorType.INVALID_WEIGHT_SUM);
        }


        // Comprobaciones atletas
        ArrayList<String> uniqueIds = new ArrayList<>();
        String[] idsCsv = parts[9].split("\\|", -1);

        boolean invalidAthlete = false;
        boolean duplicateAthlete = false;

        for (String athleteId : idsCsv) {

            String id = athleteId.trim();

            boolean exists = false;

            // Comprobar que el atleta existe
            for (Athlete athlete : athletes) {
                if (athlete.getId().equals(id)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                invalidAthlete = true;
            }

            // Comprobar que el atleta no está repetido
            if (uniqueIds.contains(id)) {
                duplicateAthlete = true;
            } else {
                uniqueIds.add(id);
            }
        }

        // Añadir los errores encontrados
        if (invalidAthlete) {
            errors.add(ErrorType.INVALID_ATHLETE);
        }

        if (duplicateAthlete) {
            errors.add(ErrorType.DUPLICATE_ATHLETE);
        }

        if (uniqueIds.size() < 3) {
            errors.add(ErrorType.NOT_ENOUGH_ATHLETES);
        }

        return errors;

    }

    public ArrayList<ArrayList<Double>> simulateCompetition(Competition competition)  {

        ArrayList<Athlete> ranking = new ArrayList<>();
        ArrayList<ArrayList<Double>> results = new ArrayList<>();

        // Comprobar participantes
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

            // Buscar la posición según el tiempo
            int position = 0;

            while (position < results.size() &&
                    results.get(position).get(1) <= time) {
                position++;
            }

            // Guardar suerte y tiempo
            ArrayList<Double> athleteResults = new ArrayList<>();
            athleteResults.add(luck);
            athleteResults.add(time);

            // Insertar en la posición correspondiente
            ranking.add(position, athlete);
            results.add(position, athleteResults);
        }

        // Actualizar la clasificación de atletas
        competition.setAthletes(ranking);

        // Asignar las medallas
        competition.setResults(
                ranking.get(0),
                ranking.get(1),
                ranking.get(2)
        );

        return results;
    }

    private double calculateTime(Athlete athlete, Competition competition, double luck){

        double weightedAttributes = athlete.getSpeed() * competition.getSpeed() +
                athlete.getEndurance() * competition.getEndurance() +
                athlete.getStrength() * competition.getStrength() +
                athlete.getTechnique() * competition.getTechnique() +
                athlete.getPrecision() * competition.getPrecision();

        return 1.0 - (weightedAttributes / 100.0) - (luck / 10.0);
    }


    // Asocia cada competicion con sus atletas y estos con sus delegaciones
    public void loadRelations() throws IOException {

        competitions = competitionDAO.readCompetitions();

        ArrayList<Athlete> athletes = athleteDAO.readAthletes();
        ArrayList<Delegation> delegations = delegationDAO.readDelegations();

        // Asociar cada atleta con su delegación
        for (Athlete athlete : athletes) {

            for (Delegation delegation : delegations) {

                if (athlete.getDelegationCode().equals(delegation.getCode())) {
                    athlete.setDelegation(delegation);
                    break;
                }
            }
        }

        // Asociar cada competición con sus atletas
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

    public void updateDelegationMedals() throws IOException {

        ArrayList<Delegation> delegations = delegationDAO.readDelegations();
        ArrayList<Athlete> athletes = athleteDAO.readAthletes();

        // Reiniciar las medallas
        for (Delegation delegation : delegations) {
            delegation.resetMedals();
        }

        // Recorrer todas las competiciones
        for (Competition competition : competitions) {

            if (competition.getResults() == null) {
                continue;
            }

            // Buscar a los atletas ganadores
            for (Athlete athlete : athletes) {

                // Buscar su delegación
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

        // Guardar las delegaciones actualizadas
        delegationDAO.saveDelegations(delegations);
    }

    public void saveSimulationResults() throws IOException {
        competitionDAO.saveCompetitions(competitions);
    }

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
