package edu.eci.arep.lab5arep.Backend.service;

import edu.eci.arep.lab5arep.Backend.exception.ResourceNotFoundException;
import edu.eci.arep.lab5arep.Backend.model.Property;
import edu.eci.arep.lab5arep.Backend.repository.PropertyRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public Property createPro(Property property) {
        return propertyRepository.save(property);
    }

    public List<Property> getAllPro() {
        return propertyRepository.findAll();
    }

    public Property getProByIdEntity(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propiedad no encontrada con ID: " + id));
    }

    public Property updatePro(Long id, Property details) {
        Property property = getProByIdEntity(id);
        property.setAddress(details.getAddress());
        property.setPrice(details.getPrice());
        property.setSize(details.getSize());
        property.setDescription(details.getDescription());
        return propertyRepository.save(property);
    }

    public void deletePro(Long id) {
        Property property = getProByIdEntity(id);
        propertyRepository.delete(property);
    }
}
