package bg.dyurukov.product.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import bg.dyurukov.product.model.Product;

public interface ProductRepository
    extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  boolean existsByName(String name);

  @Query(
      "SELECT new bg.dyurukov.product.repository.ProductByCategoryAggregate(category, count(*)) from Product group by category")
  List<ProductByCategoryAggregate> listCategories();

  @Override
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints({@QueryHint(name = "javax.persistence.query.timeout", value = "1000")})
  Optional<Product> findById(Long id);
}
