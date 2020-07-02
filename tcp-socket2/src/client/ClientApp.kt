package client

import java.io.*


import java.net.Socket
import java.nio.charset.StandardCharsets
import java.util.*


object ClientApp {
    private const val SERVER_IP = "localhost"
    private const val SERVER_PORT = 9999

    @JvmStatic
    fun main(args: Array<String>) {
        var name: String? = null
//        val scanner = Scanner(System.`in`)
        val console = BufferedReader(InputStreamReader(System.`in`))
        while (true) {
            println("대화명을 입력하세요.")
            print(">>> ")
            name = console.readLine()
            if (name.isNotEmpty()) {
                break
            }
            println("대화명을 입력해주세요.\n")
        }
//        scanner.close()
        try {
            val socket = Socket(SERVER_IP, SERVER_PORT)
            println("채팅방에 입장하였습니다.")
            val pw = PrintWriter(OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)
            val br = BufferedReader(InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8))
            val request = "join:$name\r\n"
            pw.println(request)
            SendThread(pw, console).start()
            ReceiveThread(br).start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}
