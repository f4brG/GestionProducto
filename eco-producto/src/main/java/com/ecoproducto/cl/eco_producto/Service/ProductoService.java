package com.ecoproducto.cl.eco_producto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoproducto.cl.eco_producto.Model.Producto;
import com.ecoproducto.cl.eco_producto.Repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto findById(Long id){
        return productoRepository.findById(id).get();
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto prodActualizado){
        Producto prodExistente = productoRepository.findById(id).orElse(null);
        
        if (prodExistente == null) {
            return null;
        }

        prodExistente.setNombre(prodActualizado.getNombre());
        prodExistente.setPrecio(prodActualizado.getPrecio());
        prodExistente.setStock(prodActualizado.getStock());
        prodExistente.setDescripcion(prodActualizado.getDescripcion());
        prodExistente.setCategoria(prodActualizado.getCategoria());

        return productoRepository.save(prodExistente);
        
    }

    public void delete(Long id){
        productoRepository.deleteById(id);
    }

    public List<Producto> buscarporNombre(String nombre){
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    public List<Producto> buscarporCategoria(String categoria){
        return productoRepository.findByCategoriaIgnoreCase(categoria);
    }

    public Producto findbyId(Long id){
        return productoRepository.findById(id).orElse(null);
    }

    

    

}
