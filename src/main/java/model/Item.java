package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    private String code;
    private String description;
    private Double unitPrice;
    private Integer qtyOnHand;
}
