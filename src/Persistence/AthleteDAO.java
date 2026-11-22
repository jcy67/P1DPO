package Persistence;

import Business.Entities.Athlete;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Manages the persistence of athletes in a JSON file.
 * Provides methods to read athlete data.
 */
public class AthleteDAO {

    private static final String ATHLETES_PATH = "data/athletes.json";
    private final Gson gson;


    /**
     * Creates an AthleteDAO and configures Gson to use
     * the specified date format.
     */
    public AthleteDAO() { this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();}


    /**
     * Reads all athletes from the JSON file.
     * Returns an empty list if the file does not exist.
     *
     * @return an ArrayList containing the athletes
     * @throws IOException if the file cannot be read or contains no valid list
     */
    public ArrayList<Athlete> readAthletes() throws IOException{
        ArrayList<Athlete> athletes = new ArrayList<>();

        // If the file does not exist, return an empty list.
        if (Files.notExists(Paths.get(ATHLETES_PATH))) {
            return athletes;
        }

        String jsonContent = Files.readString(Paths.get(ATHLETES_PATH));
        Athlete[] parsedItems = gson.fromJson(jsonContent, Athlete[].class);

        if (parsedItems == null){
            throw new IOException();
        }

        athletes.addAll(Arrays.asList(parsedItems));

        return athletes;
    }

    public void saveAthletes(ArrayList<Athlete> athletes) throws IOException {
        Files.writeString(Path.of(ATHLETES_PATH), gson.toJson(athletes));
    }

}


