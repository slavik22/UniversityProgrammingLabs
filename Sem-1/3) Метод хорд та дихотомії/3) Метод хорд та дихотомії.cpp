#include <iostream>
#include <cmath>

using namespace std;

double f1(double x) {
	return sin(x);
}
double f2(double x) {
	return x * x - 9 * x + 14;
}

double Dixomotry(double (*f)(double), double a, double b, int &count, double eps) {
	double x = 0;
	while ( (b - a) / 2 > eps) {
		x = (a + b) / 2;
		if ((f(a) * f(x)) > 0) a = x;
		else b = x;
		count++;
	}

	return x;
}

double Hord(double (*f)(double), double a, double b, int &count, double eps) {

	double x = a + abs(f(a) / (f(b) - f(a))) * (b - a);	
	while (abs(f(x)) > eps)
	{
		x = a + abs(f(a) / (f(b) - f(a))) * (b - a);
		if (f(a) * f(x) < 0) b = x;
		else a = x;
		count++;
	}

	return x;
}

int main() {
	double a, b, x;
	int count1 = 0, count2 = 0;
	const double eps = 1e-7;

	cout << "Interval: "; cin >> a; cin >> b;

	if (f2(a) * f2(b) > 0) {
		cout << "Interval contains more than one root!" << endl;
		return 0;
	}

	x = Dixomotry(f2, a, b, count1, eps);
	cout << "Dixomotry: Result = " << x << "; Time: " << count1 << " operations;" << endl;

	x = Hord(f2, a, b, count2, eps);
	cout << "Hord: Result = " << x << "; Time: " << count2 << " operations; " << endl;
}