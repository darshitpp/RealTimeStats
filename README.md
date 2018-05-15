# RealTimeStats

##### Challenge details: https://drive.google.com/file/d/0ByZz7te0reC7OGktN3A3MXBQS2xQRTREajRoakNaVVdJRnlr/view?usp=drivesdk

API endpoints:

* `POST`: `/transactions` should be called every time a transaction is executed
* `GET`: `/statistics` should be called when you want the statistics of the transactions executed in the last 60 seconds

It uses the thread safe `ExpiringMap`[1] to automatically invalidate transactions older than 60 seconds.

It also uses `AtomicLong`[2] and `AtomicDouble`[3] to ensure the generation of statistics is thread safe too.

-------------------------------------------

##### `POST`: `/transactions` require a JSON request body of the type `application/json` and form: 

```
{
	"amount":47,
	"timestamp":1525694370380
}
```

It returns the HTTP Status Code `201` if the transaction is valid, and `204` if the transaction is older than 60 seconds.

-------------------------------------------

##### `GET`: `/statistics` executes in `O(1)` time and space, and returns:  

```
{
    "sum": 87,
    "avg": 43.5,
    "min": 40,
    "max": 47,
    "count": 2
}
```
which are the statistics of the transactions occurring within the last 60 seconds.

[1]: https://github.com/jhalterman/expiringmap

[2]: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/AtomicLong.html

[3]: http://javadocs.okadatech.com/javadoc/guava-14.0.1/reference/com/google/common/util/concurrent/AtomicDouble.html
