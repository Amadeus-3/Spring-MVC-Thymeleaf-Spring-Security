package net.omar.repository;

import net.omar.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Recherche par nom (contient)
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Recherche par catégorie
    Page<Product> findByCategory(String category, Pageable pageable);
    
    // Recherche par nom et catégorie
    Page<Product> findByNameContainingIgnoreCaseAndCategory(String name, String category, Pageable pageable);
    
    // Recherche par plage de prix
    Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);
    
    // Recherche complexe avec JPQL
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);
    
    // Obtenir toutes les catégories distinctes
    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.category IS NOT NULL ORDER BY p.category")
    List<String> findAllCategories();
    
    // Produits en stock
    Page<Product> findByQuantityGreaterThan(Integer quantity, Pageable pageable);
    
    // Produits par nom ou description
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Product> findByNameOrDescription(@Param("search") String search, Pageable pageable);
}