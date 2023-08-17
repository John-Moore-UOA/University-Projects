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

  int sort(vector<int> &vec, int length)
  {
    // insersion
    // O(n^2)

    int c = 0;
    for (int i = 0; i < length; i++)
    {
      for (int j = 0; j < length; j++)
      {
        if (vec[i] < vec[j])
        {
          c++;
          swap(vec, i, j);
        }
      }
    }

    return c;
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
  vector<int> vec1 = generateVector(1000);
  Sorting sort;
  printVec(vec1);
  cout << "swaps: " << sort.sort(vec1, vec1.size()) << endl;

  printVec(vec1);
}