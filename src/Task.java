import java.util.ArrayList;
import java.util.Date;

public class Task extends Component {
  private ArrayList<Interval> Intervals;

  public Task(String name, Project father) {
    super(name, father);
    Intervals = new ArrayList<Interval>();
    try {
      father.addComponent(this);
    } catch(NullPointerException e) { }
  }
  //NOTE: father may be a Component, but as it is always going to be a Project (Only a Project can have sub-Tasks&sub-Project) we forced the parameter to be a Project.
  // the try-catch statement is that in case of having a null father (Project or Task Root) control the Exception.

  @Override
  public void updateDates() {
    this.durationTime = 0;
    for (int i = 0; i < Intervals.size(); i++) {
      Date initialDate = Intervals.get(i).getInitialDate();
      Date finalDate = Intervals.get(i).getFinalDate();
      int duration = Intervals.get(i).getDuration();

      if (initialDate != null) {
        if (this.initialDate == null || initialDate.compareTo(this.initialDate) < 0)
          this.initialDate = initialDate;
      }
      if (finalDate != null) {
        if (this.finalDate == null || finalDate.compareTo(this.finalDate) > 0)
          this.finalDate = finalDate;
      }
      this.durationTime += Intervals.get(i).getDuration();
    }
    notifyFather();
  }
  //This method updates the initalDate, finalDate and durationTime attributes iterating every son it has and looking for the initial and final Dates of the intervals.
  //It also notifies its father about this change, if exists.

  public ArrayList<Interval> getIntervals() { return Intervals; }

  public void initTask() {
    Interval newInterval = new Interval();
    this.Intervals.add(newInterval);
    //TODO: To update clockTimer and start couting time.
  }
  //This function creates a new Interval, which has a initialDate set to the actual System's Date.

  public void endTask() {
    //TODO
  }
}
