package kr.co.system.homework.support.exception;

public class SoldOutException extends IllegalArgumentException {

    public SoldOutException() {
        super("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
    }
}
