package cnabookstore;

public class Payed extends AbstractEvent {


    protected Long orderId;
    protected Long paymentId;
    protected String payStatus;

    public Payed() {
        super();
        payStatus="PAYMENT_COMPLETED";
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

}
