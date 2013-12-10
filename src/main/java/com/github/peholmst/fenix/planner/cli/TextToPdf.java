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
package com.github.peholmst.fenix.planner.cli;

import com.github.peholmst.fenix.planner.Locales;
import com.github.peholmst.fenix.planner.model.Event;
import com.github.peholmst.fenix.planner.model.EventType;
import com.github.peholmst.fenix.planner.model.Organizer;
import com.github.peholmst.fenix.planner.model.Program;
import com.github.peholmst.fenix.planner.pdf.PDFGenerator;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.joda.time.format.DateTimeFormat;

/**
 * TODO Document me
 *
 * @author Petter Holmström
 */
public class TextToPdf {

    private final Program program;
    private final File input;
    private final File output;
    private final Map<Block, BlockParser> parsers = new HashMap<>();
    private final Map<String, Organizer> organizers = new HashMap<>();
    private final Map<String, EventType> eventTypes = new HashMap<>();

    TextToPdf(File input, File output) throws IOException {
        this.input = input;
        this.output = output;
        program = new Program();
        parsers.put(Block.HEADER, new HeaderParser());
        parsers.put(Block.ORGANIZERS, new OrganizersParser());
        parsers.put(Block.EVENTTYPES, new EventTypesParser());
        parsers.put(Block.EVENTS, new EventsParser());
        parsers.put(Block.FOREWORD, new ForewordParser());
        parsers.put(Block.AFTERWORD, new AfterwordParser());
    }

    enum Block {

        HEADER, ORGANIZERS, FOREWORD, EVENTTYPES, EVENTS, AFTERWORD
    }

    interface BlockParser {

        void parseLine(String line);
    }

