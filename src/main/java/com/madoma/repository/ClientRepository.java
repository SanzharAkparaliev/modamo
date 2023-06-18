package com.madoma.repository;

import com.madoma.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    List<Client> findByEmail(String email);
}
