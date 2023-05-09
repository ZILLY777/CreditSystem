# CreditSystem
Проект специально для МТС Фентех Академия.
Структура проекта:
-src
 -main
  -docker (для запуска docker образов)
  -java
   -component (для компонентов проекта)
   -configuration
   -constants
   -controller
   -dto
   -entity
    -kafka
    -response
    -tables
   -exceptions
    -handlers (handler для ошибок в представленных в папке)
   -repository
    -impl (реализации интерфейсов ниже)
    -interfaces
   -service
    -impl(реализации интерфейсов ниже)
    -interfaces
     

Как запустить проект:
-Потребуется версия java не ниже 17.
-Зайдите в директорию проекта и запустите команду: mvnw package -DskipTests
-После скопируйте .jar файл из папки ...demo/target в ..demo/src/main/docker
-Находясь в этом пути пропишите docker-compose up.
-Приложение доступно будет по порту 8080.
