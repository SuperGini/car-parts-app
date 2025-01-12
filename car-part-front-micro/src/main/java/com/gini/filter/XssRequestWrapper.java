package com.gini.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.json.JsonSanitizer;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
//import org.owasp.encoder.Encode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//import org.owasp.html.HtmlPolicyBuilder;



//https://github.com/OWASP/java-html-sanitizer#getting-started
public class XssRequestWrapper extends HttpServletRequestWrapper {

    private String body = null;

    private ObjectMapper mapper = new ObjectMapper();

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
//        if (values == null) return null;
//
//        var x = new ArrayList<String>();
//        Arrays.stream(values).forEach(v -> x.add(sanitizeInput(v)));
//        return x.toArray(new String[0]);

        return values;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // Get the original input stream from the wrapped request
        ServletInputStream originalInputStream = super.getInputStream();

        // Read the entire request body into a String
        String requestBody = new String(originalInputStream.readAllBytes());
//        String requestBody = mapper.readValue(originalInputStream, JSON.class);

        // Sanitize the JSON body using the sanitizeInput method
//        String sanitizedBody = sanitizeInput(requestBody);

       var sanitizedBody =  JsonSanitizer.sanitize(requestBody);




        // Create a new ServletInputStream with the sanitized body
        return new ServletInputStream() {
            private final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    sanitizedBody.getBytes()
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
//        return Encode.forHtml(input);
        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowCommonBlockElements().disallowAttributes("div").onElements("div")
                .allowStandardUrlProtocols()
//                .allowCommonBlockElements()
                .allowElements("a")
                .allowElements("{", "}")
                .allowUrlProtocols("https")
//                .allowAttributes("href").onElements("a")
                .requireRelNofollowOnLinks()
                .allowUrlProtocols()
                .toFactory();

        String safeHTML = policy.sanitize(input);

        return safeHTML;
    }

}