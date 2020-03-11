package com.shortener.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validation Class that uses regular expressions to match URLs.
 * @author Alexandre Miquilino
 *
 */
public class URLValidator {
	public static final URLValidator INSTANCE = new URLValidator();
    private static final String URL_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    private URLValidator() {
    }

    /**
     * Matches a given string with the URL_REGEX pattern defined above.
     * @param url
     * @return true or false if the pattern matches.
     */
    public boolean validateURL(String url) {
        Matcher m = URL_PATTERN.matcher(url);
        return m.matches();
    }
}
