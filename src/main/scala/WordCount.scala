import org.apache.spark.sql.SparkSession

object WordCount {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("WordCount")
      .master("local[*]") // Use appropriate master URL for your cluster
      .getOrCreate()

    try {
      val inputPath = "Custom_folder/input.txt" // Relative path to the developer's input file
      val outputPath = "Custom_folder/output"   // Relative path for the output directory

      val textRDD = spark.sparkContext.textFile(inputPath)
      val wordCountRDD = textRDD
        .flatMap(line => line.split(" "))
        .map(word => (word, 1))
        .reduceByKey(_ + _)

      wordCountRDD.saveAsTextFile(outputPath)
      println("Word count completed successfully.")
    } catch {
      case ex: Exception =>
        println(s"An error occurred: ${ex.getMessage}")
    } finally {
      spark.stop()
    }
  }
}