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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * TODO Document me!
 *
 * @author Petter Holmström
 * @param <C>
 */
public class MultilingualContent<C> {

    private final Map<Locale, C> contents = new HashMap<>();
    private final C defaultValue;

    public MultilingualContent(C defaultValue) {
        this.defaultValue = defaultValue;
    }

    public MultilingualContent() {
        this(null);
    }

    /**
     * Returns whether there is a content instance for the specified locale or
     * not.
     *
     * @param locale the locale to check, must not be {@code null}.
     */
    public boolean hasLocale(Locale locale) {
        return contents.containsKey(locale);
    }

    /**
     * Returns the content for the specified locale (never {@code null}). If no
     * content has been set, the default value is returned (can be
     * {@code null}).
     *
     * @param locale the locale for which the content should be returned, must
     * not be {@code null}.
     */
    public C get(Locale locale) {
        C content = contents.get(locale);
        if (content == null) {
            content = defaultValue;
        }
        return content;
    }

    /**
     * Adds localized content to this object and returns the content to allow
     * for method chaining.
     *
     * @param locale the locale of the content, must not be {@code null}.
     * @param content the localized content to add, must not be {@code null}.
     */
    public C set(Locale locale, C content) {
        contents.put(locale, content);
        return content;
    }

    /**
     * Removes the content for the specified locale. If no content for the
     * locale was found, nothing happens.
     *
     * @param locale the locale for which the content should be remove, must not
     * be {@code null}.
     */
    public void remove(Locale locale) {
        contents.remove(locale);
    }
}