import java.io.*;
/*
    This class represents a user which will be using the application.
    It is currently used for testing the four tests we have.

    @version 3.0
    @since 2022-11-06
 */

public class User {
    /*
        This static method is a test in which we input a hierarchy and print it into a console.

        This test is the first test we have, that corresponds to the appendix "A".
        It is called by the main method in this class too.
     */
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


    /*
        This static method is a test in which we input a hierarchy, and we trigger events
         such as a start of a task or the end of it, and then we print it into a console
         every time we receive an update from the clockTimer class.

        This test is the second test we have, that corresponds to the appendix "B".
        It is called by the main method in this class too.

        The printing of the hierarchy is done through another Visitor class which is
        called "PrinterTestB.java".

        @usage In order to do it correctly, the best option is to modify on this method just
            the things related to the hierarchy, such as initializing a task Interval or stopping it.
            You can initialize a task Interval by doing:
                <taskName>.start();
            You can stop a task Interval by doing:
                <taskName>.stop();

        @return Component This method return's value is a Component element. It basically returns the top of the hierarchy.
            In other words, because we can analyse this as a tree problem, it returns the root of the tree,
            the node that does not have a single parent and have 0 or more decendents.

     */
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

    /*
        This static method is the main class of the User simulation.
        This method calls to the both other methods in this class, and calls too to
        another methods in other class in order to test them, such as saving in an .JSON
        file the hierarchy (SaveJSON.java) and restoring it into the program (ReadJSON.java).

        @usage After saving the hierarchy in a .JSON file, you will be able to see it in the file:
            "./data/data.json".

               After saving it into the .JSON file, it will try to recover it into this program, and
               thus, it will print the result in console.

        @param args Currently this method has no input value, so, this parameter is unused.
     */

    public static void main(String[] args) throws InterruptedException, IOException {
        testApendixA();
        Component root = testApendixB();

        new SaveJSON(root);
        ReadJSON a = new ReadJSON();
        Component newRoot = a.getRoot();
        PrinterTestA printerTestA = new PrinterTestA((Project)newRoot);
        System.out.println("Printing tree loaded from json file: \n");
        newRoot.accept(printerTestA);
    }
}
