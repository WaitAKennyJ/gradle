/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.plugins.ide.internal.resolver;

import org.gradle.cache.internal.GeneratedGradleJarCache;
import org.gradle.internal.installation.CurrentGradleInstallation;
import org.gradle.internal.installation.GradleInstallation;

import java.io.File;

public class DefaultSourcesJarFactory implements SourcesJarFactory {

    private final SourcesJarCreator sourcesJarCreator;
    private final GeneratedGradleJarCache cache;

    public DefaultSourcesJarFactory(SourcesJarCreator sourcesJarCreator, GeneratedGradleJarCache cache) {
        this.sourcesJarCreator = sourcesJarCreator;
        this.cache = cache;
    }

    @Override
    public File resolveGradleApiSources(File artifact) {
        GradleInstallation gradleInstallation = CurrentGradleInstallation.get();
        // TODO: if source dir does not exist, we might want to download a distribution and repackage sources
        if (gradleInstallation == null || !gradleInstallation.getSrcDir().exists()) {
            return null;
        }
        return cache.get("sources", file -> sourcesJarCreator.create(file, gradleInstallation.getSrcDir()));
    }
}
