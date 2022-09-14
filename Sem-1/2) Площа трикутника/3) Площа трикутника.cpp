#include <iostream>
#include <cmath>
using namespace std;

struct dot {
	short int x;
	short int y;
};

double find_length(dot one, dot two) {
	return sqrt( ((one.x - two.x) * (one.x - two.x)) + ((one.y - two.y) * (one.y - two.y)) );
}

double find_square(double a, double b, double c) {
	double p = (a + b + c) / 2;
	return sqrt(p*(p-a)*(p-b)*(p-c));
}

double find_max_square(dot dots[100], int size) {
	double max_square = 0, square = 0, a = 0, b = 0, c = 0;

	for (int i = 0; i < size - 2; i++)
	{
		for (int x = i + 1; x < size; x++)
		{
			a = find_length(dots[i], dots[x]);

			for (int z = x + 1; z < size; z++)
			{
				b = find_length(dots[i], dots[z]);
				c = find_length(dots[x], dots[z]);
				
				square =  find_square(a, b, c);

				if (z == 2) max_square = square;
				else if (square > max_square) max_square = square;
			
			}
		}
	}

	return max_square;
}

int main()
{
	dot dots[100];
	int count = 0;
	double result = 0;

	while (cin >> dots[count].x >> dots[count].y) count++;
	result = find_max_square(dots, count);
	cout << result;
}