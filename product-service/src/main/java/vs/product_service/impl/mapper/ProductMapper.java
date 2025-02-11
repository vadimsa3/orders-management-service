package vs.product_service.impl.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import vs.product_service.api.reqest.ProductCreateRequestDto;
import vs.product_service.api.response.CreateProductResponseDto;
import vs.product_service.api.response.DeleteProductResponseDto;
import vs.product_service.api.response.ProductResponseDto;
import vs.product_service.api.response.UpdateProductResponseDto;
import vs.product_service.db.entity.ProductEntity;

//@Mapper(componentModel = "spring")
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

  ProductEntity mapToProductEntity(ProductCreateRequestDto productCreateRequestDto);

  CreateProductResponseDto mapToCreateProductResponseDto(ProductEntity productEntity);

  ProductResponseDto mapToProductResponseDto(ProductEntity productEntity);

  List<ProductResponseDto> mapToProductResponseList(List<ProductEntity> productEntities);

  UpdateProductResponseDto mapToUpdateProductResponseDto(ProductEntity productEntity);

  DeleteProductResponseDto mapToDeleteProductResponseDto(ProductEntity productEntity);


}
