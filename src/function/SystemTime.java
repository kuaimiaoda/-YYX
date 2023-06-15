package function;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemTime {
    public String getStringTime()
    {
        Date date = new Date();
        // 转换成时间戳
        long time = date.getTime();
        return String.valueOf(time);
    }

    public String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public String changeTimeFormat(Timestamp time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }


    public String getStringFromFormatTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public String getFormatTimeFromString(String stringTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(stringTime);
    }
}
