/*
 * Copyright (c) 2023 MarkLogic Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marklogic.appdeployer.command.databases;

import com.marklogic.appdeployer.AbstractAppDeployerTest;
import com.marklogic.mgmt.api.API;
import com.marklogic.mgmt.api.database.Database;
import com.marklogic.mgmt.mapper.DefaultResourceMapper;
import com.marklogic.mgmt.resource.databases.DatabaseManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvokeDatabaseOperationsTest extends AbstractAppDeployerTest {

    @AfterEach
    public void teardown() {
        undeploySampleApp();
    }

    @Test
    public void test() {
        setConfigBaseDir("sample-app/db-only-config");

        initializeAppDeployer(new DeployOtherDatabasesCommand(1));
        deploySampleApp();

        DatabaseManager mgr = new DatabaseManager(manageClient);

        // Just smoke-testing these to make sure they don't blow up
        mgr.mergeDatabase(appConfig.getContentDatabaseName());
        mgr.reindexDatabase(appConfig.getContentDatabaseName());

        String json = mgr.getPropertiesAsJson(appConfig.getContentDatabaseName());
	    System.out.println(json);
        Database db = new DefaultResourceMapper(new API(manageClient)).readResource(json, Database.class);
	    assertEquals("ÉÉÉ", db.getRangeElementIndex().get(0).getLocalname());
    }
}
