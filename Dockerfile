# Utiliser une image de base avec JDK 11 (ou une autre version appropriée)
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Installer les dépendances nécessaires pour Selenium (par exemple, ChromeDriver et Google Chrome)
RUN apt-get update && \
    apt-get install -y wget unzip xvfb \
    && wget -q -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/92.0.4515.107/chromedriver_linux64.zip \
    && unzip /tmp/chromedriver.zip -d /usr/local/bin/ \
    && wget -q -O /tmp/google-chrome-stable_current_amd64.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
    && apt-get install -y /tmp/google-chrome-stable_current_amd64.deb \
    && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Copier le fichier JAR de l'application dans l'image Docker
COPY target/Linkedin_Applications-0.0.1-SNAPSHOT.jar app.jar

# Définir la variable d'environnement SPRING_MAIL_PASSWORD
ENV SPRING_MAIL_PASSWORD=


# Exposer le port de l'application Spring Boot
EXPOSE 8080

# Définir la commande pour lancer l'application
ENTRYPOINT ["sh", "-c", "Xvfb :99 -ac -screen 0 1280x1024x16 & java -Djava.awt.headless=true -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver -Dwebdriver.chrome.whitelistedIps= -Dwebdriver.chrome.binary=/usr/bin/google-chrome -jar app.jar"]
