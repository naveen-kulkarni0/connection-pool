# Connection Pool
This project signifies the importance of a connection pool and a simple implementation using blocking queue.
Blocking is the most common data structure to implement the connection poll.

# Run 
In order to run this you would need a mysql started and listening on port 3306 ( default ). You would also need to
either create `mydatabase` and `users` or modify the config with the correct values.
This app uses co-routines to spawn concurrent connections to mysql and interesting thing happens when we run this method
with `executeQueryUsingPool()` vs `executeDirectly()` which goes on to prove the importance of connection pool.
As and when the number of co-routines grow the `executeQueryUsingPool()` would not fail and execute all the queries
as we are using blocking queue. The `executeDirectly()` on the other hand starts to fail with too many connections