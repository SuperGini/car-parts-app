package com.gini.security.filters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Add headers to request: <a href="https://medium.com/@vaibhavpithadiya09/how-to-add-a-custom-header-to-an-http-request-in-java-82aeb944a81e"> Link</a>
 *
 *
 * */

public class RequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        customHeaders = new HashMap<>();
    }


    public void addHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        return this.customHeaders.containsKey(name) ? this.customHeaders.get(name) : super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return this.customHeaders.containsKey(name)
                ? Collections.enumeration(Collections.singletonList(this.customHeaders.get(name)))
                : super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        var allHeaders = Collections.list(super.getHeaderNames());
        allHeaders.addAll(this.customHeaders.keySet());
        return Collections.enumeration(allHeaders);
    }




}
