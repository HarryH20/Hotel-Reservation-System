FROM ubuntu:latest

# Install OpenJDK, Xfce desktop environment, and X11 utilities
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y \
    openjdk-17-jdk \
    xfce4 \
    xfce4-goodies \
    xorg \
    x11-xserver-utils \
    dbus-x11 \
    && rm -rf /var/lib/apt/lists/*

# Set up a non-root user for running the application (optional but recommended)
RUN useradd -ms /bin/bash user
USER user
WORKDIR /home/user

# Copy your application JAR and resources
COPY target/CSI-3471-group-project-1.0-SNAPSHOT-jar-with-dependencies.jar /home/user/your-application.jar
COPY src/main/resources /home/user/src/main/resources


# Run the JAR file
CMD ["java", "-jar", "/home/user/your-application.jar"]

