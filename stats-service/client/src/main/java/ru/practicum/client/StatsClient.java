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
import ru.practicum.library.Stats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public ResponseEntity<Void> hit(String app, String uri, String ip, LocalDateTime timestamp) {
        return post("/hit", new Request(null, app, uri, ip, timestamp), Void.class);
    }

    public List<Stats> stats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean isUnique) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Parameters parameters = Parameters.getInstance()
                .addParameter("start", start.format(dateTimeFormatter))
                .addParameter("end", end.format(dateTimeFormatter))
                .addParameter("unique", isUnique);

        if (uris != null && !uris.isEmpty()) {
            parameters.addParameter("uris", uris);
        }

        return Arrays.asList(Objects.requireNonNull(
                get(parameters.getPath("/stats"), parameters, Stats[].class).getBody()));
    }

    public List<Stats> stats(List<String> uris, Boolean isUnique) {
        return stats(LocalDateTime.of(1984, 1, 1, 0, 0),
                LocalDateTime.of(2222, 2, 2, 2, 2), uris, isUnique);
    }
}
