package bg.dyurukov.product.api.dto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class ProductModificationDto {

  @NotBlank
  @Length(max = 30)
  private String name;

  @NotBlank
  @Length(max = 20)
  private String category;

  @NotBlank
  @Length(max = 200)
  private String description;
}
