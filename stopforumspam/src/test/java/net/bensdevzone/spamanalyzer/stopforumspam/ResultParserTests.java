package net.bensdevzone.spamanalyzer.stopforumspam;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Unit tests for the ResultParser class
 *
 * @author: ben
 */
public class ResultParserTests {
    private static Date responseDate;

    @BeforeClass
    public static void setUp() throws Exception{
        responseDate = StopForumSpamResponse.getDateFormat().parse("2013-11-25 20:49:29");
    }

    @Test
    public void testUsernameQueryPositive() {
        InputStream is = toInputStream("{\"success\":1,\"username\":{\"lastseen\"" +
                ":\"2013-11-25 20:49:29\",\"frequency\":1,\"appears\":1," +
                "\"confidence\":18.18}}");

        StopForumSpamResponse response = ResultParser.parseJson(is);
        assertEquals(true, response.isSuccess());
        assertEquals(responseDate, response.getLastSeen());
        assertEquals(1, response.getFrequency());
        assertEquals(1, response.getAppears());
        assertTrue(Float.compare(response.getConfidence(), 18.18f) == 0);
    }

    @Test
    public void testIPQueryPositive() {
        InputStream is = toInputStream("{\"success\":1,\"ip\":{\"lastseen\"" +
                ":\"2013-11-25 20:49:29\",\"frequency\":1,\"appears\":1," +
                "\"confidence\":18.18}}");

        StopForumSpamResponse response = ResultParser.parseJson(is);
        assertEquals(true, response.isSuccess());
        assertEquals(responseDate, response.getLastSeen());
        assertEquals(1, response.getFrequency());
        assertEquals(1, response.getAppears());
        assertTrue(Float.compare(response.getConfidence(), 18.18f) == 0);
    }

    @Test
    public void testEmailQueryPositive() {
        InputStream is = toInputStream("{\"success\":1,\"email\":{\"lastseen\"" +
                ":\"2013-11-25 20:49:29\",\"frequency\":1,\"appears\":1," +
                "\"confidence\":18.18}}");

        StopForumSpamResponse response = ResultParser.parseJson(is);
        assertEquals(true, response.isSuccess());
        assertEquals(responseDate, response.getLastSeen());
        assertEquals(1, response.getFrequency());
        assertEquals(1, response.getAppears());
        assertTrue(Float.compare(response.getConfidence(), 18.18f) == 0);
    }

    @Test
    public void testUsernameQueryNegative() {
        InputStream is = toInputStream("{\"success\":1,\"username\":{\"" +
                "frequency\":0,\"appears\":0}}");

        StopForumSpamResponse response = ResultParser.parseJson(is);
        assertEquals(true, response.isSuccess());
        assertEquals(null, response.getLastSeen());
        assertEquals(0, response.getFrequency());
        assertEquals(0, response.getAppears());
        assertTrue(Float.compare(response.getConfidence(), 0) == 0);
    }

    @Test
    public void testIPQueryNegative() {
        InputStream is = toInputStream("{\"success\":1,\"ip\":{\"" +
                "frequency\":0,\"appears\":0}}");

        StopForumSpamResponse response = ResultParser.parseJson(is);
        assertEquals(true, response.isSuccess());
        assertEquals(null, response.getLastSeen());
        assertEquals(0, response.getFrequency());
        assertEquals(0, response.getAppears());
        assertTrue(Float.compare(response.getConfidence(), 0) == 0);
    }

    @Test
    public void testEmailQueryNegative() {
        InputStream is = toInputStream("{\"success\":1,\"email\":{\"" +
                "frequency\":0,\"appears\":0}}");

        StopForumSpamResponse response = ResultParser.parseJson(is);
        assertEquals(true, response.isSuccess());
        assertEquals(null, response.getLastSeen());
        assertEquals(0, response.getFrequency());
        assertEquals(0, response.getAppears());
        assertTrue(Float.compare(response.getConfidence(), 0) == 0);
    }

    public static InputStream toInputStream(String str) {
        return new ByteArrayInputStream(str.getBytes(Charset.forName("UTF-8")));
    }

}
