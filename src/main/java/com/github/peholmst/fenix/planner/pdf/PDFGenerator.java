/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.peholmst.fenix.planner.pdf;

import com.github.peholmst.fenix.planner.model.Event;
import com.github.peholmst.fenix.planner.model.Program;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.OutputStream;
import java.util.Locale;

/**
 *
 * @author peholmst
 */
public class PDFGenerator {

    private final Program program;
    private final Locale locale;
    private Document document;

    public PDFGenerator(Program program, Locale locale) {
        this.program = program;
        this.locale = locale;
    }

    public void generate(OutputStream output) throws DocumentException {
        document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, output);

        document.open();
        document.add(createEventTable());
        document.close();
    }

    private PdfPTable createEventTable() {
        PdfPTable table = new PdfPTable(new float[]{1, 7, 1.2f});
        table.setWidthPercentage(100f);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);

        // Fonts
        final Font headerFont = new Font(Font.FontFamily.HELVETICA);
        headerFont.setStyle(Font.BOLD);
        headerFont.setSize(12);
        headerFont.setColor(BaseColor.WHITE);

        final Font subjectFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
        final Font descriptionFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);

        // Header row
        table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
        table.getDefaultCell().setPadding(5);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(new Phrase("Datum", headerFont));
        table.addCell(new Phrase("Ämne", headerFont));
        table.addCell(new Phrase("Ansvarig", headerFont));
        table.setHeaderRows(1);
        table.getDefaultCell().setBackgroundColor(null);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

        // Events
        for (Event event : program.getEvents()) {
            if (event.getDate().getMonthOfYear() % 2 == 0) {
                table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
            } else {
                table.getDefaultCell().setBackgroundColor(null);
            }
            table.addCell(event.getDate().toString("dd.MM", locale));
            PdfPCell subjectCell = new PdfPCell(table.getDefaultCell());
            subjectCell.addElement(new Phrase(event.getSubject().get(locale), subjectFont));
            subjectCell.addElement(new Phrase(event.getDescription().get(locale), descriptionFont));
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
}
