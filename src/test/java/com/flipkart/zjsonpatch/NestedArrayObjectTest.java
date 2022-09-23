/**
 * Copyright (C) 2022 Gitana Software, Inc.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Gitana Software, Inc and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Gitana Software, Inc. and its suppliers
 * and may be covered by U.S. and Foreign Patents, patents in process,
 * and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Gitana Software, Inc.
 */
package com.flipkart.zjsonpatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class NestedArrayObjectTest
{
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonNode loadFromFile(String path) throws IOException
    {
        InputStream resourceAsStream = JsonDiffTest.class.getResourceAsStream(path);
        String testData = IOUtils.toString(resourceAsStream, "UTF-8");
        return MAPPER.readTree(testData);
    }

    @Test
    public void testNestedArrayObject() throws IOException
    {
        JsonNode jsonNode1 = loadFromFile("/testdata/nestedJson1.json");
        JsonNode jsonNode2 = loadFromFile("/testdata/nestedJson2.json");


        ArrayObjectHashGenerator hashGenerator = (pointer, obj) -> {
            if (obj.has("id")) return obj.get("id").asText();
            return "" + obj.hashCode();
        };

        JsonNode patch = JsonDiff.asJson(jsonNode1, jsonNode2, DiffFlags.defaults(), hashGenerator);
        JsonNode jsonNode2Prime = JsonPatch.apply(patch, jsonNode1);
        Assert.assertEquals(jsonNode2, jsonNode2Prime);
    }
}
