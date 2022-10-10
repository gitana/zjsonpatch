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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class NestedArrayObjectTest
{
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonNode loadFromFile(String path) throws IOException {

        InputStream resourceAsStream = JsonDiffTest.class.getResourceAsStream(path);
        String testData = IOUtils.toString(resourceAsStream, "UTF-8");
        return MAPPER.readTree(testData);
    }

    private class TestDiffFunctions extends DefaultDiffFunctions {

        @Override
        protected boolean entryEquals(String parentPath, ObjectNode node1, ObjectNode node2) {

            if (node1.has("id") && node2.has("id")) {
                JsonNode v1 = node1.get("id");
                JsonNode v2 = node2.get("id");
                return v1.equals(v2);
            }

            return super.entryEquals(parentPath, node1, node2);
        }
    }

    @Test
    public void testNestedArrayObject() throws IOException {
        JsonNode jsonNode1 = loadFromFile("/testdata/nestedJson1.json");
        JsonNode jsonNode2 = loadFromFile("/testdata/nestedJson2.json");

        DiffFunctions functions = new TestDiffFunctions();

        JsonNode patch = JsonDiff.asJson(jsonNode1, jsonNode2, DiffFlags.defaults(), functions);
        JsonNode jsonNode2Prime = JsonPatch.apply(patch, jsonNode1);
        Assert.assertEquals(jsonNode2, jsonNode2Prime);
    }
}
