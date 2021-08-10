package uz.usmonov.firstsecuretyapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.usmonov.firstsecuretyapp.Repository.ProductRepository;
import uz.usmonov.firstsecuretyapp.entity.Product;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    ProductRepository productRepository;

    @PreAuthorize(value ="hasAuthority('READ_ALL_PRODUCT')")
    @GetMapping()
    public HttpEntity<?> getProduct(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); ///sorov kelganda bu kimdan kelganini qanday huquqlar borligini bildiradi
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody Product product){
        return ResponseEntity.ok(productRepository.save(product));
    }

//    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PreAuthorize(value="hasAuthority('EDIT_PRODUCT')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody Product product,@PathVariable Integer id){
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product editingProduct = productOptional.get();
            editingProduct.setName(product.getName());
            Product save = productRepository.save(editingProduct);
            return ResponseEntity.ok(save);
        }
        return ResponseEntity.notFound().build();
    }
//    @PreAuthorize(value = "hasRole('DIRECTOR')").
    @PreAuthorize(value = "hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

//    @PreAuthorize(value = "hasAnyRole('MANAGER','DIRECTOR','WORKER')")
    @PreAuthorize(value = "hasAuthority('READ_ONE_PRODUCT')")
    @GetMapping("/{id}")
    public HttpEntity<?> get(@PathVariable Integer id){
        Optional<Product> product = productRepository.findById(id);
        return ResponseEntity.status(product.isPresent()?200:404).body(product.orElse(null));
    }
}
