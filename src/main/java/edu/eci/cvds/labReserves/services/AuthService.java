package edu.eci.cvds.labReserves.services;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.dto.AuthRequest;
import edu.eci.cvds.labReserves.dto.TokenResponse;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import edu.eci.cvds.labReserves.security.CustomUserDetailsService;
import edu.eci.cvds.labReserves.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserMongoRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public TokenResponse register(User user) throws LabReserveException {
        try{
            UserMongodb userMongo = new UserMongodb(user);
            userMongo.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(userMongo);
            String jwtToken = jwtUtil.generateToken(userMongo);
            String refreshToken = jwtUtil.generateRefreshToken(userMongo);
            return  new TokenResponse(jwtToken, refreshToken);

        } catch(Exception e){
            throw new LabReserveException("Error al crear el usuario: " + e.getMessage());
        }
    }

    public TokenResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserEmail(),
                        authRequest.getPassword()
                )
        );
        UserMongodb userMongodb = userRepo.findByMail(authRequest.getUserEmail());
        String token = jwtUtil.generateToken(userMongodb);
        String refreshToken = jwtUtil.generateRefreshToken(userMongodb);
        return new TokenResponse(token, refreshToken);
    }


}
