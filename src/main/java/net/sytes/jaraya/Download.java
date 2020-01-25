package net.sytes.jaraya;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.StandardCopyOption;

public class Download {
    public static int exec(String urlDescarga, String target) {
        try {
            if (target.startsWith(".")) {
                System.err.println("The target cannot start in dot (.)");
                return 1;
            }

            System.out.println("Downloading...");

            File file = new File(target);
            if (file.getParentFile().mkdirs()) System.out.println("Directories created for: " + target);

            URL url;
            try {
                url = new URL(urlDescarga);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return 1;
            }
            HttpURLConnection huc;
            boolean isOK = false;
            do {
                try {
                    huc = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                    return 1;
                }
                int statusCode;
                try {
                    statusCode = huc.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                    return 1;
                }
                System.out.println("status " + statusCode);
                if (statusCode == HttpURLConnection.HTTP_MOVED_TEMP
                        || statusCode == HttpURLConnection.HTTP_MOVED_PERM) {
                    urlDescarga = huc.getHeaderField("Location");
                    try {
                        url = new URL(urlDescarga);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        return 1;
                    }
                } else {
                    isOK = true;
                }
            } while (!isOK);

            try (InputStream is = huc.getInputStream()) {
                java.nio.file.Files.copy(
                        is,
                        file.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                return 1;
            }

            if (!new File(target).exists()) {
                System.err.println("File was not downloaded..");
                return 1;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
