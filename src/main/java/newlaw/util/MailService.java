package newlaw.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

@RooJavaBean
@Service
public class MailService {
	
	@Autowired
	private JavaMailSender mailSender;

	public void sendMailWithAttachment(String from, String to, String subject, String body, String filename, InputStreamSource inputStreamSource) {

		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(to);
		smm.setFrom(from);
		smm.setSubject(subject);
		smm.setText(body);

		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setText(body);
			helper.setSubject(subject);
			helper.setFrom(from);
			helper.setTo(to);
			helper.addAttachment(filename, inputStreamSource);

		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
		mailSender.send(message);
	}

	public void sendMail(String from, String to, String subject, String body) {

		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(to);
		smm.setFrom(from);
		smm.setSubject(subject);
		smm.setText(body);

		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setText(body);
			helper.setSubject(subject);
			helper.setFrom(from);
			helper.setTo(to);

		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
		mailSender.send(message);
	}
	
}