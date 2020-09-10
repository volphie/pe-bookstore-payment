
package cnabookstore.external;

import cnabookstore.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@FeignClient(name="van", url="${api.url.van}")
public interface VanCompanyService {

    @RequestMapping(method= RequestMethod.GET, path="/requestPayment")
    public @ResponseBody String pay(@RequestBody Payment payment);

}
