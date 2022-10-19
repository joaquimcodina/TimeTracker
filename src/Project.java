import java.util.ArrayList;

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
    int duration = 0;
    for (int i = 0; i < Components.size(); i++) {
      Components.get(i).updateDates();
      duration += Components.get(i).getWastedTime();
    }
    notifyFather();
    this.durationTime = duration;
  }
  //This function updates the initialDate and finalDate of a project, including the same attributes from each of its sub-Project and sub-Tasks.
  // It also updates its durationTime attribute and notifies its father about this change, if exists.

  public ArrayList<Component> getSubComponents() { return this.Components; }

  public void addComponent(Component obj) { Components.add(obj); } //check if it does not already exists
}
