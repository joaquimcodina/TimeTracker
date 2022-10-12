import java.time.Instant;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
  private Date dateTimeInit;
  private Date dateTimeEnd;
  private int duration;

  public Interval() {
    dateTimeInit = new Date(); //This sets the dateTimeInit value to the current Date of System.
    dateTimeEnd = null;
    duration = 0;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getDuration() {
    return this.duration;
  }

  public Date getInitialDate() {
    return dateTimeInit;
  }

  public Date getFinalDate() {
    return dateTimeEnd;
  }

  @Override
  public void update(Observable clockTimer, Object arg) {
    //CHANGE TO ANOTHER OBSERVABLE - OBSERVER CLASSES
  }
}
