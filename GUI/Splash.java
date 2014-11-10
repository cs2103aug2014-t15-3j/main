
import java.awt.*;

public class Splash{
	    private static final String NULL_MESSAGE = "SplashScreen.getSplashScreen() returned null";

		public Splash() {
	        final SplashScreen splash = SplashScreen.getSplashScreen();
	        if (splash == null) {
	            System.out.println(NULL_MESSAGE);
	            return;
	        }
	        for(int i=0; i<16; i++) {
	            try {
	                Thread.sleep(90);
	            }
	            catch(InterruptedException e) {
	            }
	        }
	        splash.close();
	    }
}
