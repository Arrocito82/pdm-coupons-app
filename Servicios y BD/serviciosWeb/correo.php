<?php
require("conexion.php");

if (!$conn) {
    die("La conexion ha fallado: " . mysqli_connect_error());
}

$query = "SELECT * FROM CORREO;";

//Resultados
$result = $conn->query($query);
while($fila=$result -> fetch_array()){
    $consulta[] = array_map('utf8_encode', $fila);
}

echo json_encode($consulta);

mysqli_close($conn);
