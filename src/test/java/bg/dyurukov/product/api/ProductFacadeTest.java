package bg.dyurukov.product.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import bg.dyurukov.product.api.dto.ProductCreationDto;
import bg.dyurukov.product.model.Product;
import bg.dyurukov.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductFacadeTest {

  @Mock private ProductRepository productRepository;

  @Spy private ProductMapperImpl productMapper;

  @Mock private ProductValidator productValidator;

  @Spy @InjectMocks private ProductFacadeImpl productFacade;

  @Captor private ArgumentCaptor<Product> productCaptor;

  @Test
  void whenProductIsCreated_thenItIsProperlyMapped_andPersisted() {
    var product =
        ProductCreationDto.builder()
            .name("AOC 24G2")
            .category("Monitor")
            .description("AOC G2 Monitooor")
            .quantity(10)
            .build();

    productFacade.create(product);

    verify(productValidator).validateProductCreation(product);
    verify(productMapper).toEntity(product);
    verify(productRepository).save(productCaptor.capture());
    verify(productMapper).toDto(productCaptor.getValue());

    var entity = productCaptor.getValue();

    assertThat(entity.getName(), is("AOC 24G2"));
    assertThat(entity.getCategory(), is("Monitor"));
    assertThat(entity.getDescription(), is("AOC G2 Monitooor"));
    assertThat(entity.getQuantity(), is(10));
  }
}
