package com.trendyol.bootcamp.homework

import org.apache.spark.sql.catalyst.expressions.UnixTimestamp
import org.apache.spark.sql.{DataFrame, Encoders, SaveMode, SparkSession}
import org.apache.spark.sql.expressions.{UserDefinedFunction, Window}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row

import java.io.File
import java.nio.file.{Files, StandardCopyOption}
import scala.reflect.io.Directory
import scala.util.Try

object ProductMergerJob {

  def main(args: Array[String]): Unit = {

    // Initialize a spark session
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("Trendyol Week5 Homework")
      .getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

    // Define the schema
    val schema = StructType(
      List(
        StructField("id", IntegerType),
        StructField("name", StringType),
        StructField("category", StringType),
        StructField("brand", StringType),
        StructField("color", StringType),
        StructField("price", FloatType),
        StructField("timestamp", TimestampType)
      )
    )

    // If it's first run create an empty DataFrame, if not read the current data
    val upToDateData = Try(
      spark.read
        .schema(schema)
        .json("output/homework/target")
    ).getOrElse(spark.createDataFrame(spark.sparkContext.emptyRDD[Row], schema))

    println("Current Data")
    upToDateData.show

    // Specify the path according to first run or not
    val path = {
      if (upToDateData.count()==0){
        "data/homework/initial_data.json"
      } else {
        "data/homework/cdc_data.json"
      }
    }

    println("The path contains new data")
    println(path)

    // Get the cdc data (if it's first run get initial_data)
    val cdcData = spark.read
      .schema(schema)
      .json(path)

    // Define the Window to get latest timestamps
    val rankWindow = Window.partitionBy("id").orderBy(col("timestamp").desc)

    // Union the data Frame, apply window over rankWindow and take first row_numbers only, drop rownum and, repartition
    // Write the resultant DataFrame into temp directory
    val target = upToDateData.union(cdcData)
      .withColumn("rownum", row_number().over(rankWindow))
      .filter(col("rownum") === 1)
      .drop("rownum")
      .orderBy("id")
      .repartition(1)
      .write
      .mode(SaveMode.Overwrite)
      .json("output/homework/temp/")

    // Delete full target directory
    new Directory(new File("output/homework/target/")).deleteRecursively()

    // Temp and Target directory
    val tempDir = new File("output/homework/temp").toPath
    val targetDir = new File("output/homework/target").toPath

    // Move the files from temp directory to final directory
    Files.move(tempDir, targetDir, StandardCopyOption.ATOMIC_MOVE)

    // Delete the temp directory
    new Directory(new File("output/homework/temp")).deleteRecursively()

  }
}