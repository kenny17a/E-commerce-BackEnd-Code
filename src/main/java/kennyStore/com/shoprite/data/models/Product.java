package kennyStore.com.shoprite.data.models;

import jakarta.persistence.*;
import kennyStore.com.shoprite.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "product_unitPrice", nullable = false)
    private BigDecimal unitPrice;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    @Column(name = "Product_Category", nullable = false)
    private Category category;
    @CreationTimestamp
    @Column(name = "created_time")
    private Instant createdTime = Instant.now();
    @UpdateTimestamp
    @Column(name = "updated_time")
    private Instant updatedTime = Instant.now();

}
