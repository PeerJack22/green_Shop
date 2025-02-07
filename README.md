  # Root Shop 

   ![Logo_greenShop110px](https://github.com/user-attachments/assets/87834e5a-703b-4f90-9bc4-6b5b20a90c77) 

   Este es un programa que nos ayudara a gestionar una tienda de productos organicos llamada "Root Shop", el programa tiene diferentes funcionalidades que nos ayudaran a poder Agregar,Modificar,Eliminar y Ver
   los productos que queremos tener en la tienda, además de esto podemos ingresar al programa como cliente para poder ver que catálago de productos estan disponibles y poder seleccionar lo que queremos comprar.

   Se podrá acceder a estas funcionalidades por medio de una pantalla de Login dependiendo del rol que tenga nuestro usuario podremos hacer las diferentes funciones.Algo adicional que tenemos es que podemos
   informes de el inventario de los productos de nuestra tienda y generar una factura de la compra que el cliente haga.

   Todos los datos generados en el programa se guardaran en MongoDB.

   A continuación se dara la información de la instalación, configuración y ejecución del proyecto.

   ## Instalación y configuración

   Para poder instalar este programa debemos descargar el repositorio **aqui mismo en Github** lo podemos hacer desde aqui:

   ![image](https://github.com/user-attachments/assets/3e7663a4-9f00-4e0e-a737-3e5bbaddedc8)

   Lo descargamos en **.zip**

   Luego necesitamos tambien el MongoDB para poder guardar los datos y que el programa funcione correctamente, en Mongo debemos crear una base de datos llamada **greenShop** con tres colecciones que son llamadas
   así: **productos** , **transacciones** , **usuarios**.

   Deberia quedar así:
   
   ![image](https://github.com/user-attachments/assets/632b8a7e-0fb9-4ae8-ab82-026fe01b3611)

   Por ultimo hay que abrir la carpeta que descomprimimos del .zip y lo abrimos con **Intellij Idea** y agregamos estas librerias desde **Maven** a la estructura de nuestro proyecto:

   ![image](https://github.com/user-attachments/assets/b0d2784a-e0f5-4045-88a7-6ca1039b266e)

   **Teniendo esto el programa esta listo para ejecutarse!!**

   ## Ejecución

   ### Ventana de inicio de sesión

   Primero vamos ha ver la ventana de **Inicio de sesión** la cual consta con un campo de usuario y contraseña en la cual dependiendo del rol que este asociado con el usuario en la base de datos nos mostrará
   diferente ventanas.

  ![image](https://github.com/user-attachments/assets/106eeec6-b54a-4e2f-90c8-bf545046c126)

  ### Ventana de Administrador

  En esta ventana tenemos el CRUD en general que nos ayudará a poder gestionar el inventario de la tienda.

  ![image](https://github.com/user-attachments/assets/70b4bc6d-dbbb-4dc0-bdf3-055be7506c71)

  En esta ventana podemos crear, actualizar, eliminar y ver todos los productos que tenemos en nuestra tienda, además de eso existe un botón para poder generar un informe del inventario en un archivo PDF.

  ### Venta del cliente

  ![image](https://github.com/user-attachments/assets/5e7895d8-f21e-40ac-b29d-ef8a6e4c2c78)

  En esta ventan el cliente podra escoger que producto comprar y luego poner la cantidad que desea de acuerdo al stock disponible , puede agregar cualquier producto y al ultimo finalizar la compra.
  Además a esto el cliente puede generar una factura de todos los productos comprados en pdf.

  ### Adicionales

  Aqui hay ejemplos del informe de inventario y la facturas generadas:
  
  ![image](https://github.com/user-attachments/assets/c26ec11e-55d8-4dca-8123-2f614667d7f3)

  ![image](https://github.com/user-attachments/assets/fc95976b-584a-4c3e-b055-2bf3c941c679)

  ### Video para guía de usuario

  https://youtu.be/lOYVne6mX9c 

  ## Pruebas
  
  ![image](https://github.com/user-attachments/assets/4ab2ba0f-ba7d-4063-a12f-dc4dc313dee2)

  Resultados de pruebas:

  ![image](https://github.com/user-attachments/assets/761331fa-b58d-4696-bab5-b799b09ef450)






  

   




   
