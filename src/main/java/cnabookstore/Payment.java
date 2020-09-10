package cnabookstore;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Payment_table")
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long orderId;
    private Float amount;
    private String status;
    private String type;

    @PrePersist
    public void onPrePersist(){
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        try {
            // mappings goes here
            String result = PaymentApplication.applicationContext.getBean(cnabookstore.external.VanCompanyService.class)
                    .pay(this);
            System.out.println(result);
            this.status = "PAYMENT_COMPLETED";

        }
        catch(Exception ex) {
            ex.printStackTrace();
            this.status = "PAYMENT_FAILED";
        }
    }

    @PostPersist
    public void onPostPersist(){
        if("PAYMENT_COMPLETED".equals(this.status)) {
            // mappings goes here
            Payed payed = new Payed();
            payed.setOrderId(orderId);
            payed.setPaymentId(id);
            this.status = payed.getPayStatus();

            payed.publishAfterCommit();

        }
        else if ("PAYMENT_FAILED".equals(this.status)) {
            PaymentFailed paymentFailed = new PaymentFailed();
            BeanUtils.copyProperties(this, paymentFailed);
            paymentFailed.setOrderId(orderId);
            paymentFailed.setPaymentId(id);
            this.status = paymentFailed.payStatus;

            paymentFailed.publishAfterCommit();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
