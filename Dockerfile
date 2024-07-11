FROM bellsoft/liberica-openjdk-debian:17

WORKDIR /wallet-app

COPY out/artifacts/wallets_service_jar/wallets-service.jar .

ENTRYPOINT ["java", "-jar", "wallets-service.jar"]