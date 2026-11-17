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

    public ArrayList<Delegation> getDelegationDAO() {
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

    public ArrayList<ArrayList<Double>> simulateCompetition(Competition competition) throws IOException {

        ArrayList<Athlete> ranking = new ArrayList<>();
        ArrayList<ArrayList<Double>> results = new ArrayList<>();

        // Comprobar participantes
        if (competition.getAthletes() == null ||
                competition.getAthletes().size() < 3 ||
                competition.getAthletes().size() != competition.getAthleteIds().size()) {
            throw new IOException("Invalid participants in competition " + competition.getId());
        }

        for (Athlete athlete : competition.getAthletes()) {

            if (athlete.getDelegation() == null) {
                throw new IOException("Delegation not found for athlete " + athlete.getId());
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

       return 1.0 - luck/10.0 - weightedAttributes;
    }


    // Asocia cada competicion con sus atletas y estos con sus delegaciones
    private void loadRelations() throws IOException {

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

    public ArrayList<Competition> prepareSimulation() throws IOException {
        loadRelations();
        return competitions;
    }
    public void saveSimulationResults() throws IOException {
        competitionDAO.saveCompetitions(competitions);
    }

    public ArrayList<String> getSimulationAthleteNames(Competition competition) {

        ArrayList<String> names = new ArrayList<>();

        for (Athlete athlete : competition.getAthletes()) {
            names.add(athlete.getName() + " - "
                    + athlete.getDelegation().getName());
        }

        return names;
    }

}
