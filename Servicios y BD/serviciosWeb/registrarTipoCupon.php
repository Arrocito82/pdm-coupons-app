<?php
require("conexion.php");

$nombre_tipo = $_REQUEST['nombre_tipo'];

if (!$conn) {
    die("La conexion ha fallado: " . mysqli_connect_error());
}
echo "Conexion con exito";

$query = "INSERT INTO TIPOCUPON (NOMBRE_TIPO) VALUES('" . $nombre_tipo . "');";

try {
    $resultado = mysqli_query($conn, $query) or die(mysqli_error($conn));
    //Si la respuesta es correcta enviamos 1 y sino enviamos 0
    if (mysqli_affected_rows($conn) == 1) {
        $respuesta = array('resultado' => 1);
        echo json_encode($respuesta);
    }
} catch (Exception $e) {
    echo ($e);
}

mysqli_close($conn);
