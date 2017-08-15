package nz.co.brunton.stocktransfer;

/**
 * Created by james.shen on 11/08/2017.
 */

public class ICQuantity {
    public String LocationCode;
    public String ProductCode;
    public double QuantityInStock;
    public double QuantityAvailable;

    public String getLocationCode() {
        return LocationCode;
    }

    public void setLocationCode(String locationCode) {
        LocationCode = locationCode;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public double getQuantityInStock() {
        return QuantityInStock;
    }

    public void setQuantityInStock(double quantityInStock) {
        QuantityInStock = quantityInStock;
    }

    public double getQuantityAvailable() {
        return QuantityAvailable;
    }

    public void setQuantityAvailable(double quantityAvailable) {
        QuantityAvailable = quantityAvailable;
    }
}
