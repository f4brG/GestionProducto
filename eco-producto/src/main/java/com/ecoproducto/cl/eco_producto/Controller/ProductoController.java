package com.ecoproducto.cl.eco_producto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecoproducto.cl.eco_producto.DTO.ProductoInfoDTO;
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

    @GetMapping //listado generarl de todos los productos
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}") //Busca el producto
    public ResponseEntity<Producto> buscarProd(@PathVariable Long id){
        Producto producto =productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();            
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping //Envia datos 
    public ResponseEntity<Producto> guardarProd(@RequestBody Producto producto) {
        Producto prodNuevo = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(prodNuevo);
    }

    @PutMapping("/{id}") //Actualiza datos 
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

    @DeleteMapping("/{id}") //Eliminar un producto a traves de su id
    public ResponseEntity<?> eliminarProd(@PathVariable Long id){
        try {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
            
        }
    }

    @GetMapping("/buscar") //Filtro de producto a traves de nombre
    public List<Producto> buscarporNombre(@RequestParam String nombre){
        return productoService.buscarporNombre(nombre);
    }
    
    @GetMapping("/categoria") //Filtro de producto a traves de categoria
    public List<Producto> buscarporCategoria(@RequestParam String categoria){
        return productoService.buscarporCategoria(categoria);
    }

    @GetMapping("/{id}/disponibilidad") // Validando disponibilidad
    public  ResponseEntity<Boolean> tieneDisponibilidad(@PathVariable Long id){
        Producto producto = productoService.findById(id);
        if (producto != null) {
            return ResponseEntity.ok(producto.getStock()>0);            
        } else {
            return ResponseEntity.notFound().build();
            
        }
    }

    @GetMapping("/{id}/info") //Proporcionando datos 
    public ResponseEntity<ProductoInfoDTO> obtenerInfoProducto(@PathVariable Long id){
        Producto producto = productoService.findById(id);
        if (producto != null) {
            ProductoInfoDTO dto = new ProductoInfoDTO(
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock()
            );
            return ResponseEntity.ok(dto);
            
        } else {
            return ResponseEntity.notFound().build();
            
        }
    }
    


    
    

}
