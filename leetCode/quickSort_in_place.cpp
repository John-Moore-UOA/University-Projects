// In place QuickSort
// Random Pivot

#include <iostream>
#include <vector>

using namespace std;

class Sorting
{

private:
  void swap(vector<int> &vec, int a, int b)
  {
    int temp = vec[a];
    vec[a] = vec[b];
    vec[b] = temp;
  }

public:
  int sort(vector<int> &vec, int length)
  {

    // Quick Sort
    // inplace 1 pivot varient
    // O(n log(n))

    // pick pivot
    // shuffle elements lesser and greater than pivot
    // recursive call quick sort
    // combine

    return quickSortRecursive(vec, 0, length - 1);
  }

private:
  int quickSortRecursive(vector<int> &vec, int left, int right)
  {
    // Example
    //
    //  2 60 49 19 2 5 15 13 20
    //                       20
    //                    13 20
    //                 13 15 20
    //               15 13 5 20
    //             5 15 13 2 20
    //          2 5 15 13 19 20
    //       19 2 5 15 13 49 20
    //       19 2 5 15 13 20 49
    //    13 19 2 5 15 60 20 49
    //    13 19 2 5 15 20 60 49
    // 15 13 19 2 5 2  20 60 49

    int c = 0;
    if (left >= right)
    {
      return c;
    }

    int pivotIndex = partition(vec, left, right, c);
    c += quickSortRecursive(vec, left, pivotIndex - 1);
    c += quickSortRecursive(vec, pivotIndex + 1, right);

    return c;
  }

private:
  int partition(vector<int> &vec, int left, int right, int &c)
  {
    int pivot = pickPivot(vec, left, right); // random element as pivot
    int i = left - 1;

    for (int j = left; j < right; j++)
    {
      if (vec[j] <= pivot)
      {
        i++;
        swap(vec, i, j);
        c++;
      }
    }

    swap(vec, i + 1, right);
    return i + 1;
  }

private:
  int pickPivot(vector<int> &vec, int left, int right)
  {
    int randomPivot = left + rand() % (right - left + 1);
    swap(vec, randomPivot, right); // swap element into last position
    return vec[right];
  }

public:
  void printVec(vector<int> vec)
  {
    for (int i = 0; i < vec.size(); i++)
    {
      cout << vec[i] << " ";
    }
    cout << endl;
  }
};

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
  vector<int> vec1 = generateVector(20);
  Sorting sort;

  sort.printVec(vec1);
  cout << "swaps: " << sort.sort(vec1, vec1.size()) << endl;
  sort.printVec(vec1);
}
