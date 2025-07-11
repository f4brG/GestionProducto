package com.ecoproducto.cl.eco_producto.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoproducto.cl.eco_producto.Model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByCategoriaIgnoreCase(String categoria);

}
