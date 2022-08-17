package api.Controller;

import io.jsonwebtoken.Claims;
import api.JWT.PokemonJWT;
import api.JWT.UserJWT;
import api.Model.Pokemon;
import api.Model.User;
import api.Service.PokemonService;
import api.Service.SendMailService;
import api.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.*;

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
        return UserJWT.creatJWT(username);
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public String addUser(@RequestParam(name = "username") String username,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "walletId") String walletId) {
        User u = new User(walletId, username, password);
        if (userService.findUserById(walletId) == null && userService.findUserByUsername(username) == null) {
            userService.addUser(u);
            return "done";
        }
        return "username or walletId was constained";
    }


    @RequestMapping(value = "/get-pokemon", method = RequestMethod.GET)
    public User RefreshToken(@RequestHeader(name = "acc") String acc) {
        String username=UserJWT.reJWT(acc).getSubject();
        return userService.findUserByUsername(username);
    }

    @RequestMapping(value = "/account-asset", method = RequestMethod.GET)
    public User account(@RequestHeader(value = "acc") String acceptHeader) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
        User user = userService.findUserByUsername(UserJWT.reJWT(acceptHeader).getSubject());
//
//
//
        List<Pokemon> pokemonList = pokemonService.getAllPokemon();
        List<Pokemon> pokemonSet = new ArrayList<>();
        for(Pokemon p: pokemonList)
        {
            pokemonSet.add(p);
        }
//        user.setListPokemon(pokemonSet);

        userService.deleteById(user.getId());
        userService.addUser(user);
        return user;
    }

    @RequestMapping(value = "/refresh-token", method = RequestMethod.GET)
    public List<String> tokenPokemon() {
        List<Pokemon> listPokemon = pokemonService.getAllPokemon();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < listPokemon.size(); i++) {
//            PokemonJWT p = new  PokemonJWT();
            list.add(PokemonJWT.createJWT(listPokemon.get(i)));
        }
        return list;
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withDraw(@RequestParam(name = "walletId") String walletId,
                           @RequestParam(name = "otp") String otp,
                           @RequestHeader(name = "acc") String acceptHeader) {
        if(UserJWT.validateToken(acceptHeader)==false)
        {
            return "login quá hạn";
        }

        User user = userService.findUserByWalletIdAndOtp(walletId, otp);
        String s = "Coin: " + user.getCoin() + " Subcoin: " + user.getSubcoin();
        return s;
    }

    @GetMapping("/check-online")
    public String checkOnline(@RequestHeader(name = "acc") String acceptHeader) {
        if(UserJWT.validateToken(acceptHeader)==false)
        {
            return "login quá hạn";
        }
        return "true";
    }

    @RequestMapping(value = "/update-info", method = RequestMethod.POST)
    public String updateInfo(@RequestParam(name = "email") String email,
                             @RequestParam(name = "phone") String phone,
                             @RequestHeader(name = "acc") String acceptHeader) {
        if (email.length() == 0) return "email requied";
        if(UserJWT.validateToken(acceptHeader)==false)
        {
            return "login quá hạn";
        }
        String username = UserJWT.reJWT(acceptHeader).getSubject();

        User user = userService.findUserByUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        userService.deleteById(user.getId());
        userService.addUser(user);
        return user.toString();
    }

    @RequestMapping(value = "/get-otp", method = RequestMethod.GET)
    public String opt(@RequestHeader(name = "acc") String acceptHeader) {
        if(UserJWT.validateToken(acceptHeader)==false)
        {
            return "login quá hạn";
        }
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        String username = UserJWT.reJWT(acceptHeader).getSubject();
        User user = userService.findUserByUsername(username);


        service.sendSimpleEmail(user.getEmail(),
                "Your OTP is: " + code,
                "OTP UNIPET");

        user.setOtp(String.valueOf(code));
        userService.deleteById(user.getId());
        userService.addUser(user);
        return "OTP: " + code + " sent to your email";
    }
}
