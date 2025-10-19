# Usa la imagen oficial de OpenJDK 22
FROM openjdk:22-jdk-slim
WORKDIR /usrapp/bin

# Expone el puerto de la aplicación
ENV PORT=8080
EXPOSE 8080

# Copia los archivos de compilación (Verifica que existan)
COPY target/classes /usrapp/bin/classes/
COPY target/dependency /usrapp/bin/dependency/
COPY src/main/resources/www /usrapp/bin/www/

# Comando para ejecutar la aplicación
CMD ["java", "-cp", "/usrapp/bin/classes:/usrapp/bin/dependency/*", "edu.eci.arep.lab5arep.Backend.Server"]
