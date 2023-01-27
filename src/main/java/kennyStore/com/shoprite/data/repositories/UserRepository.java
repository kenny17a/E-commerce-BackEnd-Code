package kennyStore.com.shoprite.data.repositories;

import jakarta.transaction.Transactional;
import kennyStore.com.shoprite.data.models.User;
import kennyStore.com.shoprite.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findByUsername(String name);
    @Modifying
    @Transactional
    @Query("update User user set user.role = ?2 where user.username = ?1")
    void updateUserRole(String username,  Role role);
}
