#include <bits/stdc++.h>

using namespace std;

class BigNum
{
private:
	void remLeadingZeros()
	{
		while (countDigits() >= 2 && digits[countDigits() - 1] == 0)
			digits.pop_back();		
	}
	void addTrailingZeros(int cnt)
	{
		vector<int> tmp;
		for (int i = 1; i <= cnt; i++)
			tmp.push_back(0);
		for (int i = 0; i < digits.size(); i++)
			tmp.push_back(digits[i]);
		digits = tmp;
	}
	BigNum div2()
	{
		BigNum ret, a = *this;
		ret.digits.pop_back();
		a.digits.push_back(0);
		for (int i = 0; i < a.countDigits() - 1; i++)
			ret.digits.push_back(a[i] / 2 + (a[i + 1] & 1) * 5);
		ret.remLeadingZeros();
		return ret;
	}
public:
	vector<int> digits;
	BigNum(unsigned long long x = 0)
	{
		if (x == 0)
			digits.push_back(0);
		while (x)
		{
			digits.push_back(x % 10);
			x /= 10;
		}
	}
	BigNum(string s)
	{
		if (s == "")
			s = "0";
		digits.clear();
		for (int i = 0; i < s.length(); i++)
			digits[i] = s[i];
	}
	string toString()
	{
		string ret;
		for (int i = countDigits() - 1; i >= 0; i--)
			ret.push_back(digits[i] + '0');
		return ret;
	}
	int countDigits()
	{
		return digits.size();
	}
	int& operator [] (int idx)
	{
		return digits[idx];
	}
	bool operator == (BigNum b)
	{
		return digits == b.digits;
	}
	bool operator < (BigNum b)
	{
		if (countDigits() != b.countDigits())
			return countDigits() < b.countDigits();
		for (int i = countDigits() - 1; i >= 0; i--)
			if (digits[i] != b.digits[i])
				return digits[i] < b.digits[i];
		return false;
	}
	bool operator > (BigNum b)
	{
		return b < *this;
	}
	BigNum operator = (BigNum b)
	{
		digits = b.digits;
		return *this;
	}
	BigNum operator + (BigNum b)
	{
		BigNum ret, a = *this;
		for (int i = 1; i <= max(countDigits(), b.countDigits()); i++)
			ret.digits.push_back(0);
		while (a.countDigits() < b.countDigits())
			a.digits.push_back(0);
		while (a.countDigits() > b.countDigits())
			b.digits.push_back(0);
		for (int i = 0; i < a.countDigits(); i++)
		{
			ret[i + 1] = (ret[i] + a[i] + b[i]) / 10;
			ret[i] = (ret[i] + a[i] + b[i]) % 10;
		}
		ret.remLeadingZeros();
		return ret;
	}
	BigNum operator - (BigNum b)
	{
		BigNum ret, a = *this;
		if (b > a)
			swap(a, b);
		for (int i = 1; i < a.countDigits(); i++)
			ret.digits.push_back(0);
		while (b.countDigits() < a.countDigits())
			b.digits.push_back(0);
		for (int i = 0; i < a.countDigits(); i++)
		{
			if (a.digits[i] < b.digits[i])
			{
				a.digits[i + 1]--;
				a.digits[i] += 10;
			}
			ret[i] = a.digits[i] - b.digits[i];
		}
		ret.remLeadingZeros();
		return ret;
	}
	BigNum operator * (BigNum b)
	{
		BigNum ret, a = *this;
		if (b > a)
			swap(a, b);
		for (int i = 0; i < b.countDigits(); i++)
		{
			BigNum tmp;
			tmp.digits.pop_back();
			int co = b[i], carry = 0;
			for (int j = 0; j < a.countDigits(); j++)
			{
				tmp.digits.push_back((co * a[j] + carry) % 10);
				carry = (co * a[j] + carry) / 10;
			}
			if (carry)
				tmp.digits.push_back(carry % 10);
			tmp.addTrailingZeros(i);
			tmp.remLeadingZeros();
			ret = ret + tmp; 
		}
		ret.remLeadingZeros();
		return ret;
	}
	BigNum operator ^ (unsigned long long k)
	{
		if (k == 0)
			return BigNum(1);
		BigNum tmp = *this ^ (k / 2);
		if (k % 2 == 0)
			return tmp * tmp;
		else
			return tmp * tmp * *this;
	}
	BigNum operator / (BigNum b)
	{
		BigNum l(0), r = *this, mid, one(1), ret;
		while (l < r || l == r)
		{
			mid = l + r;
			mid = mid.div2();
			if (mid * b > *this)
				r = mid - one;
			else
			{
				ret = mid;
				l = mid + one;
			}
		}
		return ret;
	}
	BigNum root(unsigned long long k)
	{
		if (k == 0)
			return BigNum(0);
		BigNum l(0), r = *this, mid, one(1), ret;
		while (l < r || l == r)
		{
			mid = l + r;
			mid = mid.div2();
			if ((mid ^ k) > *this)
				r = mid - one;
			else
			{
				ret = mid;
				l = mid + one;
			}
		}
		return ret;
	}
	void print()
	{
		for (int i = countDigits() - 1; i >= 0; i--)
			cout << digits[i];
	}
};

int main()
{
	BigNum a(5), b(66), c(777), d(8888);
	BigNum sum = c + d;
	BigNum mul = a * b;
	BigNum sub = d - c;
	BigNum pwr = a ^ 20;
	cout << sum.toString() << ", " << mul.toString() << ", " << sub.toString() << ", " << pwr.toString() << endl; 
	cout << (c < d) << " " << (c == d) << " " << (d == d) << endl; //1 0 1
	cout << b.root(2).toString() << endl;
	cout << b.root(3).toString() << endl;
	cout << (d / b).toString() << endl;
	cout << (c / b).toString() << endl;
	return 0;
}