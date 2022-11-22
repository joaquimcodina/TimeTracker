package Fita1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

// This class is a subclass of a Fita1.Component (Fita1.Component.java) class, and, therefore, it implements
// what the superclass forces to implement.
public class Project extends Component {
  private List<Component> componentList = new LinkedList<>();

  private List<String> tagList = new LinkedList<>();

  public Project(String name, Project father) {
    super(name, father);

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
  }

  public Project(String name, Project father, Duration elapsedTime,
                 LocalDateTime startDate, LocalDateTime finalDate) {
    super(name, father, elapsedTime, startDate, finalDate);
    if (father != null) {
      father.addComponent(this);
    }
  }

  public void updateElapsedTime() {
    this.setElapsedTime();

    for (Component component : this.componentList) {
      this.sumElapsedTime(component.getElapsedTime());
    }

    if (this.getFather() != null) {
      this.getFather().updateElapsedTime();
    }
  }

  protected void addComponent(Component comp) {
    assert comp != null;
    this.componentList.add(comp);
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
