import java.time.Duration;
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
        LocalDateTime now = LocalDateTime.now();
        this.setFinalDate(now);
        this.updateDates();
        //this.getFather().setFinalDate(now);
    }

    public void accept(Visitor v) {
        v.visitTask(this);
    }

    public List<Interval> getIntervals() {
        return this.intervals;
    }

    public Interval getIntervalPos(int pos) {return this.intervals.get(pos); }

    @Override
    public void updateDates() {
        this.elapsedTime = Duration.ZERO;
        Duration time = Duration.ZERO;
        for (Interval interval : intervals) {
            LocalDateTime initialDate = interval.getStart();
            LocalDateTime finalDate = interval.getEnd();
            Duration duration = interval.getElapsedTime();

            if (initialDate != null) {
                if (interval.getStart() == null || initialDate.compareTo(interval.getStart()) < 0)
                    this.setStartDate(initialDate);
            }
            if (finalDate != null) {
                if (interval.getEnd() == null || finalDate.compareTo(interval.getEnd()) > 0)
                    this.setFinalDate(finalDate);
            }
            this.elapsedTime = this.elapsedTime.plus(duration);
        }
        notifyFather();
    }
    //This method updates the initalDate, finalDate and durationTime attributes iterating every son it has and looking for the initial and final Dates of the intervals.
    //It also notifies its father about this change, if exists.

    public String toString() {
        return this.getName() + "          child of " + this.getFather().getName() + "      " + this.getStartDate() + "       " + this.getFinalDate() + "     " + this.getElapsedTime().getSeconds();
    }
}
