package fitaun;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

// This class is a subclass of a fitaun.Component (fitaun.Component.java) class, and, therefore, it implements
// what the superclass forces to implement.
public class Project extends Component {
  private List<Component> componentList = new LinkedList<>();
  private static Logger logger = LoggerFactory.getLogger("time.tracker.fita1");

  public Project(String name, Project father) {
    super(name, father);
    logger.trace("New Project Created");

    //we notify the father (that cannot be null) of this object's creation in order
    //to this object be in its descendents.
    if (father != null) {
      father.addComponent(this);
    }
  }

  public Project(String name, Project father, List<String> tagList) {
    super(name, father);
    assert tagList != null;

    this.tagList = tagList;
    logger.trace("New Project " + this.getName() + " Created");

    //we notify the father (that cannot be null) of this object's creation in order
    //to this object be in its descendents.
    if (father != null) {
      father.addComponent(this);
    }
  }

  // This Constructor calls to the superclass constructor (super statement),
  // and, finally, forces the
  // father to be null, which means that this node is the root (or one of the roots)
  // of the hierarchy.
  public Project(String name) {
    super(name);
    logger.trace("New Project " + this.getName() + " Created");
  }

  public Project(String name, Project father, Duration elapsedTime,
                 LocalDateTime startDate, LocalDateTime finalDate) {
    super(name, father, elapsedTime, startDate, finalDate);
    logger.trace("New Project " + this.getName() + " Created");
    if (father != null) {
      father.addComponent(this);
    }
  }

  public void updateElapsedTime() {
    final Duration elapsed = this.elapsedTime;
    this.setElapsedTime();

    for (Component component : this.componentList) {
      this.sumElapsedTime(component.getElapsedTime());
    }
    logger.trace("Project " + this.getName() + " updating its and fathers elapsed time");

    if (this.getFather() != null) {
      this.getFather().updateElapsedTime();
    }
    assert elapsed.compareTo(this.elapsedTime) <= 0;
  }

  protected void addComponent(Component comp) {
    assert comp != null;
    final int size = this.componentList.size();
    logger.trace("Project " + this.getName() + " adding a component to its ComponentList");
    this.componentList.add(comp);
    assert size == this.componentList.size() - 1;
  }

  public List<Component> getComponentList() {
    return this.componentList;
  }

  @Override
  public List<Interval> getIntervals() {
    return null;
  }

  public List<String> getTagList() {
    return tagList;
  }

  public void accept(Visitor v) {
    assert v != null;
    v.visitProject(this);
  }
}
