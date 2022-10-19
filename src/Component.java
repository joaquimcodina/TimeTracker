import java.util.Date;

public abstract  class Component {
  protected String name;
  protected int durationTime;
  protected Date initialDate;
  protected Date finalDate;

  protected Component father;

  public String getName() {
    return this.name;
  }
  public int getWastedTime() {
    return this.durationTime;
  }
  public Date getInitialDate() {
    return this.initialDate;
  }
  public Date getFinalDate() {
    return this.finalDate;
  }

  public Component getFather() {
    return this.father;
  }

  protected void updateInitialDate(Date dt) {
    this.initialDate = dt;
  }
  protected void updateFinalDate(Date dt) {
    this.finalDate = dt;
  }

  public abstract void updateDates();

  public Component(String name, Component father) {
    this.name = name;
    initialDate = null;
    finalDate = null;
    durationTime = 0;
    this.father = father;
  }

  protected void notifyFather() {
    if (this.father != null) {
      if (this.initialDate != null) {
        if (this.father.getInitialDate() == null || this.initialDate.compareTo(this.father.getInitialDate()) < 0)
          this.father.updateInitialDate(this.initialDate);
      }
      if (this.finalDate != null) {
        if (this.father.getFinalDate() == null || this.finalDate.compareTo(this.father.getFinalDate()) > 0)
          this.father.updateFinalDate(this.finalDate);
      }
    }
  }
  /*This function updates its father's:
  *   - initialDate: if father's initialDate is newer than object's one.
  *   - finalDate: if father's finalDate is older that object's one.
  * Duration attribute cannot be updated with this function. It is updated manually in <<updateDates>>
  * */
}
