package com.matamansadevisdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.matamansadevisdbc.utilities.UtilityManager;

public class LoginAction extends Action{
	
	private final static String ADMIN = "admin";
	private final static String MEMBER = "member";
	private final static String HOME = "home";
	private final static String FAILURE = "failure";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*DataSource dataSource=null;
		Connection connection=null;
		dataSource = getDataSource(request);
		connection = dataSource.getConnection();*/
//		System.out.println(connection.toString());
		Connection conn = UtilityManager.getConnection();
		PreparedStatement psStatement = conn.prepareStatement("select count(*) from member where memberName=? and password=? and memberType=?");
		
		System.out.println("Inside Action Servlet================================");
		LoginForm loginForm = (LoginForm) form;
		System.out.println(loginForm.getMemberType() + loginForm.getUserName()+loginForm.getPassword());
		if (loginForm.getUserName()!=null && loginForm.getPassword()!=null && loginForm.getMemberType()!=null) {
			psStatement.setString(0, loginForm.getUserName());
			psStatement.setString(1, loginForm.getPassword());
			psStatement.setString(2, loginForm.getMemberType());
			ResultSet rs = psStatement.executeQuery();
			System.out.println("before result set");
			while(rs.next())
			{
				System.out.println(rs.getString(0));
			}
			if(loginForm.getMemberType().trim().equals("Admin"))
			{
				return mapping.findForward(ADMIN);
			}
			else if(loginForm.getMemberType().trim().equals("Member"))
			{
				return mapping.findForward(MEMBER);
			}
			else
				return mapping.findForward(HOME);
			
		} else {
			
			return mapping.findForward(FAILURE);
		}
	}
}
