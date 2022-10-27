import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    public User() {
    }

    public static void simpleTest() throws InterruptedException {
        Project root = new Project("root");
        Project softwareDesign = new Project("Software Design", root);
        new Project("Software Testing", root);
        new Project("Database", root);
        new Project("Task Transportation", root);
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
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest();
    }
}
