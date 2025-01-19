package com.gini.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//import org.owasp.html.HtmlPolicyBuilder;


//https://github.com/OWASP/java-html-sanitizer#getting-started
//https://medium.com/@singhvirajat1/preventing-xss-attack-in-spring-boot-application-4118860ed3c5
//https://stackoverflow.com/questions/50857625/java-and-xss-how-to-html-escape-a-json-string-to-protect-against-xss
public class XssRequestWrapper extends HttpServletRequestWrapper {

    private final ObjectMapper mapper;

    public XssRequestWrapper(HttpServletRequest request, ObjectMapper mapper) {
        super(request);
        this.mapper = mapper;
    }

    @Override //sanitize parameters values
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) return null;

        var x = new ArrayList<String>();
        Arrays.stream(values).forEach(v -> x.add(sanitizeInput(v)));
        return x.toArray(new String[0]);

    }

    @Override //sanitize request body
    public ServletInputStream getInputStream() throws IOException {
        // Get the original input stream from the wrapped request
        ServletInputStream originalInputStream = super.getInputStream();

        // Read the entire request body into a String
        String requestBody = new String(originalInputStream.readAllBytes());
        JsonNode jsonNode = mapper.readTree(requestBody);

        sanitizeJsonNode(jsonNode);
        String sanitizeBody = mapper.writeValueAsString(jsonNode);

        // Create a new ServletInputStream with the sanitized body
        return new ServletInputStream() {
            private final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    sanitizeBody.getBytes()
            );

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // No implementation needed for this example
            }
        };
    }

    private String sanitizeInput(String input) {

        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowCommonBlockElements().disallowAttributes("div").onElements("div")
                .allowStandardUrlProtocols()
//                .allowCommonBlockElements()
                .allowElements("a")
                .allowUrlProtocols("https")
//                .allowAttributes("href").onElements("a")
                .requireRelNofollowOnLinks()
                .allowUrlProtocols()
                .toFactory();

        String safeHTML = policy.sanitize(input);

        return safeHTML;
    }

    private void sanitizeJsonNode(JsonNode node) {
        if (node.isObject())
            sanitizeNodeObject((ObjectNode) node);

        if (node.isArray())
            sanitizeNodeArray((ArrayNode) node);
    }

    private void sanitizeNodeArray(ArrayNode node) {
        for (int i = 0; i < node.size(); i++) {

            JsonNode jsonNode = node.get(i);
            if (jsonNode.isObject()) {
                sanitizeJsonNode(jsonNode);
            }

            if (jsonNode.isTextual()) {
                node.set(i, sanitizeInput(jsonNode.textValue()));
            }
        }
    }

    private void sanitizeNodeObject(ObjectNode node) {
        node.fields().forEachRemaining(entry -> {

            JsonNode valueNode = entry.getValue();
            if (valueNode.isTextual()) {
                node.put(entry.getKey(), sanitizeInput(valueNode.textValue()));
            }

            if (valueNode.isObject() || valueNode.isArray()) {
                sanitizeJsonNode(valueNode);
            }
        });
    }


//    private JsonElement sanitize(JsonElement element) {
//
//        //sanitize normal values
//        if(element.isJsonPrimitive()) {
//            JsonPrimitive primitive = element.getAsJsonPrimitive();
//
//            if(primitive.isString()) {
//              return new JsonPrimitive(sanitizeInput(primitive.getAsString()));
//            }
//
//            return primitive;
//        }
//
//        //sanitize arrays in json
//        if (element.isJsonArray()) {
//            JsonArray cleanArray = new JsonArray();
//            element.getAsJsonArray().forEach(el -> cleanArray.add(sanitize(el)));
//            return cleanArray;
//        }
//
//        //sanitize objects in json recursively
//        JsonObject obj = element.getAsJsonObject();
//        JsonObject clean = new JsonObject();
//
//        obj.entrySet().forEach(entry -> {
//            clean.add(entry.getKey(), sanitize(entry.getValue()));
//        });
//
//        return clean;
//
//    }


}