package hu.webuni.university.ws;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	
	private final SimpMessagingTemplate messagingTemplate;
	
	@MessageMapping("/chat")
	@PreAuthorize("#chatMessage.sender == authentication.principal.username")
	public void onChatMessage(ChatMessage chatMessage) throws Exception{
		messagingTemplate.convertAndSend("/topic/courseChat/" + chatMessage.getCourseId(),
				String.format("%s: %s", chatMessage.getSender(), chatMessage.getText()));
	}

}
