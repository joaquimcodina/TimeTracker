package webserver;
import fitaun.Component;
import fitaun.Project;
import fitaun.Task;

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
    Project root = new Project(1, "root");
    Project softwareDesign = new Project(2, "Software Design", root);
    new Project(3, "Software Testing", root);
    new Project(4, "Databases", root);
    new Task(5, "Transportation", root);

    Project problems = new Project(6, "Problems", softwareDesign);
    Project projectTimeTracker = new Project(7, "Project Time Tracker", softwareDesign);

    new Task(8, "First List", problems);
    new Task(9, "Second List", problems);

    new Task(10, "Read Handout", projectTimeTracker);
    new Task(11, "First Milestone", projectTimeTracker);

    return root;
  }
}