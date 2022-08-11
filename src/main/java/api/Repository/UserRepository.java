package api.Repository;

import api.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUsernameAndPassword(String username, String password);
    User findUserById(int id);
    User findUserByUsername(String username);
    User findUserByWalletIdAndOtp(String wallletId, String otp);
    @Transactional
    void deleteById(int id);
}
