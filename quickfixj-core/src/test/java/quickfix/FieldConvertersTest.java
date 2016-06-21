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

package quickfix;

import java.text.DecimalFormat;
import java.util.*;

import quickfix.field.converter.BooleanConverter;
import quickfix.field.converter.CharConverter;
import quickfix.field.converter.DoubleConverter;
import quickfix.field.converter.IntConverter;
import quickfix.field.converter.UtcDateOnlyConverter;
import quickfix.field.converter.UtcTimeOnlyConverter;
import quickfix.field.converter.UtcTimestampConverter;

import junit.framework.TestCase;

public class FieldConvertersTest extends TestCase {

    public void testIntegerConversion() throws Exception {
        assertEquals("123", IntConverter.convert(123));
        assertEquals(123, IntConverter.convert("123"));
        assertEquals(-1, IntConverter.convert("-1"));
        assertEquals(23, IntConverter.convert("00023"));
        try {
            IntConverter.convert("abc");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            IntConverter.convert("123.4");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            IntConverter.convert("+200");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
    }

    public void testDoubleConversion() throws Exception {
        assertEquals("0", DoubleConverter.convert(0));
        assertEquals("45.32", DoubleConverter.convert(45.32));
        assertEquals("45", DoubleConverter.convert(45));
        assertEquals("3.12345678901234", DoubleConverter.convert(3.12345678901234d));
        assertEquals("3.12345678901235", DoubleConverter.convert(3.12345678901235d));
        assertEquals("0.000", DoubleConverter.convert(0, 3));
        assertEquals("100.000", DoubleConverter.convert(100, 3));
        assertEquals("1234567.123", DoubleConverter.convert(1234567.123d));
        assertEquals("1234567.923", DoubleConverter.convert(1234567.923d));
        assertEquals("1234567.1230", DoubleConverter.convert(1234567.123d, 4));
        assertEquals("1234567.9230", DoubleConverter.convert(1234567.923d, 4));
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            double d = rand.nextDouble();
            //assertEquals(new DecimalFormat("0.##############").format(d), DoubleConverter.convert(d));
            String expected = new DecimalFormat("0.##############").format(d);
            String actual = DoubleConverter.convert(d);
            if (!actual.equals(expected)) {
                int j;
                for (j = 0; j < actual.length() && j < expected.length()
                        && actual.charAt(j) == expected.charAt(j); j++);
                System.out.printf("expected %s[%s], actual %s[%s]%n",
                        expected.substring(0, j), expected.substring(j),
                        actual.substring(0, j), actual.substring(j));
            }
        }

        long mx = Long.MAX_VALUE;
        long mn = Long.MIN_VALUE;
        double[] tests = { 0, 45, 45.32, 45.99,
            Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN,
            Integer.MAX_VALUE, Integer.MIN_VALUE, Long.MAX_VALUE, Long.MIN_VALUE,
            mx / 1e13, mx / 1e14, mx / 1e14 + 1, mx / 1e14 - 1, mx / 1e15,
            mn / 1e13, mn / 1e14, mn / 1e14 + 1, mn / 1e14 - 1,  mn / 1e15 };
        for (double d : tests) {
            assertEquals(new DecimalFormat("0.##############").format(d), DoubleConverter.convert(d));
        }
        assertEquals(45.32, DoubleConverter.convert("45.32"), 0);
        assertEquals(45.32, DoubleConverter.convert("45.3200"), 0);
        assertEquals(0.00340244, DoubleConverter.convert("0.00340244000"), 0);
        assertEquals(45.32, DoubleConverter.convert("45.32"), 0);
        assertEquals(12.000000000001, DoubleConverter
                .convert("12.000000000001"), 0);
        assertEquals(0, DoubleConverter.convert("0.0"), 0);
        assertEquals(45.32, DoubleConverter.convert("0045.32"), 0);
        assertEquals(0, DoubleConverter.convert("0."), 0);
        assertEquals(0, DoubleConverter.convert(".0"), 0);
        assertEquals(0.06, DoubleConverter.convert("000.06"), 0);
        assertEquals(0.06, DoubleConverter.convert("0.0600"), 0);
        assertEquals(23.0, DoubleConverter.convert("00023."), 0);

        try {
            DoubleConverter.convert("abc");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            DoubleConverter.convert("+200");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            DoubleConverter.convert("123.A");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            DoubleConverter.convert(".");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            DoubleConverter.convert("1E6");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            DoubleConverter.convert("1e6");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        assertEquals("1.500", DoubleConverter.convert(1.5, 3));
        assertEquals("45.00000", DoubleConverter.convert(45, 5));
        assertEquals("5.00", DoubleConverter.convert(5, 2));
        assertEquals("-5.00", DoubleConverter.convert(-5, 2));
        assertEquals("-12.2345", DoubleConverter.convert(-12.2345, 3));
        assertEquals("0.0", DoubleConverter.convert(0, 1));
    }

    public void testCharConversion() throws Exception {
        assertEquals("a", CharConverter.convert('a'));
        assertEquals("1", CharConverter.convert('1'));
        assertEquals("F", CharConverter.convert('F'));
        assertEquals('a', CharConverter.convert("a"));
        assertEquals('1', CharConverter.convert("1"));
        assertEquals('F', CharConverter.convert("F"));
        try {
            CharConverter.convert("");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            CharConverter.convert("a1");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
    }

    public void testBooleanConversion() throws Exception {
        assertEquals("Y", BooleanConverter.convert(true));
        assertEquals("N", BooleanConverter.convert(false));
        assertEquals(true, BooleanConverter.convert("Y"));
        assertEquals(false, BooleanConverter.convert("N"));
        try {
            BooleanConverter.convert("D");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            BooleanConverter.convert("true");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
    }

    public void testUtcTimeStampConversion() throws Exception {
        Calendar c = new GregorianCalendar(2000, 3, 26, 12, 5, 6);
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        c.set(Calendar.MILLISECOND, 555);
        assertEquals("20000426-12:05:06", UtcTimestampConverter.convert(c
                .getTime(), false));
        assertEquals("20000426-12:05:06.555", UtcTimestampConverter.convert(c
                .getTime(), true));

        Date date = UtcTimestampConverter.convert("20000426-12:05:06.555");
        c.setTime(date);
        assertEquals(12, c.get(Calendar.HOUR_OF_DAY));
        assertEquals(5, c.get(Calendar.MINUTE));
        assertEquals(6, c.get(Calendar.SECOND));
        assertEquals(555, c.get(Calendar.MILLISECOND));
        assertEquals(2000, c.get(Calendar.YEAR));
        assertEquals(3, c.get(Calendar.MONTH));
        assertEquals(26, c.get(Calendar.DAY_OF_MONTH));
        try {
            UtcTimestampConverter.convert("2000042x-12:05:06.555");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            UtcTimestampConverter.convert("200004261-2:05:06.555");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            UtcTimestampConverter.convert("20000426-1205:06.555");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            UtcTimestampConverter.convert("20000426-12:0506.555");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            UtcTimestampConverter.convert("20000426-12:05:06555");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
    }

    public void testUtcTimeOnlyConversion() throws Exception {
        Calendar c = new GregorianCalendar(0, 0, 0, 12, 5, 6);
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        c.set(Calendar.MILLISECOND, 555);
        assertEquals("12:05:06", UtcTimeOnlyConverter.convert(c.getTime(),
                false));
        assertEquals("12:05:06.555", UtcTimeOnlyConverter.convert(c.getTime(),
                true));

        Date date = UtcTimeOnlyConverter.convert("12:05:06.555");
        c.setTime(date);
        assertEquals(12, c.get(Calendar.HOUR_OF_DAY));
        assertEquals(5, c.get(Calendar.MINUTE));
        assertEquals(6, c.get(Calendar.SECOND));
        assertEquals(555, c.get(Calendar.MILLISECOND));
        assertEquals(1970, c.get(Calendar.YEAR));
        assertEquals(0, c.get(Calendar.MONTH));
        assertEquals(1, c.get(Calendar.DAY_OF_MONTH));
        try {
            UtcTimeOnlyConverter.convert("I2:05:06.555");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
    }

    public void testUtcDateOnlyConversion() throws Exception {
        Calendar c = new GregorianCalendar(2000, 3, 26, 0, 0, 0);
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        c.set(Calendar.MILLISECOND, 555);
        assertEquals("20000426", UtcDateOnlyConverter.convert(c.getTime()));

        Date date = UtcDateOnlyConverter.convert("20000426");
        c.setTime(date);
        assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, c.get(Calendar.MINUTE));
        assertEquals(0, c.get(Calendar.SECOND));
        assertEquals(0, c.get(Calendar.MILLISECOND));
        assertEquals(2000, c.get(Calendar.YEAR));
        assertEquals(3, c.get(Calendar.MONTH));
        assertEquals(26, c.get(Calendar.DAY_OF_MONTH));
        try {
            UtcDateOnlyConverter.convert("b000042b");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            UtcDateOnlyConverter.convert("2000042");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            UtcDateOnlyConverter.convert("200004268");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            UtcDateOnlyConverter.convert("2000042b");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
        try {
            UtcDateOnlyConverter.convert("200k0425");
            fail();
        } catch (FieldConvertError e) {
            // expected
        }
    }

    //    void FieldConvertorsTestCase::checkSumConvertTo::onRun( void*& )
    //    {
    //      assert( CheckSumConvertor::convert( 0 ) == "000" );
    //      assert( CheckSumConvertor::convert( 5 ) == "005" );
    //      assert( CheckSumConvertor::convert( 12 ) == "012" );
    //      assert( CheckSumConvertor::convert( 234 ) == "234" );
    //
    //      try{ CheckSumConvertor::convert( -1 ); assert( false ); }
    //      catch ( FieldConvertError& ) {}
    //      try{ CheckSumConvertor::convert( 256 ); assert( false ); }
    //      catch ( FieldConvertError& ) {}}
    //    }
}
