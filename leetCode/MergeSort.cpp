#include <iostream>
#include <vector>

using namespace std;

class Sorting
{
public:
  void swap(vector<int> &vec, int indexOne, int indexTwo)
  {
    int temp = vec[indexTwo];
    vec[indexTwo] = vec[indexOne];
    vec[indexOne] = temp;
  }

  void combineSort(vector<int> &A, int left, int right, int mid)
  {
    vector<int> B;
    B.reserve(right - left + 1);

    // two pointers to start of A, and B
    int p1 = left;
    int p2 = mid + 1;

    // loop through total length of subsections
    for (int i = 0; i < right - left + 1; i++)
    {

      // if no more p1 values
      if (p1 > mid)
      {
        B[i] = A[p2];
        p2++;
      }
      // if no more p2 values
      else if (p2 > right)
      {
        B[i] = A[p1];
        p1++;
      }
      // if A[p1] < A[p2]
      else if (A[p1] < A[p2])
      {
        // include p1 value and increment
        B[i] = A[p1];
        p1++;
      }
      else
      {
        // include p2 value and increment
        B[i] = A[p2];
        p2++;
      }
    }

    // copy back to A
    for (int i = left; i <= right; i++)
    {
      A[i] = B[i - left];
    }

    // loop between two arrays
  }

  void mergeSort(vector<int> &vec, int start, int end)
  {
    if (start == end)
    {
      return;
    }

    int mid = (start + end) / 2;

    mergeSort(vec, start, mid);
    mergeSort(vec, mid + 1, end);

    combineSort(vec, start, end, mid);
  }

  void sort(vector<int> &vec, int length)
  {
    // Merge
    // O(nLog(n))

    mergeSort(vec, 0, length);
  }
};

void printVec(vector<int> vec)
{
  for (int i = 0; i < vec.size(); i++)
  {
    cout << vec[i] << " ";
  }
  cout << endl;
}

vector<int> generateVector(int length)
{
  vector<int> vec;

  for (int i = 0; i < length; i++)
  {
    vec.push_back(rand() % 100);
  }

  return vec;
}

int main()
{
  vector<int> vec1 = generateVector(10000);
  Sorting sort;
  printVec(vec1);

  sort.sort(vec1, vec1.size());

  printVec(vec1);
}