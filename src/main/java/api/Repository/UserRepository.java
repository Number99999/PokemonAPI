package api.Repository;

import api.Model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUsernameAndPassword(String username, String password);
    User findUserById(int id);
    User findUserByUsername(String username);
    User findUserByWalletIdAndOtp(String wallletId, String otp);
    @Transactional
    void deleteById(int id);
}
