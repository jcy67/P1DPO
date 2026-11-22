package Business.Entities;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a competition in the Olympic Games.
 * Stores its information, attribute weights, participating athletes
 * and competition results.
 */
public class Competition {

    private String id;
    private COMPETITION_FORMAT format;
    private String name;
    private Date dateTime;
    private Weight weights;
    private ArrayList<String> athleteIds;
    private Result results;

    private transient ArrayList<Athlete> athletes;

    /**
     * Creates an empty competition for Gson deserialization.
     */
    public Competition() {
    }

    /**
     * Creates a competition with the given information from a CSV file.
     *
     * @param id         the unique identifier of the competition
     * @param format     the competition format
     * @param name       the name of the competition
     * @param dateTime   the date and time of the competition
     * @param weights    the attribute weights of the competition
     * @param athleteIds the identifiers of the participating athletes
     */
    public Competition(String id, COMPETITION_FORMAT format, String name,
                       Date dateTime, Weight weights, ArrayList<String> athleteIds) {

        this.id = id;
        this.format = format;
        this.name = name;
        this.dateTime = dateTime;
        this.weights = weights;
        this.athleteIds = athleteIds;
        this.results = null;
    }

    /**
     * Returns the competition identifier.
     *
     * @return the competition identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the competition format.
     *
     * @return the competition format
     */
    public COMPETITION_FORMAT getFormat() {
        return format;
    }

    /**
     * Returns the competition name.
     *
     * @return the competition name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the date and time of the competition.
     *
     * @return the competition date and time
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Returns the attribute weights of the competition.
     *
     * @return the competition weights
     */
    public Weight getWeights() {
        return weights;
    }

    /**
     * Returns the speed weight.
     *
     * @return the speed weight
     */
    public double getSpeed() {
        return weights.getSpeed();
    }

    /**
     * Returns the endurance weight.
     *
     * @return the endurance weight
     */
    public double getEndurance() {
        return weights.getEndurance();
    }

    /**
     * Returns the strength weight.
     *
     * @return the strength weight
     */
    public double getStrength() {
        return weights.getStrength();
    }

    /**
     * Returns the technique weight.
     *
     * @return the technique weight
     */
    public double getTechnique() {
        return weights.getTechnique();
    }

    /**
     * Returns the precision weight.
     *
     * @return the precision weight
     */
    public double getPrecision() {
        return weights.getPrecision();
    }

    /**
     * Returns the identifiers of the participating athletes.
     *
     * @return the list of athlete identifiers
     */
    public ArrayList<String> getAthleteIds() {
        return athleteIds;
    }

    /**
     * Returns the competition results.
     *
     * @return the competition results, or null if not available
     */
    public Result getResults() {
        return results;
    }

    /**
     * Returns the identifier of the gold medal winner.
     *
     * @return the gold medal winner's identifier
     */
    public String getGold() {
        return results.getGold();
    }

    /**
     * Returns the identifier of the silver medal winner.
     *
     * @return the silver medal winner's identifier
     */
    public String getSilver() {
        return results.getSilver();
    }

    /**
     * Returns the identifier of the bronze medal winner.
     *
     * @return the bronze medal winner's identifier
     */
    public String getBronze() {
        return results.getBronze();
    }

    /**
     * Returns the athletes associated with the competition.
     *
     * @return the list of participating athletes
     */
    public ArrayList<Athlete> getAthletes() {
        return athletes;
    }

    /**
     * Sets the competition results using the medal-winning athletes.
     *
     * @param gold   the gold medal winner
     * @param silver the silver medal winner
     * @param bronze the bronze medal winner
     */
    public void setResults(Athlete gold, Athlete silver, Athlete bronze) {
        this.results = new Result(
                gold.getId(),
                silver.getId(),
                bronze.getId()
        );
    }

    /**
     * Sets the athletes associated with the competition.
     *
     * @param athletes the list of participating athletes
     */
    public void setAthletes(ArrayList<Athlete> athletes) {
        this.athletes = athletes;
    }

    /**
     * Creates a competition from a CSV line.
     * Parses the competition information, attribute weights
     * and athlete identifiers.
     *
     * @param line the CSV line containing the competition data
     * @return the competition created from the CSV line
     */
    public static Competition createFromLine(String line) {

        // Split the line into fields using commas.
        String[] parts = line.split(",", -1);

        // Parse the competition date and time.
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .parse(parts[3], new ParsePosition(0));

        // Parse the attribute weights.
        Weight weights = new Weight(
                Double.parseDouble(parts[4].trim()),
                Double.parseDouble(parts[5].trim()),
                Double.parseDouble(parts[6].trim()),
                Double.parseDouble(parts[7].trim()),
                Double.parseDouble(parts[8].trim())
        );

        // Parse the athlete identifiers, splitting them by "|" and trimming spaces.
        ArrayList<String> athleteIds = new ArrayList<>();

        for (String id : parts[9].split("\\|")) {
            athleteIds.add(id.trim());
        }

        // Create the competition using the constructor.
        return new Competition(
                parts[0],
                COMPETITION_FORMAT.valueOf(parts[1]),
                parts[2],
                date,
                weights,
                athleteIds
        );
    }

    /**
     * Clears the competition results by removing the assigned medals.
     */
    public void clearResults() {
        results = null;
    }

//    public boolean removeAthlete(String id) {
//        boolean removed = athleteIds.removeIf(
//                athleteId -> athleteId.equals(id)
//        );
//
//        if (removed && athletes != null) {
//            athletes.removeIf(
//                    athlete -> athlete.getId().equals(id)
//            );
//        }
//
//        return removed;
//    }


}