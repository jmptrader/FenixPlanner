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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Class that holds multiple instances of the same {@link Localized} class, for
 * different locales. <b>The localized class is required to have a public
 * constructor that takes one {@link Locale} instance as its only parameter.</b>
 *
 * @author Petter Holmström
 * @param <C>
 */
public class MultilingualContent<C extends Localized> {

    private final Map<Locale, C> contents = new HashMap<>();
    private final Class<C> contentClass;

    /**
     * Constructor.
     *
     * @param contentClass the class of the localized content, must not be
     * {@code null}.
     */
    public MultilingualContent(Class<C> contentClass) {
        this.contentClass = contentClass;
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
     * content has been set, a new instance of the localized class is created
     * and returned.
     *
     * @param locale the locale for which the content should be returned, must
     * not be {@code null}.
     */
    public C get(Locale locale) {
        C content = contents.get(locale);
        if (content == null) {
            try {
                content = contentClass.getConstructor(Locale.class).newInstance(locale);
                contents.put(locale, content);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new RuntimeException("Could not create new instance of content", ex);
            }
        }
        return content;
    }

    /**
     * Adds localized content to this object and returns the content to allow
     * for method chaining.
     *
     * @param content the localized content to add, must not be {@code null}.
     */
    public C set(C content) {
        contents.put(content.getLocale(), content);
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
