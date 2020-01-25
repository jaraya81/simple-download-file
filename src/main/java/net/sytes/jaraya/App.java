package net.sytes.jaraya;


/**
 * simple-download-file
 */
public class App {

    public static void main(String[] args) {
        if (args.length != 2) {
            welcome();
            System.exit(0);
        }
        System.out.println("Url: " + args[0]);
        System.out.println("Target: " + args[1]);
        System.exit(Download.exec(args[0], args[1]));
    }

    public static void welcome() {
        System.out.println("");
        System.out.println("----------------- simple-download-file -----------------");
        System.out.println("");
        System.out.println("Arg 1: url");
        System.out.println("Arg 1: target");
        System.out.println("");
        System.out.println("--------------------------------------------------------");
    }
}

