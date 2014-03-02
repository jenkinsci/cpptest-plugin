package com.thalesgroup.hudson.plugins.cpptest;

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * A simple comparison function for dot-separated version numbers.
 * <p/>
 * It simply matches versions part-by-part with natural ordering:
 * <ul>
 * <li>{@code 7.0 < 10.0}</li>
 * <li>{@code 7.0 < 7.0.1}</li>
 * </ul>
 * <p/>
 * It won't work with alphanumerical versions such as:
 * <ul>
 * <li>{@code 9.5.0.8-alpha}</li>
 * <li>{@code 10.0.12.GA}</li>
 * </ul>
 *
 * @author Dridi Boukelmoune
 */
class VersionComparator implements Comparator<String> {

    /**
     * This comparator is stateless and can be shared safely.
     */
    static final VersionComparator INSTANCE = new VersionComparator();

    private static final Pattern MATCH_PATTERN = Pattern.compile("[0-9]+(\\.[0-9]+)*");
    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\.");

    /**
     * Compares its two version numbers for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.
     *
     * @param version1 the first version number to be compared.
     * @param version2 the second version number to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException     if an argument is null
     * @throws IllegalArgumentException if the arguments don't match {@code [0-9]+(\.[0-9]+)*}
     */
    public int compare(String version1, String version2) {
        if (version1 == null || version2 == null) {
            throw new NullPointerException();
        }
        validate(version1);
        validate(version2);

        if (version1.equals(version2)) {
            return 0;
        }

        String[] parts1 = SPLIT_PATTERN.split(version1);
        String[] parts2 = SPLIT_PATTERN.split(version2);
        int minLength = Math.min(parts1.length, parts2.length);

        for (int i = 0; i < minLength; i++) {
            int part1 = Integer.parseInt(parts1[i]);
            int part2 = Integer.parseInt(parts2[i]);
            if (part1 != part2) {
                return part1 - part2;
            }
        }

        return parts1.length - parts2.length;
    }

    private void validate(String version) {
        if (!MATCH_PATTERN.matcher(version).matches()) {
            throw new IllegalArgumentException("Invalid version : " + version);
        }
    }
}
