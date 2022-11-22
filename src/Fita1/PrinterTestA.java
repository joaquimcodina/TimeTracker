package Fita1;

import Fita1.Component;
import Fita1.Interval;
import Fita1.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

// This class implements the Fita1.Visitor Design Pattern.
// It is used to print the hierarchy of the Time Tracker.
public class PrinterTestA implements Visitor {
  static Logger logger = LoggerFactory.getLogger("time.tracker.fita1");

  public void visitProject(Project project) {
    logger.info("Fita1.Project " + project);
    for (Component component : project.getComponentList()) {
      component.accept(this);
    }
    if (project.getName().equals("root")) {
      logger.debug(null);
    }
  }

  public void visitTask(Task task) {
    logger.info("Fita1.Task " + task);
    if (task.getIntervals().size() != 0) {
      for (int pos = 0; pos < task.getIntervals().size(); pos++) {
        logger.info("   " + task.getIntervalPos(pos));
      }
    }
  }

  public void visitInterval(Interval interval) {
    logger.debug(null);
  }
}
