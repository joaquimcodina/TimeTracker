package fitaun;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

public class Task extends Component {
  private List<Interval> intervals = new LinkedList<>();
  private boolean stopped;
  private static Logger logger = LoggerFactory.getLogger("time.tracker.fita1");

  public Task(int id, String name, Project father) {
    super(id, name, father);
    logger.trace("New Task Created");

    // we notify the father (that cannot be null) of this object's creation in order
    // to this object be in its descendents.
    if (father != null) {
      father.addComponent(this);
    }
    this.stopped = true;
  }

  public Task(int id, String name, Project father, List<String> tagList) {
    super(id, name, father);
    logger.trace("New Task created");
    assert tagList != null;
    this.tagList = tagList;

    // we notify the father (that cannot be null) of this object's creation in order
    // to this object be in its descendents.
    if (father != null) {
      father.addComponent(this);
    }
    this.stopped = true;
  }

  // This Constructor is used by the fitaun.ReadJson.java class in
  // order to rebuild the hierarchy. It will be not be
  // able to the Users. It basically initializes every single
  // attribute a fitaun.Task (and fitaun.Component) has.
  public Task(int id, String name, Project father, Duration elapsedTime,
              LocalDateTime startDate, LocalDateTime finalDate) {
    super(id, name, father, elapsedTime, startDate, finalDate);
    logger.trace("New Task Created");
    this.stopped = true;
    if (father != null) {
      father.addComponent(this);
    }
  }

  public void addInterval(Interval interval) {
    assert interval != null;
    final int size = this.intervals.size();
    this.intervals.add(interval);
    assert size == this.intervals.size() - 1;
  }

  // This method is set to be called from a User class by the User or in tests.
  public void start() {
    final int size = this.intervals.size();

    Interval interval = new Interval(new Random().nextInt(999999999) + 1, this);
    ClockTimer.getInstance().addObserver(interval);
    ClockTimer.getInstance().addInterval(interval);
    logger.trace("Task" + this.getName() + " Started a new Interval");

    // Updates the start date of the task,
    // if it is the first Range that is created (which means the oldest Date).
    this.setStartDate(interval.getStart());
    this.stopped = false;
    this.intervals.add(interval);
    assert this.intervals.size() == size + 1;
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
    logger.trace("Task " + this.getName() + " Stopped every Interval");
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
  // This method is used to update its own attributes
  // initialDate, finalDate and elapsedTime.
  public void updateDates() {
    this.elapsedTime = Duration.ZERO;
    logger.trace("Task " + this.getName() + " Updating its dates and elapsed time");
    logger.trace("Task " + this.getName() + " Updating recursively its father duration and dates.");

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
  
  @Override  
  public boolean isActive() {
    return !this.stopped;
  }

  public JSONObject toJson(int depth) {
    // depth not used here
    JSONObject json = new JSONObject();
    json.put("class", "task");
    super.toJson(json);
    json.put("active", !stopped);
    if (depth > 0) {
      JSONArray jsonIntervals = new JSONArray();
      for (Interval interval : intervals) {
        jsonIntervals.put(interval.toJson());
      }
      json.put("intervals", jsonIntervals);
    } else {
      json.put("intervals", new JSONArray());
    }
    return json;
  }

  public boolean isStopped() {
    return this.stopped;
  }
}
