package nz.co.brunton.stocktransfer;

/**
 * Created by james.shen on 10/08/2017.
 */

public class ICTransferLine {
    private String ProductCode;
    private double QuantityDispatched;
    private double QuantityReceipted;

    public void setProductCode(String ProductCode) {
        this.ProductCode = ProductCode;
    }
    public String getProductCode() {
        return this.ProductCode;
    }

    public void setQuantityDispatched(double QuantityDispatched) {
        this.QuantityDispatched = QuantityDispatched;
    }
    public double getQuantityDispatched() {
        return this.QuantityDispatched;
    }

    public void setQuantityReceipted(double QuantityReceipted) {
        this.QuantityReceipted = QuantityReceipted;
    }
    public double getQuantityReceipted() {
        return  this.QuantityReceipted;
    }
}
