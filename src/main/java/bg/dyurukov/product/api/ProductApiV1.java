package bg.dyurukov.product.api;

import java.util.List;
import javax.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import bg.dyurukov.product.api.dto.ProductByCategoryDto;
import bg.dyurukov.product.api.dto.ProductCreationDto;
import bg.dyurukov.product.api.dto.ProductModificationDto;
import bg.dyurukov.product.api.dto.ProductOrderDto;
import bg.dyurukov.product.api.dto.ProductSearchFilterDto;
import bg.dyurukov.product.api.dto.ProductViewDto;
import bg.dyurukov.product.model.Product_;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductApiV1 {

  private final ProductFacade productFacade;

  @GetMapping
  @PageableAsQueryParam
  public Page<ProductViewDto> listProducts(
      @PageableDefault(sort = Product_.ID) Pageable pageable,
      ProductSearchFilterDto productSearchFilter) {
    return productFacade.search(productSearchFilter, pageable);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductViewDto createProduct(@Valid @RequestBody ProductCreationDto product) {
    return productFacade.create(product);
  }

  @PutMapping("/{id}")
  public void updateProduct(
      @PathVariable Long id, @Valid @RequestBody ProductModificationDto product) {
    productFacade.update(id, product);
  }

  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable Long id) {
    productFacade.delete(id);
  }

  @PostMapping("/{id}/quantity")
  public void orderProduct(
      @PathVariable Long id, @Valid @RequestBody ProductOrderDto productOrder) {
    productFacade.order(id, productOrder);
  }

  @GetMapping("/categories")
  public List<ProductByCategoryDto> listCategories() {
    return productFacade.listCategories();
  }
}
