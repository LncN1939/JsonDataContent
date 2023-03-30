<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'connection.php';
    $idC = $_POST["idC"];
    $nombres = $_POST["nombres"];
    $apellidos = $_POST["apellidos"];
    $fechaNac = $_POST["fechaNac"];
    $titulo = $_POST["titulo"];
    $email = $_POST["email"];
    $facultad = $_POST["facultad"];

    $my_query = "update coordinador set nombres = '$nombres', apellidos = '$apellidos' , 
    fechaNac = '$fechaNac', titulo = '$titulo', email = '$email', facultad = '$facultad' where idC = '$idC';";
    $result = $mysql -> query($my_query);
    
    if($result == true){
        echo "Registro editado satisfactoriamente...";
    }else{
        echo"Error al editar registro...";
    }
}else{
    echo"Error desconocido";
}
?>