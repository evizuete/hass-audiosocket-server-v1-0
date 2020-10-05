package es.spin21.bluu.streamer.processor;

import es.spin21.bluu.streamer.utils.DatesHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class CdrFileTrailer {
    private String recordType, nodeId, mediationNode;
    private Integer recordLength, fileSize, totalCdrs, mediationSequence;
    private Date creationDate;
    private Date startTime;

    public CdrFileTrailer(String line) {
        // 480             CS2KMDB120200622113057D1989626
        // 4823629 617026  CS2KMDB120200622113057D1989626
        // 01234567890123456789012345678901234567890123456789
        this.recordLength = Integer.parseInt(line.substring(0, 2));
        this.recordType = line.substring(2, 3);
        this.totalCdrs = Integer.parseInt(line.substring(3, 8).replaceAll(" ", ""));
        this.fileSize = Integer.parseInt(line.substring(8, 16).replaceAll(" ", ""));
        this.nodeId = line.substring(16, 24).replaceAll(" ", "");
        this.creationDate = DatesHelper.getDateFromString(line.substring(24, 38), "yyyyMMddHHmmss");

        this.mediationNode = line.substring(38, 40).replaceAll(" ", "");
        this.mediationSequence = Integer.parseInt(line.substring(40, 47).replaceAll(" ", ""));
        this.startTime = new Date();

    }

}
