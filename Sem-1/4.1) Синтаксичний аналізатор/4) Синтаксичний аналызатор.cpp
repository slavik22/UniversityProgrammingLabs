#include <iostream>
#include <string>
#include <ctype.h>

using namespace std;

struct code {
	string text;
	int pos = 0;

	char getChar() {
		return text[pos];
	}
};
double number(code& str);
double brackets(code& str);
double multiplication(code& str);
double addition(code& str);
double result(code& str);

double number(code &str) {
	string result;
	do {
		result += str.getChar();
		str.pos++;
	} while (isdigit(str.getChar()) || str.getChar() == '.');

	return stod(result);
}

double brackets(code& str) {
	if (str.getChar() == '(') {
		str.pos++;
		double result = addition(str);
		str.pos++;
		return result;
	}
	else 
		return number(str);
}

double multiplication(code &str) {
	double result = brackets(str);
	while (str.getChar() == '*' || str.getChar() == '/') {
		char operation = str.getChar();
		str.pos++;
		double multiplier = brackets(str);
		result = operation == '*' ? result * multiplier : result / multiplier;
	}
	return result;
}

double addition(code &str) {
	double result = multiplication(str);
	while (str.getChar() == '+' || str.getChar() == '-') {
		char operation = str.getChar();
		str.pos++;
		double dodanok = multiplication(str);
		result = operation == '+' ? result + dodanok : result - dodanok;
	}
	return result;
}

double result(code &str) {
	double result = addition(str);
	return result;
}

int main()
{
	code str;

	cin >> str.text;
	str.pos = 0;

	cout << result(str);
}