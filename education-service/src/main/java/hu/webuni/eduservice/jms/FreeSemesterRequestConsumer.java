package hu.webuni.eduservice.jms;

import javax.jms.Topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import hu.webuni.eduservice.xmlws.StudentXmlWs;
import hu.webuni.jms.dto.FreeSemesterRequest;
import hu.webuni.jms.dto.FreeSemesterResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FreeSemesterRequestConsumer {
	
	private final StudentXmlWs studentXmlWs;
	private final JmsTemplate jmsTemplate;

	@JmsListener(destination = "free_semester_requests")
	public void onFreeSemesterRequest(Message<FreeSemesterRequest> message) {
		int studentId = message.getPayload().getStudentId();
		
		FreeSemesterResponse response = new FreeSemesterResponse();
		response.setStudentId(studentId);
		response.setNumFreeSemesters(studentXmlWs.getFreeSemesterByStudent(studentId));
		
		jmsTemplate.convertAndSend((Topic)message.getHeaders().get(JmsHeaders.REPLY_TO), response);
	}
}
