package com.ecoproducto.cl.eco_producto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoInfoDTO {
    
    private String nombre;
    private double precio;
    private int stock; 

}
    