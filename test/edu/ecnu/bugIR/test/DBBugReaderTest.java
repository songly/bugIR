package edu.ecnu.bugIR.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ecnu.bugIR.analysis.BugReader;
import edu.ecnu.bugIR.analysis.DBBugReader;
import edu.ecnu.bugIR.analysis.model.BugReport;

public class DBBugReaderTest extends TestCase{

    BugReader reader=new DBBugReader();
    
    public DBBugReaderTest(String name) {
        super(name);
    }

    @Before
    public void setUp() throws Exception {
        
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetMeegoBugs() {
        List<BugReport> test=reader.getMeegoBugs();
        assertEquals("Result",22486,test.size());
    }

}
