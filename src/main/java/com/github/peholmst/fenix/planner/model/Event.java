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

import com.github.peholmst.fenix.planner.utils.StringUtils;
import org.joda.time.LocalDate;

/**
 * TODO Document me!
 * 
 * @author Petter Holmström
 */
public class Event extends JavaBean implements Comparable<Event> {

    public static final String PROP_DATE = "date";
    public static final String PROP_ORDER = "order";
    public static final String PROP_TYPE = "type";
    public static final String PROP_ORGANIZER = "organizer";
    public static final String PROP_SUBJECT = "subject";
    public static final String PROP_DESCRIPTION = "description";

    private LocalDate date;
    private int order;
    private String subject = "";
    private String description = "";
    private EventType type;
    private Organizer organizer;
    private Program program;

    public Program getProgram() {
        return program;
    }

    void setProgram(Program program) {
        this.program = program;
    }       
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        LocalDate old = this.date;
        this.date = date;
        getPropertyChangeSupport().firePropertyChange(PROP_DATE, old, this.date);
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        EventType old = this.type;
        this.type = type;
        getPropertyChangeSupport().firePropertyChange(PROP_TYPE, old, this.type);
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        Organizer old = this.organizer;
        this.organizer = organizer;
        getPropertyChangeSupport().firePropertyChange(PROP_ORGANIZER, old, this.organizer);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        String old = this.subject;
        this.subject = StringUtils.nullToEmptyString(subject);
        getPropertyChangeSupport().firePropertyChange(PROP_SUBJECT, old, this.subject);
    }    
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        String old = this.description;
        this.description = StringUtils.nullToEmptyString(description);
        getPropertyChangeSupport().firePropertyChange(PROP_DESCRIPTION, old, this.description);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        int old = this.order;
        this.order = order;
        getPropertyChangeSupport().firePropertyChange(PROP_ORDER, old, this.order);
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
