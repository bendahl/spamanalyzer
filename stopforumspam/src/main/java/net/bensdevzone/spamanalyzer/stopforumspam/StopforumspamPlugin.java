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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Set;

import net.bensdevzone.spamanalyzer.datatypes.CriteriaType;
import net.bensdevzone.spamanalyzer.datatypes.SearchCriteria;
import net.bensdevzone.spamanalyzer.datatypes.SearchResult;
import net.bensdevzone.spamanalyzer.plugin.AnalyzerPlugin;
import org.xml.sax.InputSource;


/**
 * This is the Plugin's main class. For details on the api definition that is implemented here
 * see: @link http://www.stopforumspam.com/usage
 *
 * @author ben
 */
public class StopforumspamPlugin implements AnalyzerPlugin {

    private final String API_URL = "http://www.stopforumspam.com/api?";
    private final String XML_DOM_FORMAT = "&f=xmldom";
    private final String JSON_FORMAT = "&f=json";
    private final Set<AnalyzerPluginCriteriaType> criteriaTypes;

    public StopforumspamPlugin() {
        criteriaTypes = new AnalyzerPluginCriteriaType.SetBuilder()
                .add("email","E-Mail")
                .add("username", "Username")
                .add("ip","IP Address")
                .build();
    }
    
    public SearchResult searchFor(SearchCriteria criteria) {
        SearchResult res = new SearchResult();
        res.setDetails(sendRequest(criteria.getCriteria().getInternalName(),
                criteria.getKey()));
        
        return res;
    }

    public String getDisplayName() {
        return "Stop Forum Spam";
    }

    public String getInternalName() {
        return "stopForumSpam";
    }

    public Set<? extends CriteriaType> getCriteriaTypes() {
        return criteriaTypes;
    }

    private String sendRequest(String type, String key) {
        URL url = null;

        try {
            url = new URL(API_URL + type + "=" + URLEncoder.encode(key, "UTF-8")
                    + JSON_FORMAT);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            return ResultParser.parseJson(is).toString();

        } catch (Exception e) {
            return "REQUEST FAILED: " + e.toString();
        }
    }
    
}
