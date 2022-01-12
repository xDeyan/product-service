package bg.dyurukov.product.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {

  @Id @GeneratedValue private Long id;

  private String name;

  private String category;

  private String description;

  private int quantity;

  @CreatedDate private LocalDate createdOn;

  @LastModifiedDate private LocalDate modifiedOn;

  public void order(int quantity) {
    if (quantity > this.quantity) {
      throw new IllegalArgumentException("Order quantity cannot exceed the available quantity.");
    }
    this.quantity -= quantity;
  }
}
