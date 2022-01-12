package bg.dyurukov.product.repository;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class ProductSearchFilter {

  private List<Long> id;

  private List<String> name;

  private List<String> category;

  private Integer quantity;

  private LocalDate createdAfter;

  private LocalDate createdBefore;
}
