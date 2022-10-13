import java.util.Date;

public abstract  class Component {
  protected String name;
  protected int durationTime;
  protected Date initialDate;
  protected Date finalDate;
  protected Component father;

  public String getName() { return this.name; }
  public int getWastedTime() { return this.durationTime; }
  public Date getInitialDate() {
    updateDates();
    return this.initialDate;
  }
  public Date getFinalDate() {
    updateDates();
    return this.finalDate;
  }

  public abstract void updateDates();

  public Component(String name, Component father) {
    this.name = name;
    initialDate = null;
    finalDate = null;
    durationTime = 0;
    this.father = father;
  }

  public Component getFather() {
    return this.father;
  }
}
