package com.epam.restclient;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by yauhen_yemelyanau on 7/27/17.
 */
public class URIBuilder {

    private String host;
    private int port;
    private String query;
    private String queryParams;

    public URI build() throws URISyntaxException {

        return new URI("http", "", host, port, query, "", "");
    }

    private URIBuilder() {

    }

    public static URIBuilder get() {
        return new URIBuilder();
    }

    public URIBuilder withHost(final String host) {
        this.host = host;
        return this;
    }

    public URIBuilder withPort(final int port) {
        this.port = port;
        return this;
    }

    public URIBuilder withQuery(final String query) {
        this.query = query;
        return this;
    }

    public URIBuilder withQueryParams(final String queryParams) {
        this.queryParams = queryParams;
        return this;
    }
}
