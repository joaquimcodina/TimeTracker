import java.util.ArrayList;

public class Project extends Component {
  private ArrayList<Component> componentList = new ArrayList<>();

  Project(String name, Project father) {
    super(name, father);
    father.addProject(this);
  }

  Project(String name) {
    super(name);
  }

  protected void addProject(Project project) { this.componentList.add(project);
  }

  protected void addTask(Task task) { this.componentList.add(task);
  }

  public ArrayList<Component> getComponentList() { return componentList; }

  public void accept(Visitor v) { v.visitProject(this); }

  @Override
  public String toString() {
    if(getFather()!=null){
      return getName() + "       child of " + getFather().getName() + "    " + getStartDate() + "       " + getFinalDate() + "      " + getElapsedTime().getSeconds();
    }
    else{
      return getName() + "     child of null" + "    " + getStartDate() + "       " + getFinalDate() + "      " + getElapsedTime().getSeconds();
    }
  }
}