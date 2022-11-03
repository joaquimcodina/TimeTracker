import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Task extends Component {
  private List<Interval> intervals = new LinkedList<>();

  public Task(String name, Project father) {
    super(name, father);
    father.addComponent(this);
  }

  @Override
  public List<Component> getComponentList() {
    return null;
  }

  public void start() {
    Interval interval = new Interval(this);
    ClockTimer.getInstance().addObserver(interval);

    this.setStartDate(interval.getStart());

    this.intervals.add(interval);
  }
  //This method Creates a new Interval and stores it into the this.intervals attribute, and,
  // also, it updates the startDate of every father it has through the setStartDate method, if needed.

  private void stopIntervals() {
    for (Interval interval : this.intervals) {
      if (interval.getEnd() == null)
        interval.update();
    }
  }

  public void stop() {
    stopIntervals();
    this.updateDates();
  }
  //This method stops every interval of a task and updates itself's finalDate and elapsedTime.

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

  public void updateDates() {
    this.elapsedTime = Duration.ZERO;

    for (Interval interval : this.intervals) {
      LocalDateTime finalDate = interval.getEnd();
      Duration duration = interval.getElapsedTime();

      if (finalDate != null)
          this.setFinalDate(finalDate);

      this.elapsedTime = this.elapsedTime.plus(duration);
    }
    this.getFather().updateElapsedTime();
  }
  //This method updates the initalDate, finalDate and durationTime attributes iterating every son it has and looking for the initial and final Dates of the intervals.
  //It also notifies its father about this change, if exists.
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
  //This method is used to print the information of a Task.
}
