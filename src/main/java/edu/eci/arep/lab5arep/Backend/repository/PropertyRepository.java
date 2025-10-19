package edu.eci.arep.lab5arep.Backend.repository;


import edu.eci.arep.lab5arep.Backend.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>{

}
