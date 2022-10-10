/*
 * Copyright 2016 flipkart.com zjsonpatch.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flipkart.zjsonpatch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Default implementation of diff functions that performs entry equality tests based on hash codes.
 */
public class DefaultDiffFunctions implements DiffFunctions
{
    @Override
    public boolean entryEquals(JsonPointer parentPath, JsonNode node1, JsonNode node2) {

        if (node1.isObject() && node2.isObject()) {
            return entryEquals(parentPath.toString(), (ObjectNode) node1, (ObjectNode) node2);
        }

        return node1.equals(node2);
    }

    /**
     * Tests the equality of two nodes using a simple hash code equivalency test.
     *
     * Provides an extension point for others to override and implement their own object-level identity tests.
     *
     * @param parentPath
     * @param node1
     * @param node2
     *
     * @return whether node1 and node2 are meant to describe a equivalent entry
     */
    protected boolean entryEquals(String parentPath, ObjectNode node1, ObjectNode node2) {

        int hash1 = node1.hashCode();
        int hash2 = node2.hashCode();

        return (hash1 == hash2);
    }
}
