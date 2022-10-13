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

  @Override
  public void updateDates() {
    if (!Intervals.isEmpty()) {
      this.initialDate = Intervals.get(0).getInitialDate();
      Date aux = null;
      for (int i = 0; i < Intervals.size(); i++) {
        Date actual = Intervals.get(i).getFinalDate();
        if (actual != null) {
          if (aux == null || actual.compareTo(aux) > 0) //si trobar una finalDate més vella que les altres ja trobades
            aux = actual;
        }
      }
      this.finalDate = aux;
    }
  }
  //This method updates the initalDate and finalDate attributes iterating every son it has and looking for the initial and final Dates of the intervals.

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
