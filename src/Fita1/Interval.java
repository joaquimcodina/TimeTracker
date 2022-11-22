package Fita1;

import Fita1.ClockTimer;
import Fita1.Visitor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

// This class Implements the Observer Design Pattern and, it is connected to the Fita1.ClockTimer.
public class Interval implements Observer {
  private LocalDateTime start;
  private LocalDateTime end;
  private Task father;
  private Duration elapsedTime = Duration.ZERO;
  private boolean active = true;

  public Interval(Task father) {
    this.start = LocalDateTime.now();
    this.father = father;
  }

  public Interval(LocalDateTime start, LocalDateTime end, Task father, Duration elapsedTime) {
    this.start = start;
    this.end = end;
    this.father = father;
    this.elapsedTime = elapsedTime;
    father.addInterval(this);
  }


  // This method updates the elapsedTime every time it receives an update of Observable.
  public void update(Observable o, Object arg) {
    this.elapsedTime = Duration.ofSeconds(
        Duration.between(this.start, ClockTimer.getInstance().getNow()).toSeconds());
  }

  public boolean getActive() {
    return active;
  }

  public void setNotActive() {
    this.active = false;
  }

  // This method expands the inner changes into the father Fita1.Task, and then,
  // if exists, recursively into the upper nodes.
  public void updateDates() {
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
    v.visitInterval(this);
  }

  @Override
  public String toString() {
    return "Fita1.Interval \t\t\t\t\t child of " + getFather().getName() + "\t\t\t"
        + getStart() + "\t\t\t" + getEnd() + "\t\t\t" + getElapsedTime().getSeconds();
  }
}