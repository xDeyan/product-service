package bg.dyurukov.product.api.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CategoryDto {

  private final String name;

  private final long availableProducts;
}
