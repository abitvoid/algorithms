
package cc.lau.kfxx;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVWrapper {
    private CSVReader cr;
    private File[] files;
    private int fidx = 0;
    private String[] heads;
    private int count = 0;
    private int errorCount = 0;

    private CSVWrapper(File file) {
        try {
            if (file.isDirectory()) {
                FilenameFilter filter = new FilenameFilter() {

                    @Override
                    public boolean accept(File dir, String fname) {
                        return fname.toLowerCase().endsWith(".csv");
                    }
                };
                files = file.listFiles(filter);
                if (files.length > 0) {
                    cr = new CSVReader(new FileReader(files[fidx]));
                    heads = cr.readNext();
                }

            } else if (file.isFile()) {
                files = new File[] {
                    file
                };
                cr = new CSVReader(new FileReader(file));
                heads = cr.readNext();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CSVWrapper() {
    }

    public static CSVWrapper newInstance() {
        return new CSVWrapper();
    }

    public static CSVWrapper newInstance(File file) {
        return new CSVWrapper(file);
    }

    public static CSVWrapper newInstance(String fname) {
        return new CSVWrapper(new File(fname));
    }

    public int getCount() {
        return count;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public synchronized List<DBObject> getFixNumberMobjs(final int limit) {
        List<String[]> list = new ArrayList<>();
        String[] nextLine;
        try {
            while ((nextLine = cr.readNext()) != null || fidx < files.length-1) {
                if (nextLine == null) {
                    fidx++;
                    cr = new CSVReader(new FileReader(files[fidx]));
                    heads = cr.readNext();
                    continue;
                }
                list.add(nextLine);
                count++;
                if (list.size() == limit) {
                    return makeMobjs(heads, list);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return makeMobjs(heads, list);
    }

    public DBObject makeMobj(String[] keys, String[] valus) {
        DBObject mobj = new BasicDBObject();
        int len;
        if ((len = keys.length) != valus.length) {
            errorCount++;
            return null;
        }
        for (int i = 0; i < len; i++) {
            mobj.put(keys[i], valus[i]);
        }
        return mobj;
    }

    public List<DBObject> makeMobjs(String[] heads, List<String[]> list) {
        List<DBObject> mobjs = new ArrayList<>();
        for (String[] valus : list) {
            DBObject mobj = makeMobj(heads, valus);
            if (mobj != null) {
                mobjs.add(mobj);
            }
        }
        return mobjs;
    }

    public static void main(String[] args) {
        CSVWrapper cw = CSVWrapper.newInstance(Constants.Linux.last5k);

        List<DBObject> mobjs;
        while ((mobjs = cw.getFixNumberMobjs(100)).size() != 0)
            System.out.println(mobjs.get(0));

        System.out.println(cw.getCount());
    }

}
