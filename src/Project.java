import java.util.ArrayList;
import java.util.Date;

public class Project extends Component {
  private ArrayList<Component> Components;
  //Components: This ArrayList will contain the sub-Tasks and Sub-Projects of a Project object.

  public Project(String name, Project father) {
    super(name, father);
    Components = new ArrayList<Component>();
    try {
      father.addComponent(this);
    }catch(NullPointerException e) { }
  }
  //Constructor: This initiates the Project with a name and sets the Components attribute to NOT NULL.
  //NOTE: father may be a Component, but as it is always going to be a Project (Only a Project can have sub-Tasks&sub-Project) we forced the parameter to be a Project.
  // the try-catch statement is that in case of having a null father (Project or Task Root) control the Exception.

  @Override
  public void updateDates() {
    if (!Components.isEmpty()) {
      for (int i = 0; i < Components.size(); i++)
        Components.get(i).updateDates();
      Date initialDate = null;
      Date finalDate = null;
      for (int i = 0; i < Components.size(); i++) {
        if (initialDate == null) {
          if (Components.get(i).getInitialDate() != null)
            initialDate = Components.get(i).getInitialDate();
        }
        else if (initialDate.compareTo(Components.get(i).getInitialDate()) > 0)
          initialDate = Components.get(i).getInitialDate();

        if (finalDate == null) {
          if (Components.get(i).getFinalDate() != null)
            finalDate = Components.get(i).getFinalDate();
        }
        else if (finalDate.compareTo(Components.get(i).getFinalDate()) < 0)
          finalDate = Components.get(i).getFinalDate();
      }
      this.finalDate = finalDate;
      this.initialDate = initialDate;
    }
  }
  //This function updates the initialDate and finalDate of a project, including the same attributes from each of its sub-Project and sub-Tasks.

  public ArrayList<Component> getSubComponents() { return this.Components; }

  public void addComponent(Component obj) { Components.add(obj); } //check if it does not already exists

  public void removeComponent(Component obj) { Components.remove(obj); } //check if it does not already exists
}
