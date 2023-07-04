package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.Parameters;
import ru.practicum.library.Request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> hit(String app, String uri, String ip, LocalDateTime timestamp) {
        return post("/hit", new Request(null, app, uri, ip, timestamp));
    }

    public ResponseEntity<Object> stats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean isUnique) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Parameters parameters = Parameters.getInstance()
                .addParameter("start", start.format(dateTimeFormatter))
                .addParameter("end", end.format(dateTimeFormatter))
                .addParameter("unique", isUnique);

        if (uris != null && !uris.isEmpty()) {
            parameters.addParameter("uris", uris);
        }

        return get(parameters.getPath("/stats"), parameters);
    }

}
