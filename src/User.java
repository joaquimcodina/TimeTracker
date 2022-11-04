import java.io.*;
public class User {

    public static Component testApendixA() throws InterruptedException {

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

        PrinterTestA printerTestA = new PrinterTestA(root);
        root.accept(printerTestA);
        System.out.println("\nEnd of Test A\n");

        return root;
    }

    public static Component testApendixB() throws InterruptedException {
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
        System.out.println("\t\t\t\t\t\t\t\t initial date \t\t\t\t final date \t\t\t\t duration");
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
        //Point 8:

        //Point 9:
        transportation.start();
        System.out.println("transportation starts");
        Thread.sleep(4000L);
        transportation.stop();
        System.out.println("transportation stops");
        //Point 9:

        ClockTimer.getInstance().stopClock();

        System.out.println("End of Test B\n");

        return root;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        /*testApendixA();  //nice and correct
        Component root = testApendixB(); //nice and correct

        new SaveJSON(root);//nice and correct*/
        ReadJSON a = new ReadJSON();
        Component newRoot = a.getRoot();
        PrinterTestA printerTestA = new PrinterTestA((Project)newRoot);
        System.out.println("Printing tree loaded from json file: \n");
        newRoot.accept(printerTestA);
    }
}
