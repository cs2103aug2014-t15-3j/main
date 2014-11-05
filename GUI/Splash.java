
import java.awt.*;

public class Splash{
	    public Splash() {
	        final SplashScreen splash = SplashScreen.getSplashScreen();
	        if (splash == null) {
	            System.out.println("SplashScreen.getSplashScreen() returned null");
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
