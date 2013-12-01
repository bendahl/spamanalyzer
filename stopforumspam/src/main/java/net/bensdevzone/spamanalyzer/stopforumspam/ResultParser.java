/* ==============================
Copyright (c) 2013 Benjamin Dahlmanns, ben@bensdevzone.net

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
============================== */
package net.bensdevzone.spamanalyzer.stopforumspam;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for parsing the xml resonse that is sent back from the website
 *
 * @author: ben
 */
public class ResultParser {

    public static StopForumSpamResponse parseJson(InputStream src) {
        StopForumSpamResponse response = new StopForumSpamResponse();
        JsonFactory factory = new JsonFactory();
        try {
            JsonParser parser = factory.createJsonParser(src);
            while(parser.nextToken() != JsonToken.END_OBJECT) {
                String token = parser.getCurrentName();

                if("success".equals(token)) {
                    parser.nextToken();
                    response.setSuccess(parser.getText().equals("1"));
                }

                if("lastseen".equals(token)) {
                    parser.nextToken();
                    response.setLastSeen(StopForumSpamResponse.getDateFormat()
                            .parse(parser.getText()));
                }

                if("frequency".equals(token)) {
                    parser.nextToken();
                    response.setFrequency(parser.getIntValue());
                }

                if("appears".equals(token)) {
                    parser.nextToken();
                    response.setAppears(parser.getIntValue());
                }

                if("confidence".equals(token)) {
                    parser.nextToken();
                    response.setConfidence(parser.getFloatValue());
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return response;
    }
}
