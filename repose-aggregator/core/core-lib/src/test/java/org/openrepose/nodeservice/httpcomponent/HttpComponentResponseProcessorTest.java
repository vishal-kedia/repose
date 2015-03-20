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
package org.openrepose.nodeservice.httpcomponent;

import org.openrepose.commons.utils.servlet.http.MutableHttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

@RunWith(Enclosed.class)
public class HttpComponentResponseProcessorTest {

    public static class WhenProcessingResponses {

        private HttpResponse response;
        private HttpServletResponse servletResponse;
        private HttpComponentResponseProcessor processor;
        private Map<String, String> headerValues;
        private List<Header> headers;
        private HttpEntity entity;
        private ServletOutputStream out;
        
        @Before
        public void setUp() throws IOException {
            final String[] headerNames = {"header1", "header2"};
            headerValues = new HashMap<String, String>();
            headerValues.put("header1", "value1");
            headerValues.put("header2", "value21,value22");
            
            headers = new ArrayList<Header>();
            for (String header : headerNames) {
                String values = headerValues.get(header);
                for (String value: values.split(",")) {
                    Header header1 = mock(Header.class);
                    when(header1.getName()).thenReturn(header);
                    when(header1.getValue()).thenReturn(value);
                    headers.add(header1);
                }
            }
            entity = mock(HttpEntity.class);
            out = mock(ServletOutputStream.class);
            response = mock(HttpResponse.class);
            servletResponse = mock(HttpServletResponse.class);
            when(response.getAllHeaders()).thenReturn(headers.toArray(new Header[0]));
            when(response.getEntity()).thenReturn(entity);
            when(servletResponse.getOutputStream()).thenReturn(out);

            processor = new HttpComponentResponseProcessor(response, servletResponse, new HttpComponentResponseCodeProcessor(200));
        }

        @Test
        public void shouldSetStatus() throws IOException {
            processor.process();
            verify(servletResponse).setStatus(eq(200));
        }

        @Test
        public void shouldSetHeaders() throws IOException {
            processor.process();
            
            verify(servletResponse).setStatus(eq(200));
            for (Header header: headers) {
                verify(servletResponse).addHeader(header.getName(), header.getValue());
            }
        }

        @Test
        public void shouldWriteResponse() throws IOException {
            processor.process();
            
            verify(servletResponse).setStatus(eq(200));
            for (Header header: headers) {
                verify(servletResponse).addHeader(header.getName(), header.getValue());
            }
            
            verify(entity).writeTo(out);
            verify(out).flush();
        }
    }

    public static class WhenProcessingMutableHttpResponses {

        private HttpResponse response;
        private MutableHttpServletResponse servletResponse;
        private HttpComponentResponseProcessor processor;
        private Map<String, String> headerValues;
        private List<Header> headers;
        private HttpEntity entity;
        private ServletOutputStream out;
        
        @Before
        public void setUp() throws IOException {
            final String[] headerNames = {"header1", "header2"};
            headerValues = new HashMap<String, String>();
            headerValues.put("header1", "value1");
            headerValues.put("header2", "value21,value22");
            
            headers = new ArrayList<Header>();
            for (String header : headerNames) {
                String values = headerValues.get(header);
                for (String value: values.split(",")) {
                    Header header1 = mock(Header.class);
                    when(header1.getName()).thenReturn(header);
                    when(header1.getValue()).thenReturn(value);
                    headers.add(header1);
                }
            }
            entity = mock(HttpEntity.class);
            out = mock(ServletOutputStream.class);
            response = mock(HttpResponse.class);
            servletResponse = mock(MutableHttpServletResponse.class);
            when(response.getAllHeaders()).thenReturn(headers.toArray(new Header[0]));
            when(response.getEntity()).thenReturn(entity);
            when(servletResponse.getOutputStream()).thenReturn(out);

            processor = new HttpComponentResponseProcessor(response, servletResponse, new HttpComponentResponseCodeProcessor(200));
        }

        @Test
        public void shouldSetStatus() throws IOException {
            processor.process();
            verify(servletResponse).setStatus(eq(200));
        }

        @Test
        public void shouldSetHeaders() throws IOException {
            processor.process();
            
            verify(servletResponse).setStatus(eq(200));
            for (Header header: headers) {
                verify(servletResponse).addHeader(header.getName(), header.getValue());
            }
        }

        @Test
        public void shouldWriteResponse() throws IOException {
            processor.process();
            
            verify(servletResponse).setStatus(eq(200));
            for (Header header: headers) {
                verify(servletResponse).addHeader(header.getName(), header.getValue());
            }
            
            verify(servletResponse).setInputStream(any(HttpComponentInputStream.class));
        }
    }
}
