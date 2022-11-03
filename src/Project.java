import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Project extends Component {
  private List<Component> componentList = new LinkedList<>();

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
  public List<Component> getComponentList() {
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
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String stringToReturn = "";
    if (this.getFather() == null)
      stringToReturn = this.getName() + "       child of null       ";
    else
      stringToReturn = this.getName() + "       child of " + this.getFather().getName() + "       ";
    if (this.getStartDate() == null)
      stringToReturn += "null     ";
    else
      stringToReturn += this.getStartDate().format(format) + "    ";
    if (this.getFinalDate() == null)
      stringToReturn += "null       " + this.getElapsedTime().getSeconds();
    else
      stringToReturn += this.getFinalDate().format(format) + "       " + this.getElapsedTime().getSeconds();
    return stringToReturn;
  }
  //This method is used to print the information of a Project.
}
