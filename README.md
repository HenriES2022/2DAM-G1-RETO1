# 2DAM-G1-RETO1

Repositorio Git para almacenar las diferentes versiones que tendrá el Reto 1 del curso 2DAM del grupo 1

Para ejecutar el script desde terminal usar mysql -u "username" -p 2dam_reto1_g1 < 2dam_reto1_g1.sql <br>
En el caso de que se use mysql query browser se deberá de eliminar la base de datos manualmente para que se ejecute correctamente <br>


# Properties

<h2> Cliente </h2>
El archivo de propiedades se encuentra en el paquete principal llamado "ClientProject",
en este se guardará varias propiedades:
  IP_ADDRESS: IP del servidor
  PORT: Puerto que el servidor está usando
  CONNECTION_TYPE= El tipo de conexión que se usa entre cliente-servidor
 
 

<h2> Servidor</h2>
El archivo de propiedades se encuentra en el paquete principal llamado "ServerProject",
en este se guardará varias propiedades:
  MODEL: La base de datos que se va usar 
  URL: Dirección de la base de datos
  USER= El usuario que se usará para la conexión
  PASS= La contraseña del usuario
  PORT= El puerto por el que se va escuchar



