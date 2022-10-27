import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Task extends Component {
    private List<Interval> intervals = new LinkedList();

    public Task(String name, Project father) {
        super(name, father);
        father.addTask(this);
    }

    public void start(LocalDateTime now) {
        Interval interval = new Interval(now, this);
        ClockTimer.getInstance().addObserver(interval);
        if (this.getStartDate() == null) {
            this.setStartDate(now);
        }

        if (this.getFather().getStartDate() == null) {
            this.getFather().setStartDate(now);
        }

        this.intervals.add(interval);
    }

    public void stop() {
        this.setFinalDate(LocalDateTime.now());
        this.getFather().setFinalDate(LocalDateTime.now());
    }

    public void accept(Visitor v) {
        v.visitTask(this);
    }

    public List<Interval> getIntervals() {
        return this.intervals;
    }

    public String toString() {
        return this.getName() + "          child of " + this.getFather().getName() + "      " + this.getStartDate() + "       " + this.getFinalDate() + "     " + this.getElapsedTime().getSeconds();
    }
}
