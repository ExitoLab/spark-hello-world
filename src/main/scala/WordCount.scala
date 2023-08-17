import org.apache.spark.sql.SparkSession

object WordCountSpark {
  def main(args: Array[String]): Unit = {
    // Create a SparkSession
    val spark = SparkSession.builder()
      .appName("WordCountSpark")
      .master("local[*]") // Use appropriate master URL for your cluster
      .getOrCreate()

    try {
      import spark.implicits._ // Import implicit encoders

      // Read the input text file into a DataFrame
      val inputPath = "custom_folder/input.txt" // Replace with the actual path
      val textDF = spark.read.textFile(inputPath)

      // Perform word count using DataFrame API
      val wordsDF = textDF
        .flatMap(line => line.split(" "))
        .filter(word => word.nonEmpty)
        .groupBy("value")
        .count()
        .orderBy($"count".desc) // Use DataFrame API for ordering

      // Show the word count results
      wordsDF.show()

      // Optionally, write the results to an output file
      val outputPath = "custom_folder/output" // Replace with the desired output path
      wordsDF.write.csv(outputPath)

      println("Word count completed successfully.")
    } catch {
      case ex: Exception =>
        println(s"An error occurred: ${ex.getMessage}")
    } finally {
      // Stop the SparkSession
      spark.stop()
    }
  }
}
