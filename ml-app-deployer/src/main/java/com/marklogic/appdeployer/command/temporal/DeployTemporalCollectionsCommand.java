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
package com.marklogic.appdeployer.command.temporal;

import com.marklogic.appdeployer.AppConfig;
import com.marklogic.appdeployer.ConfigDir;
import com.marklogic.appdeployer.command.AbstractResourceCommand;
import com.marklogic.appdeployer.command.CommandContext;
import com.marklogic.appdeployer.command.SortOrderConstants;
import com.marklogic.mgmt.resource.ResourceManager;
import com.marklogic.mgmt.resource.temporal.TemporalCollectionManager;

import java.io.File;

public class DeployTemporalCollectionsCommand extends AbstractResourceCommand {

	private TemporalCollectionManager currentTemporalCollectionManager;

	public DeployTemporalCollectionsCommand() {
		setExecuteSortOrder(SortOrderConstants.DEPLOY_TEMPORAL_COLLECTIONS);
		// if the temporal collection contains documents, then the delete operation will fail
		// TODO - could add check to get count of documents in temporal collection. If zero docs, then can delete
		setDeleteResourcesOnUndo(false);
	}

	@Override
	public void execute(CommandContext context) {
		AppConfig appConfig = context.getAppConfig();
		for (ConfigDir configDir : appConfig.getConfigDirs()) {
			deployTemporalCollections(context, configDir, appConfig.getContentDatabaseName());
			for (File dir : configDir.getDatabaseResourceDirectories()) {
				String databaseName = determineDatabaseNameForDatabaseResourceDirectory(context, configDir, dir);
				if (databaseName != null) {
					deployTemporalCollections(context, new ConfigDir(dir), databaseName);
				}
			}
		}
	}

	protected void deployTemporalCollections(CommandContext context, ConfigDir configDir, String databaseIdOrName) {
		currentTemporalCollectionManager = new TemporalCollectionManager(context.getManageClient(), databaseIdOrName);
		processExecuteOnResourceDir(context, configDir.getTemporalCollectionsDir());
	}

	/**
	 * Not used since we override execute in the parent class.
	 *
	 * @param context
	 * @return
	 */
	@Override
	protected File[] getResourceDirs(CommandContext context) {
		return null;
	}

	@Override
	protected ResourceManager getResourceManager(CommandContext context) {
		return currentTemporalCollectionManager;
	}
}
