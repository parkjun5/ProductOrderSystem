package kr.co.system.homework.product.application.exception;

public class NotEnoughStockInProduct extends RuntimeException {

    public static final String ERR_NOT_ENOUGH_STOCK = "Not enough stock in product : ";

    public NotEnoughStockInProduct(long productId) {
        super(ERR_NOT_ENOUGH_STOCK + productId);
    }
}
