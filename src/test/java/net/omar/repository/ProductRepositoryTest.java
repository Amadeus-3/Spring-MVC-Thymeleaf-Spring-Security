package net.omar.repository;

import net.omar.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private ProductRepository productRepository;
    
    private Product testProduct;
    
    @BeforeEach
    public void setUp() {
        // Créer des produits de test
        testProduct = Product.builder()
                .name("Laptop HP")
                .description("Laptop haute performance")
                .price(999.99)
                .quantity(10)
                .category("Informatique")
                .build();
        
        Product product2 = Product.builder()
                .name("Souris Gaming")
                .description("Souris RGB pour gaming")
                .price(49.99)
                .quantity(50)
                .category("Informatique")
                .build();
        
        Product product3 = Product.builder()
                .name("Table Bureau")
                .description("Table de bureau en bois")
                .price(299.99)
                .quantity(5)
                .category("Mobilier")
                .build();
        
        entityManager.persist(testProduct);
        entityManager.persist(product2);
        entityManager.persist(product3);
        entityManager.flush();
    }
    
    @Test
    public void testSaveProduct() {
        Product newProduct = Product.builder()
                .name("Clavier Mécanique")
                .price(79.99)
                .quantity(20)
                .category("Informatique")
                .build();
        
        Product savedProduct = productRepository.save(newProduct);
        
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Clavier Mécanique");
    }
    
    @Test
    public void testFindById() {
        Optional<Product> found = productRepository.findById(testProduct.getId());
        
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Laptop HP");
    }
    
    @Test
    public void testFindByNameContaining() {
        Page<Product> products = productRepository.findByNameContainingIgnoreCase("laptop", PageRequest.of(0, 10));
        
        assertThat(products.getContent()).hasSize(1);
        assertThat(products.getContent().get(0).getName()).isEqualTo("Laptop HP");
    }
    
    @Test
    public void testFindByCategory() {
        Page<Product> products = productRepository.findByCategory("Informatique", PageRequest.of(0, 10));
        
        assertThat(products.getContent()).hasSize(2);
    }
    
    @Test
    public void testSearchProducts() {
        Page<Product> products = productRepository.searchProducts("gaming", PageRequest.of(0, 10));
        
        assertThat(products.getContent()).hasSize(1);
        assertThat(products.getContent().get(0).getName()).isEqualTo("Souris Gaming");
    }
    
    @Test
    public void testFindAllCategories() {
        List<String> categories = productRepository.findAllCategories();
        
        assertThat(categories).hasSize(2);
        assertThat(categories).containsExactly("Informatique", "Mobilier");
    }
    
    @Test
    public void testFindByPriceBetween() {
        Page<Product> products = productRepository.findByPriceBetween(40.0, 100.0, PageRequest.of(0, 10));
        
        assertThat(products.getContent()).hasSize(1);
        assertThat(products.getContent().get(0).getName()).isEqualTo("Souris Gaming");
    }
    
    @Test
    public void testDeleteProduct() {
        productRepository.deleteById(testProduct.getId());
        
        Optional<Product> deleted = productRepository.findById(testProduct.getId());
        assertThat(deleted).isEmpty();
    }
    
    @Test
    public void testUpdateProduct() {
        Product product = productRepository.findById(testProduct.getId()).orElseThrow();
        product.setPrice(899.99);
        product.setQuantity(8);
        
        Product updated = productRepository.save(product);
        
        assertThat(updated.getPrice()).isEqualTo(899.99);
        assertThat(updated.getQuantity()).isEqualTo(8);
        assertThat(updated.getUpdatedAt()).isNotNull();
    }
}