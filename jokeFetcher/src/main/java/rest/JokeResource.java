package rest;

import com.google.gson.Gson;
import dto.ChuckDTO;
import dto.DadDTO;
import dto.JokeDTO;
import dto.JokesDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.HttpUtils;

/**
 * REST Web Service
 *
 * @author lam
 */
@Path("jokes")
public class JokeResource {

    ArrayList<String> URLS = new ArrayList();
    Gson GSON = new Gson();

    @Context
    private UriInfo context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJokes() throws InterruptedException, ExecutionException {
        URLS.add("https://api.chucknorris.io/jokes/random");
        URLS.add("https://icanhazdadjoke.com");

        ExecutorService es = Executors.newFixedThreadPool(2);
        List<Future<JokeDTO>> futures = new ArrayList();
        for (int i = 0; i < URLS.size(); i++) {
            Callable<JokeDTO> fetcher = new FetchJoke(URLS.get(i), i);
            Future future = es.submit(fetcher);
            futures.add(future);
        }

        ChuckDTO chuckDTO = (ChuckDTO) futures.get(0).get();
        DadDTO dadDTO = (DadDTO) futures.get(1).get();
        

        JokesDTO jokes = new JokesDTO(chuckDTO, dadDTO, URLS.get(0), URLS.get(1));

        return Response.ok().entity(GSON.toJson(jokes)).build();
    }

}

class FetchJoke implements Callable<JokeDTO> {

    String URL;
    int index;
    Gson GSON = new Gson();

    public FetchJoke(String URL, int index) {
        this.URL = URL;
        this.index = index;
    }

    @Override
    public JokeDTO call() throws Exception {
        String data = HttpUtils.fetchData(URL);
        //JokeDTO dto;
        switch (index) {
            case 0:
                return GSON.fromJson(data, ChuckDTO.class);
            case 1:
                return GSON.fromJson(data, DadDTO.class);
            default:
                return null;
        }
    }

}
