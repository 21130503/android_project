package Services;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.StringUtils;

public class UTF8Convert {

    public String convert (String status){
        String newStatus = "";
        switch (status){
            case "?? giao cho ??n v? v?n chuy?n":
                newStatus=  "Đã giao cho đơn vị vận chuyển";
            case "?ang chu?n b?":
                newStatus= "Đang chuẩn bị";
            case "?ang ch? nh?n":
                newStatus=  "Đang chờ nhận";
            case "?ã giao":
                newStatus = "Đã giao";
            case "?ã h?y":
                newStatus = "Đã hủy";

        }
        return  newStatus;
    }
}
