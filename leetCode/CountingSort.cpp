#include <iostream>
#include <vector>

using namespace std;

class Sorting
{
public:
  void sort(vector<int> &vec, int length)
  {
    int max = getMax(vec);

    vector<int> counting(max, 0);

    for (int i = 0; i < length; i++)
    {
      counting[i]++;
    }

    int p = 0;
    for (int i = 0; i <= max; i++)
    {

      while (counting[i] > 0)
      {
        vec[p++] = i;
        counting[i]--;
      }
    }
  }

private:
  int getMax(vector<int> vec)
  {
    int max = vec[0];
    for (int i = 0; i < vec.size(); i++)
    {
      if (max < vec[i])
      {
        max = vec[i];
      }
    }
    return max;
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
  vector<int> vec1 = generateVector(50);
  Sorting sort;
  printVec(vec1);

  sort.sort(vec1, vec1.size());

  cout << endl
       << "RESULT" << endl;
  printVec(vec1);
}
