package webserver;
import fitaun.Component;
import fitaun.Project;
import fitaun.Task;

import java.util.LinkedList;
import java.util.List;

public class MainWebServer {
  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    final Component root = makeTree();
    // implement this method that returns the tree of
    // appendix A in the practicum handout
    // start your clock

    new WebServer(root);
  }

  public static Component makeTree() {

    Project root = new Project(0, "root");

    List<String> tagList = new LinkedList<>();
    tagList.add("java");
    tagList.add("flutter");
    Project softwareDesign = new Project(1, "Software Design", root, tagList);

    tagList = new LinkedList<>();
    tagList.add("c++");
    tagList.add("Java");
    tagList.add("python");
    new Project(2, "Software Testing", root, tagList);

    tagList = new LinkedList<>();
    tagList.add("SQL");
    tagList.add("python");
    tagList.add("C++");
    new Project(3, "Databases", root, tagList);

    new Task(4, "Transportation", root);

    Project problems = new Project(5, "Problems", softwareDesign);

    Project projectTimeTracker = new Project(6, "Project Time Tracker", softwareDesign);

    tagList = new LinkedList<>();
    tagList.add("java");
    new Task(7, "First List", problems, tagList);

    tagList = new LinkedList<>();
    tagList.add("Dart");
    new Task(8, "Second List", problems, tagList);

    new Task(9, "Read Handout", projectTimeTracker);

    tagList = new LinkedList<>();
    tagList.add("Java");
    tagList.add("IntelliJ");
    new Task(10, "First Milestone", projectTimeTracker, tagList);

    return root;
  }
}