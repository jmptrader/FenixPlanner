/*
 * Fenix Planner
 * Copyright (C) 2013 Petter Holmström
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.peholmst.fenix.planner.model;

import org.joda.time.LocalDate;

/**
 *
 * @author peholmst
 */
public class Event implements Comparable<Event> {

    private LocalDate date;
    private int order;
    private final MultilingualContent<String> subject = new MultilingualContent<>("");
    private final MultilingualContent<String> description = new MultilingualContent<>("");
    private EventType type;
    private Organizer organizer;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public MultilingualContent<String> getSubject() {
        return subject;
    }

    public MultilingualContent<String> getDescription() {
        return description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int compareTo(Event other) {
        int o = 0;
        if (date != null && other.date != null) {
            o = date.compareTo(other.date);
        }
        if (o == 0) {
            o = order - other.order;
        }
        return o;
    }
}
