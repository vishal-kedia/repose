/*
 * #%L
 * Repose
 * %%
 * Copyright (C) 2010 - 2015 Rackspace US, Inc.
 * %%
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
 * #L%
 */
package org.openrepose.core.services.deploy;

import java.io.File;
import java.io.FilenameFilter;

public final class EarFilenameFilter implements FilenameFilter {

    private static final EarFilenameFilter INSTANCE = new EarFilenameFilter();
    private static final int EAR_EXTENSION_LENGTH = 4;

    public static FilenameFilter getInstance() {
        return INSTANCE;
    }

    private EarFilenameFilter() {
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.length() > EAR_EXTENSION_LENGTH && ".ear".equalsIgnoreCase(name.substring(name.length() - EAR_EXTENSION_LENGTH));
    }
}
