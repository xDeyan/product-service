package bg.dyurukov.product.repository;

import static org.springframework.util.CollectionUtils.isEmpty;
import org.springframework.data.jpa.domain.Specification;
import bg.dyurukov.product.model.Product;
import bg.dyurukov.product.model.Product_;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public final class ProductSpecifications {
  private static final Specification<Product> NOOP_SPEC = (r, q, c) -> null;

  private final ProductSearchFilter filter;

  public Specification<Product> compose() {
    return idIn()
        .and(nameIn())
        .and(categoryIn())
        .and(createdAfter())
        .and(createdBefore())
        .and(quantityEq());
  }

  private Specification<Product> createdAfter() {
    return filter.getCreatedAfter() == null
        ? NOOP_SPEC
        : (r, q, cb) ->
            cb.greaterThanOrEqualTo(r.get(Product_.createdOn), filter.getCreatedAfter());
  }

  private Specification<Product> createdBefore() {
    return filter.getCreatedBefore() == null
        ? NOOP_SPEC
        : (r, q, cb) -> cb.lessThanOrEqualTo(r.get(Product_.createdOn), filter.getCreatedBefore());
  }

  private Specification<Product> nameIn() {
    return isEmpty(filter.getName())
        ? NOOP_SPEC
        : (r, q, cb) -> r.get(Product_.name).in(filter.getName());
  }

  private Specification<Product> categoryIn() {
    return isEmpty(filter.getName())
        ? NOOP_SPEC
        : (r, q, c) -> r.get(Product_.category).in(filter.getName());
  }

  private Specification<Product> idIn() {
    return isEmpty(filter.getId()) ? NOOP_SPEC : (r, q, c) -> r.get(Product_.id).in(filter.getId());
  }

  private Specification<Product> quantityEq() {
    return filter.getQuantity() == null
        ? NOOP_SPEC
        : (r, q, c) -> r.get(Product_.id).in(filter.getId());
  }
}
