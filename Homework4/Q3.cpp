#include <bits/stdc++.h>

using namespace std;

template <typename T>
T f(T* a, T* b)
{
	if (sizeof(*a) == sizeof(int) && sizeof(*b) == sizeof(int))
		return *a + *b;
	else if (sizeof(*a) == sizeof(double) && sizeof(*b) == sizeof(double))
		return *a * *b;
	else
		return 0;
}

int main()
{
	int x = 10, y = 20;
	double a = 30, b = 40;
	cout << f(&x, &y) << endl; //10 + 20 = 30
	cout << f(&a, &b) << endl; //30 * 40 = 1200
	return 0;
}