import java.io.IOException;

public class SaveJSON implements Visitor {
  //This class saves a hierarchy when declared. You can save a tree doing SaveJSON a = new SaveJSON(root);
  
  

  //private JSONArray jsonArray = new JSONArray();
  //private FileWriter fileData = new FileWriter(file, false);
  // private ObjectMapper mapper = new ObjectMapper();

  public saveJSON(Component comp) {
    System.out.println("Saving data in a .json file...");
    if (comp != null)
      comp.accept(this);
  }

  @Override
  public void visitProject(Project var1) {
    try {
      //inputData(var1);
      for (Component component : var1.getComponentList())
        component.accept(this);
      //fileData.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(obj.toString())));
      //fileData.flush();
      //fileData.close();
    }
    catch(IOException err) {
      err.printStackTrace();
    }
  }

  /*private void inputData(Component var1) {
    JSONObject obj = new JSONObject();

    obj.put("name", var1.getName());

    if (var1.getFather() == null)
      obj.put("father", null);
    else
      obj.put("father", var1.getFatherName());

    obj.put("type", var1.getClass());

    if (var1.getStartDate() == null)
      obj.put("start_date", null);
    else
      obj.put("start_date", var1.getStartDate());

    if (var1.getFinalDate() == null)
      obj.put("final_date", null);
    else
      obj.put("final_date", var1.getFinalDate());

    obj.put("elapsed_time", var1.getElapsedTime());
    this.jsonArray.put(obj);
  }*/

  @Override
  public void visitTask(Task var1) {
    //inputData(var1);
    for (Interval interval : var1.getIntervals())
      interval.accept(this);
  }

  public void visitInterval(Interval var1) {
    /*JSONObject obj = new JSONObject();
    obj.put("father", var1.getFather());
    obj.put("start_date", var1.getStart());
    obj.put("end_date", var1.getEnd());
    obj.put("elapsed_time", var1.getElapsedTime());
    this.jsonArray.put(obj);*/
  }
}
