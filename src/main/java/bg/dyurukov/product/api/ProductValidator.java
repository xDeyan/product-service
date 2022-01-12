package bg.dyurukov.product.api;

import static java.text.MessageFormat.format;
import org.springframework.stereotype.Component;
import bg.dyurukov.product.api.ExeptionHandler.BadRequestException;
import bg.dyurukov.product.api.ExeptionHandler.EntityNotFoundException;
import bg.dyurukov.product.api.dto.ProductCreationDto;
import bg.dyurukov.product.api.dto.ProductModificationDto;
import bg.dyurukov.product.model.Product;
import bg.dyurukov.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductValidator {

  private final ProductRepository productRepository;

  public void validateProductCreation(ProductCreationDto product) {
    validateProductName(product.getName());
  }

  public void validateProductModification(Long id, ProductModificationDto product) {
    validateProductExists(id);
    validateProductName(id, product.getName());
  }

  private void validateProductName(Long id, String name) {
    if (productRepository.existsByNameAndIdNot(name, id)) {
      throw new BadRequestException(format("Product with name [{0}] already exists.", name));
    }
  }

  private void validateProductName(String name) {
    if (productRepository.existsByName(name)) {
      throw new BadRequestException(format("Product with name [{0}] already exists.", name));
    }
  }

  public void validateProductExists(Long id) {
    if (!productRepository.existsById(id)) {
      throw new EntityNotFoundException(Product.class, id);
    }
  }
}
