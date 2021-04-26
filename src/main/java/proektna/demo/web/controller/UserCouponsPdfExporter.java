package proektna.demo.web.controller;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.dom4j.DocumentException;
import proektna.demo.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

public class UserCouponsPdfExporter {

    private User user;

    public UserCouponsPdfExporter(User user) {
        this.user=user;
    }

    private void writeTableHeader(PdfPTable table) {

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(4);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Username", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Surname", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Coupons", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
            table.addCell(String.valueOf(user.getUsername()));
            table.addCell(user.getName());
            table.addCell(user.getSurname());
            table.addCell(user.getCoupons().toString());
    }

    public void export(HttpServletRequest request,HttpServletResponse response) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("Potvrda za koristenje kuponi", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        p = new Paragraph("Sekoj koj ja poseduva ovaa potvrda mozhe da" +
                " ja iskoristi, ne mora da ja poseduva lichno korisnikot. " +
                "Bookstore ne prezema nikakva odgovornost.");
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        document.close();

    }
}
