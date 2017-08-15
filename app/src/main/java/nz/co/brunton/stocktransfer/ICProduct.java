package nz.co.brunton.stocktransfer;

/**
 * Created by james.shen on 11/08/2017.
 */

public class ICProduct {
    public String ProductCode;
    public String Description;
    public String BarCode;

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }
}
