package fitados;

import fitaun.*;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchByTag implements Visitor {
  private String tag;
  private List<String> results = new LinkedList<>();
  private List<Integer> id_Results = new LinkedList<>();
  private static Logger logger = LoggerFactory.getLogger("time.tracker.fita2");

  public SearchByTag(String tag) {
    this.tag = tag;
  }

  public List<String> getResults() {
    return results;
  }

  public List<Integer> getResultsID() {
    return this.id_Results;
  }

  public void visitProject(Project project) {
    logger.trace("Visiting Project " + project.getName());
    for (int i = 0; i < project.getTagList().size(); i++) {
      if (project.getTagList().get(i).equalsIgnoreCase(tag)) {
        this.results.add(project.getName());
        this.id_Results.add(project.getID());
      }
    }

    for (Component component : project.getComponentList()) {
      component.accept(this);
    }
  }

  public void visitTask(Task task) {
    logger.trace("Visiting Task " + task.getName());
    for (int i = 0; i < task.getTagList().size(); i++) {
      if (task.getTagList().get(i).equalsIgnoreCase(tag)) {
        this.results.add(task.getName());
        this.id_Results.add(task.getID());
      }
    }
  }

  public void visitInterval(Interval interval) {
    assert interval != null;
    logger.debug(null);
  }

}
