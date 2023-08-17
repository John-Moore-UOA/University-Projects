#include <iostream>
#include <vector>

using namespace std;

class Sorting
{
public:
  int sort(vector<int> vec, int length)
  {
    return -1;
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
  vector<int> vec1 = generateVector(10);
  Sorting sort;
  printVec(vec1);

  sort.sort(vec1, vec1.size());
}