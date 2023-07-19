package ru.practicum.client;

import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.Parameters;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected <V> ResponseEntity<V> get(String path, @Nullable Parameters parameters, Class<V> clazz) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null, clazz);
    }

    protected <T, V> ResponseEntity<V> post(String path, T body, Class<V> clazz) {
        return post(path, null, body, clazz);
    }


    protected <T, V> ResponseEntity<V> post(String path, @Nullable Parameters parameters, T body, Class<V> clazz) {
        return makeAndSendRequest(HttpMethod.POST, path, parameters, body, clazz);
    }

    private <T, V> ResponseEntity<V> makeAndSendRequest(HttpMethod method, String path,
                                                          @Nullable Parameters parameters, @Nullable T body, Class<V> clazz) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());

        ResponseEntity<V> shareitServerResponse;
        try {
            if (parameters != null && !parameters.isEmpty()) {
                shareitServerResponse = rest.exchange(path, method, requestEntity, clazz, parameters.get());
            } else {
                shareitServerResponse = rest.exchange(path, method, requestEntity, clazz);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
        return prepareGatewayResponse(shareitServerResponse);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static <V> ResponseEntity<V> prepareGatewayResponse(ResponseEntity<V> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
