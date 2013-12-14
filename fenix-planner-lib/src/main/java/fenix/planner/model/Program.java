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
package fenix.planner.model;

import fenix.planner.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO Document me!
 *
 * @author Petter Holmström
 */
public class Program extends JavaBean {

    public static final String PROP_ORGANIZERS = "organizers";
    public static final String PROP_EVENT_TYPES = "eventTypes";
    public static final String PROP_EVENTS = "events";
    public static final String PROP_FOREWORD = "foreword";
    public static final String PROP_AFTERWORD = "afterword";

    private final Header header = new Header();
    private String foreword = "";
    private String afterword = "";
    private final List<Organizer> organizers = new ArrayList<>();
    private final List<EventType> eventTypes = new ArrayList<>();
    private final List<Event> events = new ArrayList<>();

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
    public List<Event> getSortedCopyOfEvents() {
        List<Event> eventList = new ArrayList<>(events);
        Collections.sort(eventList);
        return eventList;
    }

    /**
     *
     * @return
     */
    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
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
    public List<Organizer> getSortedCopyOfOrganizers() {
        final List<Organizer> organizerList = new ArrayList<>(organizers);
        Collections.sort(organizerList);
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
        getPropertyChangeSupport().fireIndexedPropertyChange(PROP_EVENTS,
                events.size() - 1, null, event);
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
        int index = events.indexOf(event);
        removeEvent(index);
    }

    public void removeEvent(int index) {
        if (index != -1) {
            Event removed = events.remove(index);
            removed.setProgram(null);
            getPropertyChangeSupport().fireIndexedPropertyChange(PROP_EVENTS, index,
                    removed, null);
        }
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
        eventType.setProgram(this);
        eventTypes.add(eventType);
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
            removed.setProgram(null);
            getPropertyChangeSupport().fireIndexedPropertyChange(PROP_EVENT_TYPES,
                    index,
                    removed, null);
        }
    }

    /**
     *
     * @return
     */
    public String getForeword() {
        return foreword;
    }

    /**
     *
     * @param foreword
     */
    public void setForeword(String foreword) {
        String old = this.foreword;
        this.foreword = StringUtils.nullToEmptyString(foreword);
        getPropertyChangeSupport().firePropertyChange(PROP_FOREWORD, old, this.foreword);
    }

    /**
     *
     * @return
     */
    public String getAfterword() {
        return afterword;
    }

    /**
     *
     * @param afterword
     */
    public void setAfterword(String afterword) {
        String old = this.afterword;
        this.afterword = afterword;
        getPropertyChangeSupport().firePropertyChange(PROP_AFTERWORD, old, this.afterword);
    }

}
