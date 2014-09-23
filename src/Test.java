import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.rros.core.User;




public class Test {
	
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException{
		User user = new User();
		while(true){
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter Message: ");
			String msg = reader.readLine();
			user.publish(msg);
		}
		
	}

}
