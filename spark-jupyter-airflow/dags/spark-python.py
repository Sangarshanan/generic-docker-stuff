from airflow import DAG
from airflow.operators.dummy_operator import DummyOperator
from airflow.contrib.operators.spark_submit_operator import SparkSubmitOperator
from datetime import datetime, timedelta

now = datetime.now()

default_args = {
    "owner": "airflow",
    "depends_on_past": False,
    "start_date": datetime(now.year, now.month, now.day),
    "email": ["airflow@airflow.com"],
}

dag = DAG(
        dag_id="spark-python", 
        description="Spark and Python but in a DAG",
        default_args=default_args, 
        schedule_interval=timedelta(1)
    )

start = DummyOperator(task_id="kickoff", dag=dag)

spark_job_pi = SparkSubmitOperator(
    task_id="spark_job_pi",
    application="/usr/local/spark/pyspark/calculate-pi.py",
    name="calculate-pi",
    dag=dag)

start >> spark_job_pi
