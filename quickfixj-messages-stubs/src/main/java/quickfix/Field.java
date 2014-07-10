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

/**
 * Base class for FIX message fields. This class should be
 * abstract but that would break compatibility with the QF JNI
 * classes.
 */
public /*abstract*/ class Field<T> implements Serializable{
    private int tag;
    private T object;
    private boolean isCalculated = false;
    private String data;

    public Field(int field, T object) {
        this.tag = field;
        this.object = object;
    }

    /**
     * Gets the field's tag.
     *
     * @return the tag
     */
    public int getTag() {
        return tag;
    }

    /**
     * Gets the field's tag. (QF/C++ compatibility)
     *
     * @return the tag
     * @see quickfix.Field#getTag()
     */
    public int getField() {
        return getTag();
    }

    /**
     * Sets the field's value to the given object.
     *
     * @param object
     */
    protected void setObject(T object) {
        this.object = object;
        isCalculated = false;
    }

    /**
     * Get the field value
     *
     * @return an object representing the field's value
     */
    public T getObject() {
        return object;
    }

}
