package Fita1;// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

public interface Visitor {
  void visitProject(Project var1);

  void visitTask(Task var1);

  void visitInterval(Interval interval);

}
