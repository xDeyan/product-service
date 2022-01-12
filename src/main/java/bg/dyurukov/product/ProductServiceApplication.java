package bg.dyurukov.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import bg.dyurukov.product.repository.ProductRepository;

@EnableJpaAuditing
@SpringBootApplication
public class ProductServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductServiceApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(ProductRepository productRepository) {
    return args -> {
      productRepository.findAll().forEach(System.out::println);
    };
  }
}
