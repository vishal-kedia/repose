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
package org.openrepose.filters.slf4jlogging;

import org.openrepose.commons.utils.logging.apache.HttpLogFormatter;
import org.slf4j.Logger;

public class Slf4jLoggerWrapper {

    private Logger logger;
    private HttpLogFormatter formatter;
    private String formatString;

    public Slf4jLoggerWrapper(Logger logger, String formatString) {
        this.logger = logger;
        this.formatString = formatString;
        this.formatter = new HttpLogFormatter(formatString);
    }

    public HttpLogFormatter getFormatter() {
        return formatter;
    }

    public String getFormatString() {
        return formatString;
    }

    public Logger getLogger() {
        return logger;
    }
}
