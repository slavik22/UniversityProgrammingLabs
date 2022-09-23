#include <iostream>
#include "DateTime.cpp"
int main() {
    DateTime d1{ "22.04.2004 02:50:00"};
    DateTime d2{ "21.04.2004 02:50:00"};

    cout << d1 - d2;
}
