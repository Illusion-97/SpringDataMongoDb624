package fr.dawan.springdatamongodb.typed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Size {
    private double h;
    private double w;
    private String uom;
}
