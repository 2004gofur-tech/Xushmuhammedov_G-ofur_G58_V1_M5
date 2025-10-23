package uz.pdp.carEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.brandEntity.Brand;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    private Integer id;
    private String name;
    private String year;
    private Integer price;
    private Brand brand;
}
