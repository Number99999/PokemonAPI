package api.Service;

import api.Model.Pokemon;
import api.Repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;
    public List<Pokemon> getAllPokemon()
    {
        List<Pokemon> list = new ArrayList<>();
        pokemonRepository.findAll().forEach(list::add);
        return list;
    }
}

