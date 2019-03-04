

<?php

if(isset($_GET['send_notification'])){
   send_notification ();
}

function send_notification()
{
	echo 'Hello';
define( 'API_ACCESS_KEY', 'AAAAX7v858c:APA91bG2denzls99Lw1LsGFWmh0yv4UkDAWWu5W2FLP7OBpP3es3iEoVcVxxtvB8tu61eoOMoFQoqe0CwXzdCcX8ySgs2g9m76epHQ7Y4De1KiFU8bovuHIWERVyOi6_SK47YqBNPbr4');
 //   $registrationIds = ;
#prep the bundle
     $msg = array(
		'body' 	=> 'Firebase at 4th March 5pm',
		'title'	=> 'Md. Muktadir'
             	
          );
	
		$fields = array
			(
				'to' => $_REQUEST['token'],
				'notification'	=> $msg
			);
	
	
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);
#Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		echo $result;
		curl_close( $ch );
}
?>