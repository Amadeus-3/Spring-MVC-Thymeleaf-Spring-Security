package net.omar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.omar.entity.Product;
import net.omar.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductRepository productRepository;
    
    @GetMapping
    public String listProducts(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "id") String sortBy,
                             @RequestParam(defaultValue = "ASC") String sortDir,
                             @RequestParam(required = false) String search) {
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        
        Page<Product> productPage;
        
        if (search != null && !search.trim().isEmpty()) {
            productPage = productRepository.searchProducts(search, pageRequest);
            model.addAttribute("search", search);
        } else {
            productPage = productRepository.findAll(pageRequest);
        }
        
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("ASC") ? "DESC" : "ASC");
        
        // Pour les catégories dans le formulaire
        List<String> categories = productRepository.findAllCategories();
        model.addAttribute("categories", categories);
        
        return "products/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", productRepository.findAllCategories());
        return "products/form";
    }
    
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("categories", productRepository.findAllCategories());
            return "products/form";
        }
        
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("successMessage", 
            product.getId() == null ? "Produit créé avec succès!" : "Produit mis à jour avec succès!");
        
        return "redirect:/products";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        
        model.addAttribute("product", product);
        model.addAttribute("categories", productRepository.findAllCategories());
        return "products/form";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        
        productRepository.delete(product);
        redirectAttributes.addFlashAttribute("successMessage", "Produit supprimé avec succès!");
        
        return "redirect:/products";
    }
    
    @GetMapping("/view/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        
        model.addAttribute("product", product);
        return "products/view";
    }
    
    @GetMapping("/search")
    public String searchProducts(@RequestParam String keyword,
                               @RequestParam(required = false) String category,
                               @RequestParam(defaultValue = "0") int page,
                               Model model) {
        
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("name"));
        Page<Product> productPage;
        
        if (category != null && !category.isEmpty()) {
            productPage = productRepository.findByNameContainingIgnoreCaseAndCategory(keyword, category, pageRequest);
        } else {
            productPage = productRepository.searchProducts(keyword, pageRequest);
        }
        
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", productRepository.findAllCategories());
        
        return "products/search";
    }
}