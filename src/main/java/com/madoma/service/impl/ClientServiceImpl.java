package com.madoma.service.impl;

import com.madoma.entity.Client;
import com.madoma.repository.ClientRepository;
import com.madoma.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void createCrops(String clientName,
                            String clientEmail,
                            String clientPhone,
                            String specialist,
                            String day,
                            String time) {

        Client client = new Client();
        client.setName(clientName);
        client.setEmail(clientEmail);
        client.setPhoneNumber(clientPhone);
        client.setSpecialistName(specialist);
        client.setDay(day);
        client.setTime(time);
        clientRepository.save(client);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getUserByUsername(String email) {
        return clientRepository.findByEmail(email);
    }
}
