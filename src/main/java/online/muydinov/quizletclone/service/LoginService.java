package online.muydinov.quizletclone.service;

import online.muydinov.quizletclone.record.LoginRequestRecord;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AuthenticationManager authManager;
    private final JWTService jwtService;

    public LoginService(AuthenticationManager authManager, JWTService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public String verify(LoginRequestRecord LoginRequestRecord) {
        try {
            authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(LoginRequestRecord.username(), LoginRequestRecord.password()));

            return jwtService.generateToken(LoginRequestRecord.username());
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
