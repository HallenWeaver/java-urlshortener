package com.shortener.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.RedirectView;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;

import com.shortener.service.URLConverterService;

@RunWith(SpringRunner.class)
@WebMvcTest(URLController.class)
class URLControllerTest {
	
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private URLConverterService urlConverterService;
    
	public HttpServletRequest httpServletRequest;
	public HttpServletResponse httpServletResponse;
	
	@BeforeEach
	public void setUp() {
		httpServletRequest = mock(HttpServletRequest.class);
		httpServletResponse = mock(HttpServletResponse.class);
	}
	
	@Test
	public void TestRedirectURL() throws Exception{
		String id = "a";
		String testURL = "http://www.test.com";
		
		given(this.urlConverterService.getLongURLFromID(id)).willReturn(testURL);
		
		mvc.perform(get("/" + id)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().is3xxRedirection());
		verify(urlConverterService, VerificationModeFactory.times(1)).getLongURLFromID(id);
        reset(urlConverterService);
    }
	
	@Test
	public void TestMetricsURL() throws Exception{
		long id = 2;
		String urlMetrics = "2";
		
		given(this.urlConverterService.getURLmetricsFromID(id)).willReturn(urlMetrics);
		
		mvc.perform(get("/metrics/" + id)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().is2xxSuccessful());
        reset(urlConverterService);
    }
	
	@Test
	public void TestShortenRequest() throws Exception{
		String url = "http://www.abc.com";
		ShortenRequest shortenRequest = new ShortenRequest(url);
		Assertions.assertEquals(shortenRequest.getUrl(), url);
    }

}
