package bg.dyurukov.product.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCreationDto {

  @NotBlank
  @Length(max = 30)
  private String name;

  @NotBlank
  @Length(max = 20)
  private String category;

  @NotBlank
  @Length(max = 200)
  private String description;

  @PositiveOrZero private int quantity;
}
