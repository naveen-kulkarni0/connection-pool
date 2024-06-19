package org.example

import kotlinx.coroutines.runBlocking
import java.sql.Connection
import java.sql.DriverManager.getConnection
import java.util.concurrent.ArrayBlockingQueue
import kotlin.system.measureTimeMillis


val queue = ArrayBlockingQueue<Connection>(10)

fun main() {
    createConnections()
    val timeMillis = measureTimeMillis {
        runBlocking {
            for (i in 1..10000) {
                executeQueryUsingPool()
            }
        }
    }
    println("Total time taken to execute is $timeMillis")

}

fun createConnections(){
    for(i in 1..10){
        openConnection()?.let { queue.put(it) }
    }
}

fun openConnection(): Connection?{
    val url = "jdbc:mysql://localhost:3306/mydatabase"
    val user = "root"
    val password = "password"

    val connection: Connection

    try {
        // Get connection using helper function
        return getConnection(url, user, password)

        // Perform database operations here...

    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

fun executeQueryUsingPool(){
        val conn = queue.take()
        println(conn.nativeSQL("select * from users;"))
        queue.put(conn)
}

fun executeDirectly() {
    println(openConnection()?.nativeSQL("select * from users;"))
}