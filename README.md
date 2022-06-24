# union

Сохранение состояния фичи после ее уничтожения:
Иногда требуется сохранить и восстановить состояние фичи после ее удаления, чтобы это сделать необходимо :
1) Для фичи которой необходим сохранять состояние добавить stateSaver во ViewModel и в параметрах передать модуль на котором сохранять state (StudentsFlowModule)
   1.1 Добавить stateSaver

viewModel(STUDENTS_VIEW_MODEL_QUALIFIER) {
BaseViewModel(
store = get<StudentsStore>(),
stateSaver = getExternalStateSaver<StudentsStore.State, StudentsFlowModule>(STUDENTS_STATE_SAVER)
)
}
1.2 Восстановить состояние :

factory {
StudentsStoreFactory(
DefaultStoreFactory,
get(),
get(),
initialState = getSavedState<StudentsStore.State, StudentsFlowModule>(STUDENTS_STATE_SAVER)
).create()
}

2) Для фичи на которой сохраняется состояние добавть имя скоупа и сам скоуп для сохранения :
   2.1   
   viewModel(STUDENTSFLOW_VIEW_MODEL_QUALIFIER) {
   BaseViewModel(
   get<StudentsFlowStore>(),
   scopeQualifier = named<StudentsFlowModule>()
   )
   }
   2.2  
   scope<StudentsFlowModule> {
   scoped<StateSaver<StudentsStore.State>>(STUDENTS_STATE_SAVER) {
   InMemoryStateSaver()
   }
   }

Инструкция для генерации api-client:
Полезное:
1) Источник : https://github.com/OpenAPITools/openapi-generator#overview
2) Показать описание флагов : openapi-generator help generate
3) https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/kotlin.md - параметры генерации для kotlin
4) Если требуется чтобы какой-нибудь файл не генерировался или не перезаписывался при следующей генерацию то нужно добавить его в
   .openapi-generator-ignore
5) Скачать openApi файл для генерации api-docs.json
6) Путь к api-docs.json необходимо указывать относительно модуля в котором лежит файл например app/*** или some_api_client/***

Генерация:
openapi-generator generate \
-i модуль_в_котором_лежит_файл/api-docs.json \
-g kotlin \
-o полный_путь_до_файла/ozna-android/ozna-api-client \
--additional-properties=library=jvm-retrofit2,useCoroutines=true,dateLibrary=string

TODO:
- требуется разобраться с датами в DTO моделях, сейчас дата представляется в формате string, используя параметр dateLibrary=string
- регулярка для вырезания поля operationId,"operationId":"[a-zA-Z0-9_]{1,}"

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
- Добавляем плагин itrocket-geminio.zip (лежит в корне проекта) в android studio
- https://github.com/steam0111/android-multimodule-plugin, шаблоны лежит в папке geminio
