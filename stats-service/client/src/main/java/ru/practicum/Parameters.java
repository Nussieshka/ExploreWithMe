package ru.practicum;

import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class Parameters {
    private final Map<String, Object> parameters = new HashMap<>();

    public Parameters addParameter(String key, Object value) {
        parameters.put(key, value);
        return this;
    }

    public static Parameters getInstance() {
        return new Parameters();
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    public Map<String, Object> get() {
        return parameters;
    }

    public String getPath(String prefix) {
        StringBuilder pathBuilder = new StringBuilder(prefix + "?");

        for (Map.Entry<String, Object> entry: parameters.entrySet()) {
            Object value = entry.getValue();
            String key = entry.getKey();

            pathBuilder.append(key).append('=');

            if (value instanceof Iterable<?>) {
                Iterable<?> iterable = (Iterable<?>) value;

                for (Object obj : iterable) {
                    pathBuilder.append(obj).append(',');
                }

                pathBuilder.deleteCharAt(pathBuilder.length() - 1);
            } else {
                pathBuilder.append('{').append(key).append('}');
            }

            pathBuilder.append('&');
        }

        pathBuilder.deleteCharAt(pathBuilder.length() - 1);
        return pathBuilder.toString();
    }
}

