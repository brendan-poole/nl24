package newlaw.bpm.attachment;

import newlaw.bpm.BpmException;
import newlaw.bpm.processinstance.ProcessInstance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service
public class AttachmentService {
	@Transactional
	public void saveAttachment(ProcessInstance processInstance, CommonsMultipartFile file, String code, String name, String description) {
		if(file != null) {
			if(code == null) 
				throw new BpmException("File code not set.");
			Attachment a = processInstance.getProcessDefinition().getService().createAttachment();
			a.setFileData(file.getBytes());
			a.setFileCode(code);
			a.setProcessInstance(processInstance);
			a.persist();
			//processInstance.getAttachments().add(a);
		}
	}


}
