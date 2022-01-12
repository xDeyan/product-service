package bg.dyurukov.product.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductByCategoryAggregate {

  private final String name;

  private final long count;
}
