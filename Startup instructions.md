# Инструкция по запуску проекта:
## Шаги для запуска автоматических тестов:
1. Установить на локальную машину среду для разработки - **IntelliJ IDEA Community Edition**
2. Установить на локальную машину **Docker Compose**
3. Запустить Docker Compose и IntelliJ IDEA
4. Открыть проект QA_Diploma в IntelliJ IDEA
5. В IntelliJ IDEA открыть терминал и запустить контейнер (в терминале прописать команду: docker compose up)
6. После запуска контейнера ("ready for connections") открыть .jar файл (aqa-shop.jar) тестируемого веб-сервиса при помощи команды в терминале (в IDEA открыть вторую страницу терминала) java -jar aqa-shop.jar
7. После запуска веб-сервиса, запустить автоматизированные тесты в терминале (команда: ./gradlew clean test -D db.url=jdbc:mysql://localhost:3306/app)