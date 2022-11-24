package fitaun;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

public class Task extends Component {
  private List<Interval> intervals = new LinkedList<>();
  private boolean stopped;

  public Task(String name, Project father) {
    super(name, father);

    // we notify the father (that cannot be null) of this object's creation in order
    // to this object be in its descendents.
    if (father != null) {
      father.addComponent(this);
    }
    this.stopped = false;
  }

  public Task(String name, Project father, List<String> tagList) {
    super(name, father);
    assert tagList != null;
    this.tagList = tagList;

    // we notify the father (that cannot be null) of this object's creation in order
    // to this object be in its descendents.
    if (father != null) {
      father.addComponent(this);
    }
    this.stopped = false;
  }

  // This Constructor is used by the fitaun.ReadJson.java class in
  // order to rebuild the hierarchy. It will be not be
  // able to the Users. It basically initializes every single
  // attribute a fitaun.Task (and fitaun.Component) has.
  public Task(String name, Project father, Duration elapsedTime,
              LocalDateTime startDate, LocalDateTime finalDate) {
    super(name, father, elapsedTime, startDate, finalDate);
    this.stopped = false;
    if (father != null) {
      father.addComponent(this);
    }
  }

  public void addInterval(Interval interval) {
    assert interval != null;
    this.intervals.add(interval);
  }

  // This method is set to be called from a User class by the User or in tests.
  public void start() {
    Interval interval = new Interval(this);
    ClockTimer.getInstance().addObserver(interval);
    ClockTimer.getInstance().addInterval(interval);

    // Updates the start date of the task,
    // if it is the first Range that is created (which means the oldest Date).
    this.setStartDate(interval.getStart());
    this.stopped = false;
    this.intervals.add(interval);
  }

  private void stopIntervals() {
    assert this.intervals != null;
    for (Interval interval : this.intervals) {
      interval.updateDates();
      interval.setNotActive();
      this.setFinalDate(getActualDate());
    }
  }

  // This method stops every fitaun.Interval of a fitaun.Task and
  // updates the attributes of its own, such as:
  // - initialDate
  // - finalDate
  // - Elapsed Time
  // After updating this attributes, it expands the changes to every node above him
  // in the hierarchy.
  public void stop() {
    stopIntervals();
    this.updateDates();
    this.stopped = true;
    this.setFinalDate(getActualDate());
  }

  public void accept(Visitor v) {
    assert v != null;
    v.visitTask(this);
  }

  @Override
  public List<Interval> getIntervals() {
    return this.intervals;
  }

  public Interval getIntervalPos(int pos) {
    assert pos >= 0 && pos <= this.intervals.size() - 1;
    return this.intervals.get(pos);
  }

  public List<String> getTagList() {
    return this.tagList;
  }


  // This method is used to update its own attributes
  // initialDate, finalDate and elapsedTime.
  public void updateDates() {
    this.elapsedTime = Duration.ZERO;

    for (Interval interval : this.intervals) {
      LocalDateTime finalDate = interval.getEnd();
      Duration duration = interval.getElapsedTime();

      if (finalDate != null) {
        this.setFinalDate(finalDate);
      }

      this.elapsedTime = this.elapsedTime.plus(duration);
    }
    if (this.getFather() != null) {
      this.getFather().updateElapsedTime();
    }
  }

  public boolean isStopped() {
    return this.stopped;
  }
}
