package com.madoma.service;

import com.madoma.entity.Client;
import org.springframework.web.bind.annotation.RequestParam;

public interface ClientService {
    void createCrops(String clientName,
                     String clientEmail,
                     String clientPhone,
                     String specialist,
                     String day,
                     String time);
}
