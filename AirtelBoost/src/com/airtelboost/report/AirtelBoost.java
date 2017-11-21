package com.airtelboost.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AirtelBoost extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Logger logger;
	public static Properties props = new Properties();
	public static SimpleDateFormat sdf;
	public LinkedHashMap<String, String> result = null;

	public AirtelBoost() {
	}

	public void init(ServletConfig config) throws ServletException {
		try {
			logger = Logger.getLogger(AirtelBoost.class.getName());
			props.load(AirtelBoost.class.getClassLoader().getResourceAsStream("config.properties"));
			logger.info("Logger Name: " + logger.getName());
			Trigger.loginValues(props.getProperty("Credentials"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {

	}

	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String uri = request.getRequestURI();
		Trigger model = new Trigger();

		if (uri.contains("/loginValidation.do")) {
			String username = request.getParameter("uname");
			String password = request.getParameter("psw");

			Boolean result = model.loginValidation(username, password);

			// logger.info("Validation check is done");
			RequestDispatcher rd = null;
			if (result) {
				rd = request.getRequestDispatcher("report.jsp");
			} else {
				rd = request.getRequestDispatcher("Weird.html");
			}
			rd.forward(request, response);
		}

		if (uri.contains("/msisdnReport.do")) {

			String msisdn = request.getParameter("msisdn");
			String file = props.getProperty("FileDIR") + props.getProperty("FileName");
			PrintWriter pw = response.getWriter();
			Report mrd = new Report();
			result = (Trigger.msisdnReport(msisdn, file));
			if (!result.containsKey(msisdn)) {
				mrd.reportUnavail(pw);
			} else {
				mrd.reportGatherer(result, pw, msisdn);
			}
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			process(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			process(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}