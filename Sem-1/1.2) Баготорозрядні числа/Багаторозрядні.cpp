#include <iostream>
#include <string>
#include <stdlib.h>
using namespace std;

struct num {
    string user_number = "";

    string mantisa = "";
    short int poriadok = 0;
    bool sign = false;
};


int to_egual_whole_part(num& N) {
    N.mantisa.erase(1, 1);
    if (N.mantisa[0] != '0') return 0;
    do {
        N.mantisa.erase(0, 1);
        N.poriadok--;
    } while (N.mantisa[0] == '0');
    return 0;
}
void convertNum(num& N) {
    int e_pos = N.user_number.rfind("e");
    if (N.user_number[0] == '-') N.sign = true;
    N.poriadok = stoi(N.user_number.substr(e_pos + 1));
    N.mantisa = N.user_number.substr(0, e_pos);
    to_egual_whole_part(N);
}

num add(num A, num B) {
    struct num res;
    struct num dodanok;
    int count1 = 0, count2 = 0, start_point = 0, a = 0, b = 0, sum = 0, l = 0;

    if (A.poriadok > B.poriadok){
        res.poriadok = A.poriadok;
        res.mantisa = A.mantisa;
        dodanok.mantisa = B.mantisa;
        dodanok.poriadok = B.poriadok;
        start_point = A.poriadok - B.poriadok;
    }else {
        res.poriadok = B.poriadok;
        res.mantisa = B.mantisa;
        dodanok.mantisa = A.mantisa;
        dodanok.poriadok = A.poriadok;
        start_point = B.poriadok - A.poriadok;
    }
    
    if (dodanok.poriadok > dodanok.mantisa.length() - 1) l = 1 + (dodanok.poriadok - (dodanok.mantisa.length() - 1));
    else l = dodanok.poriadok; 

    while (count2 < start_point + l) {

        if (count1 < start_point){
            if (!res.mantisa[count1]) res.mantisa.push_back('0');
            count1++;
            continue;
        }

        if (count2 > dodanok.mantisa.length()-1){
            res.mantisa.push_back('0');
            count2++;
            continue;
        }

        if (!res.mantisa[start_point + count2]){
            res.mantisa.push_back(dodanok.mantisa[count2]);
            count2++;
            continue;
        }

        a = res.mantisa[start_point + count2] - '0';
        b = dodanok.mantisa[count2] - '0';
        sum = a + b;

        if (sum < 10){
          if (count2 + start_point < res.mantisa.length()) res.mantisa[count2 + start_point] = sum + '0';
          else res.mantisa.push_back(sum + '0');
        }
        else {
            if (count2 != 0){
                int f = res.mantisa[count2 - 1] - '0';
                res.mantisa.replace(count2 - 1, 1, ++f + "");
            }
            if (count2 < res.mantisa.length()) res.mantisa[count2] = sum % 10 + '0';
            else res.mantisa.push_back(sum % 10);
            if (count2 == 0) res.mantisa.insert(0, 1, '1');
        }
        count2++;
    } 
    res.mantisa.insert(1, 1, '.');
    return res;
}
num mins(num A, num B) {
    struct num res;
    struct num risnysa;
    int count1 = 0, count2 = 0, start_point = 0, a = 0, b = 0, ris = 0, l = 0;

    if (A.poriadok > B.poriadok) {
        res.poriadok = A.poriadok;
        res.mantisa = A.mantisa;
        risnysa.mantisa = B.mantisa;
        risnysa.poriadok = B.poriadok;
        start_point = A.poriadok - B.poriadok;
    }
    else if (B.poriadok > A.poriadok)
    {
        res.poriadok = B.poriadok;
        res.mantisa = B.mantisa;
        risnysa.mantisa = A.mantisa;
        risnysa.poriadok = A.poriadok;
        start_point = B.poriadok - A.poriadok;
    }

    if (risnysa.poriadok > risnysa.mantisa.length() - 1) l = 1 + (risnysa.poriadok - (risnysa.mantisa.length() - 1));
    else l = risnysa.poriadok;

    while (count2 < start_point + l) {

        if (count1 < start_point) {
            if (!res.mantisa[count1]) res.mantisa.push_back('0');
            count1++;
            continue;
        }

        if (count2 > risnysa.mantisa.length() - 1) {
            res.mantisa.push_back('0');
            count2++;
            continue;
        }

        if (!res.mantisa[start_point + count2]) {
            res.mantisa.push_back(risnysa.mantisa[count2]);
            count2++;
            continue;
        }

        a = res.mantisa[start_point + count2] - '0';
        b = risnysa.mantisa[count2] - '0';
        ris = a - b;

        if (ris > 0) {
            if (count2 + start_point < res.mantisa.length()) res.mantisa[count2 + start_point] = ris + '0';
            else res.mantisa.push_back(ris + '0');
        }
        else {
            int f = 10 - (risnysa.mantisa[count2] - res.mantisa[start_point + count2]);
            if (count2 < res.mantisa.length()) res.mantisa[count2] = f + '0';
            else res.mantisa.push_back(f);
        
            if (count2 != 0) {
                for (int i = count2 - 1; i > 0; i--)
                {
                    if (res.mantisa[count2] < 1) continue;
                    else {
                        int r = res.mantisa[count2] - '0';
                        res.mantisa.replace(i, 1,  ++r + "");
                    }
                }
                
            }
            if (count2 == 0) res.mantisa.insert(0, 1, '1');
        }
        count2++;
    }
    res.mantisa.insert(1, 1, '.');
    return res;
}

int main()
{
    struct num A; struct num B; struct num res;

    char if_continue, operation;

    do 
    {
        cout << "A: "; cin >> A.user_number;
        cout << "B: "; cin >> B.user_number;
        cout << "Operation: "; cin >> operation;

        convertNum(A);
        convertNum(B);

        switch (operation)
        {
        case '+':
            res = add(A, B);
            cout << "Result of addition: " << res.mantisa << "e" << res.poriadok << endl;
            break;
        case '-':
            res = mins(A, B);
            cout << "Result of subtracion: " << res.mantisa << "e" << res.poriadok << endl;
            break;
        default:
            cout << "Operation is invalid" << endl;
            break;
        }

        cout << "Continue(y,n): "; cin >> if_continue;

    } while (if_continue == 'y');
}