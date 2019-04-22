package util;

import bean.Courrier;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

public class TransactionPdf {

    public void generateTransactionPdf(Courrier courrier) throws JRException, IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("contact", courrier.getContact().getNom());
        params.put("numOrdre", courrier.getNumOrdre());
        params.put("numOrdreEX", courrier.getNumOrdreExt());
        params.put("objet", courrier.getObjet());
        params.put("dateArrivee", courrier.getDateArrivee());
        params.put("parameter1", courrier.getId());
        params.put("date", new Date());
        String fileName = "Bilan-" + courrier.getId();
        PdfUtil.generatePdf(courrier.getUniteAdministratives(), params, fileName, "/report/newReport.jasper");
    }
}
