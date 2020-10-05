package es.spin21.bluu.streamer;

import es.spin21.bluu.streamer.processor.CdrFileProcessor;
import es.spin21.bluu.streamer.utils.StreamerPropertiesLoader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Streamer {
    private Path path;

    public Streamer() {
        StreamerPropertiesLoader properties = new StreamerPropertiesLoader("/aplicaciones/bluu/config/i-bluu-streamer.properties");
        this.path = Paths.get(properties.getInputDirectory());
    }

    public void read() {
        try {
            DirectoryStream<Path> files = Files.newDirectoryStream(path);
            for(Path file: files) {
                System.out.println("Reading file: " + file.toString());
                CdrFileProcessor cdrFile = new CdrFileProcessor(file);
                cdrFile.read();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
