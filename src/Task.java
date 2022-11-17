import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
    This class is a subclass of a Component (Component.java) class, and, therefore, it implements
    what the superclass forces to implement.
    As a Task can have 0 or more Intervals (Interval.java), we have a List of Intervals, which is
    initially empty.
    This class also haves a boolean attribute that indicates if the task has any active Interval in it.

    @version 5.0
    @since 2022-11-06
 */

public class Task extends Component {
    private List<Interval> intervals = new LinkedList();
    private boolean stopped;

    /*
        This Constructor calls to the superclass constructor (super statement), forces the stopped flag to false
        and, finally, we notify the father (that cannot be null) of this object's creation in order to this object
        be in its decendents.

        @param name : Must be a String. This will be the name of the task.
        @param father : Must be a Project. This param must not be null.

        @return Task
     */

    public Task(String name, Project father) {
        super(name, father);
        father.addComponent(this);
        this.stopped = false;
    }

    /*
    This Constructor calls to the superclass constructor (super statement), forces the stopped flag to false
    and, finally, forces the father to be null, which means that this node is the root (or one of the roots)
    of the hierarchy.

    @param name : Must be a String. This will be the name of the task.

    @return Task
 */
    public Task(String name) {
        super(name);
        this.stopped = false;
    }

    /*
    This Constructor is used by the ReadJSON.java class in order to rebuild the hierarchy. It will be not be
    able to the Users. It basically initializes every single attribute a Task (and Component) has.

    @return Task
 */
    public Task(String name, Project father, Duration elapsedTime, LocalDateTime startDate, LocalDateTime finalDate){
        super(name, father, elapsedTime, startDate, finalDate);
        this.stopped = false;
        if (father != null)
            father.addComponent(this);
    }

    public void addInterval(Interval interval) {
        this.intervals.add(interval);
    }

    @Override
    public ArrayList<Component> getComponentList() {
        return null;
    }

    /*
        This method is set to be called from a User class by the User or in tests.
        This method creates a new Interval and updates itself initialDate, if it is
        the first Interval to be created (which means the oldest Date).
 */
    public void start() {
        Interval interval = new Interval(this);
        ClockTimer.getInstance().addObserver(interval);
        ClockTimer.getInstance().addInterval(interval);

        this.setStartDate(interval.getStart());
        this.stopped = false;
        this.intervals.add(interval);
    }

    private void stopIntervals() {
        for (Interval interval : this.intervals) {
                interval.updateDates();
                interval.setNotActive();
                this.setFinalDate(getActualDate());
        }
    }

    /*
        This method stops every Interval of a Task and updates the attributes of its own, such as:
            · initialDate
            · finalDate
            · Elapsed Time
        After updating this attributes, it expands the changes to every node above him in the hierarchy.
*/
    public void stop() {
        stopIntervals();
        this.updateDates();
        this.stopped = true;
        this.setFinalDate(getActualDate());
    }

    public void accept(Visitor v) {
        v.visitTask(this);
    }

    @Override
    public List<Interval> getIntervals() {
        return this.intervals;
    }

    public Interval getIntervalPos(int pos) {
        return this.intervals.get(pos);
    }

    /*
        This method is used to update its own attributes initialDate, finalDate and elapsedTime.
        It calls the following interesting functions:
            · setFinalDate(): This method recursively updates the finalDate of every node above connected to this task.
            · updateElapsedTime(): This method recursively updates the ElapsedTime of every node above connected to this task.
*/
    public void updateDates() {
        this.elapsedTime = Duration.ZERO;

        for (Interval interval : this.intervals) {
            LocalDateTime finalDate = interval.getEnd();
            Duration duration = interval.getElapsedTime();

            if (finalDate != null)
                this.setFinalDate(finalDate);

            this.elapsedTime = this.elapsedTime.plus(duration);
        }
        if (this.getFather() != null)
            this.getFather().updateElapsedTime();
    }

    public boolean isStopped() {
        return this.stopped;
    }

    /*
        This method is used to print the information of a Task into console.
*/
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
}
