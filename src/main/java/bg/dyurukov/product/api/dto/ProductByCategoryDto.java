package bg.dyurukov.product.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductByCategoryDto {

  private String name;

  @JsonProperty("availableProducts")
  private long count;
}
