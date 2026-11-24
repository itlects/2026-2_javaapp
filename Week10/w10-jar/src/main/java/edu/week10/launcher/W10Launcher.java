package edu.week10.launcher;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

/** Starts the unchanged w10 WAR using an embedded Tomcat and JSP engine. */
public final class W10Launcher {
    private W10Launcher() { }

    public static void main(String[] args) throws Exception {
        int port = args.length == 0 ? 8080 : Integer.parseInt(args[0]);
        if (args.length > 1 || port < 1 || port > 65535) {
            throw new IllegalArgumentException("Usage: java -jar w10.jar [port 1-65535]");
        }
        Path base = Files.createTempDirectory("w10-tomcat-");
        Path webapps = Files.createDirectories(base.resolve("webapps"));
        Path war = webapps.resolve("w10.war");
        try (InputStream in = W10Launcher.class.getResourceAsStream("/embedded/w10.war")) {
            if (in == null) {
                deleteTree(base);
                throw new IllegalStateException("Missing embedded/w10.war. Build ../w10 first, then this project.");
            }
            Files.copy(in, war);
        }
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(base.toString());
        tomcat.setPort(port);
        tomcat.getConnector().setProperty("address", "127.0.0.1");
        tomcat.getConnector().setURIEncoding("UTF-8");
        tomcat.getHost().setAppBase(webapps.toString());
        Context context = tomcat.addWebapp("/w10", war.toString());
        context.setParentClassLoader(W10Launcher.class.getClassLoader());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                tomcat.stop();
                tomcat.destroy();
            } catch (Exception ex) {
                System.err.println("Tomcat shutdown: " + ex.getMessage());
            } finally {
                deleteTree(base);
            }
        }, "w10-shutdown"));
        tomcat.start();
        if (!context.getState().isAvailable()) {
            throw new IllegalStateException("w10 did not start. Check the server log above.");
        }
        System.out.println("\nW10 READY: http://localhost:" + port + "/w10/login");
        System.out.println("Demo account: student / Java1234!  |  Stop: Ctrl+C\n");
        tomcat.getServer().await();
    }

    private static void deleteTree(Path root) {
        try (var paths = Files.walk(root)) {
            paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                try { Files.deleteIfExists(path); }
                catch (Exception ex) { path.toFile().deleteOnExit(); }
            });
        } catch (Exception ex) {
            root.toFile().deleteOnExit();
        }
    }
}
