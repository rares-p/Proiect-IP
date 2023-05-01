package HttpRequestTestClient;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Connection {
    private final String BASE_URL = "http://localhost:5000";
    private final HttpClient client;
    private final Gson gson = new Gson();

    public Connection() {
        client = HttpClient.newBuilder().build();
    }

    public HttpResponse<String> get(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + path))
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> post(String path, List<Pair<String, String>> pairs) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + path))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(pairs)))
                .header("Content-Type", "application/json")
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> delete(String path, String lobbyId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + path + "/" + lobbyId))
                .DELETE()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
