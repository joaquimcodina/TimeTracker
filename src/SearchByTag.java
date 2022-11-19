import java.util.LinkedList;
import java.util.List;

public class SearchByTag implements Visitor {
  private String tag;
  private List<String> results = new LinkedList<>();

  public SearchByTag(String tag) {
    this.tag = tag;
  }

  public List<String> getResults() {
    return results;
  }

  public void visitProject(Project project) {
    if (project.getTagList().contains(this.tag)) {
      this.results.add(project.getName());
    }
    for (Component component : project.getComponentList()) {
      component.accept(this);
    }
    if (project.getName().equals("root")) {
      System.out.println();
    }
  }

  public void visitTask(Task task) {
    if (task.getTagList().contains(this.tag)) {
      this.results.add(task.getName());
    }
  }

  public void visitInterval(Interval interval) {
    System.out.print("");
  }
}
