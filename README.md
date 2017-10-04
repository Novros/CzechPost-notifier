Actual status: **work in progres!!!** Build status: [![build_status](https://travis-ci.org/Novros/CzechPost-notifier.svg?branch=master)](https://travis-ci.org/Novros/CzechPost-notifier)

# CzechPost-notifier
Simple springboot aplication which will automaticaly check parcel status and if status is updated, then it will send notification(an email) to corresponding user. Application supports login, working with tracking numbers, manual refresh and displaying parcel statuses.

## Deployment
Just clone source and run in project folder: "./gradlew build" and jar file is in build/libs folder. Or "./gradlew buildDocker" for docker image. And just start docker image (do not forget redirect port 8080).

## Used technologies
Java 8
Springboot (Rest client, Rest service, MVC)
Thymeleaf

## Microservice architecture
For this project in microservice architecture see https://github.com/Novros/CzechPost-notifier-microservices. But that repository is not updated and maybe never will be.
