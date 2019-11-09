import java.io.PrintWriter
import java.net.ServerSocket
import java.util.*

/** Entire project written by Lauri Nissinen
 *
 * Server-class of the server project. It handles the connections to clients and also creates the infeed and outfeed for text.
 * Server can be started by calling serve-method from this class.
 */

class ChatServer {

    fun serve(){
        val ss = ServerSocket(1994, 2)
        while(true) {
            println("Accepting clients")
            val s = ss.accept()
            println("Client accepted")

            val out = PrintWriter(s.getOutputStream(), true)
            val ins = Scanner(s.getInputStream())

            Thread(ChatConnector(ins, out)).start()
        }
    }
}