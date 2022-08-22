package com.example.SalesManagement.Service;

import com.example.SalesManagement.Model.LoginData;
import com.example.SalesManagement.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;
    public List<LoginData> getUsers()
    {
        System.out.println("AdminLoginRepository is Triggered");
        List<LoginData> admins = loginRepository.findAll();
        System.out.println("admins "+admins);
        return admins;
    }


}
