#include <bits/stdc++.h>

using namespace std;

void a(void f(), int n)
{
	for (int i = 0; i < n; i++)
		f();
}

void c(void (*f)(), int n)
{
	for (int i = 0; i < n; i++)
		f();
}

void b()
{
	cout << "b function" << endl;
}

int main()
{
	cout << "a: " << endl;
	a(b, 5);
	cout << "c: " << endl;
	c(b, 5);
	return 0;
}