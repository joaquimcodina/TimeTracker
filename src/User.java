import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.*;

public class User {
    public User() {
    }

    public static void jsonSafe(Project project, File file) throws IOException {
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
        obj.put("type", Component.class);
        obj.put("start_date", project.getComponentList().get(i).getStartDate());
        obj.put("final_date", project.getComponentList().get(i).getFinalDate());
        obj.put("elapsed_time", project.getComponentList().get(i).getElapsedTime().toSeconds());
        jsonArray.put(obj);
      }
      obj = new JSONObject(); //father object
      obj.put("name", project.getName());
      obj.put("father", "null");
      obj.put("type", Component.class); //tipus projecte, tasca o interval
      obj.put("start_date", project.getStartDate());
      obj.put("final_date", project.getFinalDate());
      obj.put("elapsed_time", project.getElapsedTime().toSeconds());
      obj.put("children", jsonArray); //add list father

      fileData.write(obj.toString());
      fileData.flush();
      fileData.close();
    }

    public static Project simpleTest() throws InterruptedException {
        Project root = new Project("root");
        Project softwareDesign = new Project("Software Design", root);
        new Project("Software Testing", root);
        new Project("Database", root);
        new Project("Transportation", root);
        Project problems = new Project("Problems", softwareDesign);
        Project projectTimeTracker = new Project("Project Time Tracker", softwareDesign);
        Task task1 = new Task("First List", problems);
        Task task2 = new Task("Second List", problems);
        new Task("Read Handout", projectTimeTracker);
        new Task("First Milestone", projectTimeTracker);
        Printer printer = new Printer(root);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now));
        root.accept(printer);
        task1.start(now);
        Thread.sleep(2000L);
        task1.stop();
        System.out.println("\n");
        now = LocalDateTime.now();
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now));
        root.accept(printer);
        Thread.sleep(2000L);
        task1.stop();
        System.out.println("\n");
        now = LocalDateTime.now();
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now));
        root.accept(printer);
        task2.start(now);
        Thread.sleep(2000L);
        task1.stop();
        task2.stop();
        System.out.println("\n");
        now = LocalDateTime.now();
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now));
        root.accept(printer);
        System.out.println("\n");
        ClockTimer.getInstance().stopClock();
        return root;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
      //read file
      String resourceName = "data/data.json";
      File file = new File(resourceName);
      //JSONArray jsonArray = new JSONArray();
      //JSONObject obj;
      //if (file.exists()) {
        /*InputStream is = Files.newInputStream(file.toPath());
        if (is.available() > 0) {
          JSONTokener tokener = new JSONTokener(is);
          obj = new JSONObject(tokener);
        }*/
      //}
      jsonSafe(simpleTest(), file);
    }
}
