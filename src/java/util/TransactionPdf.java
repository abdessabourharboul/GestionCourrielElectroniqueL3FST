package util;

import bean.Contact;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Khalid
 */
public class TransactionPdf {

    public void generateTransactionPdf(Contact contact) throws JRException, IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("adresse", contact.getAdresse());
        params.put("numTel", contact.getNumTel());
        params.put("ville", contact.getVille());
        params.put("nom", contact.getNom());
        Date date = new Date();
        params.put("date", date);
        String fileName = "Bilan-" + contact.getId();
        PdfUtil.generatePdf(contact.getCourriers(), params, fileName, "/report/attestationInscription.jasper");
    }
}
