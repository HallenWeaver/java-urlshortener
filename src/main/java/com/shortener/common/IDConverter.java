package com.shortener.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class IDConverter {
	/**
	 * Turns class into a singleton, so that we know that only a single instance of the
	 * conversion class is running and we won't have any URL collisions.
	 */
	public static final IDConverter INSTANCE = new IDConverter();
	
	private IDConverter() {
		initializeCharToIndexTable();
        initializeIndexToCharTable();
	}
	
	private static HashMap<Character, Integer> charToIndexTable;
    private static List<Character> indexToCharTable;

    /**
     * <p>
     * This method initializes the table used for mapping characters into their corresponding base 62 value
     * expressed in base 10.
     * </p>
     */
    private void initializeCharToIndexTable() {
        charToIndexTable = new HashMap<>();
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            charToIndexTable.put(c, i);
        }
        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i-26);
            charToIndexTable.put(c, i);
        }
        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            charToIndexTable.put(c, i);
        }
    }
    
    /**
     * <p> This method initializes the table used to map every character into an array, to be able to be
     * searched by position.
     * </p>
     */
    private void initializeIndexToCharTable() {
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        indexToCharTable = new ArrayList<>();
        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            indexToCharTable.add(c);
        }
        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i-26);
            indexToCharTable.add(c);
        }
        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            indexToCharTable.add(c);
        }
    }

    /**
     * Returns a unique short id from the numerical value of the id associated with a given website.
     * @param id The unique id of the web site being processed
     * @return the string produced via the conversion process
     */
    public static String createUniqueID(Long id) {
        List<Integer> base62ID = convertBase10ToBase62ID(id);
        StringBuilder uniqueURLID = new StringBuilder();
        for (int digit: base62ID) {
            uniqueURLID.append(indexToCharTable.get(digit));
        }
        return uniqueURLID.toString();
    }

    /**
     * Returns a list of base-62 digits in reverse order from the id
     * @param id The unique id of the web site being processed
     * @return the list of remainders produced by dividing by 62
     */
    private static List<Integer> convertBase10ToBase62ID(Long id) {
        List<Integer> digits = new LinkedList<>();
        while(id > 0) {
            int remainder = (int)(id % 62);
            ((LinkedList<Integer>) digits).addFirst(remainder);
            id /= 62;
        }
        return digits;
    }

    /**
     * Retrieves the associated numerical key to the generated string for a given web site
     * @param uniqueID the web site id
     * @return a numerical id
     */
    public static Long getDictionaryKeyFromUniqueID(String uniqueID) {
        List<Character> base62IDs = new ArrayList<>();
        for (int i = 0; i < uniqueID.length(); ++i) {
            base62IDs.add(uniqueID.charAt(i));
        }
        Long dictionaryKey = convertBase62ToBase10ID(base62IDs);
        return dictionaryKey;
    }

    /**
     * Converts the base 62 generated id into its numerical base 10 equivalent
     * @param ids the list of character IDs of each position of the unique id of the web site
     * @return the base 10 number of the key associated
     */
    private static Long convertBase62ToBase10ID(List<Character> ids) {
        long id = 0L;
        for (int i = 0, exp = ids.size() - 1; i < ids.size(); ++i, --exp) {
            int base10 = charToIndexTable.get(ids.get(i));
            id += (base10 * Math.pow(62.0, exp));
        }
        return id;
    }
}
