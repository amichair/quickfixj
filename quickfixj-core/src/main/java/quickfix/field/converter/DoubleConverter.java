/*******************************************************************************
 * Copyright (c) quickfixengine.org  All rights reserved.
 *
 * This file is part of the QuickFIX FIX Engine
 *
 * This file may be distributed under the terms of the quickfixengine.org
 * license as defined by quickfixengine.org and appearing in the file
 * LICENSE included in the packaging of this file.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE.
 *
 * See http://www.quickfixengine.org/LICENSE for licensing information.
 *
 * Contact ask@quickfixengine.org if any conditions of this licensing
 * are not clear to you.
 ******************************************************************************/

package quickfix.field.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickfix.FieldConvertError;
import quickfix.RuntimeError;

/**
 * Converts between a double and a String.
 */
public class DoubleConverter {
    private static final Pattern DECIMAL_PATTERN = Pattern.compile("-?\\d*(\\.\\d*)?");
    private static final ThreadLocal<DecimalFormat[]> THREAD_DECIMAL_FORMATS = new ThreadLocal<DecimalFormat[]>();
    private static final double FAST_FORMAT_MIN = Long.MIN_VALUE / 1e14;
    private static final double FAST_FORMAT_MAX = Long.MAX_VALUE / 1e14;

    static void validatePadding(int padding) {
        // FieldConvertError not supported in setDouble methods on Message, so we throw RuntimeError
        if (padding < 0) {
            throw new RuntimeError("padding must be between 0 and 14 zeroes: " + padding);
        }
        if (padding > 14) {
            throw new RuntimeError("maximum padding of 14 zeroes is supported: " + padding);
        }
    }

    static DecimalFormat getDecimalFormat(int padding) {
        validatePadding(padding);
        DecimalFormat[] decimalFormats = THREAD_DECIMAL_FORMATS.get();
        if (decimalFormats == null) {
            decimalFormats = new DecimalFormat[14];
            THREAD_DECIMAL_FORMATS.set(decimalFormats);
        }
        DecimalFormat f = decimalFormats[padding];
        if (f == null) {
            StringBuilder sb = new StringBuilder(16).append("0.");
            for (int i = 0; i < 14; i++) {
                sb.append(i < padding ? '0' : '#');
            }
            f = new DecimalFormat(sb.toString());
            f.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
            decimalFormats[padding] = f;
        }
        return f;
    }

    static StringBuilder fastFormatDouble(StringBuilder builder, double d, int padding) {
        validatePadding(padding);
        if (d < 0) {
            builder.append('-');
            d = -d;
        }
        long scaled = (long)(d * 1e14 + 0.5);
        long factor = (long)1e14;
        int digits = 15;
        while (factor * 10 <= scaled && factor < 1000000000000000000L) {
            factor *= 10;
            digits++;
        }
        padding = 14 - padding;
        while (digits > 0) {
            long c = scaled / factor % 10;
            if (digits <= padding && scaled % (10 * factor) == 0)
                break;
            if (digits == 14)
                builder.append('.');
            builder.append((char)('0' + c));
            factor /= 10;
            digits--;
        }
        return builder;
    }

    /**
     * Converts a double to a string with no padding.
     *
     * @param d the double to convert
     * @return the formatted String representing the double.
     * @see #convert(double, int)
     */
    public static String convert(double d) {
        return convert(d, 0);
    }

    /**
     * Converts a double to a string with padding.
     *
     * @param d the double to convert
     * @param padding the number of zeros to add to end of the formatted double
     * @return the formatted String representing the double.
     */
    public static String convert(double d, int padding) {
        // This method is used quite a bit and had a very inefficient implementation,
        // so optimizing it is worth the effort. We now use two implementations:
        //
        // - The custom fast format is very small, efficient, and garbage-less.
        // However it is limited in the range of values it supports, and
        // also doesn't handle special cases like NaN, infinity or -0.0.
        //
        // - The DecimalFormat is much slower and has lots of overhead, due to
        // all the formatting bells and whistles which we don't need, such as
        // thread locals (or garbage), internal StringBuffers, synchronized blocks,
        // internal helper classes, and a whole lot of code. We use it only when
        // the fast format cannot be used.
        if (d > FAST_FORMAT_MIN && d < FAST_FORMAT_MAX && !Double.isNaN(d))
            return fastFormatDouble(new StringBuilder(16), d , padding).toString();
        return getDecimalFormat(padding).format(d);
    }

    /**
     * Convert a String value to a double.
     *
     * @param value the String value to convert
     * @return the parsed double
     * @throws FieldConvertError if the String is not a valid double pattern.
     */
    public static double convert(String value) throws FieldConvertError {
        try {
            Matcher matcher = DECIMAL_PATTERN.matcher(value);
            if (!matcher.matches()) {
                throw new NumberFormatException();
            }
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new FieldConvertError("invalid double value: " + value);
        }
    }
}
