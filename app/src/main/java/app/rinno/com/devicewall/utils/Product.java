package app.rinno.com.devicewall.utils;

/**
 * Created by Dev21 on 09-11-16.
 */

public class Product {
    private String url;
    private int id;
    private String code;

    public Product(int id, String url/*, String code*/)
    {
        this.id = id;
        this.url = url;
        this.code = code;
    }

    public String getUrl()
    {
        return url;
    }

    public int getId()
    {
        return id;
    }


//    public String getCode() {
//        return code;
//    }


}
