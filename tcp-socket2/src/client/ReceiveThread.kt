package client

import java.io.BufferedReader


class ReceiveThread(
    private val bufferedReader:BufferedReader
):Thread(){


    override fun run() {
        while(true){
            val message = bufferedReader.readLine()
            if(message === null) return
            println(message)
        }
    }
}