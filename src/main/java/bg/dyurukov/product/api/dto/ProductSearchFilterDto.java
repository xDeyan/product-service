package bg.dyurukov.product.api.dto;

import java.time.LocalDate;
import java.util.List;
import org.springdoc.api.annotations.ParameterObject;
import lombok.Data;

@Data
@ParameterObject
public class ProductSearchFilterDto {

  private List<Long> id;

  private List<String> name;

  private List<String> category;

  private Integer quantity;

  private LocalDate createdAfter;

  private LocalDate createdBefore;
}
