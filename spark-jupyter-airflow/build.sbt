lazy val root = (project in file(".")).
  settings(
    name := "spark_airflow",
    version := "1.0",
    scalaVersion := "2.12.7",
    mainClass in Compile := Some("spark_airflow.SparkExample"),
    mainClass in assembly := Some("spark_airflow.SparkExample")
  )

val sparkVersion = "2.4.4"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion

assemblyJarName in assembly := "spark_airflow-assembly-fatjar-1.0.jar"

// META-INF discarding
assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}

