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
package com.github.peholmst.fenix.planner.ui.model;

import com.github.peholmst.fenix.planner.model.EventType;
import com.github.peholmst.fenix.planner.model.Program;

/**
 * Combo box model for displaying the {@link EventType}s of a {@link Program} in
 * a Swing combo box.
 *
 * @author Petter Holmström
 */
public class EventTypesComboBoxModel extends AbstractProgramComboBoxModel<EventType> {

    @Override
    public int getSize() {
        return program == null ? 0 : program.getEventTypes().size();
    }

    @Override
    public EventType getElementAt(int index) {
        return program.getEventTypes().get(index);
    }

    @Override
    protected String getItemPropertyName() {
        return Program.PROP_EVENT_TYPES;
    }
}
