package edu.eci.arep.lab5arep.Backend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import edu.eci.arep.lab5arep.Backend.model.Property;
import edu.eci.arep.lab5arep.Backend.service.PropertyService;
import org.springframework.http.ResponseEntity;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public Property create(@RequestBody Property property){
        return propertyService.createPro(property);
    }

    @GetMapping
    public List<Property> getAll(){
        return propertyService.getAllPro();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getProByIdEntity(@PathVariable Long id){
        return ResponseEntity.ok(propertyService.getProByIdEntity(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Property> updatePro(@PathVariable Long id, @RequestBody Property details){
        return ResponseEntity.ok(propertyService.updatePro(id,details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePro(@PathVariable Long id){
        propertyService.deletePro(id);
        return ResponseEntity.noContent().build();
    }

}
