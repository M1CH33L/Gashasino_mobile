# Gashasino Mobile

Gashasino Mobile es una aplicación de casino virtual desarrollada en Android con Kotlin y Jetpack Compose. La aplicación permite a los usuarios registrarse, iniciar sesión, ver una lista de juegos y gestionar su perfil de usuario, incluyendo la posibilidad de cambiar su foto de perfil.

## Estructura de Carpetas

El proyecto sigue una arquitectura estándar de Android, organizada de la siguiente manera para facilitar la escalabilidad y el mantenimiento.

## Herramientas e Imports Clave La aplicación está construida utilizando tecnologías modernas de Android.

*   **Lenguaje:** [Kotlin](https://kotlinlang.org/)
*   **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) para una interfaz de usuario declarativa y moderna.
*   **Navegación:** [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) para gestionar el flujo entre las diferentes pantallas.
*   **Carga de Imágenes:** [Coil (Coil-kt)](https://coil-kt.github.io/coil/) para cargar imágenes de forma asíncrona, especialmente para la foto de perfil desde la galería o la cámara.
*   **Permisos y Actividades:** Se utiliza `ActivityResultContracts` para gestionar los permisos en tiempo de ejecución (cámara) y para obtener contenido de la galería y la cámara.
*   **Componentes Material:** [Material Design 3](https://m3.material.io/) para los componentes de la interfaz de usuario como `Scaffold`, `Card`, `Button` y `OutlinedTextField`.

## Descripción de Archivos `.kt`

A continuación, se detalla la función de cada archivo Kotlin principal en el proyecto:

*   **`MainActivity.kt`**: Es el punto de entrada de la aplicación. Su única responsabilidad es configurar el tema de la aplicación (`Gashasino_mobileTheme`) y albergar el composable `Navegacion`, que controla toda la lógica de navegación.

*   **`ui/Navegacion.kt`**: Contiene el `NavHost` de Jetpack Compose. Aquí se definen todas las rutas (como `"juegoScreen"`, `"Login"`, `"Perfil"`) y se asocia cada ruta con su composable correspondiente, gestionando el flujo de la aplicación.

*   **`ui/juegoScreen.kt`**: Es la pantalla principal de la aplicación, que se muestra después de iniciar sesión. Presenta una lista de juegos disponibles en una `LazyVerticalGrid` y un footer con iconos para navegar a otras secciones como "Tienda", "Inicio" y "Perfil".

*   **`ui/Formulario.kt`**: Contiene la pantalla de registro de nuevos usuarios. Incluye campos de texto para el nombre, correo, edad y contraseña. Utiliza `PasswordVisualTransformation` para ocultar la contraseña y realiza validaciones básicas de los campos.

*   **`ui/Login.kt`**: Implementa la pantalla de inicio de sesión. Dispone de campos para el correo y la contraseña, también con la contraseña oculta. Incluye un botón para enviar las credenciales.

*   **`ui/PerfilScreen.kt`**: Muestra la información del usuario, como su foto de perfil, nombre y otros detalles personales. La característica más destacada es la capacidad de cambiar la foto de perfil, abriendo un diálogo para elegir entre la cámara del dispositivo o la galería.

*   **`model/Juego.kt`**: Es una `data class` que define la estructura de un objeto `Juego`, conteniendo su nombre, descripción e ID del recurso de imagen.

*   **`viewmodel/FormularioViewModel.kt` y `LoginViewModel.kt`**: Estos archivos contienen la lógica de negocio y el estado para las pantallas de `Formulario` y `Login`, respectivamente, separando la lógica de la interfaz de usuario.
