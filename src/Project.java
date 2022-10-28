import java.time.Duration;
import java.util.ArrayList;

public class Project extends Component {
    private ArrayList<Component> componentList = new ArrayList();

    Project(String name, Project father) {
        super(name, father);
        father.addProject(this);
    }

    Project(String name) {
        super(name);
    }

    protected void addProject(Project project) {
        this.componentList.add(project);
    }

    protected void addTask(Task task) {
        this.componentList.add(task);
    }

    @Override
    public void updateDates(){
        Duration duration = Duration.ZERO;
        for (Component component : componentList) {
            duration = duration.plus(component.getElapsedTime());
            notifyFather();
        }
        this.elapsedTime = duration;

    }

    public ArrayList<Component> getComponentList() { return this.componentList; }

    public void accept(Visitor v) { v.visitProject(this); }

    public String toString() {
        return this.getFather() != null ? this.getName() + "       child of " + this.getFather().getName() + "    " + this.getStartDate() + "       " + this.getFinalDate() + "      " + this.getElapsedTime().getSeconds() : this.getName() + "     child of null    " + this.getStartDate() + "       " + this.getFinalDate() + "      " + this.getElapsedTime().getSeconds();
    }
}
