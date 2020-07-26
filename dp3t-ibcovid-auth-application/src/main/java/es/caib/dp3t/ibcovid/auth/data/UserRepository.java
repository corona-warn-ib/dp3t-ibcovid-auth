package es.caib.dp3t.ibcovid.auth.data;

import es.caib.dp3t.ibcovid.auth.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndActiveIsTrue(final String username);

}
