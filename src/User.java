import fitados.SearchByTag;
import fitaun.ClockTimer;
import fitaun.Component;
import fitaun.PrinterTestA;
import fitaun.PrinterTestB;
import fitaun.Project;
import fitaun.ReadJson;
import fitaun.SaveJson;
import fitaun.Task;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

// This class represents a user which will be using the application.
// It is currently used for testing the four tests we have.
public class User {

  // This test is the first test we have, that corresponds to the appendix "A".
  // It is called by the main method in this class too.
  public static void testA() {

    //int ieqeqw = 0;
    //assert ieqeqw == 2;
    System.out.println("\nBegin of Test A\n");

    Project root = new Project(1, "root");
    new Project(2, "Software Testing", root);
    new Project(3, "Databases", root);
    new Task(4, "Transportation", root);

    Project softwareDesign = new Project(5, "Software Design", root);
    Project problems = new Project(6, "Problems", softwareDesign);
    Project projectTimeTracker = new Project(7, "Project Time Tracker", softwareDesign);

    new Task(8, "First List", problems);
    new Task(9, "Second List", problems);

    new Task(10, "Read Handout", projectTimeTracker);
    new Task(11, "First Milestone", projectTimeTracker);

    PrinterTestA printerTestA = new PrinterTestA(root);
    root.accept(printerTestA);
    System.out.println("\nEnd of Test A\n");
  }




  // This test is the second test we have, that corresponds
  // to the appendix "B".
  // The printing of the hierarchy is done through another
  // fitaun.Visitor class which is called "fitaun.PrinterTestB.java".
  // In order to do it correctly, the best option is to
  // modify on this method just the things related to the hierarchy, such as
  // initializing a task fitaun.Interval or stopping it.
  // In other words, because we can analyse this as a
  // tree problem, it returns the root of the tree,
  // the node that does not have a single parent and
  // have 0 or more descendents.
  public static Component testB() throws InterruptedException {
    Project root = new Project(1, "root");

    Project softwareDesign = new Project(5, "Software Design", root);
    new Project(2, "Software Testing", root);
    new Project(3, "Databases", root);

    Project projectTimeTracker = new Project(7, "Project Time Tracker", softwareDesign);

    new Task(6, "Read Handout", projectTimeTracker);
    new Task(7, "First Milestone", projectTimeTracker);
    System.out.println("\nBegin of Test B\n");

    Thread.sleep(1500L);

    System.out.println("\t\t\t\t\t\t\t\t initial date \t\t\t\t final date \t\t\t\t duration");
    System.out.println("Start test");
    System.out.println("transportation starts");

    Task transportation = new Task(4, "Transportation", root);
    transportation.start();

    PrinterTestB printertestB = new PrinterTestB();
    ClockTimer.getInstance().addObserver(printertestB);
    Thread.sleep(6000L);
    transportation.stop();
    System.out.println("transportation stops");

    Thread.sleep(2000L);

    Project problems = new Project(6, "Problems", softwareDesign);
    Task task1 = new Task(8, "First List", problems);

    System.out.println("first list starts");
    task1.start();
    Thread.sleep(6000L);

    Task task2 = new Task(9, "Second List", problems);

    System.out.println("second list starts");
    task2.start();
    Thread.sleep(4000L);

    task1.stop();
    System.out.println("first list stops");

    Thread.sleep(2000L);
    task2.stop();
    System.out.println("second list stops");

    Thread.sleep(2000L);

    transportation.start();
    System.out.println("transportation starts");
    Thread.sleep(4000L);
    transportation.stop();
    System.out.println("transportation stops");

    ClockTimer.getInstance().stopClock();

    System.out.println("End of Test B\n");

    // It basically returns the top of the hierarchy.
    return root;
  }

  public static void testSearchByTag() {

    List<String> tagList = new LinkedList<>();
    tagList.add("c++");
    tagList.add("Java");
    tagList.add("python");

    Project root = new Project(1, "root");
    new Project(2, "Software Testing", root, tagList);

    tagList = new LinkedList<>();
    tagList.add("SQL");
    tagList.add("python");
    tagList.add("C++");
    new Project(3, "Databases", root, tagList);

    new Task(4, "Transportation", root);

    tagList = new LinkedList<>();
    tagList.add("java");
    tagList.add("flutter");
    Project softwareDesign = new Project(5, "Software Design", root, tagList);

    Project problems = new Project(6, "Problems", softwareDesign);

    tagList = new LinkedList<>();
    tagList.add("java");
    new Task(8, "First List", problems, tagList);


    tagList = new LinkedList<>();
    tagList.add("Dart");
    new Task(9, "Second List", problems, tagList);

    Project projectTimeTracker = new Project(7, "Project Time Tracker", softwareDesign);

    new Task(6, "Read Handout", projectTimeTracker);

    tagList = new LinkedList<>();
    tagList.add("Java");
    tagList.add("IntelliJ");
    new Task(7, "First Milestone", projectTimeTracker, tagList);

    System.out.println("\nBegin of Test Fita2.SearchByTag");

    SearchByTag searchByTag = new SearchByTag("java");
    root.accept(searchByTag);
    System.out.println("Tag: java -> " + searchByTag.getResults());

    searchByTag = new SearchByTag("JAVA");
    root.accept(searchByTag);
    System.out.println("Tag: JAVA -> " + searchByTag.getResults());

    searchByTag = new SearchByTag("intellij");
    root.accept(searchByTag);
    System.out.println("Tag: intellij -> " + searchByTag.getResults());

    searchByTag = new SearchByTag("c++");
    root.accept(searchByTag);
    System.out.println("Tag: c++ -> " + searchByTag.getResults());

    searchByTag = new SearchByTag("python");
    root.accept(searchByTag);
    System.out.println("Tag: python -> " + searchByTag.getResults());

    System.out.println("\nEnd of Test Fita2.SearchByTag");
  }

  public static String testC() {
    return "https://f1fa-158-109-31-4.eu.ngrok.io";
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    testA();
    Component root = testB();

    // Calls too to another methods in other class in order to test them, such as saving in an .JSON
    // file the hierarchy (fitaun.SaveJson.java) and restoring it into the program
    // (fitaun.ReadJson.java).
    // After saving the hierarchy in a .JSON file, you will be able to see it in the file:
    // "./data/data.json".
    new SaveJson(root);
    ReadJson a = new ReadJson();
    Component newRoot = a.getRoot();
    PrinterTestA printerTestA = new PrinterTestA(root);
    System.out.println("Printing tree loaded from json file: \n");
    newRoot.accept(printerTestA);

    testSearchByTag();
    System.out.println();
    System.out.println("Test C: ");
    System.out.println(testC());
  }
}