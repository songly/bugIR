package edu.ecnu.bugIR.analysis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import edu.ecnu.bugIR.analysis.model.BugReport;
import edu.ecnu.bugIR.util.DB;
import edu.ecnu.bugIR.util.TestLoggerFactory;

/** 
 * @description: 从数据库中读取bugzilla信息
 * @author: "Song Leyi"  2012-6-29
 * @version: 1.0 
 * @modify: 
 */
public class DBBugReader implements BugReader {

    Logger logger=TestLoggerFactory.getLogger();
    
    @Override
    public List<BugReport> getMeegoBugs() {
        // 从Database meego_bugzilla中获取
        DB bugDB=new DB();
        Connection conn=bugDB.init();
        List<BugReport> bugzillaList = new ArrayList<BugReport>();
        
        //读取bugzilla_meego字段
        String bugSQL="select bugs.bug_id,bugs.short_desc,comments,product_id,component_id,version,priority " +
        		" from bugs left join bugs_fulltext on bugs.bug_id=bugs_fulltext.bug_id";
        
        ResultSet rs=bugDB.get(bugSQL,conn);
        try {
            while(rs.next()){
                BugReport br=mapBug(rs);
                bugzillaList.add(br);
            }
        }
        catch (SQLException e) {
            logger.error("读取bugzilla数据异常!",e);
        }
        bugDB.closeConnection(conn);
        return bugzillaList;
    }

    /**
     * 将数据集映射为BugReport对象
     * @param rs
     * @return
     * @throws SQLException 
     */
    private BugReport mapBug(ResultSet rs) throws SQLException {
        // 封装数据集
        BugReport br=new BugReport();
        br.setBugId(rs.getInt("BUG_ID"));
        br.setSumm(StringUtils.trim(rs.getString("short_desc")));
        br.setDesc(StringUtils.trim(rs.getString("comments")));
        br.setProd(rs.getInt("product_id"));
        br.setComp(rs.getInt("component_id"));
        br.setVersion(StringUtils.trim(rs.getString("version")));
        br.setPrio(StringUtils.trim(rs.getString("priority")));
        
        return br;
    }

}
