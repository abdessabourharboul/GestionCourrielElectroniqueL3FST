package controller.util;

import java.io.File;

/**
 *
 * @author asus
 */
public class ImageUtil {

    public static String getExtenion(String fileName) {
        fileName = fileName.replace('.', ':');
        String[] tabs = fileName.split(":");
        return tabs[tabs.length - 1];
    }

    public static boolean isValidImage(String fileName) {
        fileName = fileName.replace('.', ':');
        String[] tabs = fileName.split(":");
        System.out.println("tabs0 ==> " + tabs[0]);
        System.out.println("tabs1 ==> " + tabs[1]);
        return tabs.length <= 2;
    }

    public static String addaptExtenion(String rootPath, String fileName) {
        String fileAbsolutePath = "";
        fileName = fileName.replace('.', ':');
        String[] tabs = fileName.split(":");
        String name = tabs[0];
        String extention = tabs[1];
        File myFile = new File(rootPath + "/" + name + "." + extention.toLowerCase());
        if (!myFile.exists()) {
            fileAbsolutePath = rootPath + "/" + name + "." + extention.toUpperCase();
        } else {
            fileAbsolutePath = rootPath + "/" + name + "." + extention.toLowerCase();
        }
        System.out.println("ha howaaa  ==> " + fileAbsolutePath);
        return fileAbsolutePath;
    }
}
