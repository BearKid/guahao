import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Lu Weibiao
 * Date: 2015/3/8 12:34
 */
public class TestMain {
    public static void main(String[] args){
        String[] dateStrArr =new String[]{ "2015/06/25 7:18:46 521","2015/06/14 18:40:60 987","2015/5/5 24:05:25 145","2015/5/14 16:24:59 685"};
        String datePattern = "yyyy/MM/dd HH:mm:ss SSS";
        Date[] dateArr = new Date[dateStrArr.length];
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        for(int i = 0; i < dateArr.length; i++){
            try {
                dateArr[i] = dateFormatter.parse(dateStrArr[i]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        DateTime[] dateTimeArr = new DateTime[dateArr.length];
        for(int i = 0; i < dateArr.length; i++){
            dateTimeArr[i] = new DateTime(dateArr[i]);
        }
        DateTime d1 = dateTimeArr[0];
        DateTime d2 = dateTimeArr[1];
        DateTime d3 = dateTimeArr[2];
        DateTime d4 = dateTimeArr[3];

        DateTime t1 = d1.withDayOfMonth(1).withMillisOfDay(1);
        DateTime t2 = d2.withDayOfMonth(1).withMillisOfDay(1);
        DateTime t3 = d3.withDayOfMonth(1).withMillisOfDay(1);
        DateTime t4 = d4.withDayOfMonth(1).withMillisOfDay(1);

        System.out.println(d1.toString(datePattern).concat(" is parsed to : ").concat(t1.toString(datePattern)));
        System.out.println(d2.toString(datePattern).concat(" is parsed to : ").concat(t2.toString(datePattern)));
        System.out.println(d3.toString(datePattern).concat(" is parsed to : ").concat(t3.toString(datePattern)));
        System.out.println(d4.toString(datePattern).concat(" is parsed to : ").concat(t4.toString(datePattern)));
        System.out.println("t1 == t2 :" + t1.isEqual(t2));
        System.out.println("t3 == t4 :" + t3.isEqual(t4));
        System.out.println("t3 == t2 :" + t3.isEqual(t2));
    }
}
