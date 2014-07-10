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
 * Represents a repeating field group within a message.
 */
public class Group extends FieldMap {

    /**
     * Create a group with the specified count and delimiter fields.
     *
     * @param field the count tag number
     * @param delim the delimiter tag number (first group field)
     */
    public Group(int field, int delim) {
    }

    /**
     * Copy a group.
     *
     * @param group the group to copy
     */
    public Group(Group group) {
    }

    /**
     * Create a group with the specified count and delimiter fields and
     * field ordering.
     *
     * @param field
     * @param delim
     * @param order
     */
    public Group(int field, int delim, int[] order) {
    }

}
