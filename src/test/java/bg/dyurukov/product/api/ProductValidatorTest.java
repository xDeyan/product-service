package bg.dyurukov.product.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import bg.dyurukov.product.api.ExeptionHandler.BadRequestException;
import bg.dyurukov.product.api.ExeptionHandler.EntityNotFoundException;
import bg.dyurukov.product.api.dto.ProductCreationDto;
import bg.dyurukov.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductValidatorTest {

  @Mock ProductRepository productRepository;

  @Spy @InjectMocks ProductValidator productValidator;

  @Test
  void whenProductNameExists_thenBadRequestExceptionIsThrown() {
    when(productRepository.existsByName(anyString())).thenReturn(true);

    var product = ProductCreationDto.builder().name("AOC").build();

    var exception =
        assertThrows(
            BadRequestException.class, () -> productValidator.validateProductCreation(product));

    assertThat(exception.getMessage(), is("Product with name [AOC] already exists."));
  }

  @Test
  void whenProductNameDoesNotExists_thenBadRequestExceptionIsNotThrown() {
    when(productRepository.existsByName(anyString())).thenReturn(false);

    var product = ProductCreationDto.builder().name("AOC").build();

    productValidator.validateProductCreation(product);
  }

  @Test
  void whenProductWithIdDoesNotExists_thenBadRequestExceptionIsThrown() {
    when(productRepository.existsById(1l)).thenReturn(false);

    var exception =
        assertThrows(
            EntityNotFoundException.class, () -> productValidator.validateProductExists(1l));

    assertThat(exception.getMessage(), is("Product with id 1 cannot be found."));
  }

  @Test
  void whenProductWithIdExists_thenNoExceptionIsThrown() {
    when(productRepository.existsById(1l)).thenReturn(true);

    productValidator.validateProductExists(1l);
  }
}
