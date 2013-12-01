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

import net.bensdevzone.spamanalyzer.datatypes.CriteriaType;

import java.util.HashSet;

/**
 * Supported Criteria Types. Depend on api of www.stopforumspam.com
 *
 * @author: ben
 */
public class AnalyzerPluginCriteriaType implements CriteriaType {
    private String interalName;
    private String displayName;

    private AnalyzerPluginCriteriaType(String interalName, String displayName) {
        this.interalName = interalName;
        this.displayName = displayName;
    }

    @Override
    public String getInternalName() {
        return interalName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public static class SetBuilder {
        private HashSet<AnalyzerPluginCriteriaType> types;

        public SetBuilder() {
            types = new HashSet<>();
        }

        public SetBuilder add(String internalName, String displayName) {
            types.add(new AnalyzerPluginCriteriaType(internalName, displayName));
            return this;
        }

        public HashSet<AnalyzerPluginCriteriaType> build() {
            return types;
        }
    }
}
