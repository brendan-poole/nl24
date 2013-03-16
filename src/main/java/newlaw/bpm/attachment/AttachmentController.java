package newlaw.bpm.attachment;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import newlaw.bpm.processinstance.ProcessInstance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AttachmentController {

	@RequestMapping(value = "/{id}/attachment/{attachmentCode}", method = RequestMethod.GET)
	public void getAttachment(@PathVariable("id") Long processId,
			@PathVariable("attachmentCode") String attachmentCode, HttpServletResponse response) {
		try {

			// XXX add this to service
			// XXX create unique for pi code
			Attachment a = (Attachment) Attachment.entityManager()
					.createQuery("select a from Attachment a where a.processInstance.id=? and a.fileCode=?")
					.setParameter(1, processId).setParameter(2, attachmentCode).getSingleResult();

			response.getOutputStream().write(a.getFileData());
			response.flushBuffer();
		} catch (IOException ex) {
			throw new RuntimeException("IOError writing file to output stream");
		}

	}

}
