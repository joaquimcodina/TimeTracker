import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/*
    This class reads a .JSON file, parses its information and saves it into the
    Project in order to recover the data and maintain the persistence.
    @version 1.0
    @since 2022-11-06
 */
public class ReadJson {
  private List<Component> hierarchy;

  public Component getRoot() {
    return hierarchy.get(0);
  }

  /*
This constructor directly initializes the file reading process.
It reads the "./data/data.json" file and stores its
information into the this.hierarchy attribute.
@return ReadJSON
@usage: new ReadJSON(Component);
  If you want to get the first element of the hierarchy, in order to use it for anything
  (like printing it into console):
      ReadJSON hierarchy = new ReadJSON();
      Component comp = hierarchy.getRoot();
*/
  public ReadJson() throws FileNotFoundException {
    String path = "./data/data.json";
    System.out.println("Loading json file: from " + path);
    InputStream is;
    is = new FileInputStream(path);
    JSONTokener token = new JSONTokener(is);
    JSONObject object = new JSONObject(token);
    JSONArray components = object.getJSONArray("components");
    List<Component> componentList = new LinkedList<>();

    for (int i = 0; i < components.length(); i++) {
      JSONObject jsonObject1 = components.getJSONObject(i);
      this.hierarchy = createObject(jsonObject1, componentList);
    }
    System.out.println("File " + path + " successfully loaded \n");

  }

  /*
This method creates a certain object depending on the parsed information of the .JSON file.
@param component : The JSON component parsed.
@param createdObjects : A List of the createdObjects, in order to create new ones from these.
@return List<Component> It returns the createdObject list plus the new created Object, if created.
*/
  private List<Component> createObject(JSONObject component, List<Component> createdObjects) {
    LocalDateTime startDate = null;
    LocalDateTime finalDate = null;
    String type = component.getString("type");
    String fatherName = component.getString("father");

    if (!component.getString("start_date").equals("null")) {
      startDate = LocalDateTime.parse(component.getString("start_date"));
      //pass the string to a LocalDateTime with parse
    }
    if (!component.getString("final_date").equals("null")) {
      finalDate = LocalDateTime.parse(component.getString("final_date"));
    }
    Duration elapsedTime = Duration.parse(component.getString("elapsed_time"));

    if (type.equals("Interval")) {
      new Interval(startDate, finalDate,
          (Task) Objects.requireNonNull(searchFatherByName(fatherName,
          createdObjects)), elapsedTime);  //search father by name
    } else {  //Component
      String name = component.getString("name");

      if (type.equals("Project")) {
        Project project = new Project(name,
            (Project) searchFatherByName(fatherName, createdObjects),
            elapsedTime, startDate, finalDate);
        createdObjects.add(project);
      } else {
        Task task = new Task(name,
            (Project) searchFatherByName(fatherName, createdObjects),
            elapsedTime, startDate, finalDate);
        createdObjects.add(task);
      }
    }
    return createdObjects;
  }

  /*
This method searches in the parameter componentList the name of a Component in order to create it.
@param name : The name of the father's object to be searched.
@param createdObjects : A List of the createdObjects, in order to create new ones from these.
@return Object. It really returns a Task, an Interval or a Project.
*/
  private Object searchFatherByName(String name, List<Component> componentList) {
    for (Component component : componentList) {
      String nameVar = component.getName();
      if (nameVar.equals(name)) {
        return component;
      }
    }
    return null;
  }
}