package api.Controller;

import api.Details.MyUserDetail;
import api.JWT.PokemonJWT;
import api.Model.Pokemon;
import api.Model.User;
import api.Service.PokemonService;
import api.Service.SendMailService;
import api.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    private SendMailService service;
    @Autowired
    private UserService userService;
    @Autowired
    private PokemonService pokemonService;

    @RequestMapping(value = "/sign-in", method = RequestMethod.GET)
    public String UserLogin(@RequestParam(name = "username") String username,
                            @RequestParam(name = "password") String password) {

        if (userService.findUser(username, password) != null) return "sign in accessful";
        return "wrong username or password " + password;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public String addUser(@RequestParam(name = "username") String username,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "walletId") String walletId) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = encoder.encode(password);
        User u = new User(walletId, username, pass);
        if (userService.findUserById(walletId) == null && userService.findUserByUsername(username) == null) {
            userService.addUser(u);
            return "done";
        }
        return "username or walletId was constained";
    }


    @RequestMapping(value = "/get-pokemon", method = RequestMethod.GET)
    public List<Pokemon> RefreshToke() {
        List<Pokemon> list = pokemonService.getAllPokemon();
        return list;
    }

    @RequestMapping(value = "/account-asset")
    public User account() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByUsername(username);

        List<Pokemon> pokemonList = pokemonService.getAllPokemon();
        Set<Pokemon> pokemonSet = new HashSet<>();
        for(Pokemon p: pokemonList)
        {
            pokemonSet.add(p);
        }
        user.setListPokemon(pokemonSet);

        userService.deleteById(user.getId());
        userService.addUser(user);
        return user;
    }

    @RequestMapping(value = "/refresh-token", method = RequestMethod.GET)
    public List<String> tokenPokemon() {
        List<Pokemon> listPokemon = pokemonService.getAllPokemon();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < listPokemon.size(); i++) {
            PokemonJWT p = new PokemonJWT();
            list.add(p.createJWT(listPokemon.get(i)));
        }
        return list;
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withDraw(@RequestParam(name = "walletId") String walletId,
                           @RequestParam(name = "otp") String otp) {
        User user = userService.findUserByWalletIdAndOtp(walletId, otp);
        String s = "Coin: " + user.getCoin() + "<br>Subcoin: " + user.getSubcoin();
        return s;
    }

    @GetMapping("/check-online")
    public String checkOnline() {
        return "true";
    }

    @RequestMapping(value = "/update-info", method = RequestMethod.POST)
    public String updateInfo(@RequestParam(name = "email") String email,
                             @RequestParam(name = "phone") String phone) {
        if (email.length() == 0) return "email requied";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findUserByUsername(currentPrincipalName);
        user.setEmail(email);
        user.setPhone(phone);
        userService.deleteById(user.getId());
        userService.addUser(user);
        return user.toString();
    }

    @RequestMapping(value = "/get-otp", method = RequestMethod.GET)
    public String opt() {
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPricipalName = authentication.getName();
        User user = userService.findUserByUsername(currentPricipalName);


        service.sendSimpleEmail(user.getEmail(),
                "Your OTP is: " + code,
                "OTP UNIPET");

        user.setOtp(String.valueOf(code));
        userService.deleteById(user.getId());
        userService.addUser(user);
        return "OTP: " + code + " sent to your email";
    }
}
