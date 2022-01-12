package bg.dyurukov.product.api;

import static java.text.MessageFormat.format;
import org.springframework.stereotype.Component;
import bg.dyurukov.product.api.ExeptionHandler.BadRequestException;
import bg.dyurukov.product.api.ExeptionHandler.EntityNotFoundException;
import bg.dyurukov.product.api.dto.ProductCreationDto;
import bg.dyurukov.product.model.Product;
import bg.dyurukov.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductValidator {

  private final ProductRepository productRepository;

  public void validateProductCreation(ProductCreationDto product) {
    validateProductName(product);
  }

  public void validateProductModification(ProductCreationDto product) {
    validateProductName(product);
  }

  private void validateProductName(ProductCreationDto product) {
    if (productRepository.existsByName(product.getName())) {
      throw new BadRequestException(
          format("Product with name [{0}] already exists.", product.getName()));
    }
  }

  public void validateProductExists(Long id) {
    if (!productRepository.existsById(id)) {
      throw new EntityNotFoundException(Product.class, id);
    }
  }
}
