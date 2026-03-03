FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]

#FROM node:18-alpine
#
#WORKDIR /app
#
#COPY package*.json ./
#RUN npm ci --only=production
#
#COPY . .
#
#EXPOSE 3000
#
#CMD ["node", "src/index.js"]