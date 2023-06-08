package com.madoma.service;

import com.madoma.entity.Client;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClientService {
    void createCrops(String clientName,
                     String clientEmail,
                     String clientPhone,
                     String specialist,
                     String day,
                     String time);

    public List<Client> getAll();
}
