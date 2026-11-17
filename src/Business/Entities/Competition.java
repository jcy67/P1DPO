package Business.Entities;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Competition {

    private String id;
    private COMPETITION_FORMAT format;
    private String name;
    private Date dateTime;
    private Weight weights;
    private ArrayList<String> athleteIds;
    private Result results;

    private transient ArrayList<Athlete> athletes;

    // Para Gson
    public Competition() {}

    // Constructor para el csv
    public Competition(String id, COMPETITION_FORMAT format, String name, Date dateTime, Weight weights, ArrayList<String> athleteIds) {
        this.id = id;
        this.format = format;
        this.name = name;
        this.dateTime = dateTime;
        this.weights = weights;
        this.athleteIds = athleteIds;
        this.results = null;
    }

    public String getId() {
        return id;
    }

    public COMPETITION_FORMAT getFormat() {
        return format;
    }

    public String getName() {
        return name;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public Weight getWeights() {
        return weights;
    }

    public double getSpeed() {
        return weights.getSpeed();
    }

    public double getEndurance() {
        return weights.getEndurance();
    }

    public double getStrength() {
        return weights.getStrength();
    }

    public double getTechnique() {
        return weights.getTechnique();
    }

    public double getPrecision() {
        return weights.getPrecision();
    }

    public ArrayList<String> getAthleteIds() {
        return athleteIds;
    }

    public Result getResults() {
        return results;
    }

    public String getGold() {
        return results.getGold();
    }

    public String getSilver() {
        return results.getSilver();
    }

    public String getBronze() {
        return results.getBronze();
    }

    public ArrayList<Athlete> getAthletes() {
        return athletes;
    }

    public void setResults(Athlete gold, Athlete silver, Athlete bronze) {
        this.results = new Result(gold.getId(), silver.getId(), bronze.getId());
    }

    public void setAthletes(ArrayList<Athlete> athletes) {
        this.athletes = athletes;
    }

    // Crea una competicion desde la linea del csv
    public static Competition createFromLine(String line) {

        // Separa la linea en partes, a partir de cada , una parte
        String[] parts = line.split(",", -1);

        // Parsea al formato
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(parts[3], new ParsePosition(0));

        // Parsea los weights
        Weight weights = new Weight(
                Double.parseDouble(parts[4].trim()),
                Double.parseDouble(parts[5].trim()),
                Double.parseDouble(parts[6].trim()),
                Double.parseDouble(parts[7].trim()),
                Double.parseDouble(parts[8].trim())
        );

        // Parsea los Ids y los recorta, separandolos por "|"
        ArrayList<String> athleteIds = new ArrayList<>();
        for (String id: parts[9].split("\\|")) {
            athleteIds.add(id.trim());
        }

        // Crea la competicion con el constructor
        return new Competition( parts[0], COMPETITION_FORMAT.valueOf(parts[1]), parts[2], date, weights, athleteIds);
    }


}
