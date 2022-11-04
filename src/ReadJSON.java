import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ReadJSON {
    private List<Component> hierarchy = new LinkedList<>();
    private List<Interval> intervals = new LinkedList<>();

    public Component getRoot() { return hierarchy.get(0); }
    public ReadJSON() {
        //String path = 'data/data.json";
        //InputStream is = ReadJSON.class.getResourceAsStream("data/data.json");
        String path = "./data/data.json";
        System.out.println("Loading json file: from " + path);
        InputStream is = null;
        try {
            is = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        JSONArray components = object.getJSONArray("components");
        List<Component> componentList = new LinkedList<>();

        for (int i = 0; i < components.length(); i++) {
            JSONObject jsonObject1 = components.getJSONObject(i);
            createObject(jsonObject1, componentList);
        }
        this.hierarchy = componentList;
        System.out.println("File " + path + " successfully loaded \n");

    }
    private List<Component> createObject(JSONObject component, List<Component> createdObjects) {
        LocalDateTime startDate = null, finalDate = null;
        String type = component.getString("type");
        String fatherName = component.getString("father");
        
        if (!component.getString("start_date").equals("null"))
            startDate = LocalDateTime.parse(component.getString("start_date")); //pass the string to a LocalDateTime with parse
        if (!component.getString("final_date").equals("null"))
            finalDate = LocalDateTime.parse(component.getString("final_date"));
        Duration elapsedTime = Duration.parse(component.getString("elapsed_time"));

        if (type.equals("Interval")) {
            Interval interval = new Interval(startDate, finalDate, (Task)searchFatherByName(fatherName, createdObjects),elapsedTime);  //search father by name
            intervals.add(interval);
        }
        else {  //Component
            String name = component.getString("name");

            if (type.equals("Project")) {
                Project project = new Project(name, (Project)searchFatherByName(fatherName, createdObjects), elapsedTime, startDate, finalDate);
                createdObjects.add(project);
            }
            else {
                Task task = new Task(name,(Project)searchFatherByName(fatherName, createdObjects), elapsedTime, startDate,finalDate);
                createdObjects.add(task);
            }
        }
        return createdObjects;
    }
    //This method creates a certain object from a parsed .JSON object.

    private Object searchFatherByName(String name, List<Component> componentList) {
        for (Component component : componentList) {
            String nameVar = component.getName();
            if (nameVar.equals(name))
                return component;
        }
        return null;
    }
    //This method search which is the father by his name
}