import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*
    This class is a subclass of a Component (Component.java) class, and, therefore, it implements
    what the superclass forces to implement.
    As a Project can have 0 or more Components, we have an ArrayList of Components.

    @version 4.0
    @since 2022-11-06
 */
public class Project extends Component {
  private ArrayList<Component> componentList = new ArrayList<>();

  /*
  This Constructor calls to the superclass constructor (super statement),
  and finally we notify the father (that cannot be null) of
  this object's creation in order to this object be in its descendents.

  @param name : Must be a String. This will be the name of the task.
  @param father : Must be a Project. This param must not be null.

  @return Project
*/
  Project(String name, Project father) {
    super(name, father);
    father.addComponent(this);
  }

  /*
This Constructor calls to the superclass constructor (super statement), and, finally, forces the
father to be null, which means that this node is the root (or one of the roots) of the hierarchy.

@param name : Must be a String. This will be the name of the task.

@return Project
*/
  Project(String name) {
    super(name);
  }

  /*
This Constructor is used by the ReadJson.java class in order to rebuild the hierarchy.
It will be not be able to the Users.
It basically initializes every single attribute a Project (and Component) has.

@return Project
*/
  public Project(String name, Project father, Duration elapsedTime,
                 LocalDateTime startDate, LocalDateTime finalDate) {
    super(name, father, elapsedTime, startDate, finalDate);
    if (father != null) {
      father.addComponent(this);
    }
  }

  /*
  This method updates the elapsed time of this project and, if it has father, recursively through
  the method "setElapsedTime()", it updates the elapsed time of every node above it connected to it.
*/
  public void updateElapsedTime() {
    this.setElapsedTime(Duration.ZERO);

    for (Component component : this.componentList) {
      this.sumElapsedTime(component.getElapsedTime());
    }

    if (this.getFather() != null) {
      this.getFather().updateElapsedTime();
    }
  }

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

  /*
  This method is used to print the information of a Project into console.
*/
  public String toString() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String stringToReturn;
    if (this.getFather() == null) {
      stringToReturn = this.getName()
          + "\t\t\t\t\t\tchild of null\t\t\t";
    } else {
      stringToReturn = this.getName()
          + "\t\t\t\t\t\tchild of \t\t\t"
          + this.getFather().getName() + "\t\t\t";
    }
    if (this.getStartDate() == null) {
      stringToReturn += "null\t\t\t";
    } else {
      stringToReturn += this.getStartDate().format(format) + "\t\t\t";
    }
    if (this.getFinalDate() == null) {
      stringToReturn += "null\t\t\t" + this.getElapsedTime().getSeconds();
    } else {
      stringToReturn
          += this.getFinalDate().format(format)
          + "\t\t\t"
          + this.getElapsedTime().getSeconds();
    }
    return stringToReturn;
  }
}
