# union

Инструкция для генерации api-client:
Полезное:
1) Источник : https://github.com/OpenAPITools/openapi-generator#overview
2) Показать описание флагов : openapi-generator help generate
3) https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/kotlin.md - параметры генерации для kotlin
4) Если требуется чтобы какой-нибудь файл не генерировался или не перезаписывался при следующей генерацию то нужно добавить его в
   .openapi-generator-ignore

Генерация:
openapi-generator generate \
-i app/api-docs.json \
-g kotlin \
-o полный_путь_до_файла/ozna-android/ozna-api-client \
--additional-properties=library=jvm-retrofit2,useCoroutines=true,dateLibrary=string

TODO:
- требуется разобраться с датами в DTO моделях, сейчас дата представляется в формате string, используя параметр dateLibrary=string

Инструкция при добавление/обновление UI существующего экрана:
Если добавляем новый экран, то необходимо добавить его в app/src/androidTest/kotlin/ru/interid/weatherford/ScreensTest.kt

Пререквизиты (делается один раз для фикса проблем)
1) Удалить все данные с эмулятора Device Manager -> Wipe Data

Запуск Shot'a для получения репорта
1) чистим старые скриншоты на эмуляторе : ./gradlew debugRemoveScreenshotsBefore
2) удаляем текущую папрку с репортами: rm -R reports
3) запускаем gradle task: ./gradlew executeScreenshotTests -Precord
4) открываем страницу с отчетом: open -a "Google Chrome" reports/index.html

Архитектура:
- Clean architecture
- MVI (https://github.com/arkivanov/MVIKotlin) + Jetpack Compose (https://developer.android.com/jetpack/compose) для presentation layer

Асинхронная работа: 
- Coroutines  & Flow

DI: 
- Koin (https://insert-koin.io/)

Навигация:
- Navigation Component (https://developer.android.com/guide/navigation)

Генерация шаблонного кода для фичи:  
- https://github.com/steam0111/android-multimodule-plugin, шаблоны лежит в папке geminio
  
