package server

import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

public class TcpServer{
    private lateinit var serverSocket:ServerSocket
    private val listWriter = arrayListOf<PrintWriter>()

    public fun start(port:Int){
        serverSocket = ServerSocket(port)
        println("Server start")

        while(true){
            val socket:Socket = serverSocket.accept()
            ServerProcessThread(socket, listWriter).start()
        }
    }
    public fun stop(){
        serverSocket.close()
    }
}