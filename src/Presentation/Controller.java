package Presentation;

import Business.OlympicsManager;

public class Controller {

    private final UIManager uiManager;
    private final OlympicsManager olympicsManager;

    public Controller(OlympicsManager om, UIManager ui) {
        this.uiManager = ui;
        this.olympicsManager = om;
    }

    public void run(){

    }
}
