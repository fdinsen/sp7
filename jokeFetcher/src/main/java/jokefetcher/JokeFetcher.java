package jokefetcher;

import com.google.gson.Gson;
import java.io.IOException;
import utils.HttpUtils;
import dto.ChuckDTO;
import dto.DadDTO;
import dto.JokesDTO;


public class JokeFetcher {
    public static void main(String[] args) throws IOException {
        Gson GSON = new Gson();
        
        String chuckURL = "https://api.chucknorris.io/jokes/random";
        String dadURL = "https://icanhazdadjoke.com";
        
        String chuck = HttpUtils.fetchData(chuckURL);
        String dad = HttpUtils.fetchData(dadURL);
        
        System.out.println("JSON fetched from chucknorris:");
        System.out.println(chuck);
        System.out.println("JSON fetched from dadjokes:");
        System.out.println(dad);
        
        ChuckDTO chuckDTO = GSON.fromJson(chuck, ChuckDTO.class);
        DadDTO dadDTO = GSON.fromJson(dad, DadDTO.class);
        
       
        JokesDTO jokesDTO = new JokesDTO(chuckDTO, dadDTO, chuckURL, dadURL);
        
        String combinedJSON = GSON.toJson(jokesDTO);
        System.out.println("Combined Joke:");
        System.out.println(combinedJSON);
    }
}
