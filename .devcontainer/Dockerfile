FROM arm64v8/debian:bullseye

# Install Maven
ADD https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz /tmp/maven.tar.gz

# Install necessary packages
RUN apt-get update && \
    apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    git \
    gnupg \
    openjdk-17-jdk \
    openssh-client \
    procps \
    software-properties-common \
    wget \
    && apt-get clean && \
    rm -rf /var/lib/apt/lists/* \
    && mkdir -p /usr/share/maven && \
    tar -xzf /tmp/maven.tar.gz -C /usr/share/maven --strip-components=1 && \
    rm /tmp/maven.tar.gz && \
    ln -s /usr/share/maven/bin/mvn /usr/bin/mvn && \
    useradd -m -s /bin/bash -u 1000 -g 100 pi && \
    mkdir -p /home/pi/.ssh && \
    chown -R pi:users /home/pi/.ssh && \
    chmod 700 /home/pi/.ssh

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-arm64
# Set the default user
# Set Maven environment variables
ENV MAVEN_HOME=/usr/share/maven
ENV PATH=${PATH}:${MAVEN_HOME}/bin

USER pi
WORKDIR /home/pi

RUN echo "alias ll='ls -l'" >>/home/pi/.bashrc

CMD ["/bin/bash"]