
package cc.lau.kfxx;

public interface Constants {

    public final int DEFAULT_INSERT_LIMIT = 5000;
    public final int THREAD_COUNT = 4;

    // public final String DATA_HOME = "/home/lau/bigdata";
    public final String DATA_HOME = "/media/lau/Data/bigdata";

    public interface Linux {
        public String fname = DATA_HOME + "/2000W/1-200W.csv";
        public String last5k = DATA_HOME + "/2000W/last5000.csv";
        public String partDir = DATA_HOME + "/part";
        public String totalDir = DATA_HOME + "/2000W";
    }

}
