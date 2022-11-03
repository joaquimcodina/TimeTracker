import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Project extends Component {
  private ArrayList<Component> componentList = new ArrayList();

  Project(String name, Project father) {
    super(name, father);
    father.addComponent(this);
  }

  Project(String name) {
    super(name);
  }


  public void updateElapsedTime() {
    this.setElapsedTime(Duration.ZERO);

    for (Component component : this.componentList)
      this.sumElapsedTime(component.getElapsedTime());

    if (this.getFather() != null)
      this.getFather().updateElapsedTime();
  }
  //Updates the elapsed time of itself and its fathers recursively.

  protected void addComponent(Component comp) {
    this.componentList.add(comp);
  }

  @Override
  public ArrayList<Component> getComponentList() {
    return this.componentList;
  }

  @Override
  public List<Interval> getIntervals() {
    return null;
  }

  public void accept(Visitor v) {
    v.visitProject(this);
  }

  public String toString() {
    return this.getFather() != null ? this.getName() + "       child of " + this.getFather().getName() + "    " + this.getStartDate() + "       " + this.getFinalDate() + "      " + this.getElapsedTime().getSeconds()
            : this.getName() + "     child of null    " + this.getStartDate() + "       " + this.getFinalDate() + "      " + this.getElapsedTime().getSeconds();
  }
  //This method is used to print the information of a Project.
}
