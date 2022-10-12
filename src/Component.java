import java.util.Date;

public abstract  class Component {
  protected String name;
  protected int durationTime;
  protected Date initialDate;
  protected Date finalDate;

  public String getName() { return this.name; }
  public int getWastedTime() { return durationTime; }
  public Date getInitialDate() { return initialDate; }
  public Date getFinalDate() { return finalDate; }

  public abstract void updateDates();

  public Component(String name) {
    this.name = name;
    initialDate = null;
    finalDate = null;
    durationTime = 0;
  }
}
