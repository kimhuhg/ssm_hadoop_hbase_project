package test.testBeans;

/**
 * Created by Shinelon on 2017/7/26.
 */
public class MyResult {
    private String z;
    private String zrcd;
    private String stcd;
    private String q;
    private String tm;

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getZrcd() {
        return zrcd;
    }

    public void setZrcd(String zrcd) {
        this.zrcd = zrcd;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    @Override
    public String toString() {
        return "MyResult{" +
                "z='" + z + '\'' +
                ", zrcd='" + zrcd + '\'' +
                ", stcd='" + stcd + '\'' +
                ", q='" + q + '\'' +
                ", tm='" + tm + '\'' +
                '}';
    }
}
