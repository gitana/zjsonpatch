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

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ArrayObjectHashGenerator
{
    public String hash(String arrayPath, ObjectNode obj);
}
