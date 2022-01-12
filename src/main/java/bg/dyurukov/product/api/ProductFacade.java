package bg.dyurukov.product.api;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import bg.dyurukov.product.api.dto.ProductByCategoryDto;
import bg.dyurukov.product.api.dto.ProductCreationDto;
import bg.dyurukov.product.api.dto.ProductModificationDto;
import bg.dyurukov.product.api.dto.ProductOrderDto;
import bg.dyurukov.product.api.dto.ProductSearchFilterDto;
import bg.dyurukov.product.api.dto.ProductViewDto;

public interface ProductFacade {

  Page<ProductViewDto> search(ProductSearchFilterDto productSearchFilter, Pageable pageable);

  ProductViewDto create(ProductCreationDto product);

  Optional<ProductViewDto> findById(Long id);

  void delete(Long id);

  void order(Long id, ProductOrderDto order);

  List<ProductByCategoryDto> listCategories();

  void update(Long id, @Valid ProductModificationDto product);
}
