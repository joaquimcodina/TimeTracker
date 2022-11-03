import java.io.*;
import java.nio.file.Files;
import java.time.Clock;
import java.time.LocalDateTime;
import org.json.*;

public class User {

    public static void createFile(Component project, File file) throws IOException {
        FileWriter fileData = new FileWriter(file, false);
        jsonSafe(project, fileData);
        fileData.flush();
        fileData.close();
        System.out.print("File saved correctly");
    }

    public static void jsonSafe(Component project, FileWriter fileData) throws IOException {
        //JSON PART
        JSONArray jsonArray = new JSONArray();
        JSONObject obj;
        //JSONObject dels projectes, tasques filles
        obj = new JSONObject();
        obj.put("name", project.getName());
        if(project.getFather() == null)
            obj.put("father", "null");
        else
            obj.put("father", project.getFather().getName());

        obj.put("type", project.getClass());
        obj.put("start_date", project.getStartDate());
        obj.put("final_date", project.getFinalDate());
        obj.put("elapsed_time", project.getElapsedTime().getSeconds());
        jsonArray.put(obj);
        fileData.write(obj.toString());

        for (Component component : project.getComponentList()){
            if (component.getClass().getName().equals("Project")) {
                jsonSafe(component, fileData);
            }
            else{ //en cas de que sigui una task
                obj = new JSONObject();
                obj.put("name", component.getName());
                if(component.getFather() == null)
                    obj.put("father", "null");
                else
                    obj.put("father", component.getFather().getName());

                obj.put("type", component.getClass());
                obj.put("start_date", component.getStartDate());
                obj.put("final_date", component.getFinalDate());
                obj.put("elapsed_time", component.getElapsedTime().getSeconds());
                //if (component.getIntervals() != null)
                    //obj.put("interval", component.getIntervals().get(0));
                jsonArray.put(obj);
                fileData.write(obj.toString());
            }
        }
    }

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

    public static Component testB() throws InterruptedException {
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
        System.out.println("\nBegin of Test B\n");
        PrinterTestB printertestB = new PrinterTestB();

        //Point 1:
        //ClockTimer.getInstance().startClock();
        Thread.sleep(1500L);
        //Point 1:

        //Point 2:
        System.out.println("\t \t \t initial date \t final date \t duration");
        System.out.println("Start test");
        System.out.println("transportation starts");
        transportation.start();
        ClockTimer.getInstance().addObserver(printertestB);
        Thread.sleep(6000L);
        transportation.stop();
        System.out.println("transportation stops");
        //Point 2:

        //Point 3:
        Thread.sleep(2000L);
        System.out.println("\n");
        //Point 3:

        //Point 4:
        System.out.println("first list starts");
        task1.start();
        Thread.sleep(6000L);
        //Point 4:

        //Point 5:
        System.out.println("second list starts");
        task2.start();
        Thread.sleep(4000L);
        //Point 5:

        //Point 6:
        task1.stop();
        System.out.println("first list stops");
        //Point 6:

        //Point 7:
        Thread.sleep(2000L);
        task2.stop();
        System.out.println("second list stops");
        //Point 7:

        //Point 8:
        Thread.sleep(2000L);
        System.out.println("\n");
        //Point 8:

        //Point 9:
        transportation.start();
        System.out.println("transportation starts");
        Thread.sleep(4000L);
        transportation.stop();
        System.out.println("transportation stops");
        //Point 9:

        ClockTimer.getInstance().stopClock();

        System.out.println("End of Test B");

        return root;
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

        testA();  //nice and correct
        Component m = testB(); //nice and correct

        String resourceName = "data/data.json";
        File file = new File(resourceName);
        JSONArray jsonArray = new JSONArray();
        JSONObject obj;
        if (file.exists()) {
            InputStream is = Files.newInputStream(file.toPath());
            if (is.available() > 0) {
              JSONTokener tokener = new JSONTokener(is);
              obj = new JSONObject(tokener);
            }
        }
        createFile(m, file);

    }
}
