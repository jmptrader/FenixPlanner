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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TODO Document me!
 * 
 * @author Petter Holmström
 */
public class Program extends JavaBean {

    public static final String PROP_ORGANIZERS = "organizers";
    public static final String PROP_EVENT_TYPES = "eventTypes";
    private static final Logger logger = LogManager.getLogger();

    private final Header header = new Header();
    private final List<Organizer> organizers = new ArrayList<>();
    private final List<EventType> eventTypes = new ArrayList<>();
    private final Set<Event> events = new HashSet<>();

    /**
     *
     * @return
     */
    public Header getHeader() {
        return header;
    }

    /**
     *
     * @return
     */
    public List<Event> getEvents() {
        List<Event> eventList = new ArrayList<>(events);
        Collections.sort(eventList);
        return eventList;
    }

    /**
     *
     * @return
     */
    public List<Organizer> getOrganizers() {
        return Collections.unmodifiableList(organizers);
    }

    /**
     *
     * @return
     */
    public List<EventType> getEventTypes() {
        return Collections.unmodifiableList(eventTypes);
    }

    /**
     *
     * @return
     */
    public List<Organizer> getSortedOrganizers() {
        final List<Organizer> organizerList = new ArrayList<>(organizers);
        Collections.sort(organizerList, new Comparator<Organizer>() {

            @Override
            public int compare(Organizer o1, Organizer o2) {
                return o1.getInitials().compareTo(o2.getInitials());
            }
        });
        return organizerList;
    }

    /**
     *
     * @param event
     * @return
     */
    public Event addEvent(Event event) {
        event.setProgram(this);
        events.add(event);
        return event;
    }

    /**
     *
     * @return
     */
    public Event addEvent() {
        return addEvent(new Event());
    }

    /**
     *
     * @param event
     */
    public void removeEvent(Event event) {
        events.remove(event);
        event.setProgram(null);
    }

    /**
     *
     * @return
     */
    public Organizer addOrganizer() {
        return addOrganizer(new Organizer());
    }

    /**
     *
     * @param organizer
     * @return
     */
    public Organizer addOrganizer(Organizer organizer) {
        organizer.setProgram(this);
        organizers.add(organizer);
        logger.debug("Added organizer {}", organizer);
        getPropertyChangeSupport().fireIndexedPropertyChange(PROP_ORGANIZERS,
                organizers.size() - 1,
                null, organizer);
        return organizer;
    }

    /**
     *
     * @param organizer
     */
    public void removeOrganizer(Organizer organizer) {
        int index = organizers.indexOf(organizer);
        removeOrganizer(index);
    }

    /**
     *
     * @param index
     */
    public void removeOrganizer(int index) {
        if (index != -1) {
            Organizer removed = organizers.remove(index);
            removed.setProgram(null);
            logger.debug("Removed organizer {)", removed);
            getPropertyChangeSupport().fireIndexedPropertyChange(PROP_ORGANIZERS,
                    index,
                    removed, null);
        }
    }

    /**
     *
     * @return
     */
    public EventType addEventType() {
        return addEventType(new EventType());
    }

    /**
     *
     * @param eventType
     * @return
     */
    public EventType addEventType(EventType eventType) {
        eventTypes.add(eventType);
        logger.debug("Added event type {}", eventType);
        getPropertyChangeSupport().fireIndexedPropertyChange(PROP_EVENT_TYPES,
                eventTypes.size() - 1, null, eventType);
        return eventType;
    }

    /**
     *
     * @param eventType
     */
    public void removeEventType(EventType eventType) {
        int index = eventTypes.indexOf(eventType);
        removeEventType(index);
    }

    /**
     *
     * @param index
     */
    public void removeEventType(int index) {
        if (index != -1) {
            EventType removed = eventTypes.remove(index);
            logger.debug("Removed event type {}", removed);
            getPropertyChangeSupport().fireIndexedPropertyChange(PROP_EVENT_TYPES,
                    index,
                    removed, null);
        }
    }
}
