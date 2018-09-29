#include <bits/stdc++.h>

using namespace std;

template <typename T>
class MinHeap
{
private:
    static const int maxSize = 100000;
    T a[maxSize];
    int size;
  	void swap(int idx1, int idx2)
    {
        T tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
    }
public:
    MinHeap()
    {
        size = 0;
    }
    int getSize()
    {
        return size;
    }
    bool isEmpty()
    {
        return size == 0;
    }
    T getMinimum()
    {
        if (!isEmpty())
            return a[0];
        else
            return -1;
    }
    void insert(T value)
    {
        int idx = size++;
        a[idx] = value;
        while (idx > 0 && a[idx] < a[(idx - 1) / 2])
            swap(idx, (idx - 1) / 2);
    }
    void removeMin()
    {
        if (size == 0)
            return;
        swap(0, size - 1);
        size--;
        if (size == 0)
            return;
        int idx = 0;
        while (2 * idx + 1 < size)
        {
            int l = 2 * idx + 1;
            int r = 2 * idx + 2;
            int swapIdx = r;
            if (r >= size || a[l] < a[r])
                swapIdx = l;
            swap(idx, swapIdx);
            idx = swapIdx;
        }
    }
    void print()
    {
        for (int i = 0; i < size; i++)
            cout << a[i] << " ";
        cout << endl;
    }
};

int main()
{
	MinHeap<int> minHeap;
	minHeap.insert(5);
	minHeap.insert(10);
	minHeap.insert(2);
	minHeap.insert(4);
	minHeap.insert(20);
	minHeap.insert(15);
	cout << minHeap.getSize() << endl; //6
	minHeap.print();
	cout << minHeap.getMinimum() << endl; //2
	minHeap.removeMin();
	cout << minHeap.getMinimum() << endl; //4
	return 0;
}