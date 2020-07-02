package client

import java.io.BufferedReader
import java.io.PrintWriter
import java.util.*


class SendThread(
    private val pw: PrintWriter,
    private val bufferedReader: BufferedReader
): Thread(){

    override fun run() {
        while(true){
            pw.println("message:${bufferedReader.readLine()}")
        }
    }
}