package com.ecoproducto.cl.eco_producto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoproducto.cl.eco_producto.Model.Producto;
import com.ecoproducto.cl.eco_producto.Service.ProductoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProd(@PathVariable Long id){
        Producto producto =productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();            
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> guardarProd(@RequestBody Producto producto) {
        Producto prodNuevo = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(prodNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto>actualizarProd(@PathVariable Long id, @RequestBody Producto producto) {
        Producto existente = productoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        
        existente.setNombre(producto.getNombre());
        existente.setDescripcion(producto.getDescripcion());
        existente.setPrecio(producto.getPrecio());
        existente.setStock(producto.getStock());

        Producto prodActualizado = productoService.save(producto);
        return ResponseEntity.ok(prodActualizado);
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProd(@PathVariable Long id){
        try {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
            
        }
    }
    
    


    
    

}
