package edu.ecnu.bugIR.analysis;

import java.util.ArrayList;
import java.util.List;

import edu.ecnu.bugIR.analysis.model.BugReport;

/** 
 * @description: 从文件中读取Bug信息
 * @author: "Song Leyi"  2012-6-29
 * @version: 1.0 
 * @modify: 
 */
public class FileBugReader implements BugReader {

    @Override
    public List<BugReport> getMeegoBugs() {
        //暂无
        return new ArrayList<BugReport>();
    }

}
