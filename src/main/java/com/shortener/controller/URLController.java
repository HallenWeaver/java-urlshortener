package com.shortener.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shortener.common.IDConverter;
import com.shortener.common.URLValidator;
import com.shortener.service.URLConverterService;

@RestController
public class URLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLController.class);
    private final URLConverterService urlConverterService;
    
    public URLController(URLConverterService urlConverterService) {
		this.urlConverterService = urlConverterService;
    }

    @RequestMapping(value = "/shortener", method=RequestMethod.POST, consumes = {"application/json"})
    public String shortenUrl(@RequestBody @Valid final ShortenRequest shortenRequest, HttpServletRequest request) throws Exception {
        LOGGER.info("Received url to shorten: " + shortenRequest.getUrl());
        String longURL = shortenRequest.getUrl();
        if (URLValidator.INSTANCE.validateURL(longURL)) {
            String localURL = request.getRequestURL().toString();
            LOGGER.info("Current params: " + localURL + " and " + longURL);
            LOGGER.info(urlConverterService.toString());
            String shortenedUrl = urlConverterService.shortenURL(localURL, longURL);
            LOGGER.info("Shortened url to: " + shortenedUrl);
            return shortenedUrl;
        }
        throw new Exception("Please enter a valid URL");
    }

    @RequestMapping(value = "/{id}", method=RequestMethod.GET)
    public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
        LOGGER.info("Received shortened url to redirect: " + id);
        String redirectUrlString = urlConverterService.getLongURLFromID(id);
        urlConverterService.setMetricsValue(IDConverter.getDictionaryKeyFromUniqueID(id));
        LOGGER.info("Original URL: " + redirectUrlString);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrlString);
        return redirectView;
    }
    
    @RequestMapping(value = "/metrics/{id}", method=RequestMethod.GET)
    public String metricsUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
        LOGGER.info("Received id for metrics: " + id);
        String urlMetricsString = urlConverterService.getURLmetricsFromID(IDConverter.getDictionaryKeyFromUniqueID(id));
        LOGGER.info("Metrics: " + urlMetricsString);
        return urlMetricsString;
    }
}

class ShortenRequest{
    private String url;

    @JsonCreator
    public ShortenRequest() {

    }

    @JsonCreator
    public ShortenRequest(@JsonProperty("url") String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}