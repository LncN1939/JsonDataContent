<?php
if($_SERVER["REQUEST_METHOD"]=="GET"){
    require_once 'connection.php'; 
    $idC = $_GET["idC"];
    $my_query = "select * from coordinador where idC ='" .$idC."'";

    $result = $mysql -> query($my_query);
    
    if($mysql -> affected_rows > 0){
        while($row = $result-> fetch_assoc()){
            $array = $row;
        }
        echo json_encode($array);
    }else{
        echo"No hay registros";
    }
}else{
    echo"Error desconocido";
}
?>