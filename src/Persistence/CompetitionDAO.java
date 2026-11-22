package Persistence;

import Business.Entities.Competition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Manages the persistence of competitions in JSON and CSV files.
 * Provides methods to read, import and save competition data.
 */
public class CompetitionDAO {

    private static final String COMPETITIONS_PATH = "data/competitions.json";
    private final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    private final Gson gson;


    /**
     * Creates a CompetitionDAO and configures the Gson object.
     */
    public CompetitionDAO() {

        // serializeNulls() includes null attributes in the JSON file,
        // setDateFormat() uses the specified date format,
        // and setPrettyPrinting() formats the JSON with indentation.
        this.gson = new GsonBuilder().setDateFormat(DATETIME_FORMAT).serializeNulls().setPrettyPrinting().create();
    }


    /**
     * Reads all competitions from the JSON file.
     * Returns an empty list if the file does not exist.
     *
     * @return an ArrayList containing the competitions
     * @throws IOException if the file cannot be read or contains no valid list
     */
    public ArrayList<Competition> readCompetitions() throws IOException{

        ArrayList<Competition> competitions = new ArrayList<>();

        // If the file does not exist, return an empty list without creating the JSON file.
        if (Files.notExists(Paths.get(COMPETITIONS_PATH))) {
            return competitions;
        }

        String jsonContent = Files.readString(Paths.get(COMPETITIONS_PATH));
        Competition[] parsedItems = gson.fromJson(jsonContent, Competition[].class);

        // If Gson returns null, throws an exception
        if (parsedItems == null){
            throw new IOException();
        }

        competitions.addAll(Arrays.asList(parsedItems));

        return competitions;
    }


    /**
     * Reads all lines from a CSV file located in the data directory.
     *
     * @param filename the name of the CSV file to read
     * @return an ArrayList containing the lines of the CSV file
     * @throws IOException if an error occurs while reading the file
     */
    public ArrayList<String> readCSV(String filename) throws IOException{
        return new ArrayList<>(Files.readAllLines(Paths.get("data",filename)));
    }


    /**
     * Saves the given competitions to the JSON file.
     * Creates the file if it does not exist.
     *
     * @param competitions the list of competitions to save
     * @throws IOException if an error occurs while writing the file
     */
    public void saveCompetitions(ArrayList<Competition> competitions)throws IOException{
        Files.writeString(Path.of(COMPETITIONS_PATH), gson.toJson(competitions));
    }
}
