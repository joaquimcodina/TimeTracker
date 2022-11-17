//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

/*
    This class implements the Visitor Design Pattern.
    It is used to print the hierarchy of the Time Tracker.

    @version 1.0
    @since 2022-11-06
 */
public class PrinterTestA implements Visitor {


  public void visitProject(Project project) {
    System.out.println("Project " + project);
    for (Component component : project.getComponentList()) {
      component.accept(this);
    }
    if (project.getName().equals("root")) {
      System.out.println();
    }
  }

  public void visitTask(Task task) {
    System.out.println("Task " + task);
    if (task.getIntervals().size() != 0) {
      for (int pos = 0; pos < task.getIntervals().size(); pos++) {
        System.out.println("   " + task.getIntervalPos(pos));
      }
    }
  }

  public void visitInterval(Interval interval) {
    System.out.print("");
  }
}
