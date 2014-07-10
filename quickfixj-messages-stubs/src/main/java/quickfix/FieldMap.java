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

import java.io.Serializable;

public abstract class FieldMap implements Serializable {

    protected FieldMap(int[] fieldOrder) {
    }

    protected FieldMap() {
    }

    protected void setComponent(MessageComponent component) {
    }

    protected void getComponent(MessageComponent component) {
    }

    StringField getField(int field) throws FieldNotFound {
        return null;
    }

    Field<?> getField(int field, Field<?> defaultValue) {
        return null;
    }

    public String getString(int field) throws FieldNotFound {
        return null;
    }

    public void setField(int key, Field<?> field) {
    }

    public void setField(StringField field) {
    }

    public void setField(BooleanField field) {
    }

    public void setField(CharField field) {
    }

    public void setField(IntField field) {
    }

    public void setField(DoubleField field) {
    }

    public void setField(DecimalField field) {
    }

    public void setField(UtcTimeStampField field) {
    }

    public void setField(UtcTimeOnlyField field) {
    }

    public void setField(UtcDateOnlyField field) {
    }

    public void setField(BytesField field) {
    }

    public BytesField getField(BytesField field) throws FieldNotFound {
        return null;
    }

    public StringField getField(StringField field) throws FieldNotFound {
        return null;
    }

    public BooleanField getField(BooleanField field) throws FieldNotFound {
        return null;
    }

    public CharField getField(CharField field) throws FieldNotFound {
        return null;
    }

    public IntField getField(IntField field) throws FieldNotFound {
        return null;
    }

    public DoubleField getField(DoubleField field) throws FieldNotFound {
        return null;
    }

    public DecimalField getField(DecimalField field) throws FieldNotFound {
        return null;
    }

    public UtcTimeStampField getField(UtcTimeStampField field) throws FieldNotFound {
        return null;
    }

    public UtcTimeOnlyField getField(UtcTimeOnlyField field) throws FieldNotFound {
        return null;
    }

    public UtcDateOnlyField getField(UtcDateOnlyField field) throws FieldNotFound {
        return null;
    }


    public boolean isSetField(int field) {
        return false;
    }

    public boolean isSetField(Field<?> field) {
        return false;
    }

}
