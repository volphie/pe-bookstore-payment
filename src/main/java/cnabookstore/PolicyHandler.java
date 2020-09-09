package cnabookstore;

import cnabookstore.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_PayOrder(@Payload Ordered ordered){

        if(ordered.isMe()){
            System.out.println("##### listener PayOrder : " + ordered.toJson());
            Payment payment = new Payment();
            BeanUtils.copyProperties(ordered, payment);
            payment.setAmount(ordered.getPayAmount());
            payment.setStatus("PAYMENT_REQUESTED");
            payment.setType(ordered.getPayType());

            paymentRepository.save(payment);
        }
    }

}
