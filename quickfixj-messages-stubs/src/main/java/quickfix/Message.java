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

/**
 * Represents a FIX message.
 */
public class Message extends FieldMap {

    protected Header header = new Header();
    protected Trailer trailer = new Trailer();


    public Message() {
        // empty
    }

    protected Message(int[] fieldOrder) {
        super(fieldOrder);
    }

    public Message(String string) throws InvalidMessage {
    }

    public Message(String string, boolean validate) throws InvalidMessage {
    }

    public final Header getHeader() {
        return header;
    }

    public final Trailer getTrailer() {
        return trailer;
    }

    public static class Header extends FieldMap {

        public Header() {
        }

        public Header(int[] fieldOrder) {
        }
    }

    public static class Trailer extends FieldMap {

        public Trailer() {
        }

        public Trailer(int[] fieldOrder) {
        }
    }

    private void setField(FieldMap fields, StringField field) {
    }

}
