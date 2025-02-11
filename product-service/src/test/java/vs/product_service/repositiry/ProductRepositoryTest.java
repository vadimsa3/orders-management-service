package vs.product_service.repositiry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vs.product_service.db.entity.ProductEntity;
import vs.product_service.db.repository.ProductRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  private ProductEntity productEntity;

  @BeforeEach
  public void setUp() {
    productEntity = ProductEntity.builder()
        .productName("TEST-PRODUCT")
        .price(100.0)
        .build();
    productRepository.save(productEntity);
  }

  @Test
  void testFindByProductNameShouldReturnProductEntity() {
    Optional<ProductEntity> productEntityOptional =
        productRepository.findByProductName("TEST-PRODUCT");
    assertTrue(productEntityOptional.isPresent());
    assertThat(productEntityOptional.get().getProductName()).isEqualTo("TEST-PRODUCT");
    assertThat(productEntityOptional.get().getPrice()).isEqualTo(100.0);
  }


}
