package beans.views;

/**
 * Created by Yauhen_Yemelyanau on 7/6/2017.
 */
import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.models.Ticket;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class TicketsPdfView extends AbstractITextPdfView {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")

        PdfPTable table = new PdfPTable(5);
        table.setWidths(new int[]{27, 27, 10, 26, 10});

        table.addCell("Event Name");
        table.addCell("Date");
        table.addCell("Seats");
        table.addCell("User");
        table.addCell("Price");

        List<Ticket> tickets = (List<Ticket>) model.get("tickets");

        for(Ticket t: tickets) {
            table.addCell(t.getEvent().getName());
            table.addCell(t.getDateTime().toLocalDate().toString());
            table.addCell(t.getSeats());
            table.addCell(t.getUser().getName());
            table.addCell(Double.toString(t.getPrice()));
        }
        document.add(table);
    }
}
