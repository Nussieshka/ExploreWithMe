package ru.practicum.library;

import lombok.Data;

@Data
public class Stats {
    public Stats(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }

    private String app;
    private String uri;
    private Long hits;
}
