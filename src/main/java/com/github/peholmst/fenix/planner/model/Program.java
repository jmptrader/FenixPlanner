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

/**
 *
 * @author peholmst
 */
public class Program {

    private final Header header = new Header();
    private final Set<Organizer> organizers = new HashSet<>();
    private final Set<EventType> eventTypes = new HashSet<>();
    private final Set<Event> events = new HashSet<>();

    public Header getHeader() {
        return header;
    }

    public List<Event> getEvents() {
        List<Event> eventList = new ArrayList<>(events);
        Collections.sort(eventList);
        return eventList;
    }

    public List<Organizer> getOrganizers() {
        List<Organizer> organizerList = new ArrayList<>(organizers);
        Collections.sort(organizerList, new Comparator<Organizer>() {

            @Override
            public int compare(Organizer o1, Organizer o2) {
                return o1.getInitials().compareTo(o2.getInitials());
            }
        });
        return organizerList;
    }

    public Event addEvent(Event event) {
        events.add(event);
        return event;
    }

    public Event addEvent() {
        return addEvent(new Event());
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public Organizer addOrganizer() {
        return addOrganizer(new Organizer());
    }

    public Organizer addOrganizer(Organizer organizer) {
        organizers.add(organizer);
        return organizer;
    }
}
