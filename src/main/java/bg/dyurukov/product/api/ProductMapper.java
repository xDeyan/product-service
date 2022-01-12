package bg.dyurukov.product.api;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import bg.dyurukov.product.api.dto.ProductByCategoryDto;
import bg.dyurukov.product.api.dto.ProductCreationDto;
import bg.dyurukov.product.api.dto.ProductModificationDto;
import bg.dyurukov.product.api.dto.ProductSearchFilterDto;
import bg.dyurukov.product.api.dto.ProductViewDto;
import bg.dyurukov.product.model.Product;
import bg.dyurukov.product.repository.ProductByCategoryAggregate;
import bg.dyurukov.product.repository.ProductSearchFilter;

@Mapper
public interface ProductMapper {

  Product toEntity(ProductCreationDto product);

  ProductViewDto toDto(Product entity);

  ProductSearchFilter toSearchFilterDto(ProductSearchFilterDto productSearchFilter);

  ProductByCategoryDto toProductByCategoryAggregateDto(ProductByCategoryAggregate aggregate);

  void updateEntity(@MappingTarget Product entity, ProductModificationDto product);
}
