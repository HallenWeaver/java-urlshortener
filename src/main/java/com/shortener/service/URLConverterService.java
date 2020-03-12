package com.shortener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shortener.common.IDConverter;
import com.shortener.repository.URLRepository;

@Service
public class URLConverterService {
	private static final Logger LOGGER = LoggerFactory.getLogger(URLConverterService.class);
    private final URLRepository urlRepository;

    /**
     * Autowired Constructor
     * @param urlRepository
     */
    @Autowired
    public URLConverterService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    /**
     * <p>
     * This method accepts the URL to be shortened, calls URLRepository to save the original URL, 
     * and performs a string manipulation using the base URL to return a shortened URL.
     * </p>
     * @param localURL
     * @param longUrl
     * @return
     */
    public String shortenURL(String localURL, String longUrl) {
        LOGGER.info("Shortening {}", longUrl);
        Long id = urlRepository.incrementID();
        String uniqueID = IDConverter.createUniqueID(id);
        urlRepository.saveUrl("url:"+id, longUrl);
        String baseString = formatLocalURLFromShortener(localURL);
        String shortenedURL = baseString + uniqueID;
        urlRepository.setZeroHits(id);
        return shortenedURL;
    }

    /**
     * Returns the original URL associated with a given ID
     * @param uniqueID
     * @return
     * @throws Exception
     */
    public String getLongURLFromID(String uniqueID) throws Exception {
        Long dictionaryKey = IDConverter.getDictionaryKeyFromUniqueID(uniqueID);
        String longUrl = urlRepository.getUrl(dictionaryKey);
        LOGGER.info("Converting shortened URL back to {}", longUrl);
        return longUrl;
    }

    /**
     * Takes the base URL that will be returned alongside the formatted id and generates the
     * shortened full URL.
     * @param localURL
     * @return
     */
    private String formatLocalURLFromShortener(String localURL) {
        String[] addressComponents = localURL.split("/");
        // removes the endpoint (last index)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addressComponents.length - 1; ++i) {
            sb.append(addressComponents[i]);
        }
        sb.append('/');
        return sb.toString();
    }

	public String getURLmetricsFromID(Long id) {
		return urlRepository.getHits(id);
	}

	public void setMetricsValue(Long id) {
		Long hits = Long.parseLong(getURLmetricsFromID(id));
		hits = hits + 1;
		urlRepository.setHits(id, hits);
	}

}
