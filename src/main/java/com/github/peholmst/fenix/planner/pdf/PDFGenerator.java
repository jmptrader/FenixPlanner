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
package com.github.peholmst.fenix.planner.pdf;

import com.github.peholmst.fenix.planner.model.Event;
import com.github.peholmst.fenix.planner.model.Program;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * TODO Document me and translate strings
 *
 * @author peholmst
 */
public class PDFGenerator {

    private final Program program;
    private final Locale locale;
    private Document document;
    private static final float LOGO_HEIGHT = 70;
    private static final float LOGO_WIDTH = 70;
    private static final float LEFT_MARGIN = 36;
    private static final float RIGHT_MARGIN = 36;
    private static final float TOP_MARGIN = 100;
    private static final float BOTTOM_MARGIN = 54;
    private static final float ART_BOX_LEFT_MARGIN = 36;
    private static final float ART_BOX_RIGHT_MARGIN = 36;
    private static final float ART_BOX_TOP_MARGIN = 36;
    private static final float ART_BOX_BOTTOM_MARGIN = 36;
    private static final Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    private static final Font subjectFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private static final Font descriptionFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
    private static final Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    private static final Font departmentNameFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);
    private static final Font sectionNameFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private static final Font headingFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font authorFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private static final Font programFont = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.GRAY);

    public PDFGenerator(Program program, Locale locale) {
        this.program = program;
        this.locale = locale;
    }

    public void generate(OutputStream output) {
        final Rectangle pageSize = PageSize.A4;
        final ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();

        document = new Document(pageSize, LEFT_MARGIN, RIGHT_MARGIN, TOP_MARGIN, BOTTOM_MARGIN);
        try {
            final PdfWriter writer = PdfWriter.getInstance(document, outputBuffer);
            writer.setBoxSize("art", new Rectangle(ART_BOX_LEFT_MARGIN, ART_BOX_BOTTOM_MARGIN,
                    pageSize.getRight() - ART_BOX_RIGHT_MARGIN,
                    pageSize.getTop() - ART_BOX_TOP_MARGIN));
            writer.setPageEvent(new HeaderFooter());
            document.open();
            document.add(createEventTable());
            document.close();
            writer.close();

            // Loop through the document again to add the missing page numbers
            document = new Document();
            final PdfCopy copy = new PdfCopy(document, output);
            document.open();
            final PdfReader reader = new PdfReader(outputBuffer.toByteArray());
            addPageNumbers(reader, copy);
            document.close();
            reader.close();
        } catch (DocumentException | IOException ex) {
            throw new PDFGenerationException("Error generating PDF", ex);
        }
    }

    private PdfPTable createEventTable() {
        PdfPTable table = new PdfPTable(new float[]{1, 7, 1.2f});
        table.setWidthPercentage(100f);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);

        // Header row
        //table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
        table.getDefaultCell().setBorderWidthLeft(0);
        table.getDefaultCell().setBorderWidthTop(0);
        table.getDefaultCell().setBorderWidthRight(0);
        table.getDefaultCell().setPadding(5);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(new Phrase("Datum", headerFont));
        table.addCell(new Phrase("Ämne", headerFont));
        table.addCell(new Phrase("Ansvarig", headerFont));
        table.setHeaderRows(1);
        table.getDefaultCell().setBackgroundColor(null);

        // Events
        for (Event event : program.getEvents()) {
            if (event.getDate().getMonthOfYear() % 2 == 0) {
                table.getDefaultCell().setBackgroundColor(new BaseColor(0xdd, 0xdd, 0xdd));
            } else {
                table.getDefaultCell().setBackgroundColor(null);
            }
            table.addCell(event.getDate().toString("dd.MM", locale));
            PdfPCell subjectCell = new PdfPCell(table.getDefaultCell());
            subjectCell.addElement(new Phrase(event.getSubject().get(locale), subjectFont));
            if (event.getDescription().hasLocale(locale)) {
                subjectCell.addElement(new Phrase(event.getDescription().get(locale), descriptionFont));
            }
            if (event.getOrganizer() == null) {
                subjectCell.setColspan(2);
            }
            table.addCell(subjectCell);
            if (event.getOrganizer() != null) {
                table.addCell(event.getOrganizer().getInitials());
            }
        }

        return table;
    }

    private void addPageNumbers(PdfReader reader, PdfCopy copy) {
        int pageCount = reader.getNumberOfPages();
        PdfImportedPage page;
        PdfCopy.PageStamp stamp;

        for (int i = 1; i <= pageCount; ++i) {
            Rectangle rect = reader.getBoxSize(i, "art");
            page = copy.getImportedPage(reader, i);
            stamp = copy.createPageStamp(page);
            // add page numbers
            ColumnText.showTextAligned(stamp.getUnderContent(), Element.ALIGN_RIGHT,
                    new Phrase(String.format("%d / %d", i, pageCount), footerFont),
                    rect.getRight(), rect.getBottom() + 5, 0);
            try {
                stamp.alterContents();
                copy.addPage(page);
            } catch (BadPdfFormatException | IOException ex) {
                throw new PDFGenerationException("Error adding page number to page " + i, ex);
            }
        }
    }

    private class HeaderFooter extends PdfPageEventHelper {

        private final String currentDate;
        private final String programNameAndVersion;
        private Image logo;

        public HeaderFooter() {
            DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
            currentDate = format.format(new Date());
            logo = null;
            if (program.getHeader().hasLogo()) {
                try {
                    logo = Image.getInstance(program.getHeader().getLogo(), null);
                    logo.scaleToFit(LOGO_WIDTH, LOGO_HEIGHT);
                } catch (BadElementException | IOException ex) {
                    throw new PDFGenerationException("Error creating PDF image from logo", ex);
                }
            }
            programNameAndVersion = "Fenix Planner DEVELOPMENT"; // TODO Get this from somewhere
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            writeHeader(writer, document);
            writeFooter(writer, document);
        }

        private void writeHeader(PdfWriter writer, Document document) {
            final Rectangle rect = writer.getBoxSize("art");

            float textXOffset = 0;
            if (logo != null) {
                try {
                    logo.setAbsolutePosition(rect.getLeft(), rect.getTop() - logo.getScaledHeight() + departmentNameFont.getSize());
                    writer.getDirectContent().addImage(logo);
                    textXOffset = logo.getScaledWidth() + 10;
                } catch (DocumentException ex) {
                    throw new PDFGenerationException("Error adding logo to PDF page", ex);
                }
            }

            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                    new Phrase(program.getHeader().getDepartmentName().get(locale), departmentNameFont),
                    rect.getLeft() + textXOffset, rect.getTop(), 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                    new Phrase(program.getHeader().getSectionName().get(locale), sectionNameFont),
                    rect.getLeft() + textXOffset, rect.getTop() - 14, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                    new Phrase(program.getHeader().getHeading().get(locale).toUpperCase(), headingFont),
                    rect.getLeft() + textXOffset, rect.getTop() - 40, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT,
                    new Phrase(program.getHeader().getAuthorInitials(), authorFont),
                    rect.getRight(), rect.getTop() - 40, 0);
        }

        private void writeFooter(PdfWriter writer, Document document) {
            final Rectangle rect = writer.getBoxSize("art");
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                    new Phrase(String.format("Utskrivet %s", currentDate), footerFont),
                    rect.getLeft(), rect.getBottom() + 5, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                    new Phrase(programNameAndVersion, programFont),
                    rect.getLeft(), rect.getBottom() - 7, 0);
        }
    }
}
