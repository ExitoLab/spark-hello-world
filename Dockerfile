# Use the official Apache Spark base image
FROM apache/spark:v3.3.0

# Set the working directory inside the container
WORKDIR /app

# Copy the Spark application JAR and any required files to the container
COPY target/scala-2.12/sparkwordcount_2.12-1.0.jar /app
COPY custom_folder/input.txt /app/custom_folder/input.txt

# Expose the port your Scala application will listen on
EXPOSE 8080

# Command to run the Spark application
CMD ["java", "-jar", "sparkwordcount_2.12-1.0.jar"]