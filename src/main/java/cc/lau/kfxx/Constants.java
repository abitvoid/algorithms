package cc.lau.kfxx;

public interface Constants {

    public final int DEFAULT_INSERT_LIMIT = 5000;
    public final int THREAD_COUNT = 4;

    public interface Linux {
        public String fname = "/media/lau/Data/bigdata/2000W/1-200W.csv";
        public String last5k = "/media/lau/Data/bigdata/2000W/last5000.csv";
        public String partDir = "/media/lau/Data/bigdata/part";
        public String totalDir = "/media/lau/Data/bigdata/2000W";
    }

}
