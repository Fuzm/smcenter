package com.irdstudio.ssm.framework.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 生成指定长度的随机数，可以包括数字、字母的混合随机数，也可只生成包括字母或数字的随机数。
 *
 * @author lenovo
 */
public class RandomCodeUtils {
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成指定长度的随机数字
     *
     * @param length 生成长度
     * @return
     */
    public static String generateNumber(int length) {
        return random(length, 0, 0, false, true, null, RANDOM);
    }

    /**
     * 生成指定长度的随机字母
     *
     * @param length 生成长度
     * @return
     */
    public static String generateLetter(int length) {
        return random(length, 0, 0, true, false, null, RANDOM);
    }

    /**
     * 生成指定长度的包含数字和字母的混合随机数
     *
     * @param length 生成长度
     * @return
     */
    public static String generateMixed(int length) {
        return random(length, 0, 0, true, true, null, RANDOM);
    }

    /**
     * 该方法移植自common-lang3已有的工具类：<br>
     * <br>
     * org.apache.commons.lang3.RandomStringUtils.random(int, int, int, boolean,
     * boolean, char[], Random) <br>
     * <p>
     * <p>
     * Creates a random string based on a variety of options, using supplied
     * source of randomness.
     * </p>
     * <p>
     * <p>
     * If start and end are both {@code 0}, start and end are set to {@code ' '}
     * and {@code 'z'}, the ASCII printable characters, will be used, unless
     * letters and numbers are both {@code false}, in which case, start and end
     * are set to {@code 0} and {@code Integer.MAX_VALUE}.
     * <p>
     * <p>
     * If set is not {@code null}, characters between start and end are chosen.
     * </p>
     * <p>
     * <p>
     * This method accepts a user-supplied {@link Random} instance to use as a
     * source of randomness. By seeding a single {@link Random} instance with a
     * fixed seed and using it for each call, the same random sequence of
     * strings can be generated repeatedly and predictably.
     * </p>
     *
     * @param count   the length of random string to create
     * @param start   the position in set of chars to start at
     * @param end     the position in set of chars to end before
     * @param letters only allow letters?
     * @param numbers only allow numbers?
     * @param chars   the set of chars to choose randoms from, must not be empty. If
     *                {@code null}, then it will use the set of all chars.
     * @param random  a source of randomness.
     * @return the random string
     * @throws ArrayIndexOutOfBoundsException if there are not {@code (end - start) + 1} characters in the
     *                                        set array.
     * @throws IllegalArgumentException       if {@code count} &lt; 0 or the provided chars array is empty.
     * @since 2.0
     */
    private static String random(int count, int start, int end, final boolean letters, final boolean numbers,
                                 final char[] chars, final Random random) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        if (chars != null && chars.length == 0) {
            throw new IllegalArgumentException("The chars array must not be empty");
        }

        if (start == 0 && end == 0) {
            if (chars != null) {
                end = chars.length;
            } else {
                if (!letters && !numbers) {
                    end = Integer.MAX_VALUE;
                } else {
                    end = 'z' + 1;
                    start = ' ';
                }
            }
        } else {
            if (end <= start) {
                throw new IllegalArgumentException(
                        "Parameter end (" + end + ") must be greater than start (" + start + ")");
            }
        }

        final char[] buffer = new char[count];
        final int gap = end - start;

        while (count-- != 0) {
            char ch;
            if (chars == null) {
                ch = (char) (random.nextInt(gap) + start);
            } else {
                ch = chars[random.nextInt(gap) + start];
            }
            if (letters && Character.isLetter(ch) || numbers && Character.isDigit(ch) || !letters && !numbers) {
                if (ch >= 56320 && ch <= 57343) {
                    if (count == 0) {
                        count++;
                    } else {
                        // low surrogate, insert high surrogate after putting it
                        // in
                        buffer[count] = ch;
                        count--;
                        buffer[count] = (char) (55296 + random.nextInt(128));
                    }
                } else if (ch >= 55296 && ch <= 56191) {
                    if (count == 0) {
                        count++;
                    } else {
                        // high surrogate, insert low surrogate before putting
                        // it in
                        buffer[count] = (char) (56320 + random.nextInt(128));
                        count--;
                        buffer[count] = ch;
                    }
                } else if (ch >= 56192 && ch <= 56319) {
                    // private high surrogate, no effing clue, so skip it
                    count++;
                } else {
                    buffer[count] = ch;
                }
            } else {
                count++;
            }
        }
        return new String(buffer);
    }

}
