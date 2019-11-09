import java.io.PrintWriter
import java.net.ServerSocket
import java.util.*
fun main(args: Array<String>) {
    val ss = ServerSocket(30000, 2)
    while(true) {
        println("accepting")
        val s = ss.accept()
        println("accepted")
        val out = PrintWriter(s.getOutputStream())
        val ins = Scanner(s.getInputStream())
        Thread(CommandInterpreter(ins, out)).start()
    }
}
class CommandInterpreter(val ins: Scanner, val out: PrintWriter): Runnable {
    override fun run() {
        while(true) {
            out.print("Yessir? ")
            out.flush()
            val line = ins.nextLine()
            out.println("I read $line")
            if(line.equals("bye"))
                break
        }
    }
}