package com.example.socketwithspring.common.component.listener

import com.example.socketwithspring.common.model.ChatMessage
import com.example.socketwithspring.common.model.MessageType.LEAVE
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectedEvent

@Component
class WebSocketEventListener(
        val messagingTemplate:SimpMessageSendingOperations
){

    @EventListener
    fun handleWebSocketConnectListener(event:SessionConnectedEvent){
        println("Received a new web socket connection")
    }

    @EventListener
    fun handleWebSocketDisconnectionListener(event:SessionConnectedEvent){
        val headerAccessor = StompHeaderAccessor.wrap(event.message)
        headerAccessor.sessionAttributes?.get("username").let { username ->
            println("User Disconnected : $username")
            val chatMessage = ChatMessage("LEAVE","",username.toString())
            messagingTemplate.convertAndSend("/topic/public", chatMessage)
        }
    }
}