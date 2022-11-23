package fitaun;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

public abstract class Component {
  private String name;
  protected Duration elapsedTime;
  private LocalDateTime startDate;
  private LocalDateTime finalDate;
  private Project father;

  public Component(String name) {
    assert name != null;

    this.name = name;
    this.father = null;
    this.elapsedTime = Duration.ZERO;
    this.startDate = null;
    this.finalDate = null;
  }

  public Component(String name, Project father) {
    assert name != null;

    this.name = name;
    this.father = father;
    this.elapsedTime = Duration.ZERO;
    this.startDate = null;
    this.finalDate = null;
  }

  public Component(String name, Project father, Duration elapsedTime,
                   LocalDateTime startDate, LocalDateTime finalDate) {
    assert name != null;
    assert elapsedTime != null;

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

  protected void sumElapsedTime(Duration elapsedTime) {
    Duration assertTest = this.elapsedTime;

    this.elapsedTime = this.elapsedTime.plus(elapsedTime);

    assert assertTest.compareTo(this.elapsedTime) <= 0;
  }

  // This function updates its own startDate and expands recursively the inner changes to the
  // upper nodes in the hierarchy, if exists.
  public void setStartDate(LocalDateTime startDate) {
    assert startDate != null;

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
}