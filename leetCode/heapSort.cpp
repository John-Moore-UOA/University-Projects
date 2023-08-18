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

  int heapify(vector<int> &vec, int length)
  {
    //      100
    //   50     60
    // 20 49  29 15
    // etc

    int c = 0;

    for (int i = 0; i < length; i++)
    {
      // loop throug array
      int current = i;

      while (current > 0)
      {
        // if child is greater than parent, swap
        if (vec[(current - 1) / 2] < vec[current])
        {
          swap(vec, current, (current - 1) / 2);
          c++;
        }

        current = (current - 1) / 2;
      }
    }

    return c;
  }

  int sort(vector<int> &vec, int length)
  {
    // Heap Sort
    // O(nLog(n))

    int c = 0;

    // Heapify O(nLog(n))
    c += heapify(vec, length);

    // max and fix
    int max;
    for (int i = 0; i < length - 1; i++)
    {
      // max
      max = vec[0];
      c++;
      swap(vec, 0, length - i - 1);

      // fix
      heapify(vec, length - i - 1);
    }

    return c;
  }

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
  vector<int> vec1 = generateVector(30);
  Sorting sort;
  sort.printVec(vec1);
  cout << "swaps: " << sort.sort(vec1, vec1.size()) << endl;

  sort.printVec(vec1);
}
