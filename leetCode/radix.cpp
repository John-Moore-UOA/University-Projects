#include <iostream>
#include <vector>

using namespace std;

class Sorting
{
public:
  void sort(vector<int> &vec, int length)
  {
    // 81 4 12 2 8 4 12 41 13 15
    // sort by the first digit
    // 4 2 8 4 12 12 13 15 41 81
    // sort by the second digit
    // 2 4 4 8 12 12 13 15 41 81

    int max = getMax(vec);
    int tempMax = max;
    int digits = 0;

    do
    {
      digits++;
      tempMax /= 10;
    } while (tempMax);

    // 3
    for (int i = 1; max / i > 0; i *= 10)
    {
      // based on i-th digit
      countingSort(vec, length, i);
    }
  }

public:
  void countingSort(vector<int> &vec, int length, int place)
  {
    int max = getMax(vec);

    vector<int> output(length, 0);

    vector<int> count(max, 0);

    // Calculate count of elements
    for (int i = 0; i < length; i++)
      count[(vec[i] / place) % 10]++;

    // Calculate cumulative count
    for (int i = 1; i < max; i++)
      count[i] += count[i - 1];

    // Place the elements in sorted order
    for (int i = length - 1; i >= 0; i--)
    {
      output[count[(vec[i] / place) % 10] - 1] = vec[i];
      count[(vec[i] / place) % 10]--;
    }

    for (int i = 0; i < length; i++)
    {
      vec[i] = output[i];
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
    vec.push_back(rand() % 1000000001);
  }

  return vec;
}

int main()
{
  vector<int> vec1 = generateVector(100000);
  Sorting sort;
  printVec(vec1);

  sort.sort(vec1, vec1.size());

  cout << "result: " << endl;
  printVec(vec1);
}
