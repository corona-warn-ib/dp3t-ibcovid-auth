package es.caib.dp3t.ibcovid.auth.data;

import es.caib.dp3t.ibcovid.auth.data.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByTokenAndAndExpireAtGreaterThan(final String token, final LocalDateTime currentTime);

}
