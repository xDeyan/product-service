package bg.dyurukov.product.api;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import bg.dyurukov.product.api.ExeptionHandler.BadRequestException;
import bg.dyurukov.product.api.dto.ProductByCategoryDto;
import bg.dyurukov.product.api.dto.ProductCreationDto;
import bg.dyurukov.product.api.dto.ProductModificationDto;
import bg.dyurukov.product.api.dto.ProductOrderDto;
import bg.dyurukov.product.api.dto.ProductSearchFilterDto;
import bg.dyurukov.product.api.dto.ProductViewDto;
import bg.dyurukov.product.repository.ProductRepository;
import bg.dyurukov.product.repository.ProductSpecifications;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductFacadeImpl implements ProductFacade {

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;

  private final ProductValidator productValidator;

  @Override
  public Page<ProductViewDto> search(
      ProductSearchFilterDto productSearchFilter, Pageable pageable) {
    var filter = productMapper.toSearchFilterDto(productSearchFilter);
    var specification = ProductSpecifications.of(filter).compose();
    return productRepository.findAll(specification, pageable).map(productMapper::toDto);
  }

  @Override
  public ProductViewDto create(ProductCreationDto product) {
    productValidator.validateProductCreation(product);

    var entity = productMapper.toEntity(product);
    productRepository.save(entity);

    return productMapper.toDto(entity);
  }

  @Override
  public Optional<ProductViewDto> findById(Long id) {
    return Optional.of(id).flatMap(productRepository::findById).map(productMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    productValidator.validateProductExists(id);
    productRepository.deleteById(id);
  }

  @Override
  public void order(Long id, ProductOrderDto order) {
    productValidator.validateProductExists(id);
    var entity = productRepository.findById(id).get();
    try {
      entity.order(order.getQuantity());
    } catch (IllegalArgumentException iae) {
      throw new BadRequestException(iae.getMessage(), iae);
    }
  }

  @Override
  public List<ProductByCategoryDto> listCategories() {
    return productRepository.listCategories().stream()
        .map(productMapper::toProductByCategoryAggregateDto)
        .toList();
  }

  @Override
  public void update(Long id, @Valid ProductModificationDto product) {
    productValidator.validateProductExists(id);
    var entity = productRepository.findById(id).get();
    productMapper.updateEntity(entity, product);
  }
}
