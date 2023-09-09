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
package com.marklogic.client.ext.modulesloader.impl;

import com.marklogic.client.ext.modulesloader.Modules;

/**
 * Search options and REST properties are specific to a REST API server. So if you a REST API server for testing
 * purposes which is different from the REST API server that you use in an application, you'll most likely want to load
 * the options and REST properties via the test server as well. This ModulesFinder then only returns options and a
 * properties file, if they exist.
 */
public class TestServerModulesFinder extends BaseModulesFinder {

    @Override
    protected Modules findModulesWithResolvedBaseDir(String baseDir) {
        Modules modules = new Modules();
        addOptions(modules, baseDir);
        addPropertiesFile(modules, baseDir);
        return modules;
    }

}
