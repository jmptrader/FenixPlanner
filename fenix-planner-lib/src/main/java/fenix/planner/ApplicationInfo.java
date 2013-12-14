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
package fenix.planner;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Utility class for retrieving information about the application.
 *
 * @author Petter Holmström
 */
public final class ApplicationInfo {

    private static final String version;

    static {
        version = getPomProperties().getProperty("version", "N/A");
    }

    private ApplicationInfo() {
    }

    /**
     * Returns the name of the application.
     *
     * @return a string containing the title, never {@code null}.
     */
    public static String getTitle() {
        return "Fenix Planner";
    }

    /**
     * Returns the version of the application.
     *
     * @return a string containing the version, never {@code null}.
     */
    public static String getVersion() {
        return version;
    }

    private static Properties getPomProperties() {
        final Properties props = new Properties();
        final URL resource = ApplicationInfo.class.getResource("/META-INF/maven/fenix.planner/fenix-planner-lib/pom.properties");
        if (resource != null) {
            try {
                props.load(resource.openStream());
            } catch (IOException ex) {
                throw new RuntimeException("Error loading pom.properties", ex);
            }
        }
        return props;
    }
}
