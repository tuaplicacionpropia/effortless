package org.effortless.zkstrap;

import org.effortless.orm.DbManager;
import org.effortless.orm.MySession;
import org.zkoss.zk.ui.Session;

public class UpdateServlet extends org.zkoss.zk.au.http.DHtmlUpdateServlet {

	public UpdateServlet () {
		super();
	}
	
	public void process(Session sess, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, boolean compress) throws javax.servlet.ServletException, java.io.IOException {
//		long start1 = System.currentTimeMillis();
		DbManager db = (DbManager)sess.getAttribute("_DB_");
		if (db != null) {
			MySession.setDb(db);
		}
//		long end1 = System.currentTimeMillis();
//		long time1 = end1 - start1;

		super.process(sess, request, response, compress);
		
//		long start2 = System.currentTimeMillis();
		sess.setAttribute("_DB_", MySession.getDb());

//		long end2 = System.currentTimeMillis();
//		long time2 = end2 - start2;

//		long time = time1 + time2;
//		System.out.println("TIME =" + time);
	}
	
}
