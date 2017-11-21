package com.airtelboost.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class Report {

	LinkedHashMap<String, String> ll = null;
	LinkedHashSet<String> s = null;
	Set<String> keys = null;
	PrintWriter pw = null;

	public void reportGatherer(LinkedHashMap<String, String> result, PrintWriter pw, String Msisdn) throws IOException {
		ll = result;

		keys = ll.keySet();
		pw.println("<title>Airtel Boost</title></head><body bgcolor=\"#50fff6\">");
		for (String k : keys) {
			if (k.equals("headerTag")) {
				if (ll.containsKey(k)) {
					String Title[] = ll.get("headerTag").trim().split(",");
					pw.print("<TABLE BORDER><TR bgcolor=\"#041E87\">");
					for (String s : Title) {
						pw.print("<TH <p><font color=\"white\">" + s.trim() + "</font></p></TH>");
					}
				}
				pw.print("</TR>");
			}

		}

		for (String k : keys) {
			if (k.equals(Msisdn)) {
				if (ll.get(k).contains("#")) {
					String s[] = ll.get(k).split("#");
					for (String str : s) {
						String val[] = str.split(",");
						pw.println("<TR bgcolor=\"#8B8BD0\" >");
						for (String st : val) {
							pw.print("<TD align=\"center\">" + st.trim());
						}
						pw.print("</TD>");
					}
				} else {
					String Values[] = ll.get(k).split(",");
					pw.print("<TR bgcolor=\"#8B8BD0\">");
					for (String s : Values) {
						pw.print("<TD align=\"center\">" + s.trim());
					}
					pw.print("</TD>");
				}
			}
		}
		pw.println("</TABLE>");
		pw.println("</body></html>");
		pw.close();

	}

	public void reportUnavail(PrintWriter pw) {

		pw.println("<html><head>");
		pw.println("<title>Airtel Boost</title></head><body bgcolor=\"#50fff6\">");
		pw.println("<h1>Data UnAvailable for the searched msisdn!!</h1>");
		pw.println("</body></html>");
	}

}