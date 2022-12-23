package fitaun;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

// This class Implements the Observer Design Pattern and, it is connected to the fitaun.ClockTimer.
public class Interval implements Observer {
  private int id;
  private LocalDateTime start;
  private LocalDateTime end;
  private Task father;
  private Duration elapsedTime = Duration.ZERO;
  private boolean active = true;
  private static Logger logger = LoggerFactory.getLogger("time.tracker.fita1");

  public Interval(int id, Task father) {
    assert father != null;
    logger.info("New Interval Created");
    this.id = id;
    this.start = LocalDateTime.now();
    this.father = father;
  }

  public Interval(int id, LocalDateTime start, LocalDateTime end, Task father, Duration elapsedTime) {
    assert father != null;
    this.id = id;
    this.start = start;
    this.end = end;
    this.father = father;
    this.elapsedTime = elapsedTime;
    father.addInterval(this);
  }


  // This method updates the elapsedTime every time it receives an update of Observable.
  public void update(Observable o, Object arg) {
    assert this.start != null;
    assert this.end == null;

    if (this.start != null && this.end == null) {
      logger.trace("Updating Elapsed Time of an Interval");
      this.elapsedTime = Duration.ofSeconds(Duration.between(this.start, ClockTimer.getInstance().getNow()).toSeconds());
    }

    assert this.elapsedTime != null;
    assert this.elapsedTime.getSeconds() >= Duration.ZERO.getSeconds();
  }

  public boolean getActive() {
    return this.active;
  }

  public void setNotActive() {
    this.active = false;
    if (this.end == null) {
      this.end = LocalDateTime.now();
    }
  }

  // This method expands the inner changes into the father fitaun.Task, and then,
  // if exists, recursively into the upper nodes.
  public void updateDates() {
    logger.trace("Interval updating recursively its fathers");
    this.father.updateDates();
  }

  public LocalDateTime getStart() {
    return this.start;
  }

  public LocalDateTime getEnd() {
    return this.end;
  }

  public Task getFather() {
    return father;
  }

  public Duration getElapsedTime() {
    return this.elapsedTime;
  }

  public LocalDateTime getActualDate() {
    return LocalDateTime.now();
  }

  public Duration getActualElapsedTime() {
    return Duration.ofSeconds(Duration.between(this.start, LocalDateTime.now()).toSeconds());
  }

  public void accept(Visitor v) {
    assert v != null;
    v.visitInterval(this);
  }

  @Override
  public String toString() {
    return "fitaun.Interval \t\t\t\t\t child of " + getFather().getName() + "\t\t\t"
      + getStart() + "\t\t\t" + getEnd() + "\t\t\t" + getElapsedTime().getSeconds();
  }

  protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("class", "interval");
    json.put("id", id);
    json.put("initialDate", start == null ? JSONObject.NULL : formatter.format(start));
    json.put("finalDate", end == null ? JSONObject.NULL : formatter.format(end));
    json.put("duration", elapsedTime.toSeconds());
    json.put("active", active);
    return json;
  }
}