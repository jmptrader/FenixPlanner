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
package fenix.planner.pdf;

import fenix.planner.model.Event;
import fenix.planner.model.Organizer;
import fenix.planner.model.Program;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.imageio.ImageIO;
import org.joda.time.LocalDate;

/**
 *
 * @author peholmst
 */
public class PDFGeneratorTestProgram {

    private static final Locale sv = new Locale("sv");
    private static final String PIG_LATIN = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin a elit sit amet tortor tempus tristique. Phasellus turpis urna, iaculis ac diam nec, interdum gravida elit. Nulla sit amet semper nunc. Phasellus id adipiscing est. Sed facilisis lectus sollicitudin consectetur rhoncus. Cras at dolor velit. Cras vestibulum nec dui vel vulputate. Ut dignissim scelerisque massa, eu facilisis ligula adipiscing ullamcorper. "
            + "Nulla facilisi. Pellentesque et orci vel erat iaculis venenatis. Maecenas posuere, mauris ac ornare sodales, eros nulla mollis neque, et lobortis tellus risus at erat. Proin vehicula vulputate sodales. Duis eu risus eu massa mollis blandit. Interdum et malesuada fames ac ante ipsum primis in faucibus. Cras pretium diam nec eleifend consequat. Duis ultricies nulla et neque ullamcorper, non varius lacus consectetur. Donec pulvinar blandit venenatis. Phasellus lacus ipsum, vehicula non consectetur sed, malesuada in velit. Nunc in quam nec magna sagittis molestie. Nullam eu commodo nunc. Suspendisse lobortis nibh vitae tortor pulvinar egestas. Suspendisse potenti. Ut dignissim lobortis luctus. "
            + "Mauris vehicula sodales elit vitae laoreet. Praesent fringilla orci sed adipiscing pellentesque. Nulla ac quam nisi. Sed non mauris eu lacus posuere rutrum. Nullam sit amet enim et eros pretium gravida vitae eget sapien. Duis adipiscing urna elit, eget consequat metus lobortis nec. Phasellus elementum enim quis tellus commodo, vitae auctor turpis pretium. Duis in orci in elit egestas blandit. Vivamus et augue lorem. Aliquam fermentum nunc vitae dui ultrices, nec porttitor nunc aliquet. "
            + "Aliquam erat volutpat. Nam massa ligula, molestie in tempor quis, sagittis hendrerit dolor. Pellentesque nec sapien volutpat, mattis nisi a, imperdiet metus. Duis venenatis neque ut dui ornare, quis ultrices ipsum adipiscing. Morbi ac dui commodo, hendrerit sapien adipiscing, congue velit. Aliquam sit amet bibendum libero. Pellentesque at neque ut ante dignissim tincidunt a vel magna. Aenean id dui convallis, suscipit mauris in, molestie enim. Nam tincidunt leo condimentum fermentum ultrices. Nunc suscipit eleifend nulla, sed tristique neque congue sit amet. Nulla facilisi. Aliquam et velit eget magna dictum tempus tincidunt eu eros. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nam a nunc lacus. "
            + "Nullam mollis nisl libero, nec condimentum lorem aliquet sit amet. Donec eu arcu turpis. Sed sagittis augue at metus tempus sagittis. Proin id justo aliquet, tristique mauris eget, gravida tellus. Nulla tincidunt turpis ut odio ultricies aliquam nec eu est. Praesent a feugiat neque. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed euismod elit vel ligula vehicula, ac euismod libero vehicula. Cras et viverra orci, at ullamcorper nibh. Etiam et laoreet justo. Integer tempor tincidunt est, id posuere metus fermentum sed. Donec aliquam lacinia libero vitae fringilla. Maecenas vitae dictum nulla.";

    private static final String[] NAMES = {"Kalle Anka", "Joakim von Anka", "Alexander Lukas", "Musse Pigg", "Kajsa Anka", "Farmor Anka"};
    private static final String[] INITIALS = {"KA", "JvA", "AL", "MP", "KjA", "FA"};
    
    public static void main(String[] args) throws Exception {
        Program program = createProgram();

        PDFGenerator generator = new PDFGenerator(program, sv);
        File output = File.createTempFile("fenixplanner_sv", ".pdf");
        generator.generate(new FileOutputStream(output));
        Desktop.getDesktop().open(output);
    }

    private static Program createProgram() throws IOException {
        Program program = new Program();

        program.getHeader().setDepartmentName("Ankeborgs Frivilliga Brandkår");
        program.getHeader().setSectionName("Larmavdelningen");
        program.getHeader().setHeading("Övningsprogram för år 2013");
        program.getHeader().setLogo(ImageIO.read(PDFGeneratorTestProgram.class.getResourceAsStream("logo.png")));
        program.getHeader().setAuthorInitials("K.A.");
        
        Organizer organizer;
        List<Organizer> organizers = new ArrayList<>();
        for (int i = 0; i < NAMES.length; ++i) {
            organizer = program.addOrganizer();
            organizer.setFullName(NAMES[i]);
            organizer.setInitials(INITIALS[i]);
            organizers.add(organizer);
            // TODO phone number & e-mail
        }
        
        Event event;
        LocalDate date = new LocalDate(2013, 1, 3);
        for (int i = 0; i < 52; ++i) {
            event = program.addEvent();
            event.setDate(date);
            event.setSubject(pigLatin(30, false));
            event.setDescription(pigLatin(150, true));
            event.setOrganizer(organizers.get(rnd.nextInt(organizers.size())));
            date = date.plusWeeks(1);
        }
        return program;
    }
    private static final Random rnd = new Random();

    private static String pigLatin(int maxLength, boolean endWithPeriod) {
        final int start = rnd.nextInt(PIG_LATIN.length() - maxLength);
        final int startOfSentence = PIG_LATIN.indexOf(' ', start) + 1;
        int endOfSentence;
        if (startOfSentence + maxLength > PIG_LATIN.length()) {
            endOfSentence = PIG_LATIN.length();
        } else {
            endOfSentence = PIG_LATIN.indexOf(' ', startOfSentence + maxLength);
            if (endOfSentence == -1) {
                endOfSentence = PIG_LATIN.length();
            }
        }
        String s = PIG_LATIN.substring(startOfSentence, endOfSentence);
        StringBuilder sb = new StringBuilder();
        sb.append(s.substring(0, 1).toUpperCase());
        final char lastChar = s.charAt(s.length() -1);
        if (lastChar == ',' || lastChar == '.') {
            sb.append(s.substring(1, s.length() -1));
        } else {
            sb.append(s.substring(1));
        }
        if (endWithPeriod) {
            sb.append(".");
        }
        return sb.toString();        
    }
}
