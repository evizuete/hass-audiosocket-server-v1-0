package es.spin21.bluu.streamer.processor;

import es.spin21.bluu.entities.Cdr;
import es.spin21.bluu.entities.CdrFile;
import es.spin21.bluu.entities.Event;
import es.spin21.bluu.entities.Route;
import es.spin21.bluu.streamer.utils.Finder;
import es.spin21.bluu.utils.EntityManagerHelper;
import org.apache.commons.compress.compressors.z.ZCompressorInputStream;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

public class CdrFileProcessor {
    private InputStream fileStream;
    private ZCompressorInputStream zStream;
    private Reader decoder;
    private BufferedReader reader;
    private Integer totalRows, errors, processed;
    private Finder finder;
    private CdrFile cdrFile;

    private Date startTime;

    private ArrayList<Cdr> pending;

    public CdrFileProcessor(Path file) {
        try {
            this.fileStream = new FileInputStream(String.valueOf(file));
            this.zStream = new ZCompressorInputStream(this.fileStream);
            this.decoder = new InputStreamReader(zStream, "UTF-8");
            this.reader = new BufferedReader(decoder);
            this.finder = new Finder();

            this.totalRows = 0;
            this.errors = 0;
            this.processed = 0;

            this.cdrFile = new CdrFile(file.toFile().getName());

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void read() {
        String line;
        this.startTime = new Date();
        try {
            EntityManagerHelper.beginTransaction();
            EntityManagerHelper.save(this.cdrFile);
            this.pending = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("1703")) {
                    System.out.print(".");
                    this.totalRows++;

                    Cdr cdr = new Cdr(line, this.cdrFile);
                    EntityManagerHelper.save(cdr);

                    Route incomingRoute = this.getRoute(cdr.getIncomingRoute(), cdr.getNetworkNode());
                    if (incomingRoute != null) {
                        Route outgoingRoute = this.getRoute(cdr.getOutgoingRoute(), cdr.getNetworkNode());
                        if (outgoingRoute != null) {
                            Event incomingEvent = new Event(cdr, 0, incomingRoute); EntityManagerHelper.save(incomingEvent);
                            Event outgoingEvent = new Event(cdr, 1, outgoingRoute); EntityManagerHelper.save(outgoingEvent);
                            this.processed++;
                            continue;
                        }
                    }

                    this.pending.add(cdr);
                    this.errors++;

                } else if (line.startsWith("480")) {

                } else if (line.startsWith("482")) {
                    CdrFileTrailer trailer = new CdrFileTrailer(line);
                    Date stopTime = new Date();

                    this.cdrFile.setProcessingTime((int) (stopTime.getTime() - startTime.getTime()));
                    this.cdrFile.setCreationDate(trailer.getCreationDate());
                    this.cdrFile.setNetworkNode(trailer.getNodeId());
                    this.cdrFile.setMediationNode(trailer.getMediationNode());
                    this.cdrFile.setMediationSequence(trailer.getMediationSequence());
                    this.cdrFile.setFileSize(trailer.getFileSize());

                    this.cdrFile.setTotalCdrs(this.totalRows);
                    this.cdrFile.setProcessed(this.processed);
                    this.cdrFile.setErrors(this.errors);
                }
            }
            reader.close();
            decoder.close();
            zStream.close();
            fileStream.close();

            EntityManagerHelper.save(this.cdrFile);
            EntityManagerHelper.commit();

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.processPending(pending);

        EntityManagerHelper.beginTransaction();
        this.cdrFile.setProcessed(this.processed);
        this.cdrFile.setErrors(this.errors);
        if (this.errors == 0) this.cdrFile.setStatus(1);
        EntityManagerHelper.commit();

    }

    private Route getRoute(String adnum, String networkNode) {
        if (adnum.equals("") || adnum.equals("0")) return null;

        Route route = finder.routeByAdnumAndNodeAlias(adnum, networkNode);
        return route;
    }

    private void processPending(ArrayList<Cdr> cdrs) {
        if (cdrs == null || cdrs.isEmpty()) return;

        EntityManagerHelper.beginTransaction();
        Integer i = 0;
        while(i < cdrs.size()-1) {
            Cdr cdr1 = cdrs.get(i);
            Cdr cdr2 = cdrs.get(i+1);

            if (cdr1.getOutgoingRoute().equals("") && cdr2.getIncomingRoute().equals("")) {
                Route incomingRoute = this.getRoute(cdr1.getIncomingRoute(), cdr1.getNetworkNode());
                Route outgoingRoute = this.getRoute(cdr2.getOutgoingRoute(), cdr2.getNetworkNode());

                if (incomingRoute != null && outgoingRoute != null) {
                    Event incomingEvent = new Event(cdr1, 0, incomingRoute);
                    EntityManagerHelper.save(incomingEvent);

                    Event outgoingEvent = new Event(cdr2, 1, outgoingRoute);
                    EntityManagerHelper.save(outgoingEvent);

                    cdr1.setStatus(1); EntityManagerHelper.save(cdr1);
                    cdr2.setStatus(1); EntityManagerHelper.save(cdr2);

                    this.processed += 2;
                    this.errors -= 2;

                    i++;
                }
            }
            i++;
        }
        EntityManagerHelper.commit();
    }
}
