import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.json.*;

public class SaveJSON implements Visitor {
    //This class saves a hierarchy when declared. You can save a tree doing SaveJSON a = new SaveJSON(root);

    private JSONArray jsonArray = new JSONArray();
    private JSONObject obj = null;
    //private ObjectMapper mapper = new ObjectMapper();
    private FileWriter fileData = null;
    private InputStream is = null;
    private File file = null;

    public SaveJSON(Component root) throws IOException {
        System.out.println("Saving data in a .json file...");
        String path = "data/data.json";
        File file = new File(path);
        this.fileData = new FileWriter(file, false);
        if (root != null)
            root.accept(this); //recursive
        try {
            this.obj = new JSONObject();
            this.obj.put("components", jsonArray);
            this.fileData.write(this.obj.toString());
            this.fileData.close();
        } catch(IOException err) {
            throw new RuntimeException(err);
        }
        System.out.println(".json file succesfully created!");
    }
    //This method saves as a JSON object the hierarchy of the root node in a tree structure.

    @Override
    public void visitProject(Project var1) {
        inputData(var1);
        for (Component component : var1.getComponentList())
            component.accept(this);
    }

    private void inputData(Component var1) {
    JSONObject obj = new JSONObject();
    obj.put("name", var1.getName());
    if (var1.getFather() == null)
      obj.put("father", "null");
    else
      obj.put("father", var1.getFatherName());
    obj.put("type", var1.getClass().getName());

      if (var1.getStartDate() == null)
          obj.put("start_date", "null");
      else
          obj.put("start_date", var1.getStartDate());
    
      if (var1.getFinalDate() == null)
          obj.put("final_date", "null");
      else
          obj.put("final_date", var1.getActualDate());

    /*if (var1.getFinalDate() == null)
      obj.put("final_date", "null");
    else
      obj.put("final_date", var1.getFinalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));*/

    obj.put("elapsed_time", var1.getElapsedTime());

    this.jsonArray.put(obj);
  }
  //This method saves in a JSON Object the information to be written in a .json file.

    @Override
    public void visitTask(Task var1) {
        inputData(var1);
        for (Interval interval : var1.getIntervals())
            interval.accept(this);
    }

    public void visitInterval(Interval interval) {
    JSONObject obj = new JSONObject();
    obj.put("father", interval.getFather().getName()); //don't talk to strangers?
    if (interval.getStart() == null)
        obj.put("start_date", "null");
    else
        obj.put("start_date", interval.getStart());
    
    if (interval.getEnd() == null)
        obj.put("final_date", "null");
    else
        obj.put("final_date", interval.getEnd());
    obj.put("elapsed_time", interval.getElapsedTime());
    obj.put("type", interval.getClass().getName());
    this.jsonArray.put(obj);

    }
}