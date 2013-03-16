package nl24.report;

import java.util.Date;

import nl24.domain.Broker;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class ReportCommand {

	private Date startDate;
	private Date endDate;
	private String claimRef;
	private ReportType reportType;
	private String testHql;
	private Broker broker;
	
}
