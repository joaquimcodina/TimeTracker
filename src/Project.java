import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Project extends Component {
  private ArrayList<Component> componentList = new ArrayList<>();

  Project(String name, Project father) {
    super(name, father);
    father.addComponent(this);
  }

  Project(String name) {
    super(name);
  }

  public Project(String name, Project father, Duration elapsedTime, LocalDateTime startDate, LocalDateTime finalDate){
    super(name, father, elapsedTime, startDate, finalDate);
    if (father != null)
      father.addComponent(this);
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
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String stringToReturn = "";
    if (this.getFather() == null)
      stringToReturn = this.getName() + "\t\t\t\t\t\tchild of null\t\t\t";
    else
      stringToReturn = this.getName() + "\t\t\t\t\t\tchild of \t\t\t" + this.getFather().getName() + "\t\t\t";
    if (this.getStartDate() == null)
      stringToReturn += "null\t\t\t";
    else
      stringToReturn += this.getStartDate().format(format) + "\t\t\t";
    if (this.getFinalDate() == null)
      stringToReturn += "null\t\t\t" + this.getElapsedTime().getSeconds();
    else
      stringToReturn += this.getFinalDate().format(format) + "\t\t\t" + this.getElapsedTime().getSeconds();
    return stringToReturn;
  }
  //This method is used to print the information of a Project.
}
