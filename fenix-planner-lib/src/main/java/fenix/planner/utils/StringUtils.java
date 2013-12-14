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
package fenix.planner.utils;

/**
 * Utility methods for working with strings.
 *
 * @author Petter Holmström
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * Returns the given string {@code s} if not {@code null}, or an empty
     * string if {@code null}.
     */
    public static String nullToEmptyString(String s) {
        return s == null ? "" : s;
    }
}
