package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.record.LoginRequestRecord;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authManager;
    private final JWTService jwtService;

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
