FROM arm64v8/debian:bullseye

# Install necessary packages
RUN apt-get update && \
    apt-get install -y \
    curl \
    wget \
    gnupg \
    software-properties-common \
    apt-transport-https \
    ca-certificates \
    git \
    openssh-client \
    procps \
    && apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Install OpenJDK 17
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-arm64

# Install Maven
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz -O /tmp/maven.tar.gz && \
    mkdir -p /usr/share/maven && \
    tar -xzf /tmp/maven.tar.gz -C /usr/share/maven --strip-components=1 && \
    rm /tmp/maven.tar.gz && \
    ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# Set Maven environment variables
ENV MAVEN_HOME=/usr/share/maven
ENV PATH=${PATH}:${MAVEN_HOME}/bin

# Create a user 'pi' with the same UID/GID as your host user
RUN useradd -m -s /bin/bash -u 1000 -g 100 pi

# Set up SSH directory for the pi user
RUN mkdir -p /home/pi/.ssh && \
    chown -R pi:users /home/pi/.ssh && \
    chmod 700 /home/pi/.ssh

# Set the default user
USER pi
WORKDIR /home/pi

CMD ["/bin/bash"]