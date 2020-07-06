package com.example.socketwithspring.common.model

class ChatMessage(
        val type:String?,
        val content:String?,
        val sender:String?
)

enum class MessageType{
    CHAT,JOIN,LEAVE
}