package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemEntity {
    private String code;
    private String description;
    private Double unitPrice;
    private Integer qtyOnHand;
}
