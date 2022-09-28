#include <iostream>
#include <string>
#include <vector>
#include <regex>
#include <math.h>
using namespace std;

class DateTime{
private:
    const int START_YEAR = 1970;
    const int SECONDS_IN_YEAR = 31556952;
    const int SECONDS_IN_DAY = 86400;
    const int SECONDS_IN_HOUR = 3600;
    const int SECONDS_IN_MINUTE = 60;

    vector<int> DAYS_IN_EACH_MONTH = {31,-1,31,30,31,30,31,31,30,31,30,31};

    string weekday[7] = {"Saturday","Sunday","Monday","Tuesday", "Wednesday","Thursday","Friday"};
    string months[12] = {"January","February","March ","April", "May","June","July","August","September","October", "November", "December"};

    long long int seconds; // from 1970

    long long int toSeconds(int years, int months, int days, int hours, int minutes, int secs){
        int seconds_in_months = 0;

        for (int i = 0; i < months - 1; ++i)
            seconds_in_months += DAYS_IN_EACH_MONTH[i]  * SECONDS_IN_DAY;

        return (years - START_YEAR) * SECONDS_IN_YEAR + seconds_in_months + days * SECONDS_IN_DAY + hours * SECONDS_IN_HOUR + minutes * SECONDS_IN_MINUTE + secs;
    }

    vector<int> GetAllData(){
        vector<int> data;

        long long int t_seconds = seconds;

        data.push_back(t_seconds / SECONDS_IN_YEAR + START_YEAR);
        t_seconds = t_seconds % SECONDS_IN_YEAR;

        int days_total_with_monthes = t_seconds / SECONDS_IN_DAY;

        for (int i = 0; i < 11; ++i) {
            if(days_total_with_monthes < DAYS_IN_EACH_MONTH[i]){
                data.push_back(i+1);
                break;
            }
            days_total_with_monthes -= DAYS_IN_EACH_MONTH[i];
        }
        data.push_back(days_total_with_monthes);

        t_seconds = t_seconds % SECONDS_IN_DAY;
        data.push_back(t_seconds / SECONDS_IN_HOUR);
        t_seconds = t_seconds % SECONDS_IN_HOUR;
        data.push_back(  t_seconds / SECONDS_IN_MINUTE);
        t_seconds = t_seconds % SECONDS_IN_MINUTE;
        data.push_back( t_seconds);

        return data;
    }

    string GetMonth(){
        vector<int> data = GetAllData();;
        int monthNumber = data[1];
        return months[monthNumber - 1];
    }

public:
    DateTime(long long int secs){
        this->seconds = secs;
        DAYS_IN_EACH_MONTH[1] = checkIfLeapYear() ? 29 : 28;
    }
    DateTime(string date){
        smatch match;
        regex dtimeregex{R"((\d{2}).(\d{2}).(\d{4}) (\d{2}):(\d{2}):(\d{2}))"};

        if(std::regex_search(date, match, dtimeregex)){
            int day = stoi(match[1]);
            int month = stoi(match[2]);
            int year = stoi(match[3]);
            int hours = stoi(match[4]);
            int minutes = stoi(match[5]);
            int secs = stoi(match[6]);

            DAYS_IN_EACH_MONTH[1] = checkIfLeapYear() ? 29 : 28;

            if(!checkIfCorrectData(day,month,year,hours,minutes,secs)){
                perror("Date is incorrect");
                return;
            }

            this->seconds = toSeconds(year,month,day,hours,minutes,secs);
        }
        else
            cout << "Date is inncorrect";
    }
    bool checkIfCorrectData(int day,int month,int year,int hours,int minutes,int secs){
        if(year - START_YEAR < 0){
            cout << "Year is less than minimal";
            return false;
        }
        if(month <= 0 || month > 12){
            cout << "Month number is incorrect";
            return false;
        }
        if(day <= 0 || day > DAYS_IN_EACH_MONTH[month - 1]){
            cout << "Day number is incorrect";
            return false;
        }

        if(hours <= 0 || hours > 24){
            cout << "Hours are incorrect";
            return false;
        }

        if(minutes < 0 || minutes > 60){
            cout << "Minutes are incorrect";
            return false;
        }

        if(secs < 0 || secs > 60){
            cout << "Seconds are incorrect";
            return false;
        }

        return true;
    }
    bool checkIfLeapYear(){
        vector<int> data = GetAllData();
        int year = data[0];
        return ( (year % 4 == 0) && (year % 100 != 0) ) || (year % 400 == 0);
    }

    string toString(){
        vector<int> data = GetAllData();

        return to_string(data[2]) + "." + to_string(data[1]) + "." + to_string(data[0])
               + " " + to_string(data[3]) + ":" + to_string(data[4]) + ":" + to_string(data[5]);
    }

    string dayOfWeek(){ // Zeller’s Algorithm
        vector<int> data = GetAllData();

        int month = data[1];
        int day = data[2];
        int year = data[0];

        int mon;
        if(month > 2)
            mon = month; //for march to december month code is same as month
        else{
            mon = (12+month); //for Jan and Feb, month code will be 13 and 14
            year--; //decrease year for month Jan and Feb
        }
        int y = year % 100; //last two digit
        int c = year / 100; //first two digit
        int w = (int) (day + floor((13*(mon+1))/5) + y + floor(y/4) + floor(c/4) + (5*c));
        w = w % 7;
        return weekday[w];
    }


    long long int operator-(long long int secs){
        return this->seconds - secs;
    }
    long long int operator+(long long int secs){
        return this->seconds + secs;
    }
    long long int operator+(DateTime& other){
        return this->seconds + other.seconds;
    }
    long long int operator-(DateTime& other){
        return this->seconds - other.seconds;
    }
};