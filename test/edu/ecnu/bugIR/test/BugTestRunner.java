package edu.ecnu.bugIR.test;

import junit.textui.TestRunner;

/** 
 * @description: 手动运行测试程序入口
 * @author: "Song Leyi"  2012-6-29
 * @version: 1.0 
 * @modify: 
 */
public class BugTestRunner {
    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        TestRunner runner=new TestRunner();
        runner.run(DBBugReaderTest.class);
        
//        BugReader reader=new DBBugReader();
//        List<BugReport> test=reader.getMeegoBugs();
//        System.out.println("size:"+test.size());
    }

}
