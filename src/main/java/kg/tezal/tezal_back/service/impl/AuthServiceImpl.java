package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.apicontroller.ClientDeviceRestController;
import kg.tezal.tezal_back.entity.ClientDevice;
import kg.tezal.tezal_back.service.AuthService;
import kg.tezal.tezal_back.service.security.jwt.JwtResponse;
import kg.tezal.tezal_back.service.security.jwt.JwtTokenUtil;
import kg.tezal.tezal_back.service.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ClientDeviceRestController clientDeviceRestController;


    @Override
    public ResponseEntity<JwtResponse> login(@RequestBody JwtUser form) {
        ClientDevice user = clientDeviceRestController.findByPhone(form.getUsername());

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + form.getUsername() + " not found");
        }
        System.out.println(form.getPassword());
        System.out.println(user.getPassword());
        JwtResponse jwtResponse = new JwtResponse();
        if (form.getUsername().equals(user.getPhoneNumber()) && form.getPassword().equals(user.getPassword())){
            jwtResponse.setDeviceId(user.getId());
            jwtResponse.setClientId(user.getClient().getId());
            jwtResponse.setToken(jwtTokenUtil.generateToken(form.getUsername()));
            return ResponseEntity.accepted().body(jwtResponse);
        } else {
            return ResponseEntity.badRequest().body(jwtResponse);
        }
    }
}

