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
package net.bensdevzone.spamanalyzer.plugin;

import net.bensdevzone.spamanalyzer.datatypes.CriteriaType;
import net.bensdevzone.spamanalyzer.datatypes.SearchCriteria;
import net.bensdevzone.spamanalyzer.datatypes.SearchResult;

import java.util.Map;
import java.util.Set;

/**
 * Interface that has to be implemented by all plugins
 *
 * @author ben
 */
public interface AnalyzerPlugin {
    /**
     * Will be called in order to initiate the query for spam information. Use this
     * as the entry point in your plugin (and don't forget to return a result...)
     *
     * @param criteria
     * @return
     */
    SearchResult searchFor(SearchCriteria criteria);

    /**
     * The name that will be visible to the user. Usually, this should be a translated string
     *
     * @return
     */
    String getDisplayName();

    /**
     * This name is used internally to identify the plugin
     *
     * @return
     */
    String getInternalName();

    /**
     * Returns all available types of selection criteria
     *
     * @return A Map containing the internal name and the displayed name of the criteria types
     */
    Set<? extends CriteriaType> getCriteriaTypes();
}
