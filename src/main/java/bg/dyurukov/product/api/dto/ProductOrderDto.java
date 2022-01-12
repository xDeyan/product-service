package bg.dyurukov.product.api.dto;

import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductOrderDto {

  @Positive private int quantity;
}
