package sqlparsing;
import java.io.FileInputStream;
import java.util.Properties;
/**
 * Class have two way for start. If we start it as jar file we should choose way for properties in terminal.
 * java -jar SQLRuParser app.properties.
 * In Any case it is normal way.
 */
public class StartApplication {
    public static void main(String[] args) {
        if(args != null && args.length > 0){
            String fileWay = args[0];
            QuartzJob quartzJob = new QuartzJob(new DBPropereties(fileWay));
            quartzJob.startApplication();
        }else {
            Properties prop = new Properties();
            try {
                String path = ClassLoader.getSystemClassLoader().getResource(".").getPath() + "app.properties";
                FileInputStream file = new FileInputStream(path);
                prop.load(file);
                QuartzJob quartzJob = new QuartzJob(new DBPropereties(path));
                quartzJob.startApplication();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
