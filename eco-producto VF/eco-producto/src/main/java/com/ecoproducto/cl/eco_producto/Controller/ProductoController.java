package com.ecoproducto.cl.eco_producto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoproducto.cl.eco_producto.DTO.ProductoInfoDTO;
import com.ecoproducto.cl.eco_producto.Model.Producto;
import com.ecoproducto.cl.eco_producto.Service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Lista todos los productos")
    @ApiResponse(responseCode = "200", description = "Listado exitoso",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class)))

    @GetMapping //listado generarl de todos los productos
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Busca un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })

    @GetMapping("/{id}") //Busca el producto
    public ResponseEntity<Producto> buscarProd(@PathVariable Long id){
        Producto producto =productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();            
        }
        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Guarda un nuevo producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente", content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping //Guarda un producto
    public ResponseEntity<Producto> guardarProd(@RequestBody Producto producto) {
        Producto prodNuevo = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(prodNuevo);
    }

    @Operation(summary = "Actualiza un producto existente")

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

        Producto prodActualizado = productoService.save(existente);
        return ResponseEntity.ok(prodActualizado);
        
    }

    @Operation(summary = "Elimina un producto por su ID")

    @DeleteMapping("/{id}") //Eliminar un producto a traves de su id
    public ResponseEntity<?> eliminarProd(@PathVariable Long id){
        try {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
            
        }
    }

    @Operation(summary = "Busca productos por su nombre")

    @GetMapping("/buscar/{nombre}") //Filtro de producto a traves de nombre
    public List<Producto> buscarporNombre(@PathVariable String nombre){
        return productoService.buscarporNombre(nombre);
    }

    @Operation(summary = "Busca productos por su categoría")
    
    @GetMapping("/categoria/{categoria}") //Filtro de producto a traves de categoria
    public List<Producto> buscarporCategoria(@PathVariable String categoria){
        return productoService.buscarporCategoria(categoria);
    }

    @Operation(summary = "Verifica disponibilidad de stock por su ID")

    @GetMapping("/{id}/disponibilidad") // Validando disponibilidad
    public  ResponseEntity<Boolean> tieneDisponibilidad(@PathVariable Long id){
        Producto producto = productoService.findById(id);
        if (producto != null) {
            return ResponseEntity.ok(producto.getStock()>0);            
        } else {
            return ResponseEntity.notFound().build();
            
        }
    }

    @Operation(summary = "Proporciona Información necesaria de un producto")

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
