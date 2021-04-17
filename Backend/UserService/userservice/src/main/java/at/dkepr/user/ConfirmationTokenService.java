package at.dkepr.user;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private  ConfirmationTokenRepository repository;

    void saveConfirmationToken(ConfirmationToken token) {
        repository.save(token);
    }

    void deleteConfirmationToken(Long id) {
        repository.deleteById(id);
    }
    
}
