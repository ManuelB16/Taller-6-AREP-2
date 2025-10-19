package edu.eci.arep.lab5arep.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.eci.arep.lab5arep.Backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
