import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User{
  public static void simpleTest() throws InterruptedException {
    Project root = new Project("root");
    Project softwareDesign = new Project("Software Design", root);
    Project softwareTesting = new Project("Software Testing", root);
    Project database = new Project("Database", root);
    Project taskTransportation = new Project("Task Transportation", root);
    Project problems = new Project("Problems", softwareDesign);
    Project projectTimeTracker = new Project("Project Time Tracker", softwareDesign);

    Task task1 = new Task("First List", problems);
    Task task2 = new Task("Second List", problems);
    Task task3 = new Task("Read Handout", projectTimeTracker);
    Task task4 = new Task("First Milestone", projectTimeTracker);
    Printer printer = new Printer(root);

    LocalDateTime now = LocalDateTime.now();
    System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now));
    printer.visitProject(root);
    task1.start(now);
    Thread.sleep(2000);
    task1.stop();
    System.out.println("\n");

    now = LocalDateTime.now();
    System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now));
    printer.visitProject(root);
    Thread.sleep(2000);
    task1.stop();
    System.out.println("\n");

    now = LocalDateTime.now();
    System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now));
    printer.visitProject(root);
    task2.start(now);
    Thread.sleep(2000);
    task1.stop();
    task2.stop();
    System.out.println("\n");

    now = LocalDateTime.now();
    System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now));
    printer.visitProject(root);
    System.out.println("\n");

    ClockTimer.getInstance().stopClock();
  }
  public static void main(String[] args) throws InterruptedException {
    simpleTest();
  }
}
