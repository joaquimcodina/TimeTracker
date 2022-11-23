package fitaun;

import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

// This class implements the fitaun.Visitor Design Pattern and the Observer Design Pattern.
// It is used to print the hierarchy of the Time Tracker and its changes.
public class PrinterTestB implements Visitor, Observer {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  static Logger logger = LoggerFactory.getLogger("time.tracker.fita1");

  public PrinterTestB() {

  }

  @Override
  public void visitProject(Project project) {
    assert project != null;
    logger.info("activity: \t\t" + project.getName() + "\t\t\t\t"
        + project.getStartDate().format(formatter)
        + "\t\t\t" + project.getActualDate().format(formatter) + "\t\t\t"
        + project.getActualElapsedTime().toSeconds());

    if (project.getFather() != null) {
      project.getFather().accept(this);
    } else {
      logger.debug(null);
    }
  }

  @Override
  public void visitTask(Task task) {
    assert task != null;
    logger.info("activity: \t\t" + task.getName()
        + "\t\t\t" + task.getStartDate().format(formatter)
        + "\t\t\t" + task.getActualDate().format(formatter)
        + "\t\t\t" + task.getActualElapsedTime().toSeconds());

    if (task.getFather() != null) {
      task.getFather().accept(this);
    }
  }

  @Override
  public void visitInterval(Interval interval) {
    assert interval != null;
    if (interval.getFather().isStopped()) {
      logger.debug(null);
    }

    logger.info("interval:  \t\t\t\t\t\t\t" + interval.getStart().format(formatter)
        + "\t\t\t" + interval.getActualDate().format(formatter) + "\t\t\t\t"
        + interval.getActualElapsedTime().toSeconds());

    interval.getFather().accept(this);
  }

  @Override
  public void update(Observable o, Object arg) { // print latest active interval
    Interval interval = ClockTimer.getInstance().getObservers()
        .get(ClockTimer.getInstance().getObservers().size() - 1);
    interval.accept(this);
  }

}
