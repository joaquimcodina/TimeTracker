import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class User {
    public static void saveData(Project project, File file) {
        try{
            JSONArray jsonArray = new JSONArray();
            JSONObject obj;
            FileWriter fileData = new FileWriter(file, false);
            ObjectMapper mapper = new ObjectMapper();

            //JSONObject dels projectes, tasques filles
            for(int i=0 ; i < project.getComponentList().size(); i++){
                obj = new JSONObject();
                obj.put("name", project.getComponentList().get(i).getName());
                if(project.getComponentList().get(i).getFather() == null)
                    obj.put("father", "null");
                else
                    obj.put("father", project.getComponentList().get(i).getFather().getName());

                obj.put("type", project.getComponentList().get(i).getClass());
                obj.put("start_date", (project.getComponentList().get(i).getStartDate() == null ? null : project.getComponentList().get(i).getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                obj.put("final_date", (project.getComponentList().get(i).getFinalDate() == null ? null : project.getComponentList().get(i).getFinalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                obj.put("elapsed_time", project.getComponentList().get(i).getElapsedTime().toSeconds());
                jsonArray.put(obj);
            }
            obj = new JSONObject(); //father object
            obj.put("name", project.getName());
            obj.put("father", "null");
            obj.put("type", project.getClass()); //tipus projecte, tasca o interval
            obj.put("start_date", (project.getStartDate() == null ? null : project.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            obj.put("final_date", (project.getFinalDate() == null ? null : project.getFinalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            obj.put("elapsed_time", project.getElapsedTime().toSeconds());
            obj.put("children", jsonArray); //add list father

            fileData.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(obj.toString())));
            fileData.flush();
            fileData.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //project.accept(new DataPersistence(project, file)); //començar proces de persistencia de dades, a partir de Visitor
    }

    public static void generateTree() throws InterruptedException, IOException {
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
        //jsonSafe(simpleTest(), file);

        List<Task> tasks = new LinkedList<>(); //Llista amb projecte (root) i tasques a testejar
        Project root = new Project("root");

        Project softwareDesign = new Project("Software Design", root);
        new Project("Software Testing", root);
        new Project("Database", root);
        Task transportation = new Task("Transportation", root);

        Project problems = new Project("Problems", softwareDesign);
        Project projectTimeTracker = new Project("Project Time Tracker", softwareDesign);

        Task fl = new Task("First List", problems);
        Task sl = new Task("Second List", problems);

        new Task("Read Handout", projectTimeTracker);
        new Task("First Milestone", projectTimeTracker);

        //add tasks in taskslist
        tasks.add(transportation);
        tasks.add(fl);
        tasks.add(sl);

        //init taskA
        testA(root, tasks, file);
    }

    public static void testA(Project root, List<Task> tasks, File file) throws InterruptedException, IOException {
        Printer printer = new Printer(root);

        //Point 1:
        ClockTimer.getInstance().startClock();
        Thread.sleep(1500L);
        root.accept(printer); //accept root
        //Point 1:

        //Point 2:
        tasks.get(0).start(); //start transportation task
        Thread.sleep(6000L); //wait 6 seconds
        tasks.get(0).stop(); //stop transportation task
        root.accept(printer); //accept root
        //Point 2:

        //Point 3:
        Thread.sleep(2000L); //wait 2 seconds
        root.accept(printer); //accept root
        System.out.println("\n");
        //Point 3:

        //Point 4:
        tasks.get(1).start(); //first list task start
        Thread.sleep(6000L); //wait 6 seconds
        root.accept(printer); //accept root
        //Point 4:

        //Point 5:
        tasks.get(2).start(); //second list task start
        Thread.sleep(4000L);
        root.accept(printer); //accept root
        //Point 5:

        //Point 6:
        tasks.get(1).stop(); //first list task stop
        root.accept(printer); //accept rot
        //Point 6:

        //Point 7:
        Thread.sleep(2000L); //wait 2 seconds
        tasks.get(2).stop(); //seconds list task stop
        root.accept(printer); //accept root
        //Point 7:

        //Point 8:
        Thread.sleep(2000L);
        root.accept(printer);
        System.out.println("\n");
        //Point 8:

        //Point 9:
        tasks.get(0).start(); //transportation task start
        Thread.sleep(4000L); //wait 4 seconds
        tasks.get(0).stop(); //transportation task stop
        root.accept(printer); //accept root
        System.out.println("\n");
        //Point 9:

        System.out.println("\n");
        ClockTimer.getInstance().stopClock();

        //guardar dades en JSON
        saveData(root, file);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        generateTree();  //nice and correct
        //testA(components, file); //nice and correct
    }
}
