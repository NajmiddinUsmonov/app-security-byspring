package uz.usmonov.firstsecuretyapp.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.usmonov.firstsecuretyapp.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
