/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
/**
 *
 * @author a20111857
 */
public class PdfGenerator {
    public static final String RESULT = "hello.pdf";
    public static void crear_pdf() throws Exception{
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        document.setPageSize(PageSize.A5);
        document.setMargins(36, 72, 108, 180);
        document.setMarginMirroring(true);
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph(
            "The left margin of this odd page is 36pt (0.5 inch); " +
            "the right margin 72pt (1 inch); " +
            "the top margin 108pt (1.5 inch); " +
            "the bottom margin 180pt (2.5 inch)."));
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        for (int i = 0; i < 20; i++) {
            paragraph.add("Hello World! Hello People! " +
            		"Hello Sky! Hello Sun! Hello Moon! Hello Stars!");
        }
        document.add(paragraph);
        document.add(new Paragraph(
            "The right margin of this even page is 36pt (0.5 inch); " +
            "the left margin 72pt (1 inch)."));
        // step 5
        document.close();
    }
}
