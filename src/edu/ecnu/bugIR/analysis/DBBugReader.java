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
 * @description: �����ݿ��ж�ȡbugzilla��Ϣ
 * @author: "Song Leyi"  2012-6-29
 * @version: 1.0 
 * @modify: 
 */
public class DBBugReader implements BugReader {

    Logger logger=TestLoggerFactory.getLogger();
    
    @Override
    public List<BugReport> getMeegoBugs() {
        // ��Database meego_bugzilla�л�ȡ
        DB bugDB=new DB();
        Connection conn=bugDB.init();
        List<BugReport> bugzillaList = new ArrayList<BugReport>();
        
        //��ȡbugzilla_meego�ֶ�
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
            logger.error("��ȡbugzilla�����쳣!",e);
        }
        bugDB.closeConnection(conn);
        return bugzillaList;
    }

    /**
     * �����ݼ�ӳ��ΪBugReport����
     * @param rs
     * @return
     * @throws SQLException 
     */
    private BugReport mapBug(ResultSet rs) throws SQLException {
        // ��װ���ݼ�
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
