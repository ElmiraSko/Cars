import java.util.ArrayList;
import java.util.Arrays;

public class Race { // гонка
    private ArrayList<Stage> stages; // список этапов

    public ArrayList<Stage> getStages() { return stages; } // возвращает список этапов

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}