<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'connection.php';
    $idC = $_POST["idC"];
    $my_query = "DELETE FROM coordinador WHERE idC ='".$idC."'";

    $result = $mysql -> query($my_query);
    
    if($result == true){
        echo "Registro eliminado satisfactoriamente...";
    }else{
        echo"Error al eliminar registro...";
    }
}else{
    echo"Error desconocido";
}
?>