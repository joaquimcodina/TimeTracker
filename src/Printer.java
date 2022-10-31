//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Printer implements Visitor {
  private Project project;

  public Printer(Project project) {
    this.project = project;
  }

  public void visitProject(Project project) {
    System.out.println("Project " + project);
    for (Component component : project.getComponentList()) {
      component.accept(this);
    }
    if (project.getName() == "root")
      System.out.println();
  }
  //this method prints the information of a project and its sub-components.

  public void visitTask(Task task) {
    System.out.println("Task " + task);
    if (task.getIntervals().size() != 0) {
      for (int pos = 0; pos < task.getIntervals().size(); pos++){
        System.out.println("   " + task.getIntervalPos(pos));
      }
    }
  }
  //this method prints the information of a tasj and its intervals.

  public void visitInterval(Interval interval) {
  }
}
