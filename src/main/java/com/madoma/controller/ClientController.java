package com.madoma.controller;

import com.madoma.entity.Client;
import com.madoma.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @GetMapping("/client")
    public String getClientController(Model model){
        List<Client> clients = clientService.getAll();
        model.addAttribute("title","Clients");
        model.addAttribute("clients",clients);
        return "/admin/client";
    }
}
