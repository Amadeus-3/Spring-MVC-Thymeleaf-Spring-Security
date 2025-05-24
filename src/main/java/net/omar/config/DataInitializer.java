package net.omar.config;

import lombok.RequiredArgsConstructor;
import net.omar.entity.Product;
import net.omar.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    
    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            List<Product> products = Arrays.asList(
                Product.builder()
                    .name("Laptop Dell XPS 15")
                    .description("Laptop haute performance avec écran 4K")
                    .price(1499.99)
                    .quantity(15)
                    .category("Informatique")
                    .build(),
                
                Product.builder()
                    .name("iPhone 14 Pro")
                    .description("Smartphone dernière génération")
                    .price(1199.99)
                    .quantity(25)
                    .category("Téléphonie")
                    .build(),
                
                Product.builder()
                    .name("Samsung Galaxy S23")
                    .description("Smartphone Android premium")
                    .price(999.99)
                    .quantity(20)
                    .category("Téléphonie")
                    .build(),
                
                Product.builder()
                    .name("MacBook Pro M2")
                    .description("Ordinateur portable Apple avec puce M2")
                    .price(2499.99)
                    .quantity(10)
                    .category("Informatique")
                    .build(),
                
                Product.builder()
                    .name("iPad Air")
                    .description("Tablette Apple 10.9 pouces")
                    .price(699.99)
                    .quantity(30)
                    .category("Tablettes")
                    .build(),
                
                Product.builder()
                    .name("Sony WH-1000XM5")
                    .description("Casque à réduction de bruit")
                    .price(399.99)
                    .quantity(40)
                    .category("Audio")
                    .build(),
                
                Product.builder()
                    .name("Clavier Mécanique RGB")
                    .description("Clavier gaming avec rétroéclairage RGB")
                    .price(149.99)
                    .quantity(50)
                    .category("Accessoires")
                    .build(),
                
                Product.builder()
                    .name("Souris Logitech MX Master 3")
                    .description("Souris ergonomique sans fil")
                    .price(99.99)
                    .quantity(60)
                    .category("Accessoires")
                    .build(),
                
                Product.builder()
                    .name("Webcam 4K")
                    .description("Webcam haute définition pour streaming")
                    .price(179.99)
                    .quantity(35)
                    .category("Accessoires")
                    .build(),
                
                Product.builder()
                    .name("SSD Samsung 1TB")
                    .description("Disque SSD NVMe haute vitesse")
                    .price(129.99)
                    .quantity(45)
                    .category("Stockage")
                    .build()
            );
            
            repository.saveAll(products);
        };
    }
}