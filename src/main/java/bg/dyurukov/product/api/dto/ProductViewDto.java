package bg.dyurukov.product.api.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ProductViewDto {

  private Long id;

  private String name;

  private String category;

  private String description;

  private int quantity;

  private LocalDate createdOn;

  private LocalDate modifiedOn;
}
