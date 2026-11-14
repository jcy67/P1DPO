package Persistence;

import Business.Entities.Athlete;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;



public class AthleteDAO {


    private final Gson gson;
    public AthleteDAO() {this.gson = new Gson();}

    private static final String ATHLETES_PATH = "data/athletes.json";
    private final FileReader fr;

    public AthleteDAO() throws FileNotFoundException {
        URL resource = ClassLoader.getSystemClassLoader().getResource(ATHLETES_PATH);
        fr = new FileReader(resource.getFile());
    }

    public Athlete readAthleteFile() {
        Gson g = new Gson();
        JsonReader reader = new JsonReader(fr);
        Athlete athlete = g.fromJson(reader, Athlete.class);
        return athlete;
    }

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

