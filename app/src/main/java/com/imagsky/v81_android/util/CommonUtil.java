package com.imagsky.v81_android.util;

/**
 * Created by jasonmak on 29/1/2015.
 */
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

/**
 * some very general utility method that cant be put into other XXXUtil class
 *
 */
public class CommonUtil {

    public static void main(String[] args){
        System.out.println(getChiNumber(1203010));
        System.out.println(getChiNumber(1001000));
    }
    private CommonUtil() {
    } // this is a helper class
    private static final String dateFormatString = "dd-MM-yyyy HH:mm";
    public static final String dateOnlyFormatString = "dd-MM-yyyy";
    private static final String[] monthNames = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static String getChiNumber(long in){

        int[] I = new int[10];
        I[0] = 1;
        for (int i=1; i<=9; i++) {
            I[i] = I[i-1] * 10;
        }

        char[] C1 = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};
        char[] C2 = {'拾', '佰', '仟',
                '萬', '拾', '佰', '仟',
                '億', '拾'};

        String intputS = ""+in;
        int intputI = Integer.parseInt(intputS);

        int EndIndex = (intputS.length()*2-1)-1;
        char[] AC = new char[EndIndex+1];

// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// 把阿拉伯數字一對一轉換為中文數字+位權(億、萬、仟、佰、拾)

        int ioC1;
        int ioAC=0;

        for (int i=intputS.length()-1;i>=0;i--) {

            ioC1 = intputI / I[i];
            intputI -= ioC1 * I[i];

            AC[ioAC] = C1[ioC1];

            //排除i=0(個位數)
            if (i != 0) {
                AC[++ioAC] = C2[i-1];
            }

            ioAC++;
        }

// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// '零'後面若為'億'或'萬'，則擠掉'零'
// 若為'仟'、'佰'、'拾'或'零'，則擠掉後面這個char

        for (int i=0;i<EndIndex;i++) {
            if (AC[i] == '零') {
                switch (AC[i+1]) {
                    case '億' :
                    case '萬' :
                        //擠掉AC[i]
                        for (int j=i;j<EndIndex;j++) {
                            AC[j] = AC[j+1];
                        }
                        EndIndex--;
                        break;
                    case '仟' :
                    case '佰' :
                    case '拾' :
                    case '零' :
                        //擠掉AC[i+1]
                        for (int j=i+1;j<EndIndex;j++) {
                            AC[j] = AC[j+1];
                        }
                        EndIndex--;
                        i--;
                        break;
                } //EndSwitch
            } //EndIf
        } //EndFor

// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// 字首若為'壹'+'拾'，則改為'拾'(把'壹'擠掉)

