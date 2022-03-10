import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-course-chat',
  templateUrl: './course-chat.component.html',
  styleUrls: ['./course-chat.component.scss']
})
export class CourseChatComponent implements OnInit {

  courseId: Number;
  stompClient;
  sender: string;
  text: string;
  chatMessages: string[] = [];


  constructor(
    private route: ActivatedRoute,
    private authService: AuthService) { }

  ngOnInit(): void {
    this.courseId = Number(this.route.snapshot.paramMap.get('courseId'));
    this.connect();
  }

  connect() {
    const ws = new SockJS('/api/stomp');
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({'X-Authorization' : 'Bearer ' + this.authService.getToken()}, frame => {   
      console.log('Connected: ' + frame);
      this.subscribeToCourseChat();
    });
    
  }

  sendMessage() {
    this.stompClient.send('/app/chat', {}, JSON.stringify({
      "sender": this.sender,
      "courseId": this.courseId,
      "text": this.text
    }));
  }

  subscribeToCourseChat() {
    this.stompClient.subscribe('/topic/courseChat/' + this.courseId, 
        message => {
          const senderAndText = message.body;
          this.chatMessages.push(senderAndText);
        }
    );
  }
  
}
