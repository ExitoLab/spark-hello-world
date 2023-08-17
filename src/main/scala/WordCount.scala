import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object WordCountSpark {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("WordCountSpark")
      .master("local[*]") // Use appropriate master URL for your cluster
      .getOrCreate()

    try {
      val logger = org.apache.logging.log4j.LogManager.getLogger(getClass.getName)

      val inputPath = "custom_folder/input.txt" // Relative path to the developer's input file
      val outputPath = "custom_folder/output"   // Relative path for the output directory

      val textDF = spark.read.textFile(inputPath)
      val wordsDF = textDF.select(explode(split(col("value"), " ")).as("word"))

      val wordCountDF = wordsDF
        .groupBy("word")
        .count()

      wordCountDF.write.text(outputPath)
      logger.info("Word count completed successfully.")
    } catch {
      case ex: Exception =>
        val logger = org.apache.logging.log4j.LogManager.getLogger(getClass.getName)
        logger.error(s"An error occurred: ${ex.getMessage}", ex)
    } finally {
      spark.stop()
    }
  }
}
