package nl24.report;

import java.util.Date;
import java.util.List;

import newlaw.bpm.keydate.KeyDate;
import newlaw.bpm.report.ReportService;
import nl24.domain.Broker;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class Nl24ReportService extends ReportService {

	private String keyDatesBetweenDatesByBrokerQuery;
	
	public int findCountKeyDatesBetweenDatesByBroker(Broker broker, String phase, String transition, Date start,
			Date end) {
		return KeyDate.entityManager()
				.createQuery(keyDatesBetweenDatesByBrokerQuery.replaceFirst("%select", "count(*)"), Long.class)
				.setParameter("broker", broker).setParameter("phase", phase).setParameter("transition", transition)
				.setParameter("start", start).setParameter("end", end).getSingleResult().intValue();
	}

	public List<KeyDate> findKeyDatesBetweenDatesByBroker(Broker broker, String phase, String transition, Date start, Date end) {
		return KeyDate.entityManager()
				.createQuery(keyDatesBetweenDatesByBrokerQuery.replaceFirst("%select", "a"), KeyDate.class)
				.setParameter("broker", broker).setParameter("phase", phase).setParameter("transition", transition)
				.setParameter("start", start).setParameter("end", end).getResultList();
	}
}
