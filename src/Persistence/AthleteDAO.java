package Persistence;

import Business.Entities.Athlete;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;


public class AthleteDAO {
    private static final String ATHLETES_PATH = "data/athletes.json";

    private final Gson gson;
    public AthleteDAO() {this.gson = new Gson();}

    public ArrayList<Athlete> readAthletes() {
        ArrayList<Athlete> athletes = new ArrayList<>();
        try {
            String jsonContent = Files.readString(Paths.get(ATHLETES_PATH));
            Athlete[] parsedItems = gson.fromJson(jsonContent, Athlete[].class);
            athletes.addAll(Arrays.asList(parsedItems));
        } catch (IOException e) {
            return null;
        }
        return athletes;
    }
}