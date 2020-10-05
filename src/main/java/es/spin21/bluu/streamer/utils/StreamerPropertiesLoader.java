package es.spin21.bluu.streamer.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.Stream;

@Getter
@Setter
public class StreamerPropertiesLoader {
    private String inputDirectory;
    private String processedDirectory;
    private String errorsDirectory;

    public StreamerPropertiesLoader(String propertiesFile) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            //entrada = getClass().getClassLoader().getResourceAsStream("configuracion.properties");
            inputStream = new FileInputStream(propertiesFile);
            properties.load(inputStream);

            this.inputDirectory = properties.getProperty("cdr.dir.input");
            this.processedDirectory = properties.getProperty("cdr.dir.processed");
            this.errorsDirectory = properties.getProperty("cdr.dir.errors");

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }

    }
}
