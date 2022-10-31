import java.io.*;
import java.time.Clock;
import java.time.LocalDateTime;
//import org.json.*;

public class User {

  /*public static void jsonSafe(Project project, File file) throws IOException {
    //JSON PART
    JSONArray jsonArray = new JSONArray();
    JSONObject obj;
    FileWriter fileData = new FileWriter(file, false);

    //JSONObject dels projectes, tasques filles
    for(int i=0 ; i < project.getComponentList().size(); i++){
      obj = new JSONObject();
      obj.put("name", project.getComponentList().get(i).getName());
      if(project.getComponentList().get(i).getFather() == null)
        obj.put("father", "null");
      else
        obj.put("father", project.getComponentList().get(i).getFather().getName());

      obj.put("type", project.getComponentList().get(i).getClass());
      obj.put("start_date", project.getComponentList().get(i).getStartDate());
      obj.put("final_date", project.getComponentList().get(i).getFinalDate());
      obj.put("elapsed_time", project.getComponentList().get(i).getElapsedTime().toSeconds());
      jsonArray.put(obj);
    }
    obj = new JSONObject(); //father object
    obj.put("name", project.getName());
    obj.put("father", "null");
    obj.put("type", project.getClass()); //tipus projecte, tasca o interval
    obj.put("start_date", project.getStartDate());
    obj.put("final_date", project.getFinalDate());
    obj.put("elapsed_time", project.getElapsedTime().toSeconds());
    obj.put("children", jsonArray); //add list father

    fileData.write(obj.toString());
    fileData.flush();
    fileData.close();
  }*/

  public static Project testA() throws InterruptedException {

    System.out.println("\nBegin of Test A\n");

    Project root = new Project("root");

    Project softwareDesign = new Project("Software Design", root);
    Project st = new Project("Software Testing", root);
    Project db = new Project("Database", root);
    Task transportation = new Task("Transportation", root);

    Project problems = new Project("Problems", softwareDesign);
    Project projectTimeTracker = new Project("Project Time Tracker", softwareDesign);

    Task fl = new Task("First List", problems);
    Task sl = new Task("Second List", problems);

    Task rh = new Task("Read Handout", projectTimeTracker);
    Task fm = new Task("First Milestone", projectTimeTracker);

    System.out.println("\nEnd of Test A\n");

    return root;
  }

  public static void testB() throws InterruptedException {
    Project root = new Project("root");

    Project softwareDesign = new Project("Software Design", root);
    Project st = new Project("Software Testing", root);
    Project db = new Project("Database", root);
    Task transportation = new Task("Transportation", root);

    Project problems = new Project("Problems", softwareDesign);
    Project projectTimeTracker = new Project("Project Time Tracker", softwareDesign);

    Task task1 = new Task("First List", problems);
    Task task2 = new Task("Second List", problems);

    Task rh = new Task("Read Handout", projectTimeTracker);
    Task fm = new Task("First Milestone", projectTimeTracker);

    Printer printer = new Printer(root);

    System.out.println("\nBegin of Test B\n");

    //Point 1:
    ClockTimer.getInstance().startClock();
    Thread.sleep(1500L);
    root.accept(printer);
    //Point 1:

    //Point 2:
    transportation.start();
    Thread.sleep(6000L);
    transportation.stop();
    root.accept(printer);
    //Point 2:

    //Point 3:
    Thread.sleep(2000L);
    root.accept(printer);
    System.out.println("\n");
    //Point 3:

    //Point 4:
    task1.start();
    Thread.sleep(6000L);
    root.accept(printer);
    //Point 4:

    //Point 5:
    task2.start();
    Thread.sleep(4000L);
    root.accept(printer);
    //Point 5:

    //Point 6:
    task1.stop();
    root.accept(printer);
    //Point 6:

    //Point 7:
    Thread.sleep(2000L);
    task2.stop();
    root.accept(printer);
    //Point 7:

    //Point 8:
    Thread.sleep(2000L);
    root.accept(printer);
    System.out.println("\n");
    //Point 8:

    //Point 9:
    transportation.start();
    Thread.sleep(4000L);
    transportation.stop();
    root.accept(printer);
    System.out.println("\n");
    //Point 9:

    System.out.println("\n");
    ClockTimer.getInstance().stopClock();

    System.out.println("\nEnd of Test B\n");
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    //read file
    //String resourceName = "data/data.json";
    //File file = new File(resourceName);
    //JSONArray jsonArray = new JSONArray();
    //JSONObject obj;
    //if (file.exists()) {
        /*InputStream is = Files.newInputStream(file.toPath());
        if (is.available() > 0) {
          JSONTokener tokener = new JSONTokener(is);
          obj = new JSONObject(tokener);
        }*/
    //}
    //jsonSafe(simpleTest(), file);
    testA();  //nice and correct
    testB(); //nice and correct
  }
}
