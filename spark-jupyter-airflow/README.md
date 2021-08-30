## Spark + Jupyter + Airflow

Pyspark on Jupyter Notebooks + Scheduling spark jobs on airflow

**To submit scala projects** start by building a fat JAR with all of the project dependencies included

```bash
 sbt assembly
```

Now we have an Executable JAR which can be umm executed obviously

```bash
java -jar target/scala-2.12/spark_airflow-assembly-fatjar-1.0.jar
```

Assuming spark is all setup locally with all the PATHs necessary

You can start a standalone master server by executing:

```bash
start-master.sh
```
Now start one or more workers and connect them to the master
```bash
start-slave.sh spark://mattdaemon:7077 # API changes to start-worker
```
Once you have started a worker, look at the master’s web UI (http://localhost:8080 by default). You should see the new node listed there, along with its number of CPUs and memory (minus one gigabyte left for the OS).

Once started, the master will print out a spark://HOST:PORT URL for itself, which you can use to connect workers to it, or pass as the "master" argument to SparkContext. You can also find this URL on the master’s web UI, which is http://localhost:8080 by default.

Submit the Job to run the application

```
spark-submit \
--master local \
--class "spark_airflow.SparkExample" \
./target/scala-2.12/spark_airflow-assembly-fatjar-1.0.jar
```

Submit Pyspark Job

```
spark-submit \
--master local \
pyspark/main.py 10
```
Spark 2.x versions have a issue with later version of JVM when running Python, so run `sudo update-alternatives --config java` and move to Java 8

**Airflow** change the `spark_default` Airflow connection with `spark://spark` in Host field and Port `7077`

**Jupyter** is up on http://localhost:8888, open up `notebook.ipynb` connect to standalone cluster and go nuts 🥜
