package es.voiping.hass;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class AudioServer {
    private static final Logger logger = Logger.getLogger("AudioServer");

    private ServerSocket server;
    private Socket client;
    private InputStream inputStream;
    private OutputStream outputStream;

    public void start(int port) {
        try {
            this.server = new ServerSocket(port);
            this.client = this.server.accept();
            this.inputStream = this.client.getInputStream();
            this.outputStream = new FileOutputStream(new File("data.raw"));

            while(true) {
                byte[] header = this.inputStream.readNBytes(3);
                if (header == null || header.length == 0) break;
                if (header[0] == 0xff && header[1] == 0xff && header[2] == 0xff) break;

                int val = ((header[1] & 0xff) << 8) | (header[2] & 0xff);
                byte[] payload = this.inputStream.readNBytes(val);
                switch(header[0]) {
                    case 0x00:  logger.info("Ending the connection");
                                break;
                    case 0x01:
                                ByteBuffer byteBuffer = ByteBuffer.wrap(payload);
                                long high = byteBuffer.getLong();
                                long low = byteBuffer.getLong();
                                UUID uuid = new UUID(high, low);
                                logger.info("UUID: " + uuid.toString());
                                break;
                    case 0x10:  logger.info("Payload is signed linear, 16-bit, 8kHz, mono PCM (little-endian)");
                                this.outputStream.write(payload);
                                break;
                    case (byte) 0xff:  logger.warn("Error");
                                break;
                }
            }

            this.stop();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        try {
            this.inputStream.close();
            this.outputStream.close();
            this.client.close();
            this.server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