        if (AC.length>=2 && AC[0] == '一' && AC[1] == '拾') {
            for (int i=0;i<=EndIndex-1;i++) {
                AC[i] = AC[i+1];
                //EndIndex--;
            } //EndFor
            EndIndex--;
        } //EndIf

// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
// 列印結果
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<=EndIndex;i++) {
            sb.append(AC[i]);
            //System.out.print(AC[i]);
        }
        if(sb.toString().endsWith("零")){
            return sb.toString().substring(0, sb.toString().length()-1);
        } else {
            return sb.toString();
        }
    }

    public static StringBuffer printMapWithNewLine(Map map) {
        StringBuffer strBuffer = new StringBuffer();

        if (map != null) {
            strBuffer.append('[');
            strBuffer.append('\n');

            if (!map.isEmpty()) {
                Iterator keyIter = map.keySet().iterator();
                while (keyIter.hasNext()) {
                    Object nextKey = keyIter.next();

                    strBuffer.append(nextKey);
                    strBuffer.append('=');
                    strBuffer.append(map.get(nextKey));
                    strBuffer.append('\n');
                }
            }

            strBuffer.append(']');
            strBuffer.append('\n');
        } else {
            strBuffer.append(map);
        }

        return strBuffer;
    }

    public static String trimIfNotNull(String str) {
        if (str != null) {
            return str.trim();
        } else {
            return null;
        }
    }

    public static boolean isNullOrEmpty(String str) {
        if (str == null) {
            return true;
        } else {
            if (str.length() == 0) {
                return true;
            } else {
                if (str.trim().length() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public static boolean isNullOrZeroLength(String str) {
        if (str == null) {
            return true;
        } else {
            if (str.length() == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static StringBuffer null2EmptyBuffer(Object obj) {
        if (obj == null) {
            return new StringBuffer();
        } else {
            return (new StringBuffer()).append(obj);
        }
    }

    public static String null2Empty(Object obj) {
        if (obj == null) {
            return "";
        } else {
            if (obj instanceof String) {
                return (String) obj;
            } else {
                return String.valueOf(obj);
            }
        }
    }

    public static StringBuffer null2Empty(StringBuffer strBuffer) {
        if (strBuffer == null) {
            return new StringBuffer();
        } else {
            return strBuffer;
        }
    }

    public static String null2Empty(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    public static int null2Zero(Integer num) {
        if (num == null) {
            return 0;
        } else {
            return num.intValue();
        }
    }

    public static String leftpadding(String str, String paddingStr, int length) {
        if (str.length() >= length) {
            return str;
        } else {
            StringBuffer sb = new StringBuffer(str);
            while (sb.toString().length() < length) {
                sb.insert(0, paddingStr);
            }
            return sb.toString();
        }
    }

    public static String padding(String str, String paddingStr, int length) {
        if (str.length() >= length) {
            return str;
        } else {
            StringBuffer sb = new StringBuffer(str);
            while (sb.toString().length() < length) {
                sb.append(paddingStr);
            }
            return sb.toString();
        }
    }

    /**
     *
     * @param dateToAdd
     * @param calUnit Calendar.MONTH, Calendar.DAY_OF_YEAR, Calendar.YEAR, ...,
     * etc
     * @param amountToAdd
     */
    public static Date dateAdd(java.util.Date dateToAdd, int calUnit, int amountToAdd) {
        if (dateToAdd == null) {
            dateToAdd = new java.util.Date();
        }
        if (dateToAdd != null) {
            Calendar calObj = Calendar.getInstance();
            calObj.setTimeInMillis(dateToAdd.getTime());

            calObj.add(calUnit, amountToAdd);

            dateToAdd.setTime(calObj.getTimeInMillis());
        } // end if (dateToAdd != null)
        return dateToAdd;
    }
    //getDateDiff(Calendar.<unit>, d1, d2);
    private static final double DAY_MILLIS = 1000 * 60 * 60 * 24.0015;
    private static final double HOUR_MILLIS = DAY_MILLIS / 24.0015;
    private static final double MIN_MILLIS = HOUR_MILLIS / 60;
    private static final double WEEK_MILLIS = DAY_MILLIS * 7;
    private static final double MONTH_MILLIS = DAY_MILLIS * 30.43675;
    private static final double YEAR_MILLIS = WEEK_MILLIS * 52.2;
    private static final String[] timediff_unit_zh = new String[]{"年", "日", "小時", "分鐘"};
    private static final String[] timediff_unit_en = new String[]{"Year(s)", "Day(s)", "Hour(s)", "Min(s)"};

    public static String getDateDiffFullString(String lang, java.util.Date d1, java.util.Date d2, int showLevel) {
        String[] units = null;
        if (d1 == null || d2 == null) {
            return "";
        }

        if ("zh".equalsIgnoreCase(lang)) {
            units = timediff_unit_zh;
        } else if ("en".equalsIgnoreCase(lang)) {
            units = timediff_unit_en;
        } else {
            units = timediff_unit_zh;//default
        }
        if (showLevel < 1 || showLevel > 4) {
            showLevel = 2;
        }

        StringBuffer sb = new StringBuffer();
        java.util.Date workingDate = new java.util.Date(d1.getTime());
        if (getDateDiff(Calendar.YEAR, workingDate, d2) > 0 && showLevel > 0) {
            sb.append(getDateDiff(Calendar.YEAR, workingDate, d2) + units[0]);
            workingDate = dateAdd(workingDate, Calendar.YEAR, getDateDiff(Calendar.YEAR, workingDate, d2));
            showLevel--;
        }
        if (getDateDiff(Calendar.DAY_OF_MONTH, workingDate, d2) > 0 && showLevel > 0) {
            sb.append(getDateDiff(Calendar.DAY_OF_MONTH, workingDate, d2) + units[1]);
            workingDate = dateAdd(workingDate, Calendar.DAY_OF_MONTH, getDateDiff(Calendar.DAY_OF_MONTH, workingDate, d2));
            showLevel--;
        }
        if (getEstDiff(Calendar.HOUR_OF_DAY, workingDate, d2) > 0 && showLevel > 0) {
            sb.append(getEstDiff(Calendar.HOUR_OF_DAY, workingDate, d2) + units[2]);
            workingDate = dateAdd(workingDate, Calendar.HOUR_OF_DAY, getEstDiff(Calendar.HOUR_OF_DAY, workingDate, d2));
            showLevel--;
        }
        if (getEstDiff(Calendar.MINUTE, workingDate, d2) > 0 && showLevel > 0) {
            sb.append(getEstDiff(Calendar.MINUTE, workingDate, d2) + units[3]);
            workingDate = dateAdd(workingDate, Calendar.MINUTE, getEstDiff(Calendar.MINUTE, workingDate, d2));
            showLevel--;
        }
        return sb.toString();
    }

    public static int getDateDiff(int calUnit, java.util.Date d1, java.util.Date d2) {
        // swap if d1 later than d2
        boolean neg = false;
        if (d1.after(d2)) {
            java.util.Date temp = d1;
            d1 = d2;
            d2 = temp;
            neg = true;
        }

        // estimate the diff.  d1 is now guaranteed <= d2
        int estimate = (int) getEstDiff(calUnit, d1, d2);

        // convert the Dates to GregorianCalendars
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        // add 2 units less than the estimate to 1st date,
        //  then serially add units till we exceed 2nd date
        c1.add(calUnit, (int) estimate - 2);
        for (int i = estimate - 1;; i++) {
            c1.add(calUnit, 1);
            if (c1.after(c2)) {
                return neg ? 1 - i : i - 1;
            }
        }
    }

    private static int getEstDiff(int calUnit, java.util.Date d1, java.util.Date d2) {
        long diff = d2.getTime() - d1.getTime();
        switch (calUnit) {
            case Calendar.DAY_OF_WEEK_IN_MONTH:
            case Calendar.DAY_OF_MONTH:
                //      case Calendar.DATE :      // codes to same int as DAY_OF_MONTH
                return (int) (diff / DAY_MILLIS + .5);
            case Calendar.WEEK_OF_YEAR:
                return (int) (diff / WEEK_MILLIS + .5);
            case Calendar.MONTH:
                return (int) (diff / MONTH_MILLIS + .5);
            case Calendar.YEAR:
                return (int) (diff / YEAR_MILLIS + .5);
            case Calendar.HOUR_OF_DAY:
                return (int) (diff / HOUR_MILLIS + .5);
            case Calendar.MINUTE:
                return (int) (diff / MIN_MILLIS + .5);
            default:
                return 0;
        } /*
         * endswitch
         */
    }

    public static void trimCalendar(java.util.Calendar calObj) {
        if (calObj != null) {
            calObj.set(Calendar.HOUR_OF_DAY, 0);
            calObj.set(Calendar.MINUTE, 0);
            calObj.set(Calendar.SECOND, 0);
            calObj.set(Calendar.MILLISECOND, 0);
        }
    }

    public static void trimDate(java.util.Date dataObj) {
        if (dataObj != null) {
            Calendar calObj = Calendar.getInstance();
            calObj.setTimeInMillis(dataObj.getTime());

            calObj.set(Calendar.HOUR_OF_DAY, 0);
            calObj.set(Calendar.MINUTE, 0);
            calObj.set(Calendar.SECOND, 0);
            calObj.set(Calendar.MILLISECOND, 0);

            dataObj.setTime(calObj.getTimeInMillis());
        }
    }

    public static void trimStringList(List strList) {
        if (strList != null) {
            for (int i = 0; i < strList.size(); i++) {
                String nextStr = (String) strList.get(i);
                strList.set(i, CommonUtil.trimIfNotNull(nextStr));
            }
        }
    }

    public static String[] trimStringArray(String[] strArray) {
        String[] trimmedStrArray = null;

        if (strArray != null) {
            trimmedStrArray = new String[strArray.length];

            for (int i = 0; i < strArray.length; i++) {
                trimmedStrArray[i] = CommonUtil.trimIfNotNull(strArray[i]);
            }
        }

        return trimmedStrArray;
    }

    public static boolean isNullOrEmpty(Collection c) {
        if (c == null) {
            return true;
        }
        return (c.size() == 0);
    }

    public static String formatDate(java.util.Date inDate, String dateFormatstr) {
        if (inDate == null) {
            return "";
        } else {
            SimpleDateFormat dateFormat =
                    new SimpleDateFormat(dateFormatstr);
            return dateFormat.format(inDate);
        }
    }

    public static String formatDate(java.util.Date inDate) {
        return formatDate(inDate, dateFormatString);
    }

    /**
     * **
     * Input java.util.Date and return string array as {"dd","mm","yyyy"}
     *
     * @param inDate
     * @return
     */
    public static String[] formatDateAsTokenDDMMYYYY(java.util.Date inDate) {
        if (inDate == null) {
            return null;
        }
        String[] tokens = CommonUtil.stringTokenize(formatDate(inDate, dateOnlyFormatString), "-");
        return tokens;
    }

    public static String formatDateFromDDMMYYYY(String ddmmyyyy) {
        if (ddmmyyyy != null) {
            int monthStr = new Integer(ddmmyyyy.substring(2, 4)).intValue();
            return ddmmyyyy.substring(0, 2) + " " + monthNames[monthStr - 1] + " " + ddmmyyyy.substring(4);
        }
        return "";
    }

    /**
     * *
     * Accept input string as 31/1/2010 or 1-1-2010 First portion must be day
     * part
     *
     * @param ddmmyyyy
     * @param delimiter
     * @return
     */
    public static java.util.Date StringDDMMYYYY2Date(String ddmmyyyy, String delimiter) {
        if (ddmmyyyy == null) {
            return null;
        }
        String[] tokens = CommonUtil.stringTokenize(ddmmyyyy, delimiter);
        if (tokens == null || tokens.length != 3) {
            return null;
        }
        String day = (tokens[0].length() == 1) ? "0" + tokens[0] : tokens[0];
        String month = (tokens[1].length() == 1) ? "0" + tokens[1] : tokens[1];
        if (tokens[2].length() != 4) {
            return null;
        }
        return StringDDMMYYYY2Date(day + month + tokens[2]);
    }

    public static java.util.Date StringDDMMYYYY2Date(String ddmmyyyy) {
        Calendar calObj = Calendar.getInstance();
        calObj.set(Calendar.YEAR, new Integer(ddmmyyyy.substring(4)).intValue());
        calObj.set(Calendar.MONTH, new Integer(ddmmyyyy.substring(2, 4)).intValue() - 1);
        calObj.set(Calendar.DAY_OF_MONTH, new Integer(ddmmyyyy.substring(0, 2)).intValue());
        calObj.set(Calendar.HOUR_OF_DAY, 0);
        calObj.set(Calendar.MINUTE, 0);
        calObj.set(Calendar.SECOND, 0);
        calObj.set(Calendar.MILLISECOND, 0);
        return new java.util.Date(calObj.getTimeInMillis());
    }

    public static java.util.Date StringDDMMYYHHmm2Date(String ddmmyyyyhhmm) {
        if (CommonUtil.isNullOrEmpty(ddmmyyyyhhmm)) {
            return null;
        }
        ddmmyyyyhhmm = ddmmyyyyhhmm.replaceAll("\\.", "").replaceAll("/", "").replaceAll(" ", "").replaceAll(":", "");
        Calendar calObj = Calendar.getInstance();
        try {
            calObj.set(Calendar.YEAR, new Integer(ddmmyyyyhhmm.substring(4, 8)).intValue());
            calObj.set(Calendar.MONTH, new Integer(ddmmyyyyhhmm.substring(2, 4)).intValue() - 1);
            calObj.set(Calendar.DAY_OF_MONTH, new Integer(ddmmyyyyhhmm.substring(0, 2)).intValue());
            calObj.set(Calendar.HOUR_OF_DAY, new Integer(ddmmyyyyhhmm.substring(8, 10)).intValue());
            calObj.set(Calendar.MINUTE, new Integer(ddmmyyyyhhmm.substring(10)).intValue());
            calObj.set(Calendar.SECOND, 0);
            calObj.set(Calendar.MILLISECOND, 0);
            return new java.util.Date(calObj.getTimeInMillis());
        } catch (Exception e) {
            System.out.println("StringDDMMYYHHmm2Date: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public static boolean isBefore(Date date1, Date date2) {
        Calendar calObj = Calendar.getInstance();
        Calendar calObj2 = Calendar.getInstance();
        calObj.setTime(date1);
        calObj2.setTime(date2);
        return calObj.before(calObj2);
    }

    public static boolean isBeforeAdd(Date date1, Date date2, int calUnit, int amountToAdd) {
        dateAdd(date1, calUnit, amountToAdd);
        return isBefore(date1, date2);
    }

    public static Integer parseInteger(String intStr) {
        if (intStr == null) {
            return null;
        }

        try {
            return new Integer(intStr);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
    private static String rxInteger = "[0-9]";
    private static String rxNumber = "[0-9.\\-]";
    private static String rxLoginName = "^0-9A-Za-z@.\\_\\-";
    //private static String rxLetter = "^A-Za-z";
    private static String rxLetter = "[a-zA-Z]";
    private static String rxLetterNumeric = "[\\w]";

    public static boolean isValidInteger(String inStr) {
        if (inStr == null) {
            return false;
        }
        Pattern numPattern = Pattern.compile(rxInteger);
        Matcher numMatcher = numPattern.matcher(inStr.trim());
        return numMatcher.find();
    }

    public static boolean isValidNumber(String inStr) {
        if (inStr == null) {
            return false;
        }
        Pattern numPattern = Pattern.compile(rxNumber);
        Matcher numMatcher = numPattern.matcher(inStr.trim());
        return numMatcher.find();
    }

    public static boolean isValidLoginName(String inStr) {
        if (inStr == null) {
            return false;
        }
        Pattern numPattern = Pattern.compile(rxLoginName);
        Matcher numMatcher = numPattern.matcher(inStr.trim());
        return numMatcher.find();
    }

    public static boolean isLetter(String inStr) {
        if (inStr == null) {
            return false;
        }
        Pattern letPattern = Pattern.compile(rxLetter);
        Matcher letMatcher = letPattern.matcher(inStr.trim());
        return letMatcher.find();
    }

    public static boolean isLetterNumeric(String inStr) {
        if (inStr == null) {
            return false;
        }
        Pattern letPattern = Pattern.compile(rxLetterNumeric);
        Matcher letMatcher = letPattern.matcher(inStr.trim());
        return letMatcher.find();
    }

    public static String[] stringTokenize(String inStr, String delimiter) {
        StringTokenizer st = new StringTokenizer(inStr, delimiter);
        int count = st.countTokens();
        if (count == 0) {
            return null;
        }
        String[] result = new String[count];
        int x = 0;
        while (st.hasMoreTokens()) {
            result[x] = st.nextToken();
            x++;
        }
        return result;
    }

    public static String[] stringTokenize(String inStr) {
        return CommonUtil.stringTokenize(inStr, " \t\n\r\f"); // all writespace
    }

    public static String findValueWithStringTokenizer(String inStr, String key, String delimiter1, String delimiter2) {
        String[] tokens = stringTokenize(inStr, delimiter1);
        for (int x = 0; x < tokens.length; x++) {
            if (tokens[x].startsWith(key + delimiter2)) {
                String[] token2 = stringTokenize(tokens[x], delimiter2);
                return token2[1];
            }
        }
        return null;
    }

    public static String numericFormatWithComma(double inNum, boolean hasDecimal) {
        NumberFormat formatter;
        String inStr = new Double(inNum).toString();
        if (inStr.indexOf(".") >= 0 || hasDecimal) {
            formatter = new DecimalFormat("#,###,###,###.##");
        } else {
            formatter = new DecimalFormat("#,###,###,###");
        }
        return formatter.format(inNum);
    }

    public static String numericFormatWithComma(double inNum) {
        return numericFormatWithComma(inNum, false);
    }

    public static String numericStringWithSign(double inNum){
        if(inNum < 0 ){
            return ""+(int)inNum;
        } else{
            return "+" + (int)inNum;
        }
    }

    /**
     * no IndexOutOfBound even if the maxSize too large
     *
     * @param fullList
     * @param maxSize, negative integer = no maxSize
     * @return null if
     * <code>fullList</code> is null, otherwise return new list
     */
    public static List subList(List fullList, int maxSize) {
        List subList = null;

        if (fullList != null) {
            int endIndexExclusive = maxSize;
            if (maxSize < 0) {
                endIndexExclusive = fullList.size(); // -ve integer = no maxSize
            } else {
                if (fullList.size() < maxSize) {
                    endIndexExclusive = fullList.size();
                }
            }

            subList = fullList.subList(0, endIndexExclusive);
        }

        return subList;
    }

    public static String toString(Object[] arrayObj) {
        return CommonUtil.toStringBuffer(arrayObj).toString();
    }

    public static StringBuffer toStringBuffer(Object[] arrayObj) {
        StringBuffer strBuffer = new StringBuffer();

        if (arrayObj != null) {

            strBuffer.append('[');

            for (int i = 0; i < arrayObj.length; i++) {
                if (i != 0) {
                    strBuffer.append(',');
                    strBuffer.append(' ');
                }

                strBuffer.append(arrayObj[i]);
            }

            strBuffer.append(']');
        } else {
            strBuffer.append((String) null);
        }

        return strBuffer;
    }

    public static String list2String(List a, String deli) {
        StringBuffer sb = new StringBuffer();
        if (a == null) {
            return "";
        } else {
            Collections.sort(a);
            for (int x = 0; x < a.size(); x++) {
                if (x > 0) {
                    sb.append(deli);
                }
                sb.append(a.get(x));
            }
        }
        return sb.toString();
    }

    public static String array2String(Object[] array, String delimitor) {
        StringBuffer strBuffer = new StringBuffer();
        if (array == null || array.length == 0) {

            return "";
        } else {

            for (int i = 0; i < array.length; i++) {
                if (i > 0) {
                    strBuffer.append(delimitor);
                }

                strBuffer.append(array[i]);
            }
        }
        return strBuffer.toString();
    }

    public static void toUpperCase(String[] strArray) {
        if (strArray != null) {
            for (int i = 0; i < strArray.length; i++) {
                if (strArray[i] != null) {
                    strArray[i] = strArray[i].toUpperCase();
                }
            }
        }
    }

    public static int indexOf(Object[] array, Object value) {
        if (array == null) {
            return -1;
        } else {
            for (int i = 0; i < array.length; i++) {
                Object nextVal = array[i];

                if (value == null) {
                    if (nextVal == null) {
                        return i;
                    }
                } else {
                    if (value.equals(nextVal)) {
                        return i;
                    }
                }
            }

            return -1;
        }
    }

    public static boolean isJavaIdentifier(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        if (Character.isJavaIdentifierStart(str.charAt(0))) {
            for (int i = 1; i < str.length(); i++) {
                if (Character.isJavaIdentifierPart(str.charAt(i))) {
                    // do nothing
                } else {
                    return false;
                }
            } // end for

            return true;
        } else {
            return false;
        }
    }

    public static Date toDateFromSQLDateTime(String dateTimeStr) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").parse(dateTimeStr);
            return date;
        } catch (Exception e) {
            //V5CMALogger.error("Error in toDateFromSQLDateTime", e);
            return null;
        }
    }


    private static boolean hasNameAndDomain(String aEmailAddress) {
        String[] tokens = aEmailAddress.split("@");
        return tokens.length == 2
                && !CommonUtil.isNullOrEmpty(tokens[0])
                && !CommonUtil.isNullOrEmpty(tokens[1]);
    }

    public static String isChecked(String in, String checkboxV) {
        return (CommonUtil.null2Empty(in).equalsIgnoreCase(checkboxV)) ? " checked" : "";
    }

    public static String isSelected(String in, String checkboxV) {
        return (CommonUtil.null2Empty(in).equalsIgnoreCase(checkboxV)) ? " selected" : "";
    }

    public static String escapeJavascriptTag(String s) {
        return s.replaceAll("(?is)<script.*?>.*?</script.*?>", "") // Remove all <script> tags.
                .replaceAll("(?is)<.*?javascript:.*?>.*?</.*?>", "") // Remove tags with javascript: call.
                .replaceAll("(?is)<.*?\\s+on.*?>.*?</.*?>", ""); // Remove tags with on* attributes.
    }

    public static String escapeHTMLTag(String content) {
        return content.replaceAll("\\<.*?\\>", "");
    }

    public static String escape(String s) {
        return escapeHTMLTag(escapeJavascriptTag(null2Empty(s)));
    }

    public static String inputParamConversion(String s) {
        return escapeJavascriptTag(s);
    }


    public static String subStringWithDots(String inStr, int length) {
        String dots = "...";
        if (inStr == null) {
            return null;
        }
        if (inStr.length() > length) {
            return inStr.substring(0, length) + dots;
        } else {
            return inStr;
        }
    }

    public static java.util.Date isValidDate(String inDate) {
        return isValidDate(inDate, dateOnlyFormatString);
    }

    public static java.util.Date isValidDate(String inDate, String dateFormatStr) {
        if (inDate == null) {
            return null;
        }
        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        if (inDate.trim().length() != dateFormat.toPattern().length()) {
            return null;
        }
        dateFormat.setLenient(false);
        try {
            //parse the inDate parameter
            return dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return null;
        }
    }

    /**
     * **
     *
     * @param inString - Original String to be marked
     * @param keepFirstCharacters Keep first N character of the string
     * domain address of the email, else null or empty
     * @param replacewith marked to be
     * string after the maskUntilCharacter, false to keep
     * @return eg. waxxxxxxxx@yahoo.com.hk
     */
    public static String maskEmail(String inString, int keepFirstCharacters, String replacewith, boolean returnDomain) {
        if (CommonUtil.isNullOrEmpty(inString)) {
            return null;
        }
        if (keepFirstCharacters <= 0) {
            keepFirstCharacters = 1; //default keep the first character
        }
        if (keepFirstCharacters > inString.length()) {
            return inString; //no change if wrong keepFirstCharacter passin
        }
        if (inString.indexOf("@") < 0) {
            return inString; //not a email
        }
        if (CommonUtil.isNullOrEmpty(replacewith)) {
            replacewith = "x"; //default replace to x
        }
        StringBuffer sb = new StringBuffer(inString.substring(0, keepFirstCharacters));
        int indexofAtSign = inString.indexOf("@");
        for (int x = keepFirstCharacters; x < indexofAtSign; x++) {
            sb.append(replacewith);
        }
        if (returnDomain) {
            sb.append(inString.substring(indexofAtSign));
        }
        return sb.toString();
    }

    public static String assignParamToString(String inStr, Map<String, String> param) {
        String result = inStr;
        if (param != null) {
            for (String paramkey : param.keySet()) {
                result = result.replaceAll("@" + paramkey + "@",
                        param.get(paramkey));
            }
        }
        return result;
    }

    public static class ColorFilterGenerator {

        private static double DELTA_INDEX[] = {
                0,    0.01, 0.02, 0.04, 0.05, 0.06, 0.07, 0.08, 0.1,  0.11,
                0.12, 0.14, 0.15, 0.16, 0.17, 0.18, 0.20, 0.21, 0.22, 0.24,
                0.25, 0.27, 0.28, 0.30, 0.32, 0.34, 0.36, 0.38, 0.40, 0.42,
                0.44, 0.46, 0.48, 0.5,  0.53, 0.56, 0.59, 0.62, 0.65, 0.68,
                0.71, 0.74, 0.77, 0.80, 0.83, 0.86, 0.89, 0.92, 0.95, 0.98,
                1.0,  1.06, 1.12, 1.18, 1.24, 1.30, 1.36, 1.42, 1.48, 1.54,
                1.60, 1.66, 1.72, 1.78, 1.84, 1.90, 1.96, 2.0,  2.12, 2.25,
                2.37, 2.50, 2.62, 2.75, 2.87, 3.0,  3.2,  3.4,  3.6,  3.8,
                4.0,  4.3,  4.7,  4.9,  5.0,  5.5,  6.0,  6.5,  6.8,  7.0,
                7.3,  7.5,  7.8,  8.0,  8.4,  8.7,  9.0,  9.4,  9.6,  9.8,
                10.0
        };

        /**
         * @see groups.google.com/group/android-developers/browse_thread/thread/9e215c83c3819953
         * @see gskinner.com/blog/archives/2007/12/colormatrix_cla.html
         * @param cm
         * @param value
         */
        public static void adjustHue(ColorMatrix cm, float value)
        {
            value = cleanValue(value, 180f) / 180f * (float) Math.PI;
            if (value == 0){
                return;
            }

            float cosVal = (float) Math.cos(value);
            float sinVal = (float) Math.sin(value);
            float lumR = 0.213f;
            float lumG = 0.715f;
            float lumB = 0.072f;
            float[] mat = new float[]
                    {
                            lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) + sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                            0f, 0f, 0f, 1f, 0f,
                            0f, 0f, 0f, 0f, 1f };
            cm.postConcat(new ColorMatrix(mat));
        }

        public static void adjustBrightness(ColorMatrix cm, float value) {
            value = cleanValue(value,100);
            if (value == 0) {
                return;
            }

            float[] mat = new float[]
                    {
                            1,0,0,0,value,
                            0,1,0,0,value,
                            0,0,1,0,value,
                            0,0,0,1,0,
                            0,0,0,0,1
                    };
            cm.postConcat(new ColorMatrix(mat));
        }

        public static void adjustContrast(ColorMatrix cm, int value) {
            value = (int)cleanValue(value,100);
            if (value == 0) {
                return;
            }
            float x;
            if (value < 0) {
                x = 127 + value / 100*127;
            } else {
                x = value % 1;
                if (x == 0) {
                    x = (float)DELTA_INDEX[value];
                } else {
                    //x = DELTA_INDEX[(p_val<<0)]; // this is how the IDE does it.
                    x = (float)DELTA_INDEX[(value<<0)]*(1-x) + (float)DELTA_INDEX[(value<<0)+1] * x; // use linear interpolation for more granularity.
                }
                x = x*127+127;
            }

            float[] mat = new float[]
                    {
                            x/127,0,0,0, 0.5f*(127-x),
                            0,x/127,0,0, 0.5f*(127-x),
                            0,0,x/127,0, 0.5f*(127-x),
                            0,0,0,1,0,
                            0,0,0,0,1
                    };
            cm.postConcat(new ColorMatrix(mat));

        }

        public static void adjustSaturation(ColorMatrix cm, float value) {
            value = cleanValue(value,100);
            if (value == 0) {
                return;
            }

            float x = 1+((value > 0) ? 3 * value / 100 : value / 100);
            float lumR = 0.3086f;
            float lumG = 0.6094f;
            float lumB = 0.0820f;

            float[] mat = new float[]
                    {
                            lumR*(1-x)+x,lumG*(1-x),lumB*(1-x),0,0,
                            lumR*(1-x),lumG*(1-x)+x,lumB*(1-x),0,0,
                            lumR*(1-x),lumG*(1-x),lumB*(1-x)+x,0,0,
                            0,0,0,1,0,
                            0,0,0,0,1
                    };
            cm.postConcat(new ColorMatrix(mat));
        }



        protected static float cleanValue(float p_val, float p_limit)
        {
            return Math.min(p_limit, Math.max(-p_limit, p_val));
        }

        public static ColorFilter adjustColor(int brightness, int contrast, int saturation, int hue){
            ColorMatrix cm = new ColorMatrix();
            adjustHue(cm, hue);
            adjustContrast(cm, contrast);
            adjustBrightness(cm, brightness);
            adjustSaturation(cm, saturation);

            return new ColorMatrixColorFilter(cm);
        }
    }
}

