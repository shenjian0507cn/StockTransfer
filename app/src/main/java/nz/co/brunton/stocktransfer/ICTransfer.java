package nz.co.brunton.stocktransfer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by james.shen on 10/08/2017.
 */

public class ICTransfer {
    private String SourceLocationCode;
    private String DestinationLocationCode;
    private String TransitLocationCode;
    private String DocumentDate;
    private String ReceiptDate;
    private String Reference;
    private String Comment;

    private List<ICTransferLine> Line;

    public void setSourceLocationCode(String SourceLocationCode) {
        this.SourceLocationCode = SourceLocationCode;
    }
    public String getSourceLocationCode() {
        return this.SourceLocationCode;
    }

    public void setDestinationLocationCode(String DestinationLocationCode) {
        this.DestinationLocationCode = DestinationLocationCode;
    }
    public String getDestinationLocationCode() {
        return this.DestinationLocationCode;
    }

    public void setTransitLocationCode(String TransitLocationCode) {
        this.TransitLocationCode = TransitLocationCode;
    }
    public String getTransitLocationCode() {
        return this.TransitLocationCode;
    }

    public void setDocumentDate(String DocumentDate) {
        this.DocumentDate = DocumentDate;
    }
    public String getDocumentDate() {
        return this.DocumentDate;
    }

    public void setReceiptDate(String ReceiptDate) {
        this.ReceiptDate = ReceiptDate;
    }
    public String getReceiptDate() {
        return this.ReceiptDate;
    }

    public void setReference(String Reference) {
        this.Reference = Reference;
    }
    public String getReference() {
        return this.Reference;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }
    public String getComment() {
        return this.Comment;
    }

    public void setLine(List<ICTransferLine> Line) {
        this.Line = Line;
    }
    public List<ICTransferLine> getLine() {
        return this.Line;
    }
}
