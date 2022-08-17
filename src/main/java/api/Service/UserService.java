package api.Service;

import api.Model.User;
import api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser() {
        List<User> userRecordList = new ArrayList<>();
        userRepository.findAll().forEach(userRecordList::add);
        return userRecordList;
    }

    public void addUser(User userRecord) {
        userRepository.save(userRecord);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public User findUser(String u, String p) {
        return userRepository.findByUsernameAndPassword(u, p);
    }

    public User findUserById(String id) {
        return userRepository.findUserById(Integer.parseInt(id));
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

//    public UserDetails loginUser(String username) {
//        User u = userRepository.findUserByUsername(username);
//        if (u != null) {
//            List<GrantedAuthority> grantList = new ArrayList<>();
//            GrantedAuthority authority = new SimpleGrantedAuthority("USER");
//            grantList.add(authority);
//            UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, u.getPassword(), grantList);
//            return userDetails;
//        } else new UsernameNotFoundException("Not found username");
//        return null;
//    }
    public User findUserByWalletIdAndOtp(String walletId, String otp)
    {
        return userRepository.findUserByWalletIdAndOtp(walletId, otp);
    }

}
