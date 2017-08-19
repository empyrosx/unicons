/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Модель даты рождения
 * @author Димитрий
 */
public class DateModel {

    /**
     * Набор чисел в дате рождения
     * @param date
     */
    private Map<Integer, Integer> digits;

    /**
     * День
     */
    private int day;

    /**
     * Месяц
     */
    private int month;

    /**
     * Год
     */
    private int year;

    /**
     * Первое доолнительное число 
     */
    private int firstNumber;

    /**
     * Второе доолнительное число
     */
    private int secondNumber;

    /**
     * Третье доолнительное число
     */
    private int thirdNumber;

    /**
     * Четвертое доолнительное число
     */
    private int fourthNumber;

    /**
     * Конструктор
     * @param calendar 
     */
    public DateModel(Calendar calendar) {

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        
        digits = new HashMap<Integer, Integer>();

        // год
        addDigit(year / 1000);
        addDigit(year / 100 % 10);
        addDigit(year / 10 % 10);
        addDigit(year % 10);

        // месяц
        addDigit(month / 10);
        addDigit(month % 10);

        // день
        addDigit(day / 10);
        addDigit(day % 10);

        // вычисляем первое дополнительное число
        firstNumber = year / 1000 + year / 100 % 10 +
                year / 10 % 10 + year % 10 +
                month / 10 + month % 10 +
                day / 10 + day % 10;
        addDigit(firstNumber / 10);
        addDigit(firstNumber % 10);

        // вычисляем второе дополнительное число
        secondNumber = firstNumber / 10 + firstNumber % 10;
        addDigit(secondNumber / 10);
        addDigit(secondNumber % 10);

        // вычисляем третье дополнительное число
        if (day > 9){
            thirdNumber = firstNumber - 2 * (day / 10);
        }
        else {
            thirdNumber = firstNumber - 2 * day;
        }
        
        addDigit(thirdNumber / 10);
        addDigit(thirdNumber % 10);

        // вычисляем четвертое дополнительное число
        fourthNumber = thirdNumber / 10 + thirdNumber % 10;
        addDigit(fourthNumber / 10);
        addDigit(fourthNumber % 10);
    }

    private void addDigit(int digit){
        Integer digitCount = digits.get(digit);
        if (digitCount == null){
            digitCount = new Integer(0);
        }
        digitCount++;
        digits.put(digit, digitCount);
    }

    public Integer getDigitCount(int digit){
        return digits.get(digit);
    }

    public int getDay(){
        return day;
    }

    public int getMonth(){
        return month;
    }

    public int getYear(){
        return year;
    }

    public int getFirstNumber(){
        return firstNumber;
    }

    public int getSecondNumber(){
        return secondNumber;
    }

    public int getThirdNumber(){
        return thirdNumber;
    }

    public int getFourthNumber(){
        return fourthNumber;
    }
}
