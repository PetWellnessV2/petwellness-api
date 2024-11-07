# Usar una imagen base ligera de OpenJDK 21 para ejecutar aplicaciones Java
FROM openjdk:21-jdk-slim

# Define la variable del archivo JAR
ARG JAR_FILE=target/petwellness-api-0.0.1.jar

# Copia el archivo JAR en el contenedor 
COPY ${JAR_FILE} petwellness-api.jar

# Exponer al puerto 8080
EXPOSE 8080

# Comando para ejecutar el archivo JAR
ENTRYPOINT ["java", "-jar", "petwellness-api.jar"]
