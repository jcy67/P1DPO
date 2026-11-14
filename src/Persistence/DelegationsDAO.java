package Persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class DelegationsDAO {
    private static final String DELEGATIONS_PATH = "data/delegations.json";

    private final Gson gson;
    public DelegationsDAO() {this.gson = new Gson();}

    public ArrayList<Delegation> readDelegations() {
        ArrayList<Delegation> delegations = new ArrayList<>();
        try {
            String jsonContent = Files.readString(Paths.get(DELEGATIONS_PATH));
            Delegation[] parsedItems = gson.fromJson(jsonContent, Delegation[].class);
            delegations.addAll(Arrays.asList(parsedItems));
        } catch (IOException e) {
            return null;
        }
        return delegations;
    }
}
