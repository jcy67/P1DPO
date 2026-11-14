package Persistence;

import Business.Entities.Competition;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class CompetitionDAO {
    private static final String COMPETITIONS_PATH = "data/competitions.json";

    private final Gson gson;
    public CompetitionDAO() {this.gson = new Gson();}

    public ArrayList<Competition> readCompetitions() {
        ArrayList<Competition> competitions = new ArrayList<>();
        try {
            String jsonContent = Files.readString(Paths.get(COMPETITIONS_PATH));
            Competition[] parsedItems = gson.fromJson(jsonContent, Competition[].class);
            competitions.addAll(Arrays.asList(parsedItems));
        } catch (IOException e) {
            return null;
        }
        return competitions;
    }
}
