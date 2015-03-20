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
package org.openrepose.commons.utils.http.header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @deprecated @see(org.openrepose.commons.utils.http.header.QualityFactorHeaderChooser)
 * 
 * @author zinic
 */
@Deprecated
public final class QualityFactorUtility {

   private QualityFactorUtility() {
   }

   public static <T extends HeaderValue> T choosePreferredHeaderValue(Iterable<T> headerValues) {
      final Iterator<T> headerValueIterator = headerValues != null ? headerValues.iterator() : Collections.EMPTY_LIST.iterator();

      T prefered = headerValueIterator.hasNext() ? headerValueIterator.next() : null;

      while (headerValueIterator.hasNext()) {
         final T next = headerValueIterator.next();

         if (next != null) {
            prefered = prefered.compareTo(next) < 0 ? next : prefered;
         }
      }

      return prefered;
   }

   public static <T extends HeaderValue> List<T> choosePreferredHeaderValues(Iterable<T> headerValues) {
      final Iterator<T> headerValueIterator = headerValues != null ? headerValues.iterator() : Collections.EMPTY_LIST.iterator();

      List<T> preferred = new ArrayList<T>();

      double currentQuality = -1;

      while (headerValueIterator.hasNext()) {
         final T next = headerValueIterator.next();

         if (next.getQualityFactor() > currentQuality) {
            preferred.clear();
            preferred.add(next);
            currentQuality = next.getQualityFactor();
         } else if (next.getQualityFactor() == currentQuality) {
            preferred.add(next);
         }
      }

      return preferred;
   }
}
