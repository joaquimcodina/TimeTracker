package fitaun;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

public abstract class Component {
  private int id;
  private String name;
  protected Duration elapsedTime;
  private LocalDateTime startDate;
  private LocalDateTime finalDate;
  private Project father;
  private static Logger logger = LoggerFactory.getLogger("time.tracker.fita1");

  private List<Component> componentList = new LinkedList<>();

  protected List<String> tagList = new LinkedList<>();

  public int getID() { return this.id; }

  public Component(int id, String name) {
    assert name != null;
    assert !name.equals("");

    this.id = id;
    this.name = name;
    this.father = null;
    this.elapsedTime = Duration.ZERO;
    this.startDate = null;
    this.finalDate = null;
  }

  public Component(int id, String name, Project father) {
    assert name != null;
    assert !name.equals("");

    this.id = id;
    this.name = name;
    this.father = father;
    this.elapsedTime = Duration.ZERO;
    this.startDate = null;
    this.finalDate = null;
  }

  public Component(int id, String name, Project father, Duration elapsedTime,
                   LocalDateTime startDate, LocalDateTime finalDate) {
    assert name != null;
    assert !name.equals("");
    assert elapsedTime != null;

    this.id = id;
    this.name = name;
    this.father = father;
    this.elapsedTime = elapsedTime;
    this.startDate = startDate;
    this.finalDate = finalDate;
  }

  public abstract List<Interval> getIntervals();

  public String getName() {
    return this.name;
  }

  public LocalDateTime getStartDate() {
    return this.startDate;
  }

  //initialDate
  public LocalDateTime getFinalDate() {
    return this.finalDate;
  }

  public Duration getElapsedTime() {
    return this.elapsedTime;
  }

  public Project getFather() {
    return this.father;
  }

  protected void setElapsedTime() {
    this.elapsedTime = Duration.ZERO;
  }

  public List<Component> getComponentList() {
    return this.componentList;
  }

  // This function increments its own elapsed time with the duration
  // of a task that has recently finished.
  protected void sumElapsedTime(Duration elapsedTime) {
    assert !elapsedTime.isNegative();
    if (elapsedTime.toSeconds() == Long.MAX_VALUE) {
      logger.warn("Duration is bigger than MAX_VALUE. It may cause an Overflow");
    } else {
      logger.trace("Sum Elapsed Time");
    }

    Duration assertTest = this.elapsedTime;

    this.elapsedTime = this.elapsedTime.plus(elapsedTime);

    assert assertTest.compareTo(this.elapsedTime) <= 0;
  }

  // This function updates its own startDate and expands recursively the inner changes to the
  // upper nodes in the hierarchy, if exists.
  public void setStartDate(LocalDateTime startDate) {
    assert startDate != null;
    logger.trace("Setting Start Date");

    if (this.startDate == null) {
      this.startDate = startDate;
    }
    if (this.father != null) {
      this.father.setStartDate(startDate);
    }
  }

  // This function updates its own finalDate and expands recursively the inner changes to the
  // upper nodes in the hierarchy, if exists.
  public void setFinalDate(LocalDateTime finalDate) {
    assert finalDate != null;
    logger.trace("Setting Final Date");

    if (this.finalDate == null || this.finalDate.compareTo(finalDate) < 0) {
      this.finalDate = finalDate;
    }
    if (this.father != null) {
      this.father.setFinalDate(finalDate);
    }
  }

  public LocalDateTime getActualDate() {
    return LocalDateTime.now();
  }

  public Duration getActualElapsedTime() {
    Duration duration = this.elapsedTime;
    assert duration != null;
    logger.trace("Updating Elapsed Time");

    if (this.getIntervals() == null) {
      return Duration.ofSeconds(Duration.between(this.startDate, LocalDateTime.now()).toSeconds());
    }

    if (getIntervals() != null) {
      for (Interval inter : getIntervals()) {
        if (inter.getActive()) {
          duration = duration.plus(inter.getActualElapsedTime());
        }
      }
    }

    return Duration.ofSeconds(duration.toSeconds());
  }

  public abstract void accept(Visitor v);

  public String getFatherName() {
    assert this.father != null;
    return this.father.getName();
  }

  protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public abstract JSONObject toJson(int depth); // added 16-dec-2022

  protected void toJson(JSONObject json) {
    json.put("id", id);
    json.put("name", name);
    JSONArray jsonTags = new JSONArray();
    for (String tag : tagList) {
      jsonTags.put(tag);
      // important: decrement depth
    }
    json.put("father", father);
    json.put("tags", jsonTags);
    json.put("initialDate", startDate == null ? JSONObject.NULL : formatter.format(startDate));
    json.put("finalDate", finalDate == null ? JSONObject.NULL : formatter.format(finalDate));
    json.put("duration", elapsedTime.toSeconds());
  }

  public String toString() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String stringProject;
    if (this.getFather() == null) {
      stringProject = this.getName()
        + "\t\t\t\tchild of null\t";
    } else {
      stringProject = this.getName()
        + "\t\t\t\tchild of \t\t"
        + this.getFather().getName() + "\t\t";
    }
    if (this.getStartDate() == null) {
      stringProject += "null\t\t";
    } else {
      stringProject += this.getStartDate().format(format) + "\t\t";
    }
    if (this.getFinalDate() == null) {
      stringProject += "null\t\t" + this.getElapsedTime().getSeconds();
    } else {
      stringProject
        += this.getFinalDate().format(format)
        + "\t\t"
        + this.getElapsedTime().getSeconds();
    }
    return stringProject;
  }

  public Component findActivityById(int id) {
    if (this.id == id) {
      return this;
    }
    for (Component component : this.componentList) {
      Component aux = component.findActivityById(id);
      if (aux != null) {
        return aux;
      }
    }
    return null;
  }

  public List<String> getTagList() {
    return tagList;
  }
  
  public abstract boolean isActive();

  public void addTag(String tag) {
    this.tagList.add(tag);
  }

  // This function sorts the components list of the component it is called from
  // added 06-jan-2023
  public void sortComponentsList(){
    componentList.sort(Comparator.comparing(Component::getName));
  }
}
