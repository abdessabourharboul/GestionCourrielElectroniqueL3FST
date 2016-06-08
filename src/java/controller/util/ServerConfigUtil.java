package controller.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author asus
 */
public class ServerConfigUtil {

    //localhost:4848 > configuration > server-config >jvm-setting > jvm options > Add ..>-Dphotos.path=C:\Users\hamid\Documents\NetBeansProjects\budget_en_ligne_fstg\web
    //chemin dont laquelle on va creer le dosqsier globale qui aura pour bute de contenir la totalitees des dossier d un abonnee
    private static String vmParam = "photos.path";

    private static String getFilePath(boolean write) {
        String rootPath = "";
        if (write) {
            rootPath = System.getProperty(vmParam);
        } else {
            rootPath = "\\photos";
        }
        return rootPath;
    }

    public static String getPieceJointePath(boolean write) {
        String path = getFilePath(write) + "\\photoPieceJointe";
        if (!write) {
            path = path.replace("\\", "/");
        }
        return path;
    }

    public static void upload(UploadedFile uploadedFile, String destinationPath, String nameOfUploadeFile) {
        try {
            String fileSavePath = destinationPath + "//" + nameOfUploadeFile;
            InputStream fileContent = null;
            fileContent = uploadedFile.getInputstream();
            Files.copy(fileContent, new File(fileSavePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            JsfUtil.addErrorMessage(e, "Erreur Upload image");
            System.out.println("No update !!!!");
            e.printStackTrace();
        }
    }

    public static void delete(String destinationPath, String nameOfUploadeFile) {
        try {
            String fileSavePath = destinationPath + "//" + nameOfUploadeFile;
            File fileToDelete = new File(fileSavePath);
            Files.delete(fileToDelete.toPath());
        } catch (IOException e) {
            JsfUtil.addErrorMessage(e, "Erreur Lors de la suppresion");
            System.out.println("No delete !!!!");
            e.printStackTrace();
        }
    }
}
