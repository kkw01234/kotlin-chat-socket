package server

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket
import java.nio.charset.StandardCharsets

class ServerProcessThread(
    private val socket: Socket,
    private val listWriters: MutableList<PrintWriter>
) : Thread() {

    private lateinit var nickname:String

    override fun run() {
        val bufferedReader: BufferedReader =
            BufferedReader(InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))
        val printWriter = PrintWriter(OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))
        while (true) {
            val request:String? = bufferedReader.readLine()
            if (request == null) {
                println("클라이언트로부터 연결 끊김");
                doQuit(printWriter)
                break;
            }
            val tokens = request.split(":")
            when(tokens[0]){
                "join"->
                    doJoin(tokens[1], printWriter)
                "message"->
                    doMessage(tokens[1])
                "quit"->
                    doQuit(printWriter)
            }
        }
    }


    private fun removeWriter(writer: PrintWriter) {
        listWriters.remove(writer)
    }

    private fun doMessage(data: String) {
        broadcast(this.nickname + ":" + data)
    }

    private fun doJoin(nickname: String, writer: PrintWriter) {
        val data = nickname + "님이 입장하였습니다."
        this.nickname = nickname
        broadcast(data)
        addWriter(writer)
    }

    private fun addWriter(writer: PrintWriter) {
       synchronized(listWriters){ listWriters.add(writer) }
    }

    private fun broadcast(data: String) {
        println("$data, ${listWriters.size}")
        synchronized(listWriters) {
            for (writer in listWriters) {
                writer.println(data)
                writer.flush()
            }
        }

    }

    private fun doQuit(writer: PrintWriter) {
        removeWriter(writer)
        val data: String = this.nickname + "님이 퇴장했습니다."
        broadcast(data)
    }

}
