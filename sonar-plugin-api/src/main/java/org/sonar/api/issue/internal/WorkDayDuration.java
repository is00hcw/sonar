/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2013 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.api.issue.internal;

import java.io.Serializable;

public class WorkDayDuration implements Serializable {

  private static final int DAY = 10000;
  private static final int HOUR = 100;
  private static final int MINUTE = 1;

  private int days;
  private int hours;
  private int minutes;

  private WorkDayDuration(int minutes, int hours, int days) {
    this.minutes = minutes;
    this.hours = hours;
    this.days = days;
  }

  private WorkDayDuration(long durationInLong) {
    long time = durationInLong;
    Long currentTime = time / DAY;
    if (currentTime > 0) {
      this.days = currentTime.intValue();
      time = time - (currentTime * DAY);
    }

    currentTime = time / HOUR;
    if (currentTime > 0) {
      this.hours = currentTime.intValue();
      time = time - (currentTime * HOUR);
    }

    currentTime = time / MINUTE;
    if (currentTime > 0) {
      this.minutes = currentTime.intValue();
    }
  }

  public static WorkDayDuration of(int minutes, int hours, int days) {
    return new WorkDayDuration(minutes, hours, days);
  }

  public static WorkDayDuration fromLong(long durationInLong) {
    return new WorkDayDuration(durationInLong);
  }

  public long toLong() {
    return days * DAY + hours * HOUR + minutes * MINUTE;
  }

  public int days() {
    return days;
  }

  public int hours() {
    return hours;
  }

  public int minutes() {
    return minutes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkDayDuration workDayDuration = (WorkDayDuration) o;
    if (days != workDayDuration.days) {
      return false;
    }
    if (hours != workDayDuration.hours) {
      return false;
    }
    if (minutes != workDayDuration.minutes) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = Integer.valueOf(days).hashCode();
    result = 29 * result + Integer.valueOf(hours).hashCode();
    result = 27 * result + Integer.valueOf(minutes).hashCode();
    return result;
  }

}
