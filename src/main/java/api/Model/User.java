package api.Model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
//@Data
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String username, password, role;
    private int rank, coin, subcoin, score, pets;
    private String phone, email, walletId;
    private String otp;
//    @ManyToMany
//    @JoinTable(name = "user_pokemon",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "pokemon_id"))
//    private List<Pokemon> listPokemon;

    public User() {

    }

//    public List<Pokemon> getListPokemon() {
//        return listPokemon;
//    }
//
//    public void setListPokemon(List<Pokemon> listPokemon) {
//        this.listPokemon = listPokemon;
//    }

    public User(String walletId, String username, String password) {
        this.username = username;
        this.password = password;
        this.walletId = walletId;
        rank = 999999;
        coin = 0;
        subcoin = 0;
        score = 0;
        pets = 0;
        role = "USER";
        phone = "";
        email = "";
        otp = "";
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getSubcoin() {
        return subcoin;
    }

    public void setSubcoin(int subcoin) {
        this.subcoin = subcoin;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPets() {
        return pets;
    }

    public void setPets(int pets) {
        this.pets = pets;
    }


    @Override
    public String toString() {
        return "username: " + username + " " + "rank: " + rank + " " + "coin: " + coin + "subcoin: " + subcoin + "pets: " + pets;
    }
}