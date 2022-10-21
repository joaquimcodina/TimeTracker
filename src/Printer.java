public class Printer implements Visitor{
  private Project project;
  public Printer(Project project){
    this.project = project;
  }

  @Override
  public void visitProject(Project project) {
    System.out.println("Project " + project);
    for(Component component : project.getComponentList())
      component.accept(this);
  }

  @Override
  public void visitTask(Task task){
    System.out.println("Task " + task);
    if(task.getInterval()!=null)
      System.out.println(task.getInterval());
  }
}
