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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Base class for JavaBeans with bound properties.
 *
 * @author Petter Holmström
 */
public abstract class JavaBean {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add a PropertyChangeListener to the listener list. The listener is
     * registered for all properties. The same listener object may be added more
     * than once, and will be called as many times as it is added. If
     * <code>listener</code> is null, no exception is thrown and no action is
     * taken.
     *
     * @param listener The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a
     * PropertyChangeListener that was registered for all properties. If
     * <code>listener</code> was added more than once to the same event source,
     * it will be notified one less time after being removed. If
     * <code>listener</code> is null, or was never added, no exception is thrown
     * and no action is taken.
     *
     * @param listener The PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Add a PropertyChangeListener for a specific property. The listener will
     * be invoked only when a call on firePropertyChange names that specific
     * property. The same listener object may be added more than once. For each
     * property, the listener will be invoked the number of times it was added
     * for that property. If <code>propertyName</code> or <code>listener</code>
     * is null, no exception is thrown and no action is taken.
     *
     * @param propertyName The name of the property to listen on.
     * @param listener The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove a PropertyChangeListener for a specific property. If
     * <code>listener</code> was added more than once to the same event source
     * for the specified property, it will be notified one less time after being
     * removed. If <code>propertyName</code> is null, no exception is thrown and
     * no action is taken. If <code>listener</code> is null, or was never added
     * for the specified property, no exception is thrown and no action is
     * taken.
     *
     * @param propertyName The name of the property that was listened on.
     * @param listener The PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Returns the {@link PropertyChangeSupport} instance that backs this
     * JavaBean.
     *
     * @return a {@code PropertyChangeSupport} object, never {@code null}.
     */
    protected PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

}