    void parse() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String line;
            Block currentBlock = null;
            while ((line = reader.readLine()) != null) {
                final String trimmedLine = line.trim();
                switch (trimmedLine) {
                    case ":HEADER":
                        currentBlock = Block.HEADER;
                        break;
                    case ":ORGANIZERS":
                        currentBlock = Block.ORGANIZERS;
                        break;
                    case ":EVENTTYPES":
                        currentBlock = Block.EVENTTYPES;
                        break;
                    case ":EVENTS":
                        currentBlock = Block.EVENTS;
                        break;
                    case ":FOREWORD":
                        currentBlock = Block.FOREWORD;
                        break;
                    case ":AFTERWORD":
                        currentBlock = Block.AFTERWORD;
                        break;
                    case "":
                        break;
                    default:
                        parseLine(currentBlock, trimmedLine);
                }
            }
        }
    }

    void exportToPdf() throws IOException {
        try (FileOutputStream out = new FileOutputStream(output)) {
            new PDFGenerator(program, Locales.SWEDISH).generate(
                    out);
        }
    }

    private void parseLine(Block block, String line) {
        if (block == null) {
            throw new IllegalStateException("I don't know which block I'm in!");
        }
        parsers.get(block).parseLine(line);
    }

    class HeaderParser implements BlockParser {

        private boolean departmentNameParsed = false;
        private boolean sectionNameParsed = false;
        private boolean headingParsed = false;
        private boolean authorInitialsParsed = false;
        private boolean logoParsed = false;

        @Override
        public void parseLine(String line) {
            if (!departmentNameParsed) {
                program.getHeader().setDepartmentName(line);
                departmentNameParsed = true;
            } else if (!sectionNameParsed) {
                program.getHeader().setSectionName(line);
                sectionNameParsed = true;
            } else if (!headingParsed) {
                program.getHeader().setHeading(line);
                headingParsed = true;
            } else if (!authorInitialsParsed) {
                program.getHeader().setAuthorInitials(line);
                authorInitialsParsed = true;
            } else if (!logoParsed) {
                File logo = new File(line);
                try {
                    program.getHeader().setLogo(ImageIO.read(logo));
                } catch (IOException ex) {
                    throw new RuntimeException("Could not load logo image", ex);
                }
                logoParsed = true;
            }
        }
    }

    class OrganizersParser implements BlockParser {

        @Override
        public void parseLine(String line) {
            Organizer organizer = new Organizer();
            String[] parts = line.split("\t+");

            if (parts.length > 0) {
                organizer.setInitials(parts[0]);
            }
            if (parts.length > 1) {
                organizer.setFullName(parts[1]);
            }
            if (parts.length > 2) {
                organizer.setPhoneNumber(parts[2]);
            }
            if (parts.length > 3) {
                organizer.setEmail(parts[3]);
            }

            organizers.put(organizer.getInitials(), organizer);
            program.addOrganizer(organizer);
        }
    }

    class EventTypesParser implements BlockParser {

        @Override
        public void parseLine(String line) {
            EventType eventType = new EventType();
            String[] parts = line.split("\t+");

            if (parts.length < 5) {
                throw new IllegalStateException("I don't understand " + line);
            }

            eventType.setDescription(parts[1]);
            eventType.setOrganized(Boolean.parseBoolean(parts[2]));
            eventType.setBackgroundColor(Color.decode(parts[3]));
            eventType.setForegroundColor(Color.decode(parts[4]));

            eventTypes.put(parts[0], eventType);
            program.addEventType(eventType);
        }
    }

    class EventsParser implements BlockParser {

        private Event currentEvent;

        @Override
        public void parseLine(String line) {
            Event candidate = parseToEvent(line);
            if (candidate != null) {
                currentEvent = candidate;
                program.addEvent(currentEvent);
            } else if (currentEvent != null) {
                currentEvent.setDescription(line);
            } else {
                throw new IllegalStateException("I don't understand " + line);
            }
        }

        private Event parseToEvent(String line) {
            String[] parts = line.split("\t+");
            if (parts.length >= 3) {
                Event event = new Event();
                event.setDate(DateTimeFormat.forPattern("d.M.yyyy").parseLocalDate(parts[0]));
                event.setType(eventTypes.get(parts[1]));
                if (event.getType() == null) {
                    throw new IllegalStateException("Unknown event type: " + parts[1]);
                }
                if (event.getType().isOrganized()) {
                    event.setOrganizer(organizers.get(parts[2]));
                    if (event.getOrganizer() == null) {
                        throw new IllegalStateException("Unknown organizer: " + parts[2]);
                    }
                    if (parts.length > 3) {
                        event.setSubject(parts[3]);
                    }
                } else {
                    event.setSubject(parts[2]);
                }
                return event;
            } else {
                return null;
            }
        }
    }
    
    class ForewordParser implements BlockParser {

        @Override
        public void parseLine(String line) {
            StringBuilder sb = new StringBuilder(program.getForeword());
            if (sb.length() > 0) {
                sb.append('\n');
            }
            sb.append(line);
            program.setForeword(sb.toString());
        }        
    }

    class AfterwordParser implements BlockParser {

        @Override
        public void parseLine(String line) {
            StringBuilder sb = new StringBuilder(program.getAfterword());
            if (sb.length() > 0) {
                sb.append('\n');
            }
            sb.append(line);
            program.setAfterword(sb.toString());
        }        
    }
    
    
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Requires two arguments: [input file] [output file]");
            System.exit(1);
        }
        File input = new File(args[0]);
        File output = new File(args[1]);

        if (!input.exists() || !input.canRead()) {
            System.out.println("Cannot read from input file " + input.getAbsolutePath());
            System.exit(1);
        }
        TextToPdf textToPdf = new TextToPdf(input, output);
        textToPdf.parse();

        if (!output.exists()) {
            output.createNewFile();
        }
        if (!output.canWrite()) {
            System.out.println("Cannot write to output file " + output.getAbsolutePath());
            System.exit(1);
        }

        textToPdf.exportToPdf();
        System.out.println("Exported to PDF");
        System.exit(0);
    }
}
